package at.bif.swen.tourplanner.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tours")
public class TourItem {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private String from;
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
