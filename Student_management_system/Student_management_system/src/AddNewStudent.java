import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AddNewStudent extends JFrame{
   public AddNewStudent(){
       setTitle("Add new student");
       ImageIcon icon = new ImageIcon(ImagesUrl.APPLOGO);
       Image appIcon = icon.getImage();
       getContentPane().setBackground(Color.white);
       setIconImage(appIcon);
       setSize(800, 500);
       setLocationRelativeTo(null);
       String[] header = {"information technology", "Mechatronics", "Autotronics","Renewable energy"};
       JPanel panelContent = new JPanel(new GridLayout(3,2,10,25));
       panelContent.setBackground(Color.white);

       JLabel stuNameLabel ,stuPhoneLabel,stuAddressLabel,stuProgramTypeLabel,stuEmailLabel,stuGenderLabel;
       JTextField stuNameField ,stuPhoneField,stuAddressField,stuEmailField;
       JPanel genderPanel = new JPanel();
       JRadioButton stuGenderFieldMale = new JRadioButton("Male");
       JRadioButton stuGenderFieldFemale = new JRadioButton("Female");
       stuGenderFieldMale.setSelected(true);
       ButtonGroup genderGroup = new ButtonGroup();
       genderGroup.add(stuGenderFieldMale);
       genderGroup.add(stuGenderFieldFemale);
       genderPanel.add(stuGenderFieldMale);
       genderPanel.add(stuGenderFieldFemale);
       genderPanel.setBackground(Color.white);
       stuGenderFieldMale.setBackground(Color.white);
       stuGenderFieldFemale.setBackground(Color.white);

       JComboBox<String> stuProgramTypeField = new JComboBox<String>(header);
       stuProgramTypeField.setBackground(Color.white);
       JButton btSubmit = new JButton("Add new student");
       btSubmit.setBackground(Color.WHITE);
       btSubmit.setForeground(Color.BLACK);
       stuNameLabel = new JLabel("Student name",SwingConstants.RIGHT);
       stuPhoneLabel = new JLabel("Mobile phone",SwingConstants.RIGHT);
       stuAddressLabel = new JLabel("Address",SwingConstants.RIGHT);
       stuProgramTypeLabel = new JLabel("Program type",SwingConstants.RIGHT);
       stuEmailLabel = new JLabel("E-mail",SwingConstants.RIGHT);
       stuGenderLabel = new JLabel("Gender",SwingConstants.RIGHT);
       stuNameField = new JTextField();
       stuPhoneField = new JTextField();
       stuAddressField = new JTextField();
       stuEmailField = new JTextField();

       panelContent.add(stuNameLabel);
       panelContent.add(stuNameField);

       panelContent.add(stuPhoneLabel);
       panelContent.add(stuPhoneField);

       panelContent.add(stuAddressLabel);
       panelContent.add(stuAddressField);

       panelContent.add(stuProgramTypeLabel);
       panelContent.add(stuProgramTypeField);

       panelContent.add(stuEmailLabel);
       panelContent.add(stuEmailField);

       panelContent.add(stuGenderLabel);
       panelContent.add(genderPanel);

       panelContent.setBounds(-20,120,730,130);
       btSubmit.setBounds(340,300,160,25);

       add(panelContent);
       add(btSubmit);

       btSubmit.addActionListener(e -> {
           String nameField = stuNameField.getText();
           String phoneField = stuPhoneField.getText();
           String addressField = stuAddressField.getText();
           String emailField = stuEmailField.getText();
           String programTypeField = (String) stuProgramTypeField.getSelectedItem();
           String genderField;
           if (stuGenderFieldMale.isSelected()) {
               genderField = "Male";
           } else {
               genderField = "Female";
           }
           if(nameField.isEmpty()){
               JOptionPane.showMessageDialog(this, "The name entry field is empty", "Error", JOptionPane.ERROR_MESSAGE);
           }else if(phoneField.isEmpty()){
               JOptionPane.showMessageDialog(this, "The phone entry field is empty", "Error", JOptionPane.ERROR_MESSAGE);
           }
           else if(addressField.isEmpty()){
               JOptionPane.showMessageDialog(this, "The address entry field is empty", "Error", JOptionPane.ERROR_MESSAGE);
           }
           else if(emailField.isEmpty()){
               JOptionPane.showMessageDialog(this, "The email entry field is empty", "Error", JOptionPane.ERROR_MESSAGE);
           }else {
               try {
                   Connection connection = DriverManager.getConnection(
                           DatabaseCommunication.URL, DatabaseCommunication.USER, DatabaseCommunication.PASSWORD
                   );
                   String sql = "INSERT INTO students (name, phone, address, email, program, gender) VALUES (?, ?, ?, ?, ?, ?)";
                   try (PreparedStatement statement = connection.prepareStatement(sql)) {
                       statement.setString(1, nameField);
                       statement.setString(2, phoneField);
                       statement.setString(3, addressField);
                       statement.setString(4, emailField +"@dtu.edu.eg");
                       statement.setString(5, programTypeField);
                       statement.setString(6, genderField);

                       int rowsInserted = statement.executeUpdate();
                       if (rowsInserted > 0) {
                           stuNameField.setText("");
                           stuPhoneField.setText("");
                           stuAddressField.setText("");
                           stuEmailField.setText("");
                           JOptionPane.showMessageDialog(this, "Student account added");
                       } else {
                           System.out.println("No record added!");
                       }
                   }
               } catch (SQLException exception) {
                   System.out.println("An error occurred during connection: " + exception.getMessage());
               }
           }
       });

       setLayout(null);
       setVisible(true);
    }
}
