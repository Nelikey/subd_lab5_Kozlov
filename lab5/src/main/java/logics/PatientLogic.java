package logics;

import entities.Patient;
import entities.Region;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Scanner;

public class PatientLogic {
    public void work(SessionFactory sessionFactory){
        System.out.println("1.Создать");
        System.out.println("2.Прочитать");
        System.out.println("3.Обновить");
        System.out.println("4.Удалить");
        System.out.println("5.Вернуться в меню");
        System.out.print(">");
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        Session session;
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        switch (i) {
            case 1 -> create(session);
            case 2 -> read(session);
            case 3 -> update(session);
            case 4 -> delete(session);
            case 5 -> {
                session.close();
                return;
            }
            default -> System.out.println("Неверный ввод");
        }

        try{
            session.getTransaction().commit();
        }catch (PersistenceException ex){
            System.out.println("Невозможно удалить элемент, содержащий зависимые элементы");
        }
    }

    private void create(Session session){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите имя пациента:");
        String firstname = scanner.next();
        System.out.print("Введите фамилию пациента:");
        String lastname = scanner.next();
        System.out.print("Введите телефонный номер пациента:");
        String phonenumber = scanner.next();
        System.out.print("Введите ID мед. участка:");
        int region_id = scanner.nextInt();
        try {
            Patient patient = new Patient(firstname, lastname, phonenumber, session.get(Region.class, region_id));
            session.save(patient);
        }
        catch (Exception ex){
            System.out.println("Ошибка создания записи");
        }
    }

    private void read(Session session){
        System.out.println("1.С фильтром");
        System.out.println("2.Без фильтра");
        System.out.print(">");
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        switch (i){
            case 1-> filter(session);
            case 2-> {
                List<Patient> patientList = session.createQuery("SELECT s FROM Patient s",
                        Patient.class).getResultList();
                System.out.println("Пациент");
                System.out.printf("%-15s%-15s%-15s%-15s%-15s\n","ID","Имя","Фамилия","Телефонный номер","ID мед. участка");
                patientList.forEach(System.out::println);
            }
            default -> System.out.println("Неверный ввод");
        }

    }
    private void update(Session session){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите ID пациента:");
        int patient_id = scanner.nextInt();
        System.out.println("Что вы хотите обновить?");
        System.out.println("1.Имя пациента");
        System.out.println("2.Фамилию пациента");
        System.out.println("3.Учебный курс");
        System.out.println("4.ID мед. участка");
        System.out.println("5.ID врача");
        System.out.print(">");
        int i = scanner.nextInt();
        switch (i) {
            case 1 -> {
                System.out.print("Введите имя пациента:");
                scanner.nextLine();
                String firstname = scanner.next();
                try {
                    Patient patient = session.get(Patient.class, patient_id);
                    patient.setFirstname(firstname);
                    session.save(patient);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 2 -> {
                System.out.print("Введите фамилию пациента:");
                scanner.nextLine();
                String lastname = scanner.next();
                try {
                    Patient patient = session.get(Patient.class, patient_id);
                    patient.setLastname(lastname);
                    session.save(patient);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 3 -> {
                System.out.print("Введите телефонный номер:");
                scanner.nextLine();
                String phonenumber = scanner.next();
                try {
                    Patient patient = session.get(Patient.class, patient_id);
                    patient.setPhoneNumber(phonenumber);
                    session.save(patient);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 4 -> {
                System.out.print("Введите ID мед. участка:");
                scanner.nextLine();
                int region_id = scanner.nextInt();
                try {
                    Patient patient = session.get(Patient.class, patient_id);
                    patient.setRegion(session.get(Region.class, region_id));
                    session.save(patient);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
        }
    }

    private void delete(Session session){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите ID пациента:");
        int patient_id = scanner.nextInt();
        try {
            Patient patient = session.get(Patient.class, patient_id);
            session.delete(patient);
        }
        catch (Exception ex){
            System.out.println("Этой записи не существует");
        }
    }

    private void filter(Session session){
        System.out.println("\tПоля");
        System.out.println("1.ID пациента");
        System.out.println("2.Имя пациента");
        System.out.println("3.Фамилия пациента");
        System.out.println("4.Учебный курс");
        System.out.println("5.ID мед. участка");
        System.out.print(">");
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        List<Patient> patientList;
        switch (i) {
            case 1 -> {
                System.out.print("Введите ID пациента:");
                int patient_id = scanner.nextInt();
                try {
                    patientList = session.createQuery("SELECT s FROM Patient s WHERE patient_id =" + patient_id,
                            Patient.class).getResultList();
                    System.out.println("Пациент");
                    System.out.printf("%-30s%-30s%-30s%-30s%-30s\n","ID","Имя","Фамилия","Телефонный номер","ID мед. участка");
                    patientList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 2 -> {
                System.out.print("Введите имя пациента:");
                String patient_firstname = scanner.next();
                try {
                    patientList = session.createQuery("SELECT s FROM Patient s WHERE patient_firstname =" + patient_firstname,
                            Patient.class).getResultList();
                    System.out.println("Пациент");
                    System.out.printf("%-30s%-30s%-30s%-30s%-30s\n","ID","Имя","Фамилия","Телефонный номер","ID мед. участка");
                    patientList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 3 -> {
                System.out.print("Введите фамилию пациента:");
                String patient_lastname = scanner.next();
                try {
                    patientList = session.createQuery("SELECT s FROM Patient s WHERE patient_lastname =" + patient_lastname,
                            Patient.class).getResultList();
                    System.out.println("Пациент");
                    System.out.printf("%-30s%-30s%-30s%-30s%-30s\n","ID","Имя","Фамилия","Телефонный номер","ID мед. участка");
                    patientList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 4 -> {
                System.out.print("Введите телефонный номер:");
                int phonenumber = scanner.nextInt();
                try {
                    patientList = session.createQuery("SELECT s FROM Patient s WHERE phonenumber =" + phonenumber,
                            Patient.class).getResultList();
                    System.out.println("Пациент");
                    System.out.printf("%-30s%-30s%-30s%-30s%-30s\n","ID","Имя","Фамилия","Телефонный номер","ID мед. участка");
                    patientList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 5 -> {
                System.out.print("Введите ID мед. участка:");
                int region_id = scanner.nextInt();
                try {
                    patientList = session.createQuery("SELECT s FROM Patient s WHERE region_id =" + region_id,
                            Patient.class).getResultList();
                    System.out.println("Пациент");
                    System.out.printf("%-30s%-30s%-30s%-30s%-30s\n","ID","Имя","Фамилия","Телефонный номер","ID мед. участка");
                    patientList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            default -> System.out.println("Неверный ввод");
        }
    }

}
