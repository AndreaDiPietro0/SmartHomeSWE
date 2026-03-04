package core;

/**
 * interfaccia che rappresenta il componente generico nel composite.
 * ogni dispositivo o gruppo di dispositivi deve implementarlo.
 */
public interface SmartDevice {

    // accende il dispositivo o gruppo
    void activate();

    // spenge il dispositivo/gruppo
    void deactivate();

    // ritorna il consumo energetico in watt
    double getConsumption();
}
