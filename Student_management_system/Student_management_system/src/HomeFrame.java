import javax.swing.*;
import java.awt.*;

public class HomeFrame extends JFrame{
    public void showHomeFrame(int id,String email) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Student management system");
        ImageIcon icon = new ImageIcon(ImagesUrl.APPLOGO);
        Image appIcon = icon.getImage();
        setIconImage(appIcon);

        setSize(800, 500);
        setLocationRelativeTo(null);
        JLabel dtuLogo = new JLabel();
        ImageIcon imageLogoDtu = new ImageIcon(ImagesUrl.DTULOGO);
        Image imageDtu = imageLogoDtu.getImage();
        Image newImgDtu = imageDtu.getScaledInstance(300, 80, Image.SCALE_SMOOTH);
        dtuLogo.setIcon(new ImageIcon(newImgDtu));

        // JPanel for the main content
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(Color.WHITE);

        headerPanel.add(dtuLogo);

        // JPanel for the left side buttons
        JPanel buttonPanel = new JPanel(new GridBagLayout());


        // buttons
        JButton studentsButton = createButton("Students");
        JButton addNewStudentButton = createButton("Add New Student");
        JButton settingsButton = createButton("Settings");

        // size for buttons
        Dimension buttonSize = new Dimension(150, 40);
        studentsButton.setPreferredSize(buttonSize);
        addNewStudentButton.setPreferredSize(buttonSize);
        settingsButton.setPreferredSize(buttonSize);

        JLabel userImage = new JLabel();
        ImageIcon userImageLogo = new ImageIcon(ImagesUrl.USERLOGO);
        Image userImageDtu = userImageLogo.getImage();
        Image newUserImage = userImageDtu.getScaledInstance(145, 130, Image.SCALE_SMOOTH);
        userImage.setIcon(new ImageIcon(newUserImage));

        // Add buttons to the panel with GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding
        gbc.gridy = 1;
        buttonPanel.add(userImage,gbc);
        gbc.gridy = 2;
        buttonPanel.add(studentsButton, gbc);
        gbc.gridy = 3;
        buttonPanel.add(addNewStudentButton, gbc);
        gbc.gridy = 4;
        buttonPanel.add(settingsButton, gbc);

        // JPanel for the bottom section
        JPanel lowerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel idLabel = new JLabel("ID: " + id);
        JLabel usernameLabel = new JLabel("Email: " + email);

        // Add space between ID label and Username label
        lowerPanel.add(idLabel);
        lowerPanel.add(Box.createRigidArea(new Dimension(20, 0))); // Add space between labels
        lowerPanel.add(usernameLabel);

        JPanel homePanel = new JPanel(new GridLayout());
        JLabel programName = new JLabel("Student management system", SwingConstants.CENTER);
        Font font = new Font("Arial", Font.BOLD, 20);
        programName.setFont(font);
        homePanel.add(programName);
        homePanel.setBackground(Color.white);
        // Add the panels to the main content panel
        contentPanel.add(headerPanel, BorderLayout.NORTH);
        contentPanel.add(buttonPanel, BorderLayout.WEST);
        contentPanel.add(homePanel, BorderLayout.CENTER);
        contentPanel.add(lowerPanel, BorderLayout.SOUTH);
        // Add the main content panel to the JFrame's content pane
        setContentPane(contentPanel);

        //events
        studentsButton.addActionListener(e -> new StudentPage());
        addNewStudentButton.addActionListener(e -> new AddNewStudent());
        settingsButton.addActionListener(e -> new Setting());
        setVisible(true);
    }


    // style of buttons
    public JButton createButton(String text){
            JButton button = new JButton(text);
            button.setOpaque(true); // Make the button opaque
            button.setBackground(Color.WHITE); // Set background color to white
            button.setForeground(Color.BLACK); // Set text color
            return button;
        }
    }

