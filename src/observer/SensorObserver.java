package observer;

/**
 * interfaccia che rappresenta un ascoltatore (observer)
 * tutti i componenti che vogliono essere avvisati dal sensore devono implementarla.
 */
public interface SensorObserver {

    // metodo chiamato dal Subject (sensore) per inviare la notifica
    void update(String event);
}
