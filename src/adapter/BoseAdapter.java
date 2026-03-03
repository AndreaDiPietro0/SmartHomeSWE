package adapter;
import core.SmartDevice;

/**
 * adapter nel modello Adapter
 * rende il BoseSoundSystem compatibile con il mio standard SmartDevice.
 */
public class BoseAdapter implements SmartDevice {

    // l'adapter "nasconde" l'oggetto estraneo al suo interno
    private BoseSoundSystem boseSpeakers;

    // costruttore: richiede il sistema Bose da adattare
    public BoseAdapter(BoseSoundSystem boseSpeakers) {
        this.boseSpeakers = boseSpeakers;
    }

    @Override
    public void activate() {
        System.out.println("Adapter: Translating 'activate' to 'playMusic'...");
        boseSpeakers.playMusic();
    }

    @Override
    public void deactivate() {
        System.out.println("Adapter: Translating 'deactivate' to 'stopMusic'...");
        boseSpeakers.stopMusic();
    }

    @Override
    public double getConsumption() {
        // imposto un consumo fisso per l'impianto audio
        return 120.0;
    }
}