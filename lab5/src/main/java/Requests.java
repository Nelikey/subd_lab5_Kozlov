import entities.Patient;
import entities.Record;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Scanner;

public class Requests {
    public void work(SessionFactory sessionFactory){
        System.out.println("1.Первый запрос");
        System.out.println("2.Второй запрос");
        System.out.println("3.Третий запрос");
        System.out.println("4.Вернуться в меню");
        System.out.print(">");
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        Session session;
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        switch (i) {
            case 1 -> firstRequest(session);
            case 2 -> secondrequest(session);
            case 3 -> dateRequest(session);
            default -> System.out.println("Неверный ввод");
        }
        session.getTransaction().commit();
    }

    private void dateRequest(Session session){
        List<Record> recordList = session.createQuery("SELECT s FROM Record s", Record.class).getResultList();
        System.out.printf("%-25s%-25s%-25s%-25s \n", "Имя пациента", "Фамилия пациента", "Диагноз", "Дата выздоровления");
        for (Record record : recordList)
            if(record.getRecoveryDate().after(java.sql.Date.valueOf("2020-05-05")))
                System.out.printf("%-25s%-25s%-25s%-25tF \n", record.getPatient().getFirstname(), record.getPatient().getLastname(),
                        record.getDesease(), record.getRecoveryDate());
    }

    private void firstRequest(Session session){
        List<Record> records = session.createQuery("SELECT r FROM Record r",
                Record.class).getResultList();
        System.out.printf("%-20s%-20s%-20s\n", "Имя пациента", "Фамилия пациента", "Диагноз");
        for (Record record:records) {
            System.out.printf("%-20s%-20s%-20s\n", record.getPatient().getFirstname(), record.getPatient().getLastname(),
                    record.getDesease());
        }
    }

    private void secondrequest(Session session){
        List<Patient> patients = session.createQuery("SELECT p FROM Patient p",
                Patient.class).getResultList();
        System.out.printf("%-20s%-20s%-20s%-20s\n", "Имя пациента", "Фамилия пациента", "Телефонный номер пациента",
                "Мед. участок");
        for (Patient patient:patients) {
            System.out.printf("%-20s%-20s%-20s%-20s\n", patient.getFirstname(), patient.getLastname(),
                    patient.getPhoneNumber(), patient.getRegion().getDescription());
        }
    }

}
