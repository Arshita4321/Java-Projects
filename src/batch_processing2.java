//batch processing using prepared interface

import java.sql.*;
import java.util.Scanner;

public class batch_processing2 {

    private static final String url = "jdbc:mysql://localhost:3306/mydb";
    private static final String username="root";
    private static final String password="Arshita@123";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (Connection connection = DriverManager.getConnection(url,username,password)){
            System.out.println("Connected to database");
            String query = "INSERT INTO student(id,name,age,marks) VALUES(?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            System.out.println("How many students do you want to add: ");
            int n = scanner.nextInt();
            for(int i = 1; i<=n;i++){
                System.out.println("Enter details for students: ");
                System.out.println("Id: ");
                int id = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Name: ");
                String name = scanner.nextLine();

                System.out.print("Age: ");
                int age = scanner.nextInt();
                System.out.print("Marks: ");
                double marks = scanner.nextDouble();
                scanner.nextLine();

                preparedStatement.setInt(1, id);
                preparedStatement.setString(2, name);
                preparedStatement.setInt(3, age);
                preparedStatement.setDouble(4, marks);

                preparedStatement.addBatch();
            }
           int[] result =  preparedStatement.executeBatch();     //executeBatch -- store int type array
            System.out.println(result.length + " students added successfully");
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
