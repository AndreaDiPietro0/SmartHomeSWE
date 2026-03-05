package observer;

//interfaccia che rappresenta un ascoltatore (observer) tutti i componenti che vogliono essere notificati dal sensore devono implementarlo

public interface SensorObserver {

    // metodo chiamato dal subject (sensore) per inviare la notifica
    void update(String event);
}
