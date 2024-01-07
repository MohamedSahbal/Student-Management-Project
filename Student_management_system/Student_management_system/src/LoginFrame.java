import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginFrame extends JFrame {
    public LoginFrame(){
        setTitle("Student management system");
        setSize(450, 450);
        getContentPane().setBackground(Color.white);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon(ImagesUrl.APPLOGO);
        Image appIcon = icon.getImage();
        setIconImage(appIcon);

        setLocationRelativeTo(null);
        setResizable(false);

        JLabel emailLabel = new JLabel("Email",SwingConstants.RIGHT);
        JLabel passwordLabel = new JLabel("Password",SwingConstants.RIGHT);

        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        JButton loginButton = new JButton("Submit");

        JLabel imageLabel = new JLabel();
        ImageIcon imageLogoDtu = new ImageIcon(ImagesUrl.DTULOGO);
        Image image = imageLogoDtu.getImage();
        Image newImg = image.getScaledInstance(290, 90,  Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(newImg));

        JPanel container1 = new JPanel(new GridLayout(2,2,10,10));
        container1.setBackground(Color.white);
        loginButton.setBackground(Color.WHITE);
        loginButton.setForeground(Color.BLACK);
        container1.add(emailLabel);
        container1.add(emailField);
        container1.add(passwordLabel);
        container1.add(passwordField);
        imageLabel.setBounds(60, 40, 290, 90);
        container1.setBounds(-60,170,400,65);
        loginButton.setBounds(145,260,195,25);
        add(imageLabel);
        add(container1);
        add(loginButton);
        setLayout(null);
        setVisible(true);

        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword()) ;

            if (email.isEmpty()){
                JOptionPane.showMessageDialog(LoginFrame.this, "The email entry field is empty", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (password.isEmpty()) {
                JOptionPane.showMessageDialog(LoginFrame.this, "The password entry field is empty", "Error", JOptionPane.ERROR_MESSAGE);
            }else {
                checkAuth(email,password);
            }
        });
        setVisible(true);
    }
    private void checkAuth (String email,String password){
        try {
            Connection connection = DriverManager.getConnection(
                    DatabaseCommunication.URL, DatabaseCommunication.USER,DatabaseCommunication.PASSWORD
            );

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM admin");

            while (resultSet.next()) {
                int id = resultSet.getInt("id_admin");
                String getEmailFromDatabase = resultSet.getString("email");
                String getPasswordFromDatabase = resultSet.getString("password");

                if(getEmailFromDatabase.equals(email) && !getPasswordFromDatabase.equals(password)){
                    JOptionPane.showMessageDialog(LoginFrame.this, "The password is incorrect", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
                } else if (!getEmailFromDatabase.equals(email) && !getPasswordFromDatabase.equals(password)) {
                    JOptionPane.showMessageDialog(LoginFrame.this, "Account not found", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
                } else if(getEmailFromDatabase.equals(email)){
                    hideLoginFrame();
                    HomeFrame homeFrame = new HomeFrame();
                    homeFrame.showHomeFrame(id,email);
                    break;
                }
            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException exception) {
            System.out.println("An error occurred during connection: " + exception.getMessage());
        }
    }
    private void hideLoginFrame(){
        setVisible(false);
    }
}
