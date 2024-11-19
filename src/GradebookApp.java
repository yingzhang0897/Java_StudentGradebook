
//just for testing
import java.util.ArrayList;
import java.util.Collections;//sorting ArrayList numerically
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Abstract class for gradebook functionality
abstract class Gradebook {
    protected HashMap<String, ArrayList<HashMap<String, Integer>>> studentGrades = new HashMap<>();

    public abstract void addStudent(String studentName);

    public abstract void addGrade(String studentName, String subject, int grade);

    public abstract double calculateAverage(String studentName);
}

// Concrete class for managing gradebook
class StudentGradebook extends Gradebook {
    @Override
    public void addStudent(String studentName) {
        if (!studentGrades.containsKey(studentName)) {
            studentGrades.put(studentName, new ArrayList<>());
            System.out.println("Student " + studentName + " added successfully.");
        } else {
            System.out.println("Student " + studentName + " already exists.");
        }
    }

    @Override
    public void addGrade(String studentName, String subject, int grade) {
        if (studentGrades.containsKey(studentName)) {
        	HashMap<String, Integer> gradeEntry = new HashMap<>();
        	gradeEntry.put(subject, grade);
            studentGrades.get(studentName).add(gradeEntry);
            System.out.printf("Added grade %d for %s to %s.%n", grade, subject, studentName);
        } else {
            System.out.println("Student " + studentName + " does not exist. Add the student first.");
        }
    }

    @Override
    public double calculateAverage(String studentName) {
        if (studentGrades.containsKey(studentName)) {
            ArrayList<HashMap<String, Integer>> grades = studentGrades.get(studentName);
            if (grades.isEmpty()) {
                return 0.0;
            }
            int total = 0;
            int count = 0;
            for (HashMap<String, Integer> gradeEntry: grades) {
                for (int grade: gradeEntry.values()) {
                	total += grade;
                	count ++;                	
                }
            }
            return (double) total / count;
        } else {
            System.out.println("Student " + studentName + " does not exist.");
            return -1;
        }
    }

    public void displayGradebook() {
        System.out.println("\nGradebook:");
        for (String student : studentGrades.keySet()) {
        	ArrayList<HashMap<String, Integer>> gradesList = studentGrades.get(student);
            System.out.println(student + ": ");
            for (HashMap<String, Integer> gradeEntry: gradesList) {
            	for (Map.Entry<String, Integer> entry: gradeEntry.entrySet()) {
            		System.out.println(" Subject: " + entry.getKey() + ", Grade: " + entry.getValue());
            	}
            }
           
        }
    }
}

// Interface for gradebook operations
interface GradebookOperations {
    void start();
}

// Main class implementing the interface
public class GradebookApp implements GradebookOperations {
    private final StudentGradebook gradebook = new StudentGradebook();
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void start() {
        while (true) {
            System.out.println("\nGradebook Menu:");
            System.out.println("1. Add Student");
            System.out.println("2. Add Grade");
            System.out.println("3. Calculate Average");
            System.out.println("4. Display Gradebook");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter student name: ");
                    String name = scanner.nextLine();
                    gradebook.addStudent(name);
                }
                case 2 -> {
                    System.out.print("Enter student name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter subject: ");
                    String subject = scanner.nextLine();
                    System.out.print("Enter grade: ");
                    int grade = scanner.nextInt();
                    gradebook.addGrade(name, subject, grade);
                }
                case 3 -> {
                    System.out.print("Enter student name: ");
                    String name = scanner.nextLine();
                    double average = gradebook.calculateAverage(name);
                    if (average >= 0) {
                        System.out.printf("Average grade for %s: %.2f%n", name, average);
                    }
                }
                case 4 -> gradebook.displayGradebook();
                case 5 -> {
                    System.out.println("Exiting Gradebook. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    public static void main(String[] args) {
        GradebookApp app = new GradebookApp();
        app.start();
    }
}
