package core;

/**
 * componente foglia nel modello composite
 * rappresenta un singolo termostato smart.
 */
public class Thermostat implements SmartDevice {

    private double baseConsumption;

    // costruttore
    public Thermostat(double baseConsumption) {
        this.baseConsumption = baseConsumption;
    }

    @Override
    public void activate() {
        System.out.println("Thermostat turned ON.\n");

    }

    @Override
    public void deactivate() {
        System.out.println("Thermostat turned OFF.\n");

    }

    @Override
    public double getConsumption() {
        return baseConsumption;
    }
}
