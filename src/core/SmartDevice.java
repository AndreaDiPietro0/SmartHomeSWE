package core;

/**
 * interfaccia che rappresenta il “Componente” generico nel modello composite.
 * ogni dispositivo o gruppo di dispositivi presenti in casa deve implementarlo.
 */
public interface SmartDevice {

    // accende il dispositivo o gruppo
    void activate();

    // spenge il dispositivo/gruppo
    void deactivate();

    // ritorna il consumo energetico in watt
    double getConsumption();
}
