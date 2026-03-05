package observer;

// sirena d'allarme della casa. concrete observer
public class AlarmSystem implements SensorObserver {

    private boolean isRinging = false;

    @Override
    public void update(String event) {
        isRinging = true;
        System.out.println("[Allarme Sirena] ALLARME SONORO: " + event);
    }

    public boolean isRinging() {
        return isRinging;
    }
}