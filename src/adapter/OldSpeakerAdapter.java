package adapter;

import core.SmartDevice;

public class OldSpeakerAdapter implements SmartDevice {
    private OldSpeaker oldSpeaker;
    private boolean isPlaying = false;
    private double powerDraw = 45.0;

    public OldSpeakerAdapter(OldSpeaker legacySpeaker) {
        this.oldSpeaker = legacySpeaker;
    }

    @Override
    public void activate() {
        System.out.print("[Adapter] Traduco il comando per il vecchio stereo -> ");
        oldSpeaker.playMusic();
        isPlaying = true;
    }

    @Override
    public void deactivate() {
        System.out.print("[Adapter] Traduco il comando per il vecchio stereo -> ");
        oldSpeaker.stopMusic();
        isPlaying = false;
    }

    @Override
    public double getConsumption() {
        return isPlaying ? powerDraw : 0.0;
    }
}