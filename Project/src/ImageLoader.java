import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class ImageLoader extends JFrame implements ActionListener {
    private JLabel imageLabel;
    private JButton button;

    public ImageLoader() {
        // Setting up the frame
        super("Image Loader");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Label to display image
        imageLabel = new JLabel("", SwingConstants.CENTER);
        add(imageLabel, BorderLayout.CENTER);

        // Button to open the file chooser
        button = new JButton("Select an Image");
        button.addActionListener(this);
        add(button, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Open file chooser when button is clicked
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select an Image");
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif"));

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            // Set the selected image to the label
            imageLabel.setIcon(new ImageIcon(file.getPath()));
            this.pack(); // Adjusts frame size to fit the image
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ImageLoader frame = new ImageLoader();
            frame.setVisible(true);
        });
    }
}
