import reader.MyFileReader;
import servicesHandlers.ServiceHandler;
import transferAttributes.RequestAttributes;
import transferAttributes.ResponseData;
import transferAttributes.ResultFormatter;

import javax.swing.*;
import javax.xml.transform.TransformerException;
import java.awt.*;
import java.awt.event.*;
import java.io.File;


public class FrameApplication extends JFrame {

    String fileDir;
    RequestAttributes requestAttributes = new RequestAttributes();
    ResponseData responseData = new ResponseData();

    public FrameApplication() {
        super("Поиск логов");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final JLabel label = new JLabel("Файл не выбран");

        JButton chooseFileButton = new JButton("Выбор файла логов");

        JButton buttonStartSearch = new JButton("Поиск");

        JTextField number = new JTextField("777771");
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


        custInqRq.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                requestAttributes.custInqRq = custInqRq.isSelected();
            }
        });

        checkInStopListRq.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                requestAttributes.checkInStopListRq = checkInStopListRq.isSelected();
            }
        });

        buttonStartSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                requestAttributes.number = number.getText().toLowerCase();
                requestAttributes.seria = seria.getText().toLowerCase();
                requestAttributes.surname = surname.getText().toLowerCase();

                MyFileReader fileReader = new MyFileReader();
                ResultFormatter result = new ResultFormatter(fileReader.getServiceMessages(fileDir, requestAttributes));
                ServiceHandler serviceHandler = new ServiceHandler();

                try {
                    result.result(serviceHandler.getCustInq(result.hashMap, requestAttributes, responseData));
                } catch (TransformerException e1) {
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
