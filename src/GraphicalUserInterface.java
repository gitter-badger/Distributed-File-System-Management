import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GraphicalUserInterface extends JFrame {
    private static final long serialVersionUID = 1L;

    public GraphicalUserInterface() {
        super();

        var applicationLabel = new JLabel("Distributed File System Management");
        var usernameLabel = new JLabel("Username: ");
        var usernameTextField = new JTextField("Admin", 10);
        var addressLabel = new JLabel("Server Address: ");
        var addressTextField = new JTextField("localhost", 10);
        var portLabel = new JLabel("Server Port: ");
        var portTextField = new JTextField("3", 10);
        var logInButton = new JButton("Log In");
        logInButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                FileManagement.main(usernameTextField.getText(), addressTextField.getText(),
                        portTextField.getText());
            }
        });

        usernameLabel.setBounds(30, 80, 100, 25);
        usernameTextField.setBounds(150, 80, 200, 20);
        addressLabel.setBounds(30, 150, 100, 25);
        addressTextField.setBounds(150, 150, 200, 20);
        portLabel.setBounds(30, 220, 100, 25);
        portTextField.setBounds(150, 220, 200, 20);
        logInButton.setBounds(250, 270, 100, 30);
        applicationLabel.setBounds(100, 25, 250, 15);

        setTitle("Distributed File System Management");
        setBounds(200, 200, 400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        getContentPane().add(applicationLabel);
        getContentPane().add(usernameLabel);
        getContentPane().add(usernameTextField);
        getContentPane().add(addressLabel);
        getContentPane().add(addressTextField);
        getContentPane().add(portLabel);
        getContentPane().add(portTextField);
        getContentPane().add(logInButton);
        setVisible(true);
    }

    public static void main(String args[]) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GraphicalUserInterface();
            }
        });
    }
}
