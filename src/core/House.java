package core;

import java.util.ArrayList;
import java.util.List;

// radice nel modello composite, rappresenta la casa che può contenere più aree e/o stanze.

public class House implements SmartDevice {
    private String name;
    private List<SmartDevice> areas;

    public House(String name) {
        this.name = name;
        this.areas = new ArrayList<>();
    }

    // può aggiungere una room ma anche un solo SmartDevice
    public void addArea(SmartDevice area) {
        areas.add(area);
    }

    @Override
    public void activate() {
        System.out.println("\n[" + name + "] - Attivazione totale -");
        for (SmartDevice area : areas) {
            area.activate();
        }
    }

    @Override
    public void deactivate() {
        System.out.println("\n[" + name + "] - Disattivazione totale -");
        for (SmartDevice area : areas) {
            area.deactivate();
        }
    }

    @Override
    public double getConsumption() {
        double totalConsumption = 0.0;
        for (SmartDevice area : areas) {
            totalConsumption += area.getConsumption();
        }
        return totalConsumption;
    }
}