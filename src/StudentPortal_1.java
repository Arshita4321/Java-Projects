// JDBC Setup
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;


class DBConnection{
    private static final String url = "jdbc:mysql://localhost:3306/mydb";
    private static final String username = "root";
    private static final String password = "Arshita@123";

    public static Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(url,username,password);
    }

}

//class for all db operations
class StudentDB{
    //addStudent
    void addStudent(int id,String name,int age,double marks) throws SQLException {
        String query = "INSERT INTO student(id,name,age,marks) VALUES(?,?,?,?)";

        try(Connection connection = DBConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            preparedStatement.setString(2,name);
            preparedStatement.setInt(3,age);
            preparedStatement.setDouble(4,marks);
            preparedStatement.executeUpdate();
        }
    }
    //getSTUDENTS

    ArrayList<String> getAllStudents() throws SQLException{
        ArrayList<String> students = new ArrayList<>();
        String query = "SELECT * FROM student";
        try(Connection connection = DBConnection.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query); //return table that will store in resultset
            while(resultSet.next()){
                String record = resultSet.getInt("id") + " | " + resultSet.getString("name")
                        + " | " + resultSet.getInt("age") + " | " + resultSet.getDouble("marks");
                students.add(record);
            }
        }
        return students;
    }

    //deleteStudent
    void deleteStudent(int id) throws SQLException {
        String query = "DELETE FROM student WHERE id = ?";
        try(Connection connection = DBConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    //searchStudent
    String searchStudent(int id) throws SQLException {
        String query = "SELECT * FROM student WHERE id = ?";
        try(Connection connection = DBConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt("id") + " | " +  resultSet.getString("name") + " | " + resultSet.getInt("age") + " | " + resultSet.getDouble("marks");
            }
        }
        return "Student not found";
    }

    //UpdateStudent

    void updateStudents(int newId, String newName, int newAge, double newMarks) throws SQLException {
        String query = "UPDATE student SET name = ? , age=?, marks = ? WHERE id = ?";
        try (Connection connection = DBConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, newAge);
            preparedStatement.setDouble(3, newMarks);
            preparedStatement.setInt(4, newId);
            preparedStatement.executeUpdate();
        }
    }
}
//Swing gui (one class responsible for behaviour working)

class StudentPortalGUI extends JFrame {

    private final StudentDB dbo = new StudentDB();
    private final JTextArea outputArea = new JTextArea();


    public StudentPortalGUI() {
        setTitle("Student Portal");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //top buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 5));
        JButton viewButton = new JButton("View All Students");
        buttonPanel.add(viewButton);

        JButton addButton = new JButton("Add Student");
        buttonPanel.add(addButton);

        JButton deleteButton = new JButton("Delete");
        buttonPanel.add(deleteButton);

        JButton searchButton = new JButton("Search");
        buttonPanel.add(searchButton);

        JButton updateButton = new JButton("Update ");
        buttonPanel.add(updateButton);

        //output area
        outputArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(outputArea);

        add(buttonPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        //button actions

        viewButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ArrayList<String> students = dbo.getAllStudents();
                    outputArea.setText("student list : \n");
                    students.forEach(s -> outputArea.append(s + "\n"));

                } catch (SQLException exc) {
                    System.out.println(exc.getMessage());
                }
            }
        });

        //add button
        addButton.addActionListener(e -> {
            JTextField id = new JTextField();
            JTextField name = new JTextField();
            JTextField age = new JTextField();
            JTextField marks = new JTextField();
            Object[] fields = {
                    "Id: ", id,
                    "Name: ", name,
                    "Age: ", age,
                    "Marks: ", marks
            };
            int result = JOptionPane.showConfirmDialog(this, fields, "Add Students", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {   //ok_option -- enum value
                try {
                    dbo.addStudent(Integer.parseInt(id.getText()), name.getText(), Integer.parseInt(age.getText()), Double.parseDouble(marks.getText()));
                    outputArea.setText("Student Added Successfully");
                } catch (SQLException exc) {
                    System.out.println(exc.getMessage());
                }
            }

        });

        //deleteButton
        deleteButton.addActionListener(e->{
            String id = JOptionPane.showInputDialog(this, "Enter id to delete: ");

            if(id != null && !id.isEmpty()){
                try{
                    dbo.deleteStudent(Integer.parseInt(id));
                    outputArea.setText("Student Deleted Successfully");

                }catch(SQLException exc){
                    throw new RuntimeException(exc);
                }
            }

        });

        //searchButton
        searchButton.addActionListener(e-> {
            String id = JOptionPane.showInputDialog(this, "Enter id to search: ");
            try{
                if(id != null && !id.trim().isEmpty()){
                    //search student and display it on gui
                    String student = dbo.searchStudent(Integer.parseInt(id));
                    if(student != null){
                        outputArea.setText("Student Found Successfully\n"+ student);
                    }else{
                        outputArea.setText("Student Not Found");
                    }
                }
            }catch(SQLException exc){
                throw new RuntimeException(exc);
            }
            catch(NumberFormatException exc){
                outputArea.setText("Invalid Id. Please enter a valid number ");
            }

        });

        //update button
        updateButton.addActionListener(e->{
            JTextField id = new JTextField();
            JTextField name = new JTextField();
            JTextField age = new JTextField();
            JTextField marks = new JTextField();

            Object[] fields = {
                    "Id: (to update) ", id,
                    "New Name: ", name,
                    "New Age: ", age,
                    "New Marks: ", marks
            };
            int result = JOptionPane.showConfirmDialog(this, fields, "Update Students", JOptionPane.OK_CANCEL_OPTION);
            if(result == JOptionPane.OK_OPTION){
                try {
                    dbo.updateStudents(Integer.parseInt(id.getText()), name.getText(), Integer.parseInt(age.getText()), Double.parseDouble(marks.getText()));
                    outputArea.setText("Student Updated Successfully");
                }catch(SQLException exc){
                    throw new RuntimeException(exc);
                }
            }
        });

        setVisible(true);
    }
}


public class StudentPortal_1 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentPortalGUI::new);

    }
}
