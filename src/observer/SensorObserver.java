package observer;

//interfaccia che rappresenta un ascoltatore (observer) tutti i componenti che vogliono essere notificati dal sensore devono implementarlo
// il T generico rende + indipendente dal tipo di oggetto notificato
public interface SensorObserver<T> {

    // metodo chiamato dal subject (sensore) per inviare la notifica generica
    void update(T event);
}