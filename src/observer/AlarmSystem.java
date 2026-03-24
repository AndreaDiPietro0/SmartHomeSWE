package observer;

// sirena d'allarme della casa. concrete observer
public class AlarmSystem implements SensorObserver<SensorEvent> {

    private boolean isRinging = false;

    @Override
    public void update(SensorEvent event) {
        isRinging = true;
        System.out.println("[Allarme Sirena] ALLARME SONORO: " + event.getMessage() + " presso " + event.getLocation());
    }

    public boolean isRinging() {
        return isRinging;
    }
}