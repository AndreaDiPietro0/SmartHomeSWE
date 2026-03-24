package core;

import java.util.ArrayList;
import java.util.List;

// radice nel modello composite, rappresenta la casa che può contenere più aree e/o stanze.

public class House implements SmartDevice {
    private String name;
    private List<SmartDevice> devices;

    public House(String name) {
        this.name = name;
        this.devices = new ArrayList<>();
    }

    // può aggiungere una room ma anche un solo SmartDevice
    public void addDevices(SmartDevice device) {
        devices.add(device);
    }

    @Override
    public void activate() {
        System.out.println("\n[" + name + "] - Attivazione totale -");
        for (SmartDevice device : devices) {
            device.activate();
        }
    }

    @Override
    public void deactivate() {
        System.out.println("\n[" + name + "] - Disattivazione totale -");
        for (SmartDevice device : devices) {
            device.deactivate();
        }
    }

    @Override
    public double getConsumption() {
        double totalConsumption = 0.0;
        for (SmartDevice device : devices) {
            totalConsumption += device.getConsumption();
        }
        return totalConsumption;
    }
}