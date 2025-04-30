//batch processing using statement interface

import java.sql.*;
import java.util.Scanner;

public class batch_processing {
    private static final String url = "jdbc:mysql://localhost:3306/mydb";
    private static final String username="root";
    private static final String password="Arshita@123";

    public static void main(String[] args) {
        try {  //for retriving data from db and displaying

                Class.forName("com.mysql.cj.jdbc.Driver");

        }
        catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try{
            Connection connection = DriverManager.getConnection(url,username,password);
            Statement statement = connection.createStatement();
            Scanner scanner = new Scanner(System.in);

            while(true){
                System.out.println("Enter student id: ");
                int id = scanner.nextInt();

                System.out.println("Enter student name: ");
                String name = scanner.next();

                System.out.println("Enter student age: ");
                int age = scanner.nextInt();

                System.out.println("Enter student marks: ");
                double marks = scanner.nextDouble();

                String query = String.format("INSERT INTO student(id,name,age,marks) VALUES(%d,'%s',%d,%f)",id,name,age,marks);
                statement.addBatch(query);

                System.out.println("Enter more details: (Y/N)");
                String choice = scanner.next();

                if(choice.equalsIgnoreCase("N")){
                    break;
                }

            }
            int[] results = statement.executeBatch();
            System.out.println(results.length + " record(s) inserted successfully.");
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
