package adapter;
//adaptee che non implementa l'interfaccia SmartDevice
public class OldSpeaker {
    public void playMusic() {
        System.out.println("[OldSpeaker] Riproduzione musica in corso...");
    }

    public void stopMusic() {
        System.out.println("[OldSpeaker] Riproduzione musica finita..");
    }
}
