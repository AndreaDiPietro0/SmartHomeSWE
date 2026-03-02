package observer;

/**
 * 1 concrete observer: app sul telefono del proprietario
 */
public class MobileApp implements SensorObserver {

    @Override
    public void update(String event) {
        System.out.println("App Mobile: ricevuta notifica -> " + event);
    }
}