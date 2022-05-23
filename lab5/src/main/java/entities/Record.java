package entities;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "records")
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    private int record_id;

    @Column(name = "desease")
    private String desease;

    @Column(name = "recoverydate")
    private Date recoverydate;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    public Record(){

    }

    public Record(String _desease, Date _recoverydate, Patient _patient ){
        desease = _desease;
        recoverydate = _recoverydate;
        patient = _patient;
    }

    public int getRecord_id() {
        return record_id;
    }

    public String getDesease() {
        return desease;
    }

    public void setDesease(String desease) {
        this.desease = desease;
    }

    public Date getRecoveryDate() {
        return recoverydate;
    }

    public void setRecoveryDate(Date recoverydate) {
        this.recoverydate = recoverydate;
    }

    public String toString(){ return String.format("%-25s%-25s%-25s-25s",record_id,desease,recoverydate,patient.getPatient_id());
}

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
