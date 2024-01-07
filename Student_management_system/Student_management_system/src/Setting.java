import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

public class Setting extends JFrame {
   public Setting(){
        setTitle("Setting page");
        ImageIcon icon = new ImageIcon(ImagesUrl.APPLOGO);
        getContentPane().setBackground(Color.white);
        Image appIcon = icon.getImage();
        setIconImage(appIcon);
        setSize(800, 500);
        setLocationRelativeTo(null);

       JLabel emailLabel = new JLabel("Email",SwingConstants.RIGHT);
       JLabel passwordLabel = new JLabel("Password",SwingConstants.RIGHT);
       JTextField emailField = new JTextField(30);
       JPasswordField passwordField = new JPasswordField(20);
       JButton addNewUserButton = new JButton("Add new user");
       addNewUserButton.setBackground(Color.WHITE);
       addNewUserButton.setForeground(Color.BLACK);

       JPanel addUserContentPanel = new JPanel(new GridLayout(1,4,10,10));
       addUserContentPanel.setBackground(Color.white);
       addUserContentPanel.add(emailLabel);
       addUserContentPanel.add(emailField);
       addUserContentPanel.add(passwordLabel);
       addUserContentPanel.add(passwordField);
       addUserContentPanel.setBounds(-20,100,700,25);
       addNewUserButton.setBounds(158,160,165,25);
       add(addUserContentPanel);
       add(addNewUserButton);
       setLayout(null);

       addNewUserButton.addActionListener(e -> {
           String getEmail = emailField.getText();
           String getPassword = new String(passwordField.getPassword());
           if (getEmail.isEmpty()){
               JOptionPane.showMessageDialog(this, "The email entry field is empty", "Error", JOptionPane.ERROR_MESSAGE);
           } else if (getPassword.isEmpty()) {
               JOptionPane.showMessageDialog(this, "The password entry field is empty", "Error", JOptionPane.ERROR_MESSAGE);
           }else {
               try {
                   Connection connection = DriverManager.getConnection(
                           DatabaseCommunication.URL, DatabaseCommunication.USER, DatabaseCommunication.PASSWORD
                   );
                   String sql = "INSERT INTO admin (email, password) VALUES (?, ?)";
                   try (PreparedStatement statement = connection.prepareStatement(sql)) {
                       statement.setString(1, getEmail);
                       statement.setString(2, getPassword);
                       int rowsInserted = statement.executeUpdate();
                       if (rowsInserted > 0) {
                           emailField.setText("");
                           passwordField.setText("");
                           JOptionPane.showMessageDialog(this, "A new user has been added");
                       } else {
                           System.out.println("No record added!");
                       }
                   }
               } catch (SQLException exception) {
                   System.out.println("An error occurred during connection: " + exception.getMessage());
               }
           }
       });
       setVisible(true);
    }
}
