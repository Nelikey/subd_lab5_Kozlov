package entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
@Entity
@Table(name = "regions")
public class Region {
    @Id
    @OnDelete(action = OnDeleteAction.CASCADE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "region_id")
    private int region_id;

    @Column(name = "description")
    private String description;

    public Region(){

    }

    public Region(String _desription){
        description = _desription;
    }

    public int getRegion_id() {
        return region_id;
    }

    @Override
    public String toString(){ return String.format("%-25d%-25s", region_id, description); }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
