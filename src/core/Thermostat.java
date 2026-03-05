package core;

//foglia nel modello composite che rappresenta un termostato smart.

public class Thermostat implements SmartDevice {

    private double baseConsumption;
    private boolean isOn = false;

    // costruttore
    public Thermostat(double baseConsumption) {
        this.baseConsumption = baseConsumption;
    }

    @Override
    public void activate() {
        isOn = true;
        System.out.println("[Termostato] Riscaldamento ACCESO.");
    }

    @Override
    public void deactivate() {
        isOn = false;
        System.out.println("[Termostato] Riscaldamento SPENTO.");
    }

    @Override
    public double getConsumption() {
        // consuma energia solo se è acceso
        return isOn ? baseConsumption : 0.0;
    }
}