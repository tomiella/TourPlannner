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
    private int id;
    private String name;
    private String description;
    @Column(name = "from_location")
    private String from;
    @Column(name = "to_location")
    private String to;
    private TransportType transportType;
    private String imagePath;


    public TourItem(String name) {
        this.name = name;
    }

    public TourItem(String name, String description, String from, String to, TransportType transportType) {
        this.name = name;
        this.description = description;
        this.from = from;
        this.to = to;
        this.transportType = transportType;
    }

    @Override
    public String toString() {
        return name;
    }

}
