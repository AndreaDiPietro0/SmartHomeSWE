package core;
//copmonente foglia

public class SmartCamera implements SmartDevice {
    private String location;
    private double baseConsumption = 15.0;
    private boolean isRecording = false;

    // quando creo la telecamera specifico dove si trova
    public SmartCamera(String location) {
        this.location = location;
    }

    @Override
    public void activate() {
        isRecording = true;
        System.out.println("[SmartCamera - " + location + "] ATTIVATA e in registrazione.");
    }

    @Override
    public void deactivate() {
        isRecording = false;
        System.out.println("[SmartCamera -" + location + "] DISATTIVATA.");
    }

    @Override
    public double getConsumption() {
        return isRecording ? baseConsumption : 0.0;
    }

    // metodo chiamato dall'app
    public String getLiveFeed() {
        if (isRecording) {
            return "[REC] Inquadratura '" + location + "'";
        } else {
            return "[OFF] Segnale assente. Telecamera '" + location + "' offline.";
        }
    }
}