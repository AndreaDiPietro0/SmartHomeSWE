package observer;

/**
 * 2 concrete observer: la sirena d'allarme della casa.
 */
public class AlarmSystem implements SensorObserver {

    @Override
    public void update(String event) {
        System.out.println("Sistema di Allarme: ATTIVAZIONE SIRENA! Motivo: " + event);
    }
}