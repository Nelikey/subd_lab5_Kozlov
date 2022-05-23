package entities;

import javax.persistence.*;
@Entity
@Table(name = "doctors")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id")
    private int doctor_id;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;

    public Doctor(){

    }

    public Doctor(String _firstname, String _lastname, Region _region){
        firstname = _firstname;
        lastname = _lastname;
        region = _region;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    @Override
    public String toString(){ return String.format("%-25d%-25s%-25s%-25s", doctor_id, firstname, lastname, region.getRegion_id()); }
}
