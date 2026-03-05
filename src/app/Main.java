package app;

import core.*;
import adapter.*;
import observer.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("* INIZIALIZZAZIONE SMART HOME *\n");

        // 1. creazione delle foglie
        SmartDevice luceSalotto = new LightBulb(10.0);
        SmartDevice termostato = new Thermostat(1500.0);
        SmartCamera camIngresso = new SmartCamera("Ingresso Principale");

        // 2. creazione dei vecchi dispositivi per l'adapter
        OldTV vecchiaTV = new OldTV();
        SmartDevice tvAdapter = new OldTVAdapter(vecchiaTV);

        OldSpeaker vecchioStereo = new OldSpeaker();
        SmartDevice stereoAdapter = new OldSpeakerAdapter(vecchioStereo);

        // 3. creazione del sensore (il subject dell'observer)
        MotionSensor sensoreIngresso = new MotionSensor("Porta d'ingresso");

        // 4. creazione dell'app e della sirena (observer)
        MobileApp miaApp = new MobileApp(camIngresso);
        AlarmSystem sirena = new AlarmSystem();

        // iscriviamo app e sirena agli avvisi del sensore
        sensoreIngresso.attach(miaApp);
        sensoreIngresso.attach(sirena);

        // 5. creazione delle stanze e della radice casa
        Room salotto = new Room("Salotto");
        salotto.addDevice(luceSalotto);
        salotto.addDevice(tvAdapter);
        salotto.addDevice(stereoAdapter);

        Room corridoio = new Room("Corridoio");
        corridoio.addDevice(camIngresso);
        corridoio.addDevice(sensoreIngresso);

        House miaVilla = new House("Villa smart");
        miaVilla.addArea(salotto);
        miaVilla.addArea(corridoio);

        System.out.println("Configurazione completata\n");

        // test COMPOSITE e ADAPTER tramite l'App
        System.out.println("- L'utente usa l'app per interagire con la sua casa smart -");

        // l'app accende tutta la casa
        miaApp.turnOn(miaVilla, "Villa smart");

        // l'app controlla i consumi globali
        miaApp.checkConsumption(miaVilla, "Villa smart");

        // l'utente vuole spegnere il vecchio stereo dal salotto
        miaApp.turnOff(stereoAdapter, "Vecchio stereo salotto");

        // ricontrollo i consumi che dovrebber essere scesi
        miaApp.checkConsumption(miaVilla, "Villa smart");


        // Test OBSERVER
        System.out.println("\n\n-Un ladro prova a entrare-");

        // simula il movimento rilevato dal sensore
        sensoreIngresso.detectIntruder();

        System.out.println("\n* FINE SIMULAZIONE *");
    }
}