import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.image.RescaleOp;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ImageEditor extends JFrame implements ActionListener, ChangeListener {
    public JSlider Brightness;
    public JSlider Contrast;
    public JButton upload;
    public JLabel Orginal;
    public BufferedImage originalImage;  // instance variable
    public BufferedImage alteredImage;   // instance variable to store the altered image

    public ImageEditor() {
        upload = new JButton("UPLOAD IMAGE");
        Brightness = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);
        Contrast = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);
        Orginal = new JLabel("Original Image");
        JPanel imagePanel = new JPanel();
        imagePanel.add(Orginal);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(4, 1));
        controlPanel.add(upload);
        controlPanel.add(Brightness);
        controlPanel.add(Contrast);

        // Add labels for sliders
        controlPanel.add(new JLabel("Brightness"));
        controlPanel.add(new JLabel("Contrast"));

        // Add listeners
        upload.addActionListener(this);
        Brightness.addChangeListener(this);
        Contrast.addChangeListener(this);

        // Set layout for the main frame
        setLayout(new BorderLayout());
        add(imagePanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.EAST);

        setVisible(true);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == upload) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    File selectedFile = fileChooser.getSelectedFile();
                    originalImage = ImageIO.read(selectedFile);
                    Orginal.setIcon(new ImageIcon(originalImage));
                    // Initialize alteredImage with a copy of the original image
                    alteredImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), originalImage.getType());
                    Graphics g = alteredImage.getGraphics();
                    g.drawImage(originalImage, 0, 0, null);
                    g.dispose();
                } catch (Exception e) {
                    System.out.println("Failed To Upload Image");
                }
            }
        }
    }

    // Update the altered image based on the current slider values
    public void stateChanged(ChangeEvent e) {
        if (originalImage != null) {
            int brightnessValue = Brightness.getValue();
            float contrastValue = 1.0f + (Contrast.getValue() / 100.0f);
            RescaleOp rescaleOp = new RescaleOp(contrastValue, brightnessValue, null);
            rescaleOp.filter(originalImage, alteredImage);
            Orginal.setIcon(new ImageIcon(alteredImage));
        }
    }

    public static void main(String[] args) {
        new ImageEditor();
    }
}
