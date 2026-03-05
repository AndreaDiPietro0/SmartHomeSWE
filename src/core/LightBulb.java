package core;

//componente foglia nel modello composite, rappresenta una lampadina smart

public class LightBulb implements SmartDevice {
    private double baseConsumption;
    private boolean isOn = false;

    public LightBulb(double baseConsumption) {
        this.baseConsumption = baseConsumption;
    }

    @Override
    public void activate() {
        isOn = true;
        System.out.println("[Lampadina] Luce ACCESA.");
    }

    @Override
    public void deactivate() {
        isOn = false;
        System.out.println("[Lampadina] Luce SPENTA.");
    }

    @Override
    public double getConsumption() {
        return isOn ? baseConsumption : 0.0;
    }
}