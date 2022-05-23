package logics;

import entities.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Scanner;

public class DoctorLogic {
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
        session.getTransaction().commit();
    }

    private void create(Session session){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите имя участкового врача:");
        String doctor_firstname = scanner.next();
        System.out.print("Введите фамилию участкового врача:");
        String doctor_lastname = scanner.next();
        System.out.print("Введите ID мед. участка:");
        int region_id = scanner.nextInt();
        try {
            Doctor doctor = new Doctor(doctor_firstname, doctor_lastname, session.get(Region.class, region_id));
            session.save(doctor);
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
                List<Doctor> doctorList = session.createQuery("SELECT d FROM Doctor d",
                        Doctor.class).getResultList();
                System.out.println("Участковый врач");
                System.out.printf("%-15s%-15s%-15s%-15s\n","ID","Имя участкового врача","Фамилия участкового врача", "ID мед. участка");
                doctorList.forEach(System.out::println);
            }
            default -> System.out.println("Неверный ввод");
        }

    }
    private void update(Session session){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите ID куратора:");
        int doctor_id = scanner.nextInt();
        System.out.println("Что вы хотите обновить?");
        System.out.println("1.Имя участкового врача");
        System.out.println("2.Фамилию участкового врача");
        System.out.println("3.ID участка");
        System.out.print(">");
        int i = scanner.nextInt();
        switch (i) {
            case 1 -> {
                System.out.print("Введите имя участкового врача:");
                scanner.nextLine();
                String doctor_firstname = scanner.next();
                try {
                    Doctor doctor = session.get(Doctor.class, doctor_id);
                    doctor.setFirstname(doctor_firstname);
                    session.save(doctor);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 2 -> {
                System.out.print("Введите фамилию участкового врача:");
                scanner.nextLine();
                String doctor_lastname = scanner.next();
                try {
                    Doctor doctor = session.get(Doctor.class, doctor_id);
                    doctor.setLastname(doctor_lastname);
                    session.save(doctor);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 3 -> {
                System.out.print("Введите ID участка:");
                scanner.nextLine();
                int region_id = scanner.nextInt();
                try {
                    Doctor doctor = session.get(Doctor.class, doctor_id);
                    doctor.setRegion(session.get(Region.class, region_id));
                    session.save(doctor);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            default -> System.out.println("Неверный ввод");
        }
    }

    private void delete(Session session){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите ID участкового врача:");
        int doctor_id = scanner.nextInt();
        try {
            Doctor doctor = session.get(Doctor.class, doctor_id);
            session.delete(doctor);
        }
        catch (Exception ex){
            System.out.println("Этой записи не существует");
        }
    }

    private void filter(Session session){
        System.out.println("\tПоля");
        System.out.println("1.ID участкового врача");
        System.out.println("2.Имя участкового врача");
        System.out.println("3.Фамилия участкового врача");
        System.out.println("4.ID мед. участка");
        System.out.print(">");
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        List<Doctor> doctorList;
        switch (i) {
            case 1 -> {
                System.out.print("Введите ID участкового врача:");
                int doctor_id = scanner.nextInt();
                try {
                    doctorList = session.createQuery("SELECT d FROM Doctor d WHERE doctor_id =" + doctor_id,
                            Doctor.class).getResultList();
                    System.out.println("Участковый врач");
                    System.out.printf("%-30s%-30s%-30s%-30s\n","ID","Имя участкового врача","Фамилия участкового врача", "ID мед. участка");
                    doctorList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 2 -> {
                System.out.print("Введите имя участкового врача:");
                String doctor_firstname = scanner.next();
                try {
                    doctorList = session.createQuery("SELECT с FROM Doctor с WHERE firstname =" + doctor_firstname,
                            Doctor.class).getResultList();
                    System.out.println("Участковый врач");
                    System.out.printf("%-30s%-30s%-30s%-30s\n","ID","Имя участкового врача","Фамилия участкового врача", "ID мед. участка");
                    doctorList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 3 -> {
                System.out.print("Введите фамилию участкового врача:");
                String doctor_lastname = scanner.next();
                try {
                    doctorList = session.createQuery("SELECT g FROM Doctor g WHERE doctor_lastname =" + doctor_lastname,
                            Doctor.class).getResultList();
                    System.out.println("Участковый врач");
                    System.out.printf("%-30s%-30s%-30s%-30s\n","ID","Имя участкового врача","Фамилия участкового врача", "ID мед. участка");
                    doctorList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 4 -> {
                System.out.print("Введите ID мед. участка:");
                int region_id = scanner.nextInt();
                try {
                    doctorList = session.createQuery("SELECT g FROM Doctor g WHERE region_id =" + region_id,
                            Doctor.class).getResultList();
                    System.out.println("Участковый врач");
                    System.out.printf("%-30s%-30s%-30s%-30s\n","ID","Имя участкового врача","Фамилия участкового врача", "ID мед. участка");
                    doctorList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            default -> System.out.println("Неверный ввод");
        }
    }

}
