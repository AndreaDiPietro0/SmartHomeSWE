package core;

/**
 * componente foglia nel modello composite. rappresenta una singola lampadina smart.
 */
public class LightBulb implements SmartDevice {
    private double baseConsumption = 10.0; // consuma 10 Watt
    private boolean isOn = false;

    @Override
    public void activate() {
        isOn = true;
        System.out.println("[LightBulb] 💡 Luce ACCESA.");
    }

    @Override
    public void deactivate() {
        isOn = false;
        System.out.println("[LightBulb] 💡 Luce SPENTA.");
    }

    @Override
    public double getConsumption() {
        return isOn ? baseConsumption : 0.0;
    }
}
