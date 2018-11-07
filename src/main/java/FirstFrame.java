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

        final JLabel label = new JLabel("Файл не выбран");

        JButton chooseFileButton = new JButton("Выбор файла логов");

        JButton buttonStartSearch = new JButton("Поиск");
        //buttonStartSearch.setEnabled(false);

        JTextField number = new JTextField();
        JTextField seria = new JTextField();
        JTextField surname = new JTextField();

        JCheckBox custInqRq = new JCheckBox("CustinqRQ");
        JCheckBox checkInStopListRq = new JCheckBox("CheckInStopListRq");

        custInqRq.setMnemonic(KeyEvent.VK_G);
        custInqRq.setSelected(false);

        checkInStopListRq.setMnemonic(KeyEvent.VK_G);
        checkInStopListRq.setSelected(false);


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

//        searchWord.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                buttonStartSearch.setEnabled(true);
//            }
//        });

        custInqRq.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchAttr.custInqRq = custInqRq.isSelected();
            }
        });

        checkInStopListRq.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchAttr.checkInStopListRq = checkInStopListRq.isSelected();
            }
        });

        buttonStartSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchAttr.number = number.getText().toLowerCase();
                searchAttr.seria = seria.getText().toLowerCase();
                searchAttr.surname = surname.getText().toLowerCase();

                MyFileReader fileReader = new MyFileReader();
                ResultFormatter result = new ResultFormatter(fileReader.getServiceMessages(fileDir, searchAttr));

                //Нужно это вынести внутрь getServiceMessages
                result.getCustInqOfSearch(result.getCurrentArray(result.hashMap,searchAttr),searchAttr);
            }
        });

        Box box = Box.createHorizontalBox();
        box.add(chooseFileButton);
        box.add(Box.createHorizontalStrut(90));
        box.add(label);

        Box baseBox = Box.createVerticalBox();
        baseBox.add(box);
        baseBox.add(custInqRq);
        baseBox.add(checkInStopListRq);
        baseBox.add(number);
        baseBox.add(seria);
        baseBox.add(surname);
        baseBox.add(Box.createVerticalStrut(10));
        baseBox.add(buttonStartSearch);

        getContentPane().add(baseBox);

        setPreferredSize(new Dimension(500, 500));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
