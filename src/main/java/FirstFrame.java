import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;


public class FirstFrame extends JFrame {

    String fileDir;
    SearchAttr searchAttr = new SearchAttr();

    public FirstFrame() {
        super("Поиск логов");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final JLabel label = new JLabel("Выбранный файл");

        JButton chooseFileButton = new JButton("Выбор файла логов");

        JButton buttonStartSearch = new JButton("Поиск");

        JTextPane textForSearch = new JTextPane();

        JCheckBox custinqrq = new JCheckBox("CustinqRQ");
        JCheckBox custinqrs = new JCheckBox("CustinqRS");

        custinqrq.setMnemonic(KeyEvent.VK_G);
        custinqrq.setSelected(false);

        custinqrs.setMnemonic(KeyEvent.VK_G);
        custinqrs.setSelected(false);


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

        custinqrq.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchAttr.custinqrq = custinqrq.isSelected();
            }
        });

        custinqrs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchAttr.custinqrs = custinqrs.isSelected();
            }
        });

        buttonStartSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyFileReader fileReader3 = new MyFileReader();
                fileReader3.searchRequests(fileDir, searchAttr);
            }
        });

        Box box = Box.createHorizontalBox();
        box.add(chooseFileButton);
        box.add(Box.createHorizontalStrut(90));
        box.add(label);

        Box baseBox = Box.createVerticalBox();
        baseBox.add(box);
        baseBox.add(custinqrq);
        baseBox.add(custinqrs);
        baseBox.add(textForSearch);
        baseBox.add(Box.createVerticalStrut(10));
        baseBox.add(buttonStartSearch);

        getContentPane().add(baseBox);

        setPreferredSize(new Dimension(500, 500));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
