package entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    private int patient_id;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "phonenumber")
    private String phonenumber;

    //@OnDelete(action = OnDeleteAction)
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "region_id")
    private Region region;


    public Patient(){

    }

    public Patient(String _firstname, String _lastname, String _phonenumber, Region _region){
        firstname = _firstname;
        lastname = _lastname;
        phonenumber = _phonenumber;
        region = _region;
    }

    public int getPatient_id() {
        return patient_id;
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

    public String getPhoneNumber() {
        return phonenumber;
    }

    public void setPhoneNumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    @Override
    public String toString(){ return String.format("%-25s%-25s%-25s%-25s%-25s",patient_id,firstname,lastname,phonenumber,region.getRegion_id()); }
}
