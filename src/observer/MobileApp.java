package observer;

import core.SmartCamera;
import core.SmartDevice;

// app sul telefono del proprietario, concrete observer
public class MobileApp implements SensorObserver<SensorEvent> {
    private SmartCamera securityCam;

    public MobileApp(SmartCamera securityCam) {
        this.securityCam = securityCam;
    }

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

    // metodo dell'observer che ora riceve SensorEvent
    @Override
    public void update(SensorEvent event) {
        System.out.println("[NOTIFICA PUSH APP] ALLARME RICEVUTO: " + event.getMessage() + " presso " + event.getLocation());
        System.out.println("[APP] Apertura del video di sicurezza...");
        System.out.println(securityCam.getLiveFeed()); // interroga la telecamera
    }
}