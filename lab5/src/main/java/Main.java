import entities.Doctor;
import entities.Patient;
import entities.Record;
import entities.Region;
import logics.DoctorLogic;
import logics.PatientLogic;
import logics.RecordLogic;
import logics.RegionLogic;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Scanner;

public class Main {

    public static void main(String[] arg) {
        SessionFactory sessionFactory = new Configuration()
                .addAnnotatedClass(Patient.class)
                .addAnnotatedClass(Record.class)
                .addAnnotatedClass(Doctor.class)
                .addAnnotatedClass(Region.class)
                .buildSessionFactory();

    boolean working = true;
        while (working) {
        System.out.println("\n\t\tМенюшка");
        System.out.println("1.Работать с 'patient'");
        System.out.println("2.Работать с 'record'");
        System.out.println("3.Работать с 'doctor'");
        System.out.println("4.Работать с 'region'");
        System.out.println("5.Работать с запросами");
        System.out.println("6.Выход");
        System.out.print(">");
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        switch (i) {
            case 1 -> {
                PatientLogic patientLogic = new PatientLogic();
                patientLogic.work(sessionFactory);
            }
            case 2 -> {
                RecordLogic recordLogic = new RecordLogic();
                recordLogic.work(sessionFactory);
            }
            case 3 -> {
                DoctorLogic doctorLogic = new DoctorLogic();
                doctorLogic.work(sessionFactory);
            }
            case 4 -> {
                RegionLogic regionLogic = new RegionLogic();
                regionLogic.work(sessionFactory);
            }
            case 5 -> {
                Requests requests = new Requests();
                requests.work(sessionFactory);
            }
            case 6 -> {
                System.out.println("Exit...");
                working = false;
            }
            default -> System.out.println("Invalid input");
        }
    }
        sessionFactory.close();
}
}
