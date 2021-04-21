package studentinfo;

import java.util.InputMismatchException;
import java.util.Scanner;
import javax.persistence.*;
import java.util.List;

public class Menu {
    Scanner scan = new Scanner(System.in);
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Vadsomhelst");
    public void mainMenu() {
        System.out.println("Student information registry");
        System.out.println("1. Add Student Member");
        System.out.println("2. Remove Student member");
        System.out.println("3. Edit Student member");
        System.out.println("4. Find Student member");
        System.out.println("0. Exit");
        int choice = scanInt();
        switch (choice) {
            case 1:
                addStudent();
                break;
            case 2:
                removeStudent();
                break;
            case 3:
                editStudent();
                break;
            case 4:
                findStudent();
                break;
            case 0:
                System.exit(0);
            default:
                System.out.println("Incorrect selection");
        }

    }

    private void findStudent() {
        EntityManager em = emf.createEntityManager();
        System.out.println("Find student based on:");
        System.out.println("1. Name");
        System.out.println("2. Grade");
        System.out.println("3. ID");
        int choice = scanInt();
        String suffix;
        switch (choice) {
            case 1:
                System.out.print("Name to find: ");
                String name = scan.nextLine();
                suffix = "name = '" + name + "'";
                break;
            case 2:
                System.out.print("Grade to find: ");
                int Grade = scanInt();
                suffix = "Grade = " + Grade;
                break;
            case 3:
                System.out.print("ID to find: ");
                int id = scanInt();
                suffix = "id = " +id;
                break;
            default:
                System.out.println("Invalid selection");
                return;
        }
        String query = "SELECT stu FROM Students stu WHERE " + suffix;
        Query whom = em.createQuery(query);
        List<Students> Students = whom.getResultList();
        for (Students stu: Students) {
            System.out.println(stu);
        }
    }

    private void editStudent() {
        EntityManager em = emf.createEntityManager();
        showAll();
        System.out.print("ID of student to edit: ");
        int Id = scanInt();
        Students stu = em.find(Students.class, Id);
        if (stu!=null) {
            System.out.println("Student found:");
            System.out.println(stu);
            System.out.println("Please select variable to modify");
            System.out.println("1. Name");
            System.out.println("2. Grade");
            int choice = scanInt();
            em.getTransaction().begin();
            switch (choice) {
                case 1:
                    System.out.print("New name: ");
                    String name = scan.nextLine();
                    stu.setName(name);
                    break;
                case 2:
                    System.out.print("New age: ");
                    int age = scanInt();
                    stu.setGrade(age);
                    break;
                default:
                    System.out.println("Invalid selection");
            }
            em.getTransaction().commit();
        } else {
            System.out.println("Student not found");
        }
        em.close();
    }

    private void removeStudent() {
        EntityManager em = emf.createEntityManager();
        showAll();
        System.out.print("ID of Student to remove: ");
        int Id = scanInt();
        Students stu = em.find(Students.class, Id);
        if (stu!= null) {
            em.getTransaction().begin();
            em.remove(stu);
            em.getTransaction().commit();
            System.out.println(stu + " removed from registry");
        } else {
            System.out.println("No such student in the registry");
        }
        em.close();
    }

    private void showAll() {
        EntityManager em = emf.createEntityManager();
        Query what = em.createQuery("SELECT stu FROM Students stu");
        List<Students> result = what.getResultList();
        for (Students stu: result) {
            System.out.println(stu);
        }
    }

    private void addStudent() {
        EntityManager em = emf.createEntityManager();
        System.out.print("Name of new student: ");
        String name = scan.nextLine();
        System.out.print("Grade: ");
        int age = scanInt();
        Students x = new Students(name, age);
        em.getTransaction().begin();
        em.persist(x);
        em.getTransaction().commit();
        em.close();
    }

    private int scanInt() {
        int scannedInt;
        while (true) {
            try {
                scannedInt=scan.nextInt();
                scan.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Please use numerical inputs");
                scan.nextLine();
            }
        }
        return scannedInt;
    }
}
