import adapter.OldSpeaker;
import adapter.OldSpeakerAdapter;
import adapter.OldTV;
import adapter.OldTVAdapter;
import core.*;
import observer.AlarmSystem;
import observer.MobileApp;
import observer.MotionSensor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SmartHomeTest {

    private House miaCasa;
    private Room salotto;
    private Room giardino;
    private SmartDevice luce;
    private Thermostat termostato;
    private SmartCamera telecamera;
    private OldTVAdapter tvAdapter;
    private OldSpeakerAdapter stereoAdapter;
    private MotionSensor sensore;
    private AlarmSystem sirena;
    private MobileApp app;

    // @BeforeEach eseguito prima di ogni test, cosi ho sempre una casa nuova
    @BeforeEach
    public void setUp() {
        // inizializzo tutti i componenti
        luce = new LightBulb(10.0);
        termostato = new Thermostat(1500.0);
        telecamera = new SmartCamera("Giardino");

        tvAdapter = new OldTVAdapter(new OldTV());
        stereoAdapter = new OldSpeakerAdapter(new OldSpeaker());

        sensore = new MotionSensor("Cancello");
        sirena = new AlarmSystem();
        app = new MobileApp(telecamera);

        // costruisco il composite
        salotto = new Room("Salotto");
        salotto.addDevice(luce);
        salotto.addDevice(tvAdapter);

        giardino = new Room("Giardino");
        giardino.addDevice(telecamera);
        giardino.addDevice(sensore);

        miaCasa = new House("Villa smart");
        miaCasa.addDevices(salotto);
        miaCasa.addDevices(giardino);
        miaCasa.addDevices(termostato); // il termostato è collegato direttamente alla casa
    }

    // 1. Test foglie
    @Test
    public void testSmartCameraLogic() {
        // verifico lo stato spento
        assertEquals(0.0, telecamera.getConsumption(), 0.01, "La telecamera spenta consuma 0");
        assertTrue(telecamera.getLiveFeed().contains("[OFF]"));

        // accendo
        telecamera.activate();

        // verifico lo stato acceso
        assertEquals(15.0, telecamera.getConsumption(), 0.01, "La telecamera accesa consuma 15W");
        assertTrue(telecamera.getLiveFeed().contains("[REC]"));
        assertTrue(telecamera.getLiveFeed().contains("Giardino"));
    }

    // 2. Test adapter
    @Test
    public void testOldAdapter() {
        tvAdapter.activate();
        assertEquals(120.0, tvAdapter.getConsumption(), "L'Adapter oldTV non consuma il valore corretto");

        stereoAdapter.activate();
        assertEquals(45.0, stereoAdapter.getConsumption(), "L'Adapter oldSpeaker non consuma il valore corretto");

        stereoAdapter.deactivate();
        assertEquals(0.0, stereoAdapter.getConsumption(), "Lo stereo spento consuma 0");
    }

    // 3. Test composite
    @Test
    public void testDeepCompositeTree() {
        // accendo l'intera casa
        miaCasa.activate();

        // consumo totale atteso:
        // salotto: llampadina 10 + TV 120 = 130
        // giardino: telecamera15 + sensore2 = 17
        // casa: termostato1500
        // tot = 130 + 17 + 1500 = 1647.0

        assertEquals(1647.0, miaCasa.getConsumption(), 0.01, "Il composite non calcola bene la somma");

        // spengo il salotto
        salotto.deactivate();

        // nuovo tot 1647 - 130 = 1517.0
        assertEquals(1517.0, miaCasa.getConsumption(), 0.01, "Il composite non aggiorna i dispositivi spenti");
    }

    // 4. test observer e app
    @Test
    public void testObserverAndEmergency() {
        sensore.attach(sirena);
        sensore.attach(app);

        // sensore spento, il movimento non dovrebbe far scattare nulla
        sensore.detectIntruder();
        assertFalse(sirena.isRinging(), "La sirena si è accesa ma il sensore era disattivato");

        // attivo sensore e simulo intruso
        sensore.activate();
        sensore.detectIntruder();

        assertTrue(sirena.isRinging(), "Il sensore non ha notificato la sirena");
        // l'app stampa e la sirena conferma che il subject ha inviato la notifica a tutti
    }
}