import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import lombok.Cleanup;

@SuppressWarnings("rawtypes")
public class FileManagement extends JFrame {
    private static final long serialVersionUID = 1L;
    private File fileToUpload;
    private DefaultListModel<String> defaultList = new DefaultListModel<String>();
    private JTextField directory;
    private JList fileList;
    private Client client;

    @SuppressWarnings("unchecked")
    public FileManagement(String username, String serverHost, String serverPort)
            throws NumberFormatException, IOException {
        this.client = new Client(serverHost, serverPort);
        this.client.sendMessage("000 " + username);
        this.updateFileList();

        this.fileList = new JList(this.defaultList);
        this.directory = new JTextField(10);
        var selectFileButton = new JButton("Select File");
        selectFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                var chooser = new JFileChooser();
                var returnValue = chooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    fileToUpload = chooser.getSelectedFile();
                    directory.setText(fileToUpload.getAbsolutePath());
                }
            }
        });
        var uploadButton = new JButton("Upload");
        uploadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
                    byte fileInBytes[];
                    fileInBytes = client.fileToByteArray(fileToUpload.getAbsolutePath(),
                            fileToUpload.length());
                    var fileContents = new String(fileInBytes);
                    client.sendMessage("200 " + fileToUpload.getName());
                    client.sendMessage("202 " + fileContents);
                    updateFileList();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        });
        var downloadButton = new JButton("Download");
        downloadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
                    var selectedFile = fileList.getSelectedValue().toString();
                    client.sendMessage("203 " + selectedFile);
                    var message = client.getLastMessage();
                    if (!message.equals("File does not exist!")) {
                        byte[] fileInBytes = client.getLastMessage().getBytes();
                        Files.createDirectories(Paths.get("C:\\Network\\Downloads\\"));
                        var newFile = new File("C:\\Network\\Downloads\\" + selectedFile);
                        newFile.createNewFile();
                        @Cleanup
                        var fout = new FileOutputStream("C:\\Network\\Downloads\\" + selectedFile);
                        fout.write(fileInBytes);
                    } else
                        JOptionPane.showMessageDialog(null, message);
                    updateFileList();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        });
        var logOutButton = new JButton("Log Out");
        logOutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
                    client.sendMessage("001 " + username);
                    dispose();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        });

        selectFileButton.setBounds(60, 30, 120, 25);
        uploadButton.setBounds(260, 90, 120, 25);
        downloadButton.setBounds(260, 140, 120, 25);
        logOutButton.setBounds(260, 190, 120, 25);
        this.fileList.setBounds(30, 70, 180, 180);
        this.directory.setBounds(220, 30, 200, 25);

        super.setTitle("Dashboard");
        super.setBounds(800, 300, 450, 300);
        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        super.getContentPane().setLayout(null);
        super.getContentPane().add(selectFileButton);
        super.getContentPane().add(uploadButton);
        super.getContentPane().add(downloadButton);
        super.getContentPane().add(logOutButton);
        super.getContentPane().add(this.fileList);
        super.getContentPane().add(this.directory);
        super.setVisible(true);
    }

    private void updateFileList() throws IOException {
        this.client.sendMessage("201 ");
        this.defaultList.clear();
        String[] files = this.client.getLastMessage().split(";");
        for (var i = 0; i < files.length; ++i)
            this.defaultList.addElement(files[i]);
    }

    public static void main(String... args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new FileManagement(args[0], args[1], args[2]);
                } catch (NumberFormatException | IOException exception) {
                    exception.printStackTrace();
                }
            }
        });
    }
}
