package core;
//composite, nodo contenitore
import java.util.ArrayList;
import java.util.List;

public class Room implements SmartDevice {
    private String name;
    private List<SmartDevice> devices;

    public Room(String name) {
        this.name = name;
        this.devices = new ArrayList<>();
    }

    public void addDevice(SmartDevice device) {
        devices.add(device);
    }

    @Override
    public void activate() {
        System.out.println("\n[" + name + "] Accensione dispositivi della stanza...");
        for (SmartDevice device : devices) {
            device.activate(); // attiva tutti a cascata
        }
    }

    @Override
    public void deactivate() {
        System.out.println("\n[" + name + "] Spegnimento dispositivi della stanza...");
        for (SmartDevice device : devices) {
            device.deactivate();
        }
    }

    @Override
    public double getConsumption() {
        double totalConsumption = 0.0;
        for (SmartDevice device : devices) {
            totalConsumption += device.getConsumption(); // somma i watt di tutte le foglie
        }
        return totalConsumption;
    }
}