package observer;

import core.SmartDevice;

import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;
import java.util.List;

public class MotionSensor implements SmartDevice {
    private String location;
    private boolean isActive = false;
    private double powerDraw = 2.0;

    // lista degli observer (app, allarme)
    private List<SensorObserver> observers = new ArrayList<>();

    public MotionSensor(String location) {
        this.location = location;
    }

    //metodi da smartDevice, composite
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

    //metodi per gli oibserver
    public void attach(SensorObserver o) {
        observers.add(o);
    }

    public void detach(SensorObserver o) {
        observers.remove(o);
    }

    private void notifyObservers(String event) {
        for (SensorObserver o : observers) {
            o.update(event); // Avvisa tutti gli iscritti!
        }
    }

    // simula movimenti
    public void detectIntruder() {
        if (isActive) {
            System.out.println("\n[Sensore - " + location + "] RILEVATO MOVIMENTO SOSPETTO");
            notifyObservers("Intruso rilevato presso: " + location);
        } else {
            System.out.println("\n[Sensore- " + location + "] (Movimento rilevato, ma sensore disattivato. Nessun allarme).");
        }
    }
}