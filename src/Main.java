import java.sql.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final String url = "jdbc:mysql://localhost:3306/mydb";
    private static final String username="root";
    private static final String password="Arshita@123";

    public static void main(String[] args) {
//        try {  //for retriving data from db and displaying
//
//                Class.forName("com.mysql.cj.jdbc.Driver");
//
//        }
//        catch (ClassNotFoundException e) {
//            System.out.println(e.getMessage());
//        }
//        try{
//            Connection connection = DriverManager.getConnection(url,username,password);
//            Statement statement = connection.createStatement();
//            String qury = "select * from student";
//            statement.executeQuery(qury);
//            ResultSet resultSet = statement.getResultSet(); // resultSet is interface
//            while(resultSet.next()){
//                int id = resultSet.getInt("id");
//                String name = resultSet.getString("name");
//                int age = resultSet.getInt("age");
//                double marks = resultSet.getDouble("marks");
//
//                System.out.println(id + " " + name + " " + age + " " + marks + " " + marks);
//            }
//        }


        //Insert in  dbs
//        try{
//            Connection connection = DriverManager.getConnection(url,username,password);
//            Statement statement = connection.createStatement();
//            String query = String.format("INSERT INTO student(id, name, age, marks) VALUES(%d, '%s', %d, %f)", 2, "Jack", 23, 78.5);
//
//            int rowAffected = statement.executeUpdate(query);  //we are doing on particular row not table
//
//            if(rowAffected>0){
//                System.out.println("Record Inserted successfully");
//            }else{
//                System.out.println("Record Not Inserted successfully");
//            }
//
//        }
        //update in db
//          try{
//              Connection connection = DriverManager.getConnection(url,username,password);
//              Statement statement = connection.createStatement();
//              String query = String.format("UPDATE student SET marks = %f WHERE id = %d ",50.5,1);
//
//             int rowAffected = statement.executeUpdate(query);
//             if(rowAffected > 0){
//                 System.out.println("Data updated successfully");;
//             }else{
//                 System.out.println("Data not updated");
//             }
//
//          }
        //delete in db
//            try{
//                Connection connection = DriverManager.getConnection(url,username,password);
//                Statement statement = connection.createStatement();
//
//                String query = String.format("DELETE FROM student WHERE id= %d",2);
//                int rowAffected = statement.executeUpdate(query);
//                if(rowAffected>0){
//                    System.out.println("Data deleted successfully");
//                }else{
//                    System.out.println("Data not deleted successfully");
//                }
//            }
        //preparedInterface
        //retrive data from db using preparedInterface
//        try{
//           Connection connection =  DriverManager.getConnection(url,username,password);
//           String query = "SELECT marks FROM student WHERE id = ?";
//           PreparedStatement preparedStatement = connection.prepareStatement(query);
//           preparedStatement.setInt(1, 1);
//
//           ResultSet resultSet = preparedStatement.executeQuery();
//           if(resultSet.next()){
//               double marks = resultSet.getDouble("marks");
//               System.out.println(marks);
//           }else{
//               System.out.println("marks not found");;
//           }
//        }

        //insert using prepared interface
//        try{
//            Connection connection = DriverManager.getConnection(url, username, password);
//            String query = "INSERT INTO student(id,name,age,marks) VALUES(?,?,?,?)";
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//
//            preparedStatement.setInt(1, 3);
//            preparedStatement.setString(2,"Marry");
//            preparedStatement.setInt(3,19);
//            preparedStatement.setDouble(4,96.5);
//
//            int rowAffected = preparedStatement.executeUpdate();
//            if(rowAffected > 0){
//                System.out.println("Data inserted Successfully");
//            }else{
//                System.out.println("Data insertion Failed");
//            }
//
//        }
        //update using preparediNTERFACE
//        try{
//            Connection connection = DriverManager.getConnection(url,username,password);
//            String query = "UPDATE student SET marks=? WHERE ID = ?" ;
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setDouble(1,67.8);
//            preparedStatement.setInt(2,1);
//            int rowAffected = preparedStatement.executeUpdate();
//            if(rowAffected>0){
//                System.out.println("Data updated successfully");
//            }else{
//                System.out.println("Data not updated");
//            }
//        }
        //Delete in db using preparedInterface
        try{
            Connection connection = DriverManager.getConnection(url,username,password);
            String querey = "DELETE FROM student WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(querey);
            preparedStatement.setInt(1,5);
            int rowAffected = preparedStatement.executeUpdate();
            if(rowAffected > 0){
                System.out.println("data deleted successfully");
            }else{
                System.out.println("data not deleted successfully");
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}

//statement interface

//prepaired statements (flexible and scalable) (for multiple insertion,deletion updations)
//we just give placeholder and can take input from user insread of giving it in code