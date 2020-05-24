package ru.geekbrains;

import org.hibernate.cfg.Configuration;
import ru.geekbrains.persist.Customer;
import ru.geekbrains.persist.CustomerProducts;
import ru.geekbrains.persist.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static EntityManagerFactory emFactory = new Configuration().configure("hibernate.cfg.xml")
            .buildSessionFactory();
    private static EntityManager entityManager = emFactory.createEntityManager();

    public static void main(String[] args) {
        int userEnty;
        while ((userEnty = userMenuSelection()) != 7) {
            scanner.nextLine();
            switch (userEnty) {
                case 1:
                    save(new Customer(getStringFromUser("Введите имя клиента")));
                    break;
                case 2:
                    save(new Product(
                            getStringFromUser("Введите название товара"),
                            getFloatFromUser("Введите стоимость товара")
                    ));
                    break;
                case 3:
                    save(new CustomerProducts(
                            (Customer) findRecord(
                                    "Customer.findByName",
                                    "name",
                                    getStringFromUser("Введите имя клиента")
                            ), (Product) findRecord(
                                    "Product.findByName",
                                    "name",
                                    getStringFromUser("Введите название товара")
                            )
                    ));
                    break;
                case 4:
                    printRecords(findRecords(
                            "CustomerProducts.getCustomersByProduct",
                            "name",
                            getStringFromUser("Введите название товара")
                    ));
                    break;
                case 5:
                    delete(findRecord(
                                    "Product.findByName",
                                    "name",
                                    getStringFromUser("Введите название товара")
                    ));
                    break;
                case 6:
                    delete(findRecord(
                            "Customer.findByName",
                            "name",
                            getStringFromUser("Введите имя клиента")
                    ));
                    break;
            }
        }
    }

    private static void delete(Object object) {
        entityManager.getTransaction().begin();
        entityManager.remove(object);
        entityManager.getTransaction().commit();
    }

    private static void printRecords(List<Object> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    private static List findRecords(String namedQuery, String parameter, String value) {
        return entityManager.createNamedQuery(namedQuery).setParameter(parameter, value).getResultList();
    }

    private static Object findRecord(String namedQuery, String parameter, String value) {
        return entityManager.createNamedQuery(namedQuery).setParameter(parameter, value).getSingleResult();
    }

    private static float getFloatFromUser(String message) {
        System.out.println(message);
        while (true) {
            try {
                return scanner.nextFloat();
            } catch (Exception e) {
                System.out.println("Введите корректное дробное число");
                scanner.nextLine();
            }
        }
    }

    private static String getStringFromUser(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }

    private static void save(Object object) {
        entityManager.getTransaction().begin();
        entityManager.persist(object);
        entityManager.getTransaction().commit();
    }

    private static int userMenuSelection() {
        int userEntry;
        System.out.println("Выберите действие:\n1. Добавить клиента;\n2. Добавить товар;\n3. Добавить покупку" +
                "\n4. Поиск клиентов, купивших определенный товар;\n5. Удалить товар;\n6. Удалить клиента;\n7. Выход.");
        while (true) {
            try {
                userEntry = scanner.nextInt();
                if (userEntry >= 1 && userEntry <= 7) {
                    return userEntry;
                }
                System.out.println("Введен некорректный пункт меню!");
            } catch (Exception e) {
                System.out.println("Необходимо ввести число от 1 до 7");
                scanner.nextLine();
            }
        }
    }
}
