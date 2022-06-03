import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GUI implements ActionListener {
    List<String> output = new ArrayList<>();
    static JPanel homeContainer;
    static JPanel home;
    static JPanel customer;
    static JPanel admin;
    static CardLayout cl;
    JComboBox diameter;
    JLabel labelDiameterOutput = new JLabel();
    JTextArea listOutput = new JTextArea();
    String inputDiameter = "";


    public GUI()
    {
        JFrame frame = new JFrame("GUI");
        JButton admin = new JButton("Admin");
        JButton customer = new JButton("Customer");
        JButton adminHome = new JButton("Home");
        JButton customerHome = new JButton("Home");
        JButton customerSearchNext = new JButton("Next");

        String[] diameterOptions = {"16", "17", "18", "19", "20", "21", "22", "23", "24"};
        String[] widthOptions = {"6", "6.5", "7", "7.5", "8", "8.5", "9", "9.5", "10", "10.5", "11", "11.5", "12"};
        String[] bpOptions = {"4x100", "4x114.3",
                "5x100", "5x112", "5x114.3", "5x120", "5x130", "5x135",
                "6x114.3", "6x135"};
        String[] offsetOptions = {"55", "54", "53", "52", "51", "50",
                "49", "48", "47", "46", "45", "44", "43", "42", "41", "40",
                "39", "38", "37", "36", "35", "34", "33", "32", "31", "30",
                "29", "28", "27", "26", "25", "24", "23", "22", "21", "20",
                "19", "18", "17", "16", "15", "14", "13", "12", "11", "10",
                "9", "8", "7", "6", "5", "4", "3", "2", "1", "0",
                "-1", "-2", "-3", "-4", "-5", "-6", "-7", "-8", "-9", "-10",
                "-11", "-12", "-13", "-14", "-15", "-16", "-17", "-18", "-19", "-20",
                "-21", "-22", "-23", "-24", "-25", "-26", "-27", "-28", "-29", "-30",
                "-31", "-32", "-33", "-34", "-35", "-36", "-37", "-38", "-39", "-40",
                "-41", "-42", "-43", "-44", "-45", "-46", "-47", "-48", "-49", "-50",
                "-51", "-52", "-53", "-54", "-55"};



        diameter = new JComboBox<>(diameterOptions);
        diameter.setSelectedIndex(-1);
        JComboBox<String> width = new JComboBox<>(widthOptions);
        width.setSelectedIndex(-1);
        JComboBox<String> bp = new JComboBox<>(bpOptions);
        bp.setSelectedIndex(-1);
        JComboBox<String> offset = new JComboBox<>(offsetOptions);
        offset.setSelectedIndex(-1);

        JLabel labelDiameter = new JLabel("Diameter: ");
        JLabel labelWidth = new JLabel("Width: ");
        JLabel labelBp = new JLabel("Bolt Pattern: ");
        JLabel labelOffset = new JLabel("Offset: ");





        cl = new CardLayout(50, 50);
        homeContainer = new JPanel(cl);
        homeContainer.setSize(600, 400);

        GUI.home = new JPanel();
        GUI.home.add(admin);
        GUI.home.add(customer);
        homeContainer.add(GUI.home, "Home");




        GUI.customer = new JPanel();
        GUI.customer.add(customerHome);
        GUI.customer.add(labelDiameter);
        GUI.customer.add(diameter);
//        diameter.addActionListener(this);
        inputDiameter = (String)diameter.getSelectedItem();
//        JLabel outputDiameter = new JLabel(inputDiameter);
//        GUI.customer.add(outputDiameter);




        GUI.customer.add(labelWidth);
        GUI.customer.add(width);
        GUI.customer.add(labelBp);
        GUI.customer.add(bp);
        GUI.customer.add(labelOffset);
        GUI.customer.add(offset);
        GUI.customer.add(customerSearchNext);
        customerSearchNext.addActionListener(this);
        GUI.customer.add(listOutput);
        GUI.customer.add(labelDiameterOutput);


        homeContainer.add(GUI.customer, "Customer");

        GUI.admin = new JPanel();
        GUI.admin.add(adminHome);
        homeContainer.add(GUI.admin, "Admin");

        adminHome.addActionListener(e -> cl.show(homeContainer, "Home"));
        customerHome.addActionListener(e -> cl.show(homeContainer, "Home"));
        admin.addActionListener(e -> cl.show(homeContainer, "Admin"));
        customer.addActionListener(e -> cl.show(homeContainer, "Customer"));

        frame.add(homeContainer);
        cl.show(homeContainer, "Home");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(GUI::new);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            BufferedReader br = new BufferedReader(new FileReader("output_demo.txt"));
            while (br.ready()) {
                output.add(br.readLine());
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        if (inputDiameter.equals("18")) {
            listOutput.setText("18, 8.5, 5x120.0, 35, false, 1");
        }

//        for (String str : output) {
//            String[] values = str.split(", ");
//            String fileDiameter = (values[0]);
//                if (inputDiameter.equals(fileDiameter)) {
//                    listOutput.setText(str);
//                }
//
//        }

//        if (e.getSource() == diameter) {
//            JComboBox cb = (JComboBox)e.getSource();
//            String msg = (String)cb.getSelectedItem();
//            switch (Objects.requireNonNull(msg)) {
//                case "16" : labelDiameterOutput.setText("Diameter: 16");
//                    break;
//                case "17" : labelDiameterOutput.setText("Diameter: 17");
//                    break;
//                case "18" : labelDiameterOutput.setText("Diameter: 18");
//                    break;
//                case "19" : labelDiameterOutput.setText("Diameter: 19");
//                    break;
//                case "20" : labelDiameterOutput.setText("Diameter: 20");
//                    break;
//                case "21" : labelDiameterOutput.setText("Diameter: 21");
//                    break;
//                case "22" : labelDiameterOutput.setText("Diameter: 22");
//                    break;
//                case "23" : labelDiameterOutput.setText("Diameter: 23");
//                    break;
//                case "24" : labelDiameterOutput.setText("Diameter: 24");
//                    break;
//
//
//                default:labelDiameterOutput.setText("none");
//            }
//        }
    }
}