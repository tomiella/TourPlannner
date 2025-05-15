package at.bif.swen.tourplanner.model;

public class RouteItem {

    private String name;
    private String description;
    private String from;
    private String to;
    private TransportType transportType;

    public RouteItem(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(name).append("\n");
        result.append(description).append("\n");
        result.append(from).append(" -> ").append(to).append("\n");
        result.append(transportType.toString());

        return result.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public TransportType getTransportType() {
        return transportType;
    }

    public void setTransportType(TransportType transportType) {
        this.transportType = transportType;
    }
}
