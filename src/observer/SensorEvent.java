package observer;

// nuovo oggetto evento che incapsula le informazioni dell'evento
// modello push, il subject invia i dati all'observer
public class SensorEvent {
    private String message;
    private String location;

    public SensorEvent(String message, String location) {
        this.message = message;
        this.location = location;
    }

    public String getMessage() {
        return message;
    }

    public String getLocation() {
        return location;
    }
}
