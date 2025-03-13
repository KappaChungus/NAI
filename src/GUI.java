import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.Vector;

public class GUI extends JFrame {
    private int k = -1;
    private HashMap<Vector<Double>, String> trainVectors;
    private HashMap<Vector<Double>, String> testVectors;
    private final JButton runKnnButton;

    GUI() {
        setLayout(new FlowLayout());
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // k
        JTextField kField = new JTextField(2);
        add(kField);
        JButton kButton = new JButton("set k");
        kButton.addActionListener(e -> {
            try {
                k = Integer.parseInt(kField.getText());
                checkIfReady();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number for k.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(kButton);

        // set up train and test vectors
        JButton setTrainDataPathButton = getFileChooserButton("train");
        JButton setTestDataPathButton = getFileChooserButton("test");

        add(setTrainDataPathButton);
        add(setTestDataPathButton);

        // knn button
        runKnnButton = new JButton("run knn");
        runKnnButton.setEnabled(false);
        runKnnButton.addActionListener(e -> {
            double result = Math.round(Main.knn(trainVectors, testVectors, k) * 100.0);
            JOptionPane.showMessageDialog(this, "Dokładność modelu dla k = "+k+":\n" + result+"%", "Wynik", JOptionPane.INFORMATION_MESSAGE);
        });

        add(runKnnButton);
        setVisible(true);
    }

    private JButton getFileChooserButton(String type) {
        JButton button = new JButton("Set " + type + " Data Path");
        button.addActionListener(e -> {
            JFileChooser fc = new JFileChooser(new File("."));
            int returnValue = fc.showOpenDialog(this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                String selectedFilePath = fc.getSelectedFile().getAbsolutePath();
                if (type.equals("train")) {
                    trainVectors = Main.preprocess(selectedFilePath);
                } else {
                    testVectors = Main.preprocess(selectedFilePath);
                }
                checkIfReady();
            }
        });
        return button;
    }

    private void checkIfReady() {
        runKnnButton.setEnabled(k > 0 && trainVectors != null && testVectors != null);
    }
}

