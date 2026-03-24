package observer;

//il sensore gestisce una lista di SensorObserver<SensorEvent> e crea l'oggetto evento quando c'è un intrusione
import core.SmartDevice;
import java.util.ArrayList;
import java.util.List;

public class MotionSensor implements SmartDevice {
    private String location;
    private boolean isActive = false;
    private double powerDraw = 2.0;

    // lista degli observer tipizzata sull'oggetto SensorEvent
    private List<SensorObserver<SensorEvent>> observers = new ArrayList<>();

    public MotionSensor(String location) {
        this.location = location;
    }

    @Override
    public void activate() {
        isActive = true;
        System.out.println("[Sensore - " + location + "] Sensore attivo.");
    }

    @Override
    public void deactivate() {
        isActive = false;
        System.out.println("[Sensore - " + location + "] Sensore disattivo.");
    }

    @Override
    public double getConsumption() {
        return isActive ? powerDraw : 0.0;
    }

    // metodi per gli observer aggiornati con il generico SensorEvent
    public void attach(SensorObserver<SensorEvent> o) {
        observers.add(o);
    }

    public void detach(SensorObserver<SensorEvent> o) {
        observers.remove(o);
    }

    // passa l'oggetto SensorEvent invece che la stringa
    private void notifyObservers(SensorEvent event) {
        for (SensorObserver<SensorEvent> o : observers) {
            o.update(event);
        }
    }

    public void detectIntruder() {
        if (isActive) {
            System.out.println("\n[Sensore - " + location + "] RILEVATO MOVIMENTO SOSPETTO");
            // impacchetta l'evento
            SensorEvent event = new SensorEvent("Intruso rilevato", location);
            notifyObservers(event);
        } else {
            System.out.println("\n[Sensore - " + location + "] (Movimento rilevato, ma sensore disattivato. Nessun allarme).");
        }
    }
}