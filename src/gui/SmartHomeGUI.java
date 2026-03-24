package gui;

import core.*;
import adapter.*;
import observer.*;

import javax.swing.*;
import java.awt.*;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SmartHomeGUI {

    // componenti backend
    private House house;
    private SmartCamera cam;
    private MotionSensor sensor;
    private JLabel consumptionLabel;

    // strutture dati per la grafica
    private List<JToggleButton> roomButtons = new ArrayList<>();
    private Map<JToggleButton, List<JToggleButton>> roomToDevicesMap = new HashMap<>();
    private JToggleButton btnMaster; // globale per poterlo aggiornare dal basso

    public SmartHomeGUI() {
        initializeBackend();

        JFrame frame = new JFrame("Smart Home App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 850);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(25, 25, 25));

        // header e master button
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(new Color(40, 40, 40));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel("Smart Home", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        consumptionLabel = new JLabel("Consumo totale: 0.0 W/h", SwingConstants.CENTER);
        consumptionLabel.setForeground(new Color(255, 200, 0));
        consumptionLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        consumptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnMaster = new JToggleButton("ACCENDI TUTTA LA CASA");
        customizeButton(btnMaster, new Color(41, 128, 185), 20);
        btnMaster.addActionListener(e -> {
            boolean isOn = btnMaster.isSelected();
            if (isOn) {
                house.activate();
                updateButtonGraphics(btnMaster, true, "SPENGI TUTTA LA CASA", new Color(192, 57, 43));
            } else {
                house.deactivate();
                updateButtonGraphics(btnMaster, false, "ACCENDI TUTTA LA CASA", new Color(41, 128, 185));
            }

            // aggiorna top down, verso il basso
            for (JToggleButton rb : roomButtons) {
                rb.setSelected(isOn);
                String nomeStanza = rb.getText().substring(rb.getText().indexOf("-") + 2).trim();
                updateButtonGraphics(rb, isOn, (isOn ? "ON - " : "OFF - ") + nomeStanza, isOn ? new Color(46, 204, 113) : new Color(70, 70, 70));

                for (JToggleButton db : roomToDevicesMap.get(rb)) {
                    db.setSelected(isOn);
                    String nomeDevice = db.getText().substring(db.getText().indexOf("-") + 2).trim();
                    updateButtonGraphics(db, isOn, (isOn ? "ON - " : "OFF - ") + nomeDevice, isOn ? new Color(241, 196, 15) : new Color(50, 50, 50));
                }
            }
            updateConsumption();
        });

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        headerPanel.add(consumptionLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        headerPanel.add(btnMaster);
        frame.add(headerPanel, BorderLayout.NORTH);

        // lista stanze e dispositivi
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.Y_AXIS));
        bodyPanel.setBackground(new Color(25, 25, 25));
        bodyPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        bodyPanel.add(createRoomPanel("Salotto",
                new Object[][]{{"Luce principale", new LightBulb(15.0)}, {"Vecchia TV", new OldTVAdapter(new OldTV())}}));
        bodyPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Camera con 3 dispositivi
        bodyPanel.add(createRoomPanel("Camera",
                new Object[][]{
                        {"Lampada", new LightBulb(10.0)},
                        {"Termostato", new Thermostat(500.0)},
                        {"Vecchio Stereo", new OldSpeakerAdapter(new OldSpeaker())}
                }));
        bodyPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Cucina con 1 solo dispositivo
        bodyPanel.add(createRoomPanel("Cucina",
                new Object[][]{
                        {"Luce fornelli", new LightBulb(20.0)}
                }));
        bodyPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        bodyPanel.add(createRoomPanel("Ingresso",
                new Object[][]{{"Telecamera smart", cam}, {"Sensore di movimento", sensor}}));
        bodyPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JButton btnIntruso = new JButton("SIMULA INTRUSO");
        btnIntruso.setBackground(new Color(231, 76, 60));
        btnIntruso.setForeground(Color.WHITE);
        btnIntruso.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnIntruso.setFocusPainted(false);
        btnIntruso.setOpaque(true);
        btnIntruso.setBorderPainted(false);
        btnIntruso.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnIntruso.setMaximumSize(new Dimension(400, 45));
        btnIntruso.addActionListener(e -> {
            System.out.println("\n[SIMULAZIONE] Un intruso passa davanti al sensore...");
            sensor.detectIntruder();
        });

        bodyPanel.add(btnIntruso);

        JScrollPane scrollBody = new JScrollPane(bodyPanel);
        scrollBody.setBorder(null);
        scrollBody.getVerticalScrollBar().setUnitIncrement(16);
        frame.add(scrollBody, BorderLayout.CENTER);

        // terminale
        JTextArea consoleArea = new JTextArea(8, 30);
        consoleArea.setBackground(new Color(10, 10, 10));
        consoleArea.setForeground(new Color(0, 255, 0));
        consoleArea.setFont(new Font("Monospaced", Font.BOLD, 12));
        consoleArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(consoleArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Centro Notifiche", 0, 0, new Font("SansSerif", Font.BOLD, 12), Color.LIGHT_GRAY));

        frame.add(scrollPane, BorderLayout.SOUTH);

        redirectSystemOut(consoleArea);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void initializeBackend() {
        house = new House("Smart Home");

        cam = new SmartCamera("Ingresso");
        MobileApp app = new MobileApp(cam);
        AlarmSystem sirena = new AlarmSystem();

        sensor = new MotionSensor("Ingresso");
        sensor.attach(app);
        sensor.attach(sirena);
    }

    private JPanel createRoomPanel(String nomeStanza, Object[][] dispositiviInfo) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(35, 35, 35));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 60, 60), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        Room room = new Room(nomeStanza.substring(nomeStanza.indexOf(" ") + 1));
        house.addDevices(room);

        JToggleButton btnStanza = new JToggleButton("OFF - " + nomeStanza);
        customizeButton(btnStanza, new Color(70, 70, 70), 16);
        panel.add(btnStanza);
        roomButtons.add(btnStanza);

        List<JToggleButton> deviceBtns = new ArrayList<>();
        roomToDevicesMap.put(btnStanza, deviceBtns);

        for (Object[] info : dispositiviInfo) {
            String nomeDevice = (String) info[0];
            SmartDevice device = (SmartDevice) info[1];
            room.addDevice(device);

            JToggleButton btnDevice = new JToggleButton("OFF - " + nomeDevice);
            customizeButton(btnDevice, new Color(50, 50, 50), 14);
            btnDevice.setMaximumSize(new Dimension(350, 35));

            btnDevice.addActionListener(e -> {
                boolean isOn = btnDevice.isSelected();
                if (isOn) {
                    device.activate();
                    updateButtonGraphics(btnDevice, true, "ON - " + nomeDevice, new Color(241, 196, 15));
                } else {
                    device.deactivate();
                    updateButtonGraphics(btnDevice, false, "OFF - " + nomeDevice, new Color(50, 50, 50));
                }

                // aggiornamento dal basso verso l'alto
                checkRoomState(btnStanza, deviceBtns);
                updateConsumption();
            });

            panel.add(Box.createRigidArea(new Dimension(0, 5)));
            panel.add(btnDevice);
            deviceBtns.add(btnDevice);
        }

        btnStanza.addActionListener(e -> {
            boolean isOn = btnStanza.isSelected();
            String nStanza = btnStanza.getText().substring(btnStanza.getText().indexOf("-") + 2).trim();

            if (isOn) {
                room.activate();
                updateButtonGraphics(btnStanza, true, "ON - " + nStanza, new Color(46, 204, 113));
            } else {
                room.deactivate();
                updateButtonGraphics(btnStanza, false, "OFF - " + nStanza, new Color(70, 70, 70));
            }

            for (JToggleButton db : deviceBtns) {
                db.setSelected(isOn);
                String n = db.getText().substring(db.getText().indexOf("-") + 2).trim();
                updateButtonGraphics(db, isOn, (isOn ? "ON - " : "OFF - ") + n, isOn ? new Color(241, 196, 15) : new Color(50, 50, 50));
            }

            // aggiorna dal basso verso l'alto
            checkHouseState();
            updateConsumption();
        });

        return panel;
    }

    // sinconizzazione bottom up

    // se almeno un dispositivo è acceso, allora accende la stanza
    // se tutti spenti, spenge la stanza
    private void checkRoomState(JToggleButton btnStanza, List<JToggleButton> deviceBtns) {
        boolean isAnyDeviceOn = deviceBtns.stream().anyMatch(JToggleButton::isSelected);
        String nStanza = btnStanza.getText().substring(btnStanza.getText().indexOf("-") + 2).trim();

        btnStanza.setSelected(isAnyDeviceOn);
        updateButtonGraphics(btnStanza, isAnyDeviceOn,
                (isAnyDeviceOn ? "ON - " : "OFF - ") + nStanza,
                isAnyDeviceOn ? new Color(46, 204, 113) : new Color(70, 70, 70));

        checkHouseState(); // Propaga l'aggiornamento alla casa
    }

    // controlla se almeno una stanza è accesa
    // se tutte spente, spenge il bottone master.
    private void checkHouseState() {
        boolean isAnyDeviceOn = roomButtons.stream().anyMatch(JToggleButton::isSelected);

        btnMaster.setSelected(isAnyDeviceOn);
        updateButtonGraphics(btnMaster, isAnyDeviceOn,
                isAnyDeviceOn ? "SPENGI TUTTA LA CASA" : "ACCENDI TUTTA LA CASA",
                isAnyDeviceOn ? new Color(192, 57, 43) : new Color(41, 128, 185));
    }

    // metodi helper

    private void updateConsumption() {
        double watt = house.getConsumption();
        consumptionLabel.setText("Consumo Totale: " + watt + " W/h");
    }

    private void customizeButton(JToggleButton btn, Color bg, int fontSize) {
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(400, 45));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false); // disattivo lo sfondo quadrato
        btn.setBorderPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, fontSize));

        // personlizzazione bottoni
        btn.setUI(new javax.swing.plaf.basic.BasicToggleButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                //antialiasing per evitare sfocature
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(c.getBackground());
                // rettangolo arrotondato
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 25, 25);
                super.paint(g2, c);
                g2.dispose();
            }
        });
    }

    private void updateButtonGraphics(JToggleButton btn, boolean isOn, String text, Color color) {
        btn.setText(text);
        btn.setBackground(color);
    }

    private void redirectSystemOut(JTextArea textArea) {
        OutputStream out = new OutputStream() {
            @Override
            public void write(int b) { updateText(String.valueOf((char) b)); }
            @Override
            public void write(byte[] b, int off, int len) { updateText(new String(b, off, len)); }
            private void updateText(String text) {
                SwingUtilities.invokeLater(() -> {
                    textArea.append(text);
                    textArea.setCaretPosition(textArea.getDocument().getLength());
                });
            }
        };
        System.setOut(new PrintStream(out, true));
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); }
        catch (Exception e) { e.printStackTrace(); }
        SwingUtilities.invokeLater(SmartHomeGUI::new);
    }
}