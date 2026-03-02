package observer;

/**
 * 3 concrete observer: sistema che salva un registro degli eventi.
 */
public class SystemLogger implements SensorObserver {

    @Override
    public void update(String event) {
        System.out.println("Logger: Salvataggio evento nel database -> [" + event + "]");
    }
}