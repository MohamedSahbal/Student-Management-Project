import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentPage extends JFrame {
    public StudentPage() {
        setTitle("All students");
        ImageIcon icon = new ImageIcon(ImagesUrl.APPLOGO);
        Image appIcon = icon.getImage();
        setIconImage(appIcon);
        setSize(800, 500);
        setLocationRelativeTo(null);

        try {
            Connection connection = DriverManager.getConnection(
                    DatabaseCommunication.URL, DatabaseCommunication.USER, DatabaseCommunication.PASSWORD
            );

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM students");

            // Get metadata for column names
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            String[] header = new String[columnCount];

            for (int i = 1; i <= columnCount; i++) {
                header[i - 1] = metaData.getColumnName(i);
            }

            // Get data from ResultSet
            List<String[]> rowDataList = new ArrayList<>();
            while (resultSet.next()) {
                String[] rowData = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = resultSet.getString(i);
                }
                rowDataList.add(rowData);
            }

            // Convert List<String[]> to String[][]
            String[][] names = new String[rowDataList.size()][];
            names = rowDataList.toArray(names);

            // Add Table
            JLabel searchLabel = new JLabel("Student Name ");
            JTextField searchField = new JTextField(20);
            JButton searchButton = createButton("Search");
            JButton deleteButton = createButton("Delete");

            JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            topPanel.setBackground(Color.white);

            topPanel.add(searchLabel);
            topPanel.add(searchField);
            topPanel.add(searchButton);
            topPanel.add(deleteButton);

            JTable table = new JTable(names, header);
            table.setBackground(Color.white);
            JScrollPane scrollPane = new JScrollPane(table);
            add(topPanel, BorderLayout.NORTH);
            add(scrollPane, BorderLayout.CENTER);

            searchButton.addActionListener(e -> {
                try {
                    // Convert string to integer
                    int searchedStudentId = Integer.parseInt(searchField.getText());

                    String query = "SELECT * FROM students WHERE id_student = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, searchedStudentId);

                    // Execute the query
                    ResultSet result = preparedStatement.executeQuery();

                    // Fetch the student data and display it in the table
                    List<String[]> studentDataList = new ArrayList<>();
                    while (result.next()) {
                        String[] rowData = new String[columnCount];
                        for (int i = 1; i <= columnCount; i++) {
                            rowData[i - 1] = result.getString(i);
                        }
                        studentDataList.add(rowData);
                    }

                    // تحويل List<String[]> إلى String[][]
                    String[][] studentData = new String[studentDataList.size()][];
                    studentData = studentDataList.toArray(studentData);

                    // Recreate the table with the retrieved student data
                    DefaultTableModel studentTableModel = new DefaultTableModel(studentData, header);
                    JTable studentTable = new JTable(studentTableModel);
                    studentTable.setBackground(Color.white);
                    JScrollPane studentScrollPane = new JScrollPane(studentTable);

                    // Remove all items from the frame
                    getContentPane().removeAll();

                    // Add items to the frame
                    add(topPanel, BorderLayout.NORTH);
                    add(studentScrollPane, BorderLayout.CENTER);

                    // Redraw the window to update the changes
                    revalidate();
                    repaint();

                } catch (SQLException | NumberFormatException exception) {
                    System.out.println("An error occurred: " + exception.getMessage());
                }
            });
            deleteButton.addActionListener(e -> {
                try {
                    int studentIdToDelete = Integer.parseInt(searchField.getText());
                    String deleteQuery = "DELETE FROM students WHERE id_student = ?";
                    PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
                    deleteStatement.setInt(1, studentIdToDelete);

                    int rowsAffected = deleteStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(this, "The student has been successfully deleted.");
                        // Re-fetch the data and update the table model
                        String selectAllQuery = "SELECT * FROM students";
                        Statement selectAllStatement = connection.createStatement();
                        ResultSet newResultSet = selectAllStatement.executeQuery(selectAllQuery);

                        List<String[]> studentDataList = new ArrayList<>();
                        while (newResultSet.next()) {
                            String[] rowData = new String[columnCount];
                            for (int i = 1; i <= columnCount; i++) {
                                rowData[i - 1] = newResultSet.getString(i);
                            }
                            studentDataList.add(rowData);
                        }

                        String[][] studentData = new String[studentDataList.size()][];
                        studentData = studentDataList.toArray(studentData);

                        DefaultTableModel studentTableModel = new DefaultTableModel(studentData, header);
                        JTable studentTable = new JTable(studentTableModel);
                        studentTable.setBackground(Color.white);
                        JScrollPane studentScrollPane = new JScrollPane(studentTable);

                        // Remove all items from the frame and add the new table
                        getContentPane().removeAll();
                        add(topPanel, BorderLayout.NORTH);
                        add(studentScrollPane, BorderLayout.CENTER);

                        // Redraw the window to update the changes
                        revalidate();
                        repaint();
                    } else {
                        JOptionPane.showMessageDialog(this, "The student was not found to delete.");
                    }
                    revalidate();
                    repaint();

                }catch (SQLException | NumberFormatException exception) {
                    System.out.println("An error occurred: " + exception.getMessage());
                }


            });


            setVisible(true);

        } catch (SQLException exception) {
            System.out.println("An error occurred during connection: " + exception.getMessage());
        }
    }




    public JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setOpaque(true); // Make the button opaque
        button.setBackground(Color.WHITE); // Set background color to white
        button.setForeground(Color.BLACK); // Set text color
        return button;
    }
}
