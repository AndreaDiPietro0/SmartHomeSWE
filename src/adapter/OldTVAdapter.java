package adapter;

import core.SmartDevice;

public class OldTVAdapter implements SmartDevice {
    private OldTV oldTV;
    private boolean isOn = false;
    private double powerDraw = 120.0;

    // il costruttore prende l'oggetto vecchio da adattare
    public OldTVAdapter(OldTV vecchiaTV) {
        this.oldTV = vecchiaTV;
    }

    @Override
    public void activate() {
        System.out.print("[Adapter] Traduco il comando per la vecchia TV -> ");
        oldTV.powerOn(); // traduce "activate" in "powerOn"
        isOn = true;
    }

    @Override
    public void deactivate() {
        System.out.print("[Adapter] Traduco il comando per la vecchia TV -> ");
        oldTV.powerOff();
        isOn = false;
    }

    @Override
    public double getConsumption() {

        return isOn ? powerDraw : 0.0;
    }
}
