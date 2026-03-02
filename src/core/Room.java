package core;

import java.util.ArrayList;
import java.util.List;

/**
 * composite intermedio nel modello composite.
 * rappresenta una stanza contenente più smart devices.
 */
public class Room implements SmartDevice {

    // lista che conterrà le foglie o gli altri rami
    private List<SmartDevice> devices;

    public Room() {
        // inizializzo la lista vuota
        this.devices = new ArrayList<>();
    }

    // per aggiungere un dispositivo alla stanza
    public void addDevice(SmartDevice d) {
        devices.add(d);
    }

    @Override
    public void activate() {
        System.out.println("Activating Room");
        // ciclo for: delega il comando a tutti i dispositivi nella lista
        for (SmartDevice d : devices) {
            d.activate();
        }
    }

    @Override
    public void deactivate() {
        System.out.println("Deactivating Room");
        for (SmartDevice d : devices) {
            d.deactivate();
        }
    }

    @Override
    public double getConsumption() {
        double total = 0;
        // somma i consumi di tutti i dispositivi contenuti
        for (SmartDevice d : devices) {
            total += d.getConsumption();
        }
        return total;
    }
}
