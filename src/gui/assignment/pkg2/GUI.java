package gui.assignment.pkg2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class GUI implements ActionListener {

    public static String url = "jdbc:derby:GUIDB;create=true;";
    public static String user = "user";
    public static String password = "password";
    static JPanel homeContainer;
    static JPanel home;
    static JPanel customer;
    static JPanel adminLogin;

    static JPanel adminMode;
    static JPanel adminStatusChange;
    static CardLayout cl;
    JComboBox <String> diameter;
    JComboBox <String> width;
    JComboBox <String> bp;
    JComboBox <String> offset;
    String inputDiameter;
    String inputWidth;
    String inputBp;
    String inputOffset;


    JButton login;
    JLabel usernameLabel;
    JLabel passwordLabel;
    JTextField usernameInput;
    JPasswordField passwordInput;

    JTextArea outputResults = new JTextArea();
    JCheckBox showPassword;

    Boolean confirm = false;


    JComboBox<String> addDiameter;
    JComboBox<String> addWidth;
    JComboBox<String> addBp;
    JComboBox<String> addOffset;
    JComboBox<String> addInStock;


    private Connection conn;
    private DB db;


    public GUI(DB db) {
        this.db = db;

        JFrame frame = new JFrame("GUI");

        JButton admin = new JButton(new AbstractAction("Admin") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "username: admin\npassword: admin", "login details", JOptionPane.PLAIN_MESSAGE);
            }
        });
        JButton customer = new JButton("Customer");
        JButton adminHome = new JButton("Home");
        JButton customerHome = new JButton("Home");
        JButton customerSearchNext = new JButton("Search");

