package logics;

import entities.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Scanner;

public class RegionLogic {
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
        System.out.print("Введите название мед. участка:");
        String region_name = scanner.next();
        try {
            Region region = new Region(region_name);
            session.save(region);
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
                List<Region> regionList = session.createQuery("SELECT r FROM Region r",
                        Region.class).getResultList();
                System.out.println("Участок");
                System.out.printf("%-15s%-15s\n","ID","Название мед. участка");
                regionList.forEach(System.out::println);
            }
            default -> System.out.println("Неверный ввод");
        }

    }
    private void update(Session session){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите ID участка:");
        int region_id = scanner.nextInt();
        System.out.println("Что вы хотите обновить?");
        System.out.println("1.Название участка");
        System.out.print(">");
        int i = scanner.nextInt();
        switch (i) {
            case 1 -> {
                System.out.print("Введите название мед. участка:");
                scanner.nextLine();
                String region_name = scanner.next();
                try {
                    Region region = session.get(Region.class, region_id);
                    region.setDescription(region_name);
                    session.save(region);
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
        System.out.print("Введите ID участка:");
        int region_id = scanner.nextInt();
        try {
            Region region = session.get(Region.class, region_id);
            session.delete(region);
        }
        catch (Exception ex){
            System.out.println("Этой записи не существует");
        }
    }

    private void filter(Session session){
        System.out.println("\tПоля");
        System.out.println("1.ID мед. участка");
        System.out.println("2.Название мед. участка");
        System.out.println("3.ФИО заведующего мед. участка");
        System.out.print(">");
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        List<Region> regionList;
        switch (i) {
            case 1 -> {
                System.out.print("Введите ID мед. участка:");
                int region_id = scanner.nextInt();
                try {
                    regionList = session.createQuery("SELECT g FROM Region g WHERE region_id =" + region_id,
                            Region.class).getResultList();
                    System.out.println("Мед. участок");
                    System.out.printf("%-30s%-30s%-30s\n","ID","Название мед. участка","ФИО заведующего мед. участка");
                    regionList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 2 -> {
                System.out.print("Введите название мед. участка:");
                int region_name = scanner.nextInt();
                try {
                    regionList = session.createQuery("SELECT d FROM Region d WHERE region_name =" + region_name,
                            Region.class).getResultList();
                    System.out.println("Мед. участок");
                    System.out.printf("%-30s%-30s%-30s\n","ID","Название мед. участка","ФИО заведующего мед. участка");
                    regionList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 3 -> {
                System.out.print("Введите ФИО заведующего мед. участка:");
                int chairman_name = scanner.nextInt();
                try {
                    regionList = session.createQuery("SELECT d FROM Region d WHERE chairman_name =" + chairman_name,
                            Region.class).getResultList();
                    System.out.println("Мед. участок");
                    System.out.printf("%-30s%-30s%-30s\n","ID","Название мед. участка","ФИО заведующего мед. участка");
                    regionList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            default -> System.out.println("Неверный ввод");
        }
    }

}
