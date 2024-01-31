import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.image.RescaleOp;

public class ImageEditor extends JFrame implements ActionListener {
    public JSlider Brightness;
    public JSlider Contrast;
    public JButton upload, adjust;
    public JLabel Orginal, Altered;
    public BufferedImage orginalImage;  // instance variable
    public BufferedImage adjustedImage;  // instance variable

    public ImageEditor() {
        upload = new JButton("UPLOAD IMAGE");
        Brightness = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);
        Contrast = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);
        adjust = new JButton("ADJUST");
        Orginal = new JLabel("Orginal Image");
        Altered = new JLabel("Altered");
        JPanel p = new JPanel();

        upload.addActionListener(this);
        adjust.addActionListener(this);

        p.setLayout(new GridLayout(5, 0));
        p.add(upload);
        p.add(Orginal);
        p.add(Altered);
        p.add(Brightness);
        p.add(Contrast);
        p.add(adjust);
        add(p);

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
                    orginalImage = ImageIO.read(selectedFile);
                    Orginal.setIcon(new ImageIcon(orginalImage));
                } catch (Exception e) {
                    System.out.println("Failed To Upload Image");
                }
            }
        } else if (ae.getSource() == adjust) {
            if (orginalImage != null) {
                int brightnessValue = Brightness.getValue();
                float contrastValue = 1.0f + (Contrast.getValue() / 100.0f);
                RescaleOp rescaleOp = new RescaleOp(contrastValue,
brightnessValue, null);
                adjustedImage = rescaleOp.filter(orginalImage, null);

                Altered.setIcon(new ImageIcon(adjustedImage));
            }
        }
    }

    public static void main(String[] args) {
        new ImageEditor();
    }
}

