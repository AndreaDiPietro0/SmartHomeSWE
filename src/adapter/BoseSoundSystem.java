package adapter;

/**
 * adaptee nel modello Adapter.
 * simula una libreria esterna di terze parti che non posso modificare.
 */
public class BoseSoundSystem {

    public void playMusic() {
        System.out.println("Bose: playing music...");
    }

    public void stopMusic() {
        System.out.println("Bose: music stopped.");
    }
}
