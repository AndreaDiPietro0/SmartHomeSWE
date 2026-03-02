package core;

import java.util.ArrayList;
import java.util.List;

/**
 * componente radice nel modello composito.
 * rappresenta l'intera casa contenente più aree e/o stanze.
 */
public class House implements SmartDevice {

    private List<SmartDevice> areas;

    public House() {
        this.areas = new ArrayList<>();
    }

    public void addArea(SmartDevice area) {
        areas.add(area);
    }

    @Override
    public void activate() {
        System.out.println("Activating Entire House");
        for (SmartDevice area : areas) {
            area.activate();
        }
    }

    @Override
    public void deactivate() {
        System.out.println("Deactivating Entire House");
        for (SmartDevice area : areas) {
            area.deactivate();
        }
    }

    @Override
    public double getConsumption() {
        double total = 0;
        for (SmartDevice area : areas) {
            total += area.getConsumption();
        }
        return total;
    }
}
