import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

// Student class to store student details
class Student implements Serializable {
    private int id;
    private String name;
    private int age;
    private String grade;

    public Student(int id, String name, int age, String grade) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.grade = grade;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Student ID: " + id + ", Name: " + name + ", Age: " + age + ", Grade: " + grade;
    }
}

// StudentManagement class for managing students
class StudentManagement {
    private ArrayList<Student> students;
    private Scanner scanner = new Scanner(System.in);
    private final String FILE_NAME = "students.dat";  // File to store data

    public StudentManagement() {
        students = loadFromFile();  // Load existing data from file
    }

    // Add a new student
    public void addStudent() {
        System.out.println("Enter Student ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline

        System.out.println("Enter Student Name: ");
        String name = scanner.nextLine();

        System.out.println("Enter Student Age: ");
        int age = scanner.nextInt();

        System.out.println("Enter Student Grade: ");
        String grade = scanner.next();

        Student student = new Student(id, name, age, grade);
        students.add(student);
        System.out.println("Student added successfully!");
        saveToFile();  // Save data to file after adding
    }

    // View all students
    public void viewAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }

    // Update a student record
    public void updateStudent() {
        System.out.println("Enter Student ID to update: ");
        int id = scanner.nextInt();

        for (Student student : students) {
            if (student.getId() == id) {
                System.out.println("Enter new Name: ");
                String newName = scanner.next();
                student.setName(newName);

                System.out.println("Enter new Age: ");
                int newAge = scanner.nextInt();
                student.setAge(newAge);

                System.out.println("Enter new Grade: ");
                String newGrade = scanner.next();
                student.setGrade(newGrade);

                System.out.println("Student updated successfully!");
                saveToFile();  // Save data to file after updating
                return;
            }
        }
        System.out.println("Student not found.");
    }

    // Delete a student
    public void deleteStudent() {
        System.out.println("Enter Student ID to delete: ");
        int id = scanner.nextInt();

        students.removeIf(student -> student.getId() == id);
        System.out.println("Student removed successfully.");
        saveToFile();  // Save data to file after deletion
    }

    // Save students data to file
    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(students);
            System.out.println("Data saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    // Load students data from file
    private ArrayList<Student> loadFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (ArrayList<Student>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No existing data found. Starting fresh.");
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}

// Main class to run the program
public class Main {
    public static void main(String[] args) {
        StudentManagement management = new StudentManagement();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Student Management System ---");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    management.addStudent();
                    break;
                case 2:
                    management.viewAllStudents();
                    break;
                case 3:
                    management.updateStudent();
                    break;
                case 4:
                    management.deleteStudent();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
