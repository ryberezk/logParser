import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;


public class FirstFrame extends JFrame {

    String fileDir;

    public FirstFrame()  {
        super("Поиск логов");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final JLabel label = new JLabel("Выбранный файл");

        JButton chooseFileButton = new JButton("Выбор файла логов");

        JButton buttonStartSearch = new JButton("Поиск");

        JTextPane textForSearch = new JTextPane ();


        chooseFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileopen = new JFileChooser();
                int ret = fileopen.showDialog(null, "Открыть файл");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileopen.getSelectedFile();
                    label.setText(file.getName());
                    fileDir = file.getAbsolutePath();
                }
            }
        });

        buttonStartSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileReader f = new FileReader ();
                try {
                    f.fileReader(fileDir,textForSearch.getText());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        Box box = Box.createHorizontalBox();
        box.add(chooseFileButton);
        box.add(Box.createHorizontalStrut(90));
        box.add(label);

        Box baseBox = Box.createVerticalBox();
        baseBox.add(box);
        baseBox.add(textForSearch);
        baseBox.add(Box.createVerticalStrut(10));
        baseBox.add(buttonStartSearch);

        getContentPane().add(baseBox);

        setPreferredSize(new Dimension(500, 500));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }}
