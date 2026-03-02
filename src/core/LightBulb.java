package core;

/**
 * componente foglia nel modello composite.
 * rappresenta una singola lampadina smart.
 */
public class LightBulb implements SmartDevice {

    private double baseConsumption;

    // costruttore
    public LightBulb(double baseConsumption) {
        this.baseConsumption = baseConsumption;
    }

    @Override
    public void activate() {
        System.out.println("LightBulb turned ON.");
    }

    @Override
    public void deactivate() {
        System.out.println("LightBulb turned OFF.");
    }

    @Override
    public double getConsumption() {
        return baseConsumption;
    }
}
