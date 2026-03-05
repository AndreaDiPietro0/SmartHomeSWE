package core;

//interfaccia che rappresenta il componente generico nel composite, ogni dispositivo o gruppo di dispositivi deve implementarlo.

public interface SmartDevice {

    void activate(); // accende il dispositivo o gruppo
    void deactivate(); // spenge il dispositivo/gruppo
    double getConsumption(); // ritorna il consumo energetico in watt

}
