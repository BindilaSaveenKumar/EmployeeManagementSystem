import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Employee {
    private int id;
    private String name;
    private double salary;

    public Employee(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Salary: $" + salary;
    }
}

public class EmployeeManagementSystem {
    private static final String FILE_NAME = "employee_data.txt";
    private static final ArrayList<Employee> employees = new ArrayList<>();

    public static void main(String[] args) {
        loadEmployeeData();

        Scanner scanner = new Scanner(System.in);

        int choice;
        do {
            System.out.println("\nEmployee Management System Menu:");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Update Employee");
            System.out.println("4. Search Employee");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addEmployee(scanner);
                    break;
                case 2:
                    viewEmployees();
                    break;
                case 3:
                    updateEmployee(scanner);
                    break;
                case 4:
                    searchEmployee(scanner);
                    break;
                case 5:
                    saveEmployeeData();
                    System.out.println("Exiting Employee Management System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        } while (choice != 5);

        scanner.close();
    }

    private static void addEmployee(Scanner scanner) {
        System.out.print("Enter employee ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter employee name: ");
        String name = scanner.nextLine();
        System.out.print("Enter employee salary: $");
        double salary = scanner.nextDouble();

        Employee newEmployee = new Employee(id, name, salary);
        employees.add(newEmployee);

        System.out.println("Employee added successfully!");
    }

    private static void viewEmployees() {
        System.out.println("\nList of Employees:");
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            for (Employee employee : employees) {
                System.out.println(employee);
            }
        }
    }

    private static void updateEmployee(Scanner scanner) {
        System.out.print("Enter the ID of the employee to update: ");
        int searchId = scanner.nextInt();
        int index = findEmployeeIndexById(searchId);

        if (index == -1) {
            System.out.println("Employee with ID " + searchId + " not found.");
        } else {
            System.out.print("Enter new name for the employee: ");
            scanner.nextLine(); // Consume newline
            String newName = scanner.nextLine();
            System.out.print("Enter new salary for the employee: $");
            double newSalary = scanner.nextDouble();

            Employee updatedEmployee = new Employee(searchId, newName, newSalary);
            employees.set(index, updatedEmployee);

            System.out.println("Employee updated successfully!");
        }
    }

    private static void searchEmployee(Scanner scanner) {
        System.out.print("Enter the ID of the employee to search: ");
        int searchId = scanner.nextInt();
        int index = findEmployeeIndexById(searchId);

        if (index == -1) {
            System.out.println("Employee with ID " + searchId + " not found.");
        } else {
            System.out.println("Employee found:\n" + employees.get(index));
        }
    }

    private static int findEmployeeIndexById(int id) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    private static void loadEmployeeData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                double salary = Double.parseDouble(parts[2]);
                employees.add(new Employee(id, name, salary));
            }
            System.out.println("Employee data loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading employee data: " + e.getMessage());
        }
    }

    private static void saveEmployeeData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Employee employee : employees) {
                writer.write(employee.getId() + "," + employee.getName() + "," + employee.getSalary());
                writer.newLine();
            }
            System.out.println("Employee data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving employee data: " + e.getMessage());
        }
    }
}
