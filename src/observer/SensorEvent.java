package observer;

// nuovo oggetto evento che contiene tutte le informazioni su cosa accade
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
