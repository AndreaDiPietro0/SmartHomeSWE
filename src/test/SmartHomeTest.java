package test;

import core.*;
import adapter.*;
import observer.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SmartHomeTest {

    @Test
    public void testCompositeConsumption() {
        // 1. preparazione
        LightBulb bulb1 = new LightBulb(15.0);
        LightBulb bulb2 = new LightBulb(15.0);
        Thermostat thermostat = new Thermostat(2000.0);

        Room livingRoom = new Room();
        livingRoom.addDevice(bulb1);
        livingRoom.addDevice(thermostat);

        Room bedroom = new Room();
        bedroom.addDevice(bulb2);

        House myHouse = new House();
        myHouse.addArea(livingRoom);
        myHouse.addArea(bedroom);

        // 2. esecuzione
        double totalConsumption = myHouse.getConsumption();

        // 3. verifica
        assertEquals(2030.0, totalConsumption, "Il pattern composite non calcola correttamente il consumo totale");
    }

    @Test
    public void testAdapterIntegration() {
        // 1. preparazione
        Room entertainmentRoom = new Room();
        LightBulb ledLight = new LightBulb(5.0);

        BoseSoundSystem boseSystem = new BoseSoundSystem();
        BoseAdapter boseAdapter = new BoseAdapter(boseSystem);

        // 2. esecuzione
        entertainmentRoom.addDevice(ledLight);
        entertainmentRoom.addDevice(boseAdapter); // aggiugno l'adapter come se fosse un dispositivo normale

        double roomConsumption = entertainmentRoom.getConsumption();

        // 3. verifica
        // il BoseAdapter ha un consumo fisso di 120.0W nel codice, 120 + 5 = 125
        assertEquals(125.0, roomConsumption, "L'adapter non si integra correttamente nel composite");
    }

    @Test
    public void testObserverAttachment() {
        // 1. preparazione
        MotionSensor sensor = new MotionSensor("garage");
        MobileApp app = new MobileApp();
        AlarmSystem alarm = new AlarmSystem();

        // 2. esecuzione
        sensor.attach(app);
        sensor.attach(alarm);
        sensor.detach(app); // rimuovo l'app per testare la disiscrizione

        // 3. verifica
        // siccome non posso testare l'output su console in modo semplice, verifico che il sensore non vada in crash,
        // eseguo la notifica dopo aver aggiunto e rimosso gli observer.
        assertDoesNotThrow(() -> {
            sensor.detectIntruder();
        }, "Il pattern observer ha generato un'eccezione durante la notifica");
    }
}
