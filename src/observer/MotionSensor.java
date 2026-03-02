package observer;

import java.util.ArrayList;
import java.util.List;

/**
 * subject (osservato): il sensore di movimento che genera gli eventi.
 * avvisa tutti gli iscritti insieme e ne tiene traccia
 */
public class MotionSensor {

    // lista degli osservatori iscritti
    private List<SensorObserver> observers;

    // stanza in cui è installato il sensore
    private String location;

    // costruttore: chiede di specificare la stanza in cui viene posizionato
    public MotionSensor(String location) {
        this.observers = new ArrayList<>();
        this.location = location;
    }

    // iscrizione di un nuovo ascoltatore alla lista
    public void attach(SensorObserver o) {
        observers.add(o);
    }

    // disiscrizione di un ascoltatore dalla lista
    public void detach(SensorObserver o) {
        observers.remove(o);
    }

    // scorre la lista e avvisa tutti
    private void notifyObservers(String event) {
        System.out.println("----Sensore (" + location + "): invio notifiche a " + observers.size() + " dispositivi...\n");
        for (SensorObserver o : observers) {
            o.update(event);
        }
    }

    // simula il rilevamento di un movimento fisico
    public void detectIntruder() {
        System.out.println("\n[!] Sensore di Movimento [" + location + "]: RILEVATA PRESENZA ESTRANEA!");
        notifyObservers("Intruso rilevato nella stanza: " + location);
    }
}