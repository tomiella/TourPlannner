package at.bif.swen.tourplanner.model;

public enum TransportType {
    WALKING("foot-walking"),
    CAR("driving-car"),
    BIKE("cycling-regular"),
    PUBLIC_TRANSPORT("driving-car");

    private final String transportType;

    TransportType(String transportType) {
        this.transportType = transportType;
    }

    public String toString() {
        return transportType;
    }
}
