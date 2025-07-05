package at.bif.swen.tourplanner.model;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class TourItem {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    @Column(name = "from_location")
    private String from;
    @Column(name = "to_location")
    private String to;
    private TransportType transportType;
    private String imagePath;
    @Column(name="distance")
    private Double distance;
    @Column(name="estimated_duration")
    private long estimatedDuration;




    public TourItem(String name) {
        this.name = name;
    }

    public TourItem(String name, String description, String from, String to, TransportType transportType,double distance, long estimatedDuration) {
        this.name = name;
        this.description = description;
        this.from = from;
        this.to = to;
        this.transportType = transportType;
        this.distance = distance;
        this.estimatedDuration = estimatedDuration;

    }

    @Override
    public String toString() {
        return name;
    }

    public String toPrettyString() {
        StringBuilder builder = new StringBuilder();
        builder.append("TourItem [id=");
        builder.append(id);
        builder.append(", name=");
        builder.append(name);
        builder.append(", description=");
        builder.append(description);
        builder.append(", from=");
        builder.append(from);
        builder.append(", to=");
        builder.append(to);
        builder.append(", transportType=");
        builder.append(transportType);
        builder.append(", imagePath=");
        builder.append(imagePath);
        builder.append(", distance=");
        builder.append(distance);
        builder.append(", estimatedDuration=");
        builder.append(estimatedDuration);
        builder.append("]");
        return builder.toString();
    }

}