//        options for JComboBox
        String[] diameterOptions = {"unknown", "16", "17", "18", "19", "20", "21", "22", "23", "24"};
        String[] widthOptions = {"unknown", "6", "6.5", "7", "7.5", "8", "8.5", "9", "9.5", "10", "10.5", "11", "11.5", "12"};
        String[] bpOptions = {"unknown", "4x100.0", "4x114.3",
                "5x100.0", "5x112.0", "5x114.3", "5x120.0", "5x130.0", "5x135.0",
                "6x114.3", "6x135.0"};
        String[] offsetOptions = {"unknown", "55", "54", "53", "52", "51", "50",
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
        String[] inStockOptions = {"Unknown", "true", "false"};




        diameter = new JComboBox<>(diameterOptions);
        diameter.setSelectedIndex(0);
        width = new JComboBox<>(widthOptions);
        width.setSelectedIndex(0);
        bp = new JComboBox<>(bpOptions);
        bp.setSelectedIndex(0);
        offset = new JComboBox<>(offsetOptions);
        offset.setSelectedIndex(0);

        JLabel labelDiameter = new JLabel("Diameter: ");
        JLabel labelWidth = new JLabel("Width: ");
        JLabel labelBp = new JLabel("Bolt Pattern: ");
        JLabel labelOffset = new JLabel("Offset: ");

        cl = new CardLayout(50, 50);
        homeContainer = new JPanel(cl);
        homeContainer.setSize(1200, 900);


        //home panel
        GUI.home = new JPanel();
        GUI.home.add(admin);
        GUI.home.add(customer);
        homeContainer.add(GUI.home, "Home");

        //user mode panel
        GUI.customer = new JPanel();
        GUI.customer.add(customerHome);
        GUI.customer.add(labelDiameter);
        GUI.customer.add(diameter);


        GUI.customer.add(labelWidth);
        GUI.customer.add(width);
        GUI.customer.add(labelBp);
        GUI.customer.add(bp);
        GUI.customer.add(labelOffset);
        GUI.customer.add(offset);
        GUI.customer.add(customerSearchNext);
        customerSearchNext.addActionListener(this);
        GUI.customer.add(outputResults, BorderLayout.SOUTH);
        outputResults.setEditable(false);


        homeContainer.add(GUI.customer, "Customer");

        //admin login panel
        GUI.adminLogin = new JPanel();

        usernameLabel = new JLabel();
        usernameLabel.setText("username: ");
        usernameInput = new JTextField(20);

        passwordLabel = new JLabel();
        passwordLabel.setText("password: ");
        passwordInput = new JPasswordField(20);

        showPassword = new JCheckBox("Show password");

        GUI.adminLogin.add(adminHome);
        GUI.adminLogin.add(usernameLabel);
        GUI.adminLogin.add(usernameInput);

        GUI.adminLogin.add(passwordLabel);
        GUI.adminLogin.add(passwordInput);
        GUI.adminLogin.add(showPassword);
        //show password checkbox
        showPassword.addActionListener(e -> {
            if (showPassword.isSelected()) {
                passwordInput.setEchoChar((char) 0);
            } else {
                passwordInput.setEchoChar('\u2022');
            }
        });
        //on press of login button, checks users input and if incorrect an error message pops out
        //if login is successful the panel for admin functions is opened
        login = new JButton(new AbstractAction("Login") {
            @Override
            public void actionPerformed(ActionEvent e) {


                try (Statement statement = db.getConn().createStatement()) {

                    String userInput = usernameInput.getText();
                    String passInput = String.valueOf(passwordInput.getPassword());

                    String checkUser = "";
                    String checkPass = "";

                    String query = ("SELECT * FROM \"Logins\"");
                    if (!userInput.equals("") && !passInput.equals("")) {
                        query += " WHERE username = \'" + userInput + "\' AND password = \'" + passInput + "\'";
                        System.out.println(query);
                        PreparedStatement ps = db.getConn().prepareStatement(query);

                        ResultSet rs = ps.executeQuery();



                        while (rs.next()) {
                            System.out.println("check");


                            checkUser = rs.getString("username");
                            checkPass = rs.getString("password");


                            if (checkUser.equals(userInput) && checkPass.equals(passInput)) {
                                confirm = true;
                                System.out.println("login success");
                                cl.show(homeContainer, "AdminMode");
                                JOptionPane.showMessageDialog(null, "Add specifications using dropdowns to add wheel to database\nTo edit the in stock status use edit ", "add wheel instructions", JOptionPane.PLAIN_MESSAGE);
                            } else {
                                System.out.println("Incorrect username or password");
                                JOptionPane.showMessageDialog(null, "Incorrect username or password", "incorrect", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }

                    if (checkUser.equals("") || checkPass.equals("")){
                        JOptionPane.showMessageDialog(null, "Incorrect username or password", "incorrect", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });
        if (confirm = true) {
            cl.show(homeContainer, "AdminMode");
        }
        GUI.adminLogin.add(login);
        homeContainer.add(GUI.adminLogin, "Admin");


        adminHome.addActionListener(e -> cl.show(homeContainer, "Home"));
        customerHome.addActionListener(e -> cl.show(homeContainer, "Home"));
        admin.addActionListener(e -> cl.show(homeContainer, "Admin"));
        customer.addActionListener(e -> cl.show(homeContainer, "Customer"));
        //admin functions panel
        GUI.adminMode = new JPanel();

        addDiameter = new JComboBox<>(diameterOptions);
        addWidth = new JComboBox<>(widthOptions);
        addBp = new JComboBox<>(bpOptions);
        addOffset = new JComboBox<>(offsetOptions);
        addInStock = new JComboBox<>(inStockOptions);

        JLabel addDiameterLabel = new JLabel("Diameter: ");
        JLabel addWidthLabel = new JLabel("Width: ");
        JLabel addBpLabel = new JLabel("Bolt Pattern: ");
        JLabel addOffsetLabel = new JLabel("Offset: ");
        JLabel addInStockLabel = new JLabel("In stock: ");

        JButton homeAdminMode = new JButton("Home");
        homeAdminMode.addActionListener(e -> cl.show(homeContainer, "Home"));
        GUI.adminMode.add(homeAdminMode);
        GUI.adminMode.add(addDiameterLabel);
        GUI.adminMode.add(addDiameter);
        GUI.adminMode.add(addWidthLabel);
        GUI.adminMode.add(addWidth);
        GUI.adminMode.add(addBpLabel);
        GUI.adminMode.add(addBp);
        GUI.adminMode.add(addOffsetLabel);
        GUI.adminMode.add(addOffset);
        GUI.adminMode.add(addInStockLabel);
        GUI.adminMode.add(addInStock);
        JButton edit = new JButton("Edit");
        edit.addActionListener(e -> cl.show(homeContainer, "AdminStatusChange"));

        //on press of add button, check inputs and if correct, will add to database or will give user error message
        JButton addWheel = new JButton(new AbstractAction("Add") {
            @Override
            public void actionPerformed(ActionEvent e) {
                addWheel();
            }
        });
        GUI.adminMode.add(addWheel);
        GUI.adminMode.add(edit);


        homeContainer.add(GUI.adminMode, "AdminMode");

        GUI.adminStatusChange = new JPanel();
        JTextArea displayResult = new JTextArea();
        displayResult.setEditable(false);
        JTextField productNoSearch = new JTextField(4);
        //when searching product code, only numbers are allowed ,all other chracters are ignored
        productNoSearch.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                    e.consume();
                }
            }
        });
        JLabel productNoEnter = new JLabel("Enter product number to change stock status of: ");
        //on press of search button, checks if it is a valid product number
        //if valid it will display the full details of wheel or gives user error message
        JButton search = new JButton(new AbstractAction("Search") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (Statement statement = db.getConn().createStatement()) {
                    String productNoToSearch = productNoSearch.getText();
                    if (productNoToSearch.equals("")){
                        JOptionPane.showMessageDialog(null, "Please enter a product number to search", "Please enter product number", JOptionPane.ERROR_MESSAGE);

                    }


                    String sqlQuery = ("SELECT * FROM \"Wheels\" WHERE productNo = " + productNoToSearch);
                    System.out.println(sqlQuery);
                    PreparedStatement resultOutput = db.getConn().prepareStatement(sqlQuery);

                    ResultSet rsnext = resultOutput.executeQuery();
                    int diameter = 0;
                    displayResult.setText("");
                    while (rsnext.next()) {

                        diameter = rsnext.getInt("diameter");
                        System.out.println(diameter);
                        System.out.println("checkpoint here");

                        double width = rsnext.getDouble("width");
                        String bp = rsnext.getString("bp");
                        int offset = rsnext.getInt("offset");
                        boolean inStock = rsnext.getBoolean("inStock");
                        int productNo = rsnext.getInt("productNo");

                        String output = ("diameter: " + diameter + ", width: " + width + ", bolt pattern: " + bp + ", offset: " + offset + ", in stock: " + inStock + ", product number: " + productNo);

                        displayResult.setText(output);
                        System.out.println(output);


                        int answer = JOptionPane.showConfirmDialog(null, "Confirm change of stock status?", "confirm", JOptionPane.YES_NO_OPTION);
                        if (answer == 0) {
                            System.out.println(statement.execute("UPDATE \"Wheels\" SET INSTOCK = NOT INSTOCK WHERE PRODUCTNO = " + productNoToSearch));
                            System.out.println("checkpoint for execution");
                            System.out.println(statement.execute("SELECT * FROM \"Wheels\" WHERE productNo = " + productNoToSearch));
                            System.out.println("checkpoint 2");
                        }
                    }

                    if (diameter == 0) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid product number", "invalid product number", JOptionPane.ERROR_MESSAGE);
                    }



                } catch (SQLException ex) {
                    ex.getMessage();
                }
            }
        });
        //admin edit stock status panel
        JButton homeAdminStatusChange = new JButton("Home");
        homeAdminStatusChange.addActionListener(e -> cl.show(homeContainer, "Home"));
        GUI.adminStatusChange.add(homeAdminStatusChange);

        GUI.adminStatusChange.add(productNoEnter);

        GUI.adminStatusChange.add(productNoSearch);

        GUI.adminStatusChange.add(search);

        GUI.adminStatusChange.add(displayResult);

        homeContainer.add(GUI.adminStatusChange, "AdminStatusChange");

        JOptionPane.showMessageDialog(null, "Welcome to the wheel finder catalogue\nUse the user mode to find a wheel or admin mode to add wheels or change stock status", "Welcome", JOptionPane.PLAIN_MESSAGE);
        //set frame
        frame.add(homeContainer);
        cl.show(homeContainer, "Home");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1200, 600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

    }

    public static void main(String[] args) throws SQLException {
        DB db = new DB();
        GUI gui = new GUI(db);
    }

    //on press of search button if 1 or more parameters are filled, it will search database and return list of matches
    //if no input, will give user error message
    //if no matches, will display no matches
    @Override
    public void actionPerformed(ActionEvent e) {
        try (Statement statement = db.getConn().createStatement()) {

            inputDiameter = (String) diameter.getSelectedItem();
            inputWidth = (String) width.getSelectedItem();
            inputBp = (String) bp.getSelectedItem();
            inputOffset = (String) offset.getSelectedItem();
            String query = "";

            if (!inputDiameter.equalsIgnoreCase("unknown") || !inputWidth.equalsIgnoreCase("unknown") || !inputBp.equalsIgnoreCase("unknown") || !inputOffset.equalsIgnoreCase("unknown")) {
                query = ("SELECT * FROM \"Wheels\"");
                query += (" WHERE");

                if (!inputDiameter.equalsIgnoreCase("unknown")) {
                    query += (" diameter = " + inputDiameter);
                }

                if (!inputWidth.equalsIgnoreCase("unknown")) {
                    if (!inputWidth.equalsIgnoreCase("unknown") && !inputDiameter.equalsIgnoreCase("unknown")) {
                        query += (" AND width = " + inputWidth);
                    } else {
                        query += (" width = " + inputWidth);
                    }
                }
                if (!inputBp.equalsIgnoreCase("unknown")) {
                    if (!inputBp.equalsIgnoreCase("unknown") && (!inputWidth.equalsIgnoreCase("unknown") || !inputDiameter.equalsIgnoreCase("unknown"))) {
                        query += (" AND bp = \'" + inputBp + "\'");
                    } else {
                        query += (" bp = \'" + inputBp + "\'");
                    }
                }

                if (!inputOffset.equalsIgnoreCase("unknown")) {
                    if (!inputOffset.equalsIgnoreCase("unknown") && (!inputDiameter.equalsIgnoreCase("unknown") || !inputWidth.equalsIgnoreCase("unknown") || !inputBp.equalsIgnoreCase("unknown"))) {
                        query += (" AND offset = " + inputOffset);
                    } else {
                        query += (" offset = " + inputOffset);
                    }
                }

                System.out.println(query);
                PreparedStatement ps = db.getConn().prepareStatement(query);

                ResultSet rs = ps.executeQuery();
                outputResults.setText("");
                outputResults.setEditable(false);
                System.out.println("checkpoint");

                int count = 0;
                while (rs.next()) {
                    int diameter = rs.getInt("diameter");
                    double width = rs.getDouble("width");
                    String bp = rs.getString("bp");
                    int offset = rs.getInt("offset");
                    boolean inStock = rs.getBoolean("inStock");
                    int productNo = rs.getInt("productNo");

                    String output = ("diameter: " + diameter + ", width: " + width + ", bolt pattern: " + bp + ", offset: " + offset + ", in stock: " + inStock + ", product number: " + productNo);

                    System.out.println("diameter: " + diameter + ", width: " + width + ", bolt pattern: " + bp + ", offset: " + offset + ", in stock: " + inStock + ", product number: " + productNo);

                    outputResults.append(output + "\n");

                    System.out.println(count);
                    count += 1;
                }

                if (outputResults.getText().equalsIgnoreCase("")) {
                    outputResults.append("                                      Search returned no matches                                    \n");
                }

            } else {
                System.out.println("Please enter parameters");
                JOptionPane.showMessageDialog(null, "Please enter parameters to search with", "Please enter parameters", JOptionPane.ERROR_MESSAGE);

//                outputResults.setText("Please enter Parameters");
            }


        } catch (SQLException f) {
            f.printStackTrace();
        }
    }
        //on click of add under admin, method is run
        //requires all parameters to be filled or error message is displayed
        //product number is automatically assigned by searching db for number of rows and + 1 to give new number
        public void addWheel() {
        this.db = db;

        try (Statement statement = db.getConn().createStatement()) {
            String diameterToAdd = (String) addDiameter.getSelectedItem();
            String widthToAdd = (String) addWidth.getSelectedItem();
            String bpToAdd = (String) addBp.getSelectedItem();
            String offsetToAdd = (String) addOffset.getSelectedItem();
            String inStockToAdd = (String) addInStock.getSelectedItem();


            String getItems = "SELECT COUNT(*) FROM \"Wheels\"";
            System.out.println(getItems);
            PreparedStatement ps = db.getConn().prepareStatement(getItems);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int count = rs.getInt(1) + 1;
            String productNoToAdd = String.valueOf(count);

            if (diameterToAdd.equals("unknown") || widthToAdd.equals("unknown") || widthToAdd.equals("unknown") || bpToAdd.equals("unknown") || offsetToAdd.equals("unknown") || inStockToAdd.equals("unknown")) {
                System.out.println("please fill all parameters");
                JOptionPane.showMessageDialog(null, "Please fill out all wheel specifications", "incorrect input", JOptionPane.ERROR_MESSAGE);

            } else {
                String[][] data = {{diameterToAdd, widthToAdd, bpToAdd, offsetToAdd, inStockToAdd, productNoToAdd}};
                for (String[] s : data) {
                    int diameterTemp = Integer.parseInt(s[0]);
                    double widthTemp = Double.parseDouble(s[1]);
                    String bpTemp = s[2];
                    int offsetTemp = Integer.parseInt(s[3]);
                    boolean inStockTemp = Boolean.parseBoolean(s[4]);
                    int productNoTemp = Integer.parseInt(s[5]);

                    String query = String.format("INSERT INTO \"Wheels\" (diameter, width, bp, offset, inStock, productNo) VALUES (%d, %f, '%s', %d, %b, %d)", diameterTemp, widthTemp, bpTemp, offsetTemp, inStockTemp, productNoTemp);
                    statement.execute(query);
                    System.out.println(query);
                }

            }

        } catch (SQLException ex) {
            ex.getMessage();
        }

    }


}