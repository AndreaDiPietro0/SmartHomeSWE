package observer;

import core.SmartCamera;
import core.SmartDevice;

// app sul telefono del proprietario, concrete observer

public class MobileApp implements SensorObserver {
    private SmartCamera securityCam;

    public MobileApp(SmartCamera securityCam) {
        this.securityCam = securityCam;
    }

    //metodi per controllare la casa usando il composite
    public void turnOn(SmartDevice device, String name) {
        System.out.println("\n[APP] Invio comando di ACCENSIONE a: " + name);
        device.activate();
    }

    public void turnOff(SmartDevice device, String name) {
        System.out.println("\n[APP] Invio comando di SPEGNIMENTO a: " + name);
        device.deactivate();
    }

    public void checkConsumption(SmartDevice device, String name) {
        double power = device.getConsumption();
        System.out.println("\n[APP] Consumo attuale di [" + name + "]: " + power + " Watt");
    }

    //metodo dell'observer
    @Override
    public void update(String event) {
        System.out.println("[NOTIFICA PUSH APP] ALLARME RICEVUTO: " + event);
        System.out.println("[APP] Apertura del video di sicurezza...");
        System.out.println(securityCam.getLiveFeed()); // interroga la telecamera
    }
}