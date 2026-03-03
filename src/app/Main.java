package app;

import core.*;
import adapter.*;
import observer.*;

public class Main {

    public static void main(String[] args) {

        System.out.println(".");
        System.out.println("        INIZIALIZZAZIONE SMART HOME");
        System.out.println(".\n");

        //
        // Test Composite e Adapter
        //
        System.out.println("1. Configurazione Dispositivi e Stanze");

        // creo dispositivi base (foglie)
        LightBulb luceSalotto = new LightBulb(10.0);
        Thermostat termostatoSalotto = new Thermostat(1500.0);

        // creo il salotto (ramo) e aggiungo i dispositivi
        Room salotto = new Room();
        salotto.addDevice(luceSalotto);
        salotto.addDevice(termostatoSalotto);

        // adapter
        // integro le casse Bose nel salotto come se fossero un dispositivo normale
        BoseSoundSystem sistemaBose = new BoseSoundSystem();
        BoseAdapter boseAdapter = new BoseAdapter(sistemaBose);
        salotto.addDevice(boseAdapter);

        // creo un altra stanza
        LightBulb luceCamera = new LightBulb(10.0);
        Room camera = new Room();
        camera.addDevice(luceCamera);

        // Composite
        // creo la casa (radice) e aggiungo le stanze
        House miaCasa = new House();
        miaCasa.addArea(salotto);
        miaCasa.addArea(camera);

        System.out.println("\n2. Attivazione totale della casa ");
        miaCasa.activate();

        System.out.println("\nConsumo totale della casa: " + miaCasa.getConsumption() + " Watt\n");

        //
        // Test Observer
        //
        System.out.println(".");
        System.out.println("        TEST SISTEMA DI SICUREZZA");
        System.out.println(".");

        // creo sensore
        MotionSensor sensoreIngresso = new MotionSensor("Ingresso Principale");

        // creo i 3 osservatori
        MobileApp appProprietario = new MobileApp();
        AlarmSystem sirena = new AlarmSystem();
        SystemLogger databaseLogger = new SystemLogger();

        // iscrivo gli osservatori al sensore
        sensoreIngresso.attach(appProprietario);
        sensoreIngresso.attach(sirena);
        sensoreIngresso.attach(databaseLogger);

        // simulo ingresso di un ladro
        sensoreIngresso.detectIntruder();

        //
        // Spegnimento
        //
        System.out.println("\n.");
        System.out.println("        SPEGNIMENTO GLOBALE");
        System.out.println(".\n");
        miaCasa.deactivate();
    }
}