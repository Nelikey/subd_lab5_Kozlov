package logics;

import entities.Patient;
import entities.Record;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.awt.*;
import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class RecordLogic {
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
        System.out.print("Введите диагноз:");
        String desease = scanner.next();
        System.out.print("Введите дату выздоровления:");
        String _recoverydate = scanner.next();
        Date recoverydate = Date.valueOf(_recoverydate);
        System.out.print("Введите ID пациента:");
        int patient_id = scanner.nextInt();
        try {
            Record record = new Record(desease, recoverydate, session.get(Patient.class, patient_id));
            session.save(record);
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
                List<Record> recordList = session.createQuery("SELECT g FROM Record g",
                        Record.class).getResultList();
                System.out.println("Запись о заболевании");
                System.out.printf("%-15s%-15s%-15s%-15s\n","ID","Диагноз","Дата выздоровления","ID пациента");
                recordList.forEach(System.out::println);
            }
            default -> System.out.println("Неверный ввод");
        }

    }
    private void update(Session session){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите ID записи:");
        int record_id = scanner.nextInt();
        System.out.println("Что вы хотите обновить?");
        System.out.println("1.Диагноз");
        System.out.println("2.Дата выздоровления");
        System.out.println("3.ID пациент");
        System.out.print(">");
        int i = scanner.nextInt();
        switch (i) {
            case 1 -> {
                System.out.print("Введите диагноз:");
                scanner.nextLine();
                String desease = scanner.next();
                try {
                    Record record = session.get(Record.class, record_id);
                    record.setDesease(desease);
                    session.save(record);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 2 -> {
                System.out.print("Введите дату выздоровления:");
                scanner.nextLine();
                String _recoverydate = scanner.next();
                Date recoverydate = Date.valueOf(_recoverydate);
                try {
                    Record record = session.get(Record.class, record_id);
                    record.setRecoveryDate(recoverydate);
                    session.save(record);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 3 -> {
                System.out.print("Введите ID пациента:");
                scanner.nextLine();
                int patient_id = scanner.nextInt();
                Patient patient = session.get(Patient.class, patient_id);
                try {
                    Record record = session.get(Record.class, record_id);
                    record.setPatient(patient);
                    session.save(record);
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
        System.out.print("Введите ID записи:");
        int record_id = scanner.nextInt();
        try {
            Record record = session.get(Record.class, record_id);
            session.delete(record);
        }
        catch (Exception ex){
            System.out.println("Этой записи не существует");
        }
    }

    private void filter(Session session){
        System.out.println("\tПоля");
        System.out.println("1.ID записи");
        System.out.println("2.Диагноз");
        System.out.println("3.Дата выздоровления");
        System.out.println("4.Пациент");
        System.out.print(">");
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        List<Record> recordList;
        switch (i) {
            case 1 -> {
                System.out.print("Введите ID записи:");
                int record_id = scanner.nextInt();
                try {
                    recordList = session.createQuery("SELECT g FROM Record g WHERE record_id =" + record_id,
                            Record.class).getResultList();
                    System.out.println("Запись о заболевании");
                    System.out.printf("%-30s%-30s%-30s%-30s\n","ID","Диагноз","Дата выздоровления","ID пациента");
                    recordList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 2 -> {
                System.out.print("Введите диагноз:");
                String desease = scanner.next();
                try {
                    recordList = session.createQuery("SELECT g FROM Record g WHERE desease =" + desease,
                            Record.class).getResultList();
                    System.out.println("Запись о заболевании");
                    System.out.printf("%-30s%-30s%-30s%-30s\n","ID","Диагноз","Дата выздоровления","ID пациента");
                    recordList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 3 -> {
                System.out.print("Введите дату выздоровления:");
                String _recoverydate = scanner.next();
                Date recoverydate = Date.valueOf(_recoverydate);
                try {
                    recordList = session.createQuery("SELECT g FROM Record g WHERE recoverydate =" + recoverydate,
                            Record.class).getResultList();
                    System.out.println("Запись о заболевании");
                    System.out.printf("%-30s%-30s%-30s%-30s\n","ID","Диагноз","Дата выздоровления","ID пациента");
                    recordList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 4 -> {
                System.out.print("Введите ID пациента:");
                int patient_id = scanner.nextInt();
                try {
                    recordList = session.createQuery("SELECT g FROM Record g WHERE patient_id =" + patient_id,
                            Record.class).getResultList();
                    System.out.println("Запись о заболевании");
                    System.out.printf("%-30s%-30s%-30s%-30s\n","ID","Диагноз","Дата выздоровления","ID пациента");
                    recordList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            default -> System.out.println("Неверный ввод");
        }
    }

}
