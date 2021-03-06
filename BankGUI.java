package project3;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class BankGUI {

    private JButton add, delete, update, clear;
    private JTextField textAccountNum, textAccountOwner, textDateOpened, textAccountBal, textMonthlyFee,
            textInterestRate, textMinBal;
    private JLabel labelAccountNum, labelAccountOwner, labelDateOpened, labelAccountBal, labelMonthlyFee,
            labelInterestRate, labelMinBal;
    private JScrollPane scrollPane;
    private JTable table;
    private JFrame frame;
    private JRadioButton checking, savings;
    private JPanel panelTop, panelBottom, panelBottomLeft, panelBottomRight;
    private JMenuBar toolBar;
    private JMenu file, sort;
    private JMenuItem loadBinary, saveBinary, loadText, saveText, loadXML, saveXML, quit, byAccountNum, byAccountOwner,
            byDateOpened;
    private ButtonListener listener;
    private BankModel bankModel;

    /**
     * Run GUI.
     *
     * @param args > /dev/null
     */
    public static void main(String[] args) {
        BankGUI bank = new BankGUI();
    }

    public BankGUI() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        listener = new ButtonListener();

        panelTop = new JPanel();
        panelBottom = new JPanel();
        bankModel = new BankModel();
        table = new JTable(bankModel);

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                // TODO Auto-generated method stub
                if (table.getSelectedRow() >= 0) {
                    textAccountNum.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
                    textDateOpened.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
                    textAccountOwner.setText(table.getValueAt(table.getSelectedRow(), 2).toString());
                    textAccountBal.setText(table.getValueAt(table.getSelectedRow(), 3).toString());

                    if(bankModel.getAccount(table.getSelectedRow())
                            instanceof CheckingAccount){
                        checking.setSelected(true);
                        checking.doClick();
                    }
                    else{
                        savings.setSelected(true);
                        savings.doClick();
                    }
                    if (bankModel.getAccount(table.getSelectedRow()) instanceof CheckingAccount
                            && checking.isSelected()) {
                        textMonthlyFee.setText(table.getValueAt(table.getSelectedRow(), 4).toString());
                    } else if (bankModel.getAccount(table.getSelectedRow()) instanceof SavingsAccount
                            && savings.isSelected()) {
                        textInterestRate.setText(table.getValueAt(table.getSelectedRow(), 5).toString());
                        textMinBal.setText(table.getValueAt(table.getSelectedRow(), 6).toString());
                    }

                }
            }
        });

        scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setViewportView(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Bottom panel
        panelBottom = new JPanel();
        panelBottom.setLayout(new BorderLayout());

        panelBottomLeft = setUpBottomPanel();
        panelBottomRight = setUpRightPanel();

        panelBottom.add(panelBottomLeft, BorderLayout.CENTER);
        panelBottom.add(panelBottomRight, BorderLayout.EAST);

        // Add toolbar
        toolBar = new JMenuBar();
        file = new JMenu("File");
        sort = new JMenu("Sort");
        loadBinary = new JMenuItem("Load From Binary...");
        loadBinary.addActionListener(listener);
        saveBinary = new JMenuItem("Save As Binary...");
        saveBinary.addActionListener(listener);
        loadText = new JMenuItem("Load From Text...");
        loadText.addActionListener(listener);
        saveText = new JMenuItem("Save As Text...");
        saveText.addActionListener(listener);
        loadXML = new JMenuItem("Load From XML...");
        loadXML.addActionListener(listener);
        saveXML = new JMenuItem("Save As XML...");
        saveXML.addActionListener(listener);
        quit = new JMenuItem("Quit");
        quit.addActionListener(listener);
        byAccountNum = new JMenuItem("By Account Number");
        byAccountNum.addActionListener(listener);
        byAccountOwner = new JMenuItem("By Account Owner");
        byAccountOwner.addActionListener(listener);
        byDateOpened = new JMenuItem("By Date Opened");
        byDateOpened.addActionListener(listener);

        file.add(loadBinary);
        file.add(saveBinary);
        file.addSeparator();
        file.add(loadText);
        file.add(saveText);
        file.addSeparator();
        file.add(loadXML);
        file.add(saveXML);
        file.addSeparator();
        file.add(quit);
        sort.add(byAccountNum);
        sort.add(byAccountOwner);
        sort.add(byDateOpened);
        toolBar.add(file);
        toolBar.add(sort);

        frame.add(panelBottom, BorderLayout.SOUTH);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setJMenuBar(toolBar);
        frame.setVisible(true);
    }

    private JPanel setUpBottomPanel() {
        JPanel panelBottom = new JPanel();
        panelBottom.setLayout(new BoxLayout(panelBottom, BoxLayout.Y_AXIS));

        JPanel panel8 = new JPanel();
        ButtonGroup group = new ButtonGroup();
        checking = new JRadioButton("Checking");
        checking.addActionListener(listener);
        checking.setSelected(true);
        savings = new JRadioButton("Savings");
        savings.addActionListener(listener);
        group.add(checking);
        group.add(savings);
        panel8.add(checking);
        panel8.add(savings);

        JPanel panel1 = new JPanel();
        labelAccountNum = new JLabel("Account Number: ");
        textAccountNum = new JTextField(20);
        panel1.add(labelAccountNum);
        panel1.add(Box.createRigidArea(new Dimension(35, 0)));
        panel1.add(textAccountNum);

        JPanel panel2 = new JPanel();
        labelAccountOwner = new JLabel("Account Owner: ");
        textAccountOwner = new JTextField(20);
        panel2.add(labelAccountOwner);
        panel2.add(Box.createRigidArea(new Dimension(42, 0)));
        panel2.add(textAccountOwner);

        JPanel panel3 = new JPanel();
        labelDateOpened = new JLabel("Date Opened: ");
        textDateOpened = new JTextField(20);
        panel3.add(labelDateOpened);
        panel3.add(Box.createRigidArea(new Dimension(57, 0)));
        panel3.add(textDateOpened);

        JPanel panel4 = new JPanel();
        labelAccountBal = new JLabel("Account Balance: ");
        textAccountBal = new JTextField(20);
        panel4.add(labelAccountBal);
        panel4.add(Box.createRigidArea(new Dimension(35, 0)));
        panel4.add(textAccountBal);

        JPanel panel5 = new JPanel();
        labelMonthlyFee = new JLabel("Monthly Fee: ");
        textMonthlyFee = new JTextField(20);
        panel5.add(labelMonthlyFee);
        panel5.add(Box.createRigidArea(new Dimension(62, 0)));
        panel5.add(textMonthlyFee);

        JPanel panel6 = new JPanel();
        labelInterestRate = new JLabel("Interest Rate: ");
        textInterestRate = new JTextField(20);
        panel6.add(labelInterestRate);
        panel6.add(Box.createRigidArea(new Dimension(57, 0)));
        panel6.add(textInterestRate);

        JPanel panel7 = new JPanel();
        labelMinBal = new JLabel("Minimum Balance: ");
        textMinBal = new JTextField(20);
        panel7.add(labelMinBal);
        panel7.add(Box.createRigidArea(new Dimension(30, 0)));
        panel7.add(textMinBal);

        textInterestRate.setEditable(false);
        textMinBal.setEditable(false);

        panelBottom.add(panel8);
        panelBottom.add(panel1);
        panelBottom.add(panel2);
        panelBottom.add(panel3);
        panelBottom.add(panel4);
        panelBottom.add(panel5);
        panelBottom.add(panel6);
        panelBottom.add(panel7);
        panelBottom.add(new JPanel());

        return panelBottom;
    }

    private JPanel setUpRightPanel() {
        JPanel rightPanel = new JPanel();
        rightPanel.setOpaque(true);
        rightPanel.setBackground(Color.GRAY);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        add = new JButton("Add");
        add.addActionListener(listener);
        delete = new JButton("Delete");
        delete.addActionListener(listener);
        update = new JButton("Update");
        update.addActionListener(listener);
        clear = new JButton("Clear");
        clear.addActionListener(listener);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 60)));
        rightPanel.add(add);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        rightPanel.add(delete);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        rightPanel.add(update);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        rightPanel.add(clear);

        return rightPanel;
    }

    private boolean checkEmptySettings() {
        boolean isEmpty = false;
        if (textAccountBal.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter an account balance", "Empty Fields",
                    JOptionPane.WARNING_MESSAGE);
            isEmpty = true;
        }
        if (textAccountNum.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter an account number", "Empty Fields",
                    JOptionPane.WARNING_MESSAGE);
            isEmpty = true;
        }
        if (textAccountOwner.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter an account owner", "Empty Fields",
                    JOptionPane.WARNING_MESSAGE);
            isEmpty = true;
        }
        if (textDateOpened.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter date opened", "Empty Fields",
                    JOptionPane.WARNING_MESSAGE);
            isEmpty = true;
        } else {
            isEmpty = false;
        }
        return isEmpty;
    }

    private boolean checkEmptyCheckingsSettings() {
        boolean isEmpty = false;
        if (textMonthlyFee.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a monthly fee", "Empty Fields",
                    JOptionPane.WARNING_MESSAGE);
            isEmpty = true;
        } else {
            isEmpty = false;
        }
        return isEmpty;
    }

    private boolean checkEmptySavingsSettings() {
        boolean isEmpty = false;
        if (textInterestRate.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter an interest rate", "Empty Fields",
                    JOptionPane.WARNING_MESSAGE);
            isEmpty = true;
        }
        if (textMinBal.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a min balance", "Empty Fields",
                    JOptionPane.WARNING_MESSAGE);
            isEmpty = true;
        } else {
            isEmpty = false;
        }
        return isEmpty;
    }

    private void clearAllTextFields() {
        textAccountBal.setText("");
        textAccountNum.setText("");
        textAccountOwner.setText("");
        textDateOpened.setText("");
        textInterestRate.setText("");
        textMinBal.setText("");
        textMonthlyFee.setText("");
    }

    public static GregorianCalendar parseTimestamp(String timestamp)
            throws Exception {
        DateFormat        df   = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date    date = df.parse(timestamp);
        GregorianCalendar cal  = new GregorianCalendar();
        cal.setTime(date);
        return cal;
    }

    public static String formatTimestamp(GregorianCalendar gc) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date date = gc.getTime();
        return df.format(date);
    }

    public boolean checkNegativeChecking() {
        boolean checker;
        if(Double.parseDouble(textAccountBal.getText()) >= 0 && Double.parseDouble(textMonthlyFee.getText()) >= 0 && Double.parseDouble(textAccountNum.getText()) >= 0){
            checker = true;
        }
        else {
            checker = false;
        }
        return checker;
    }
    public boolean checkNegativeSavings() {
        boolean checker;
        if(Double.parseDouble(textAccountBal.getText()) >= 0 && Double.parseDouble(textAccountNum.getText()) >= 0 && Double.parseDouble(textInterestRate.getText()) >= 0 && Double.parseDouble(textMinBal.getText()) >= 0){
            checker = true;
        }
        else {
            checker = false;
        }
        return checker;
    }
    
    public void addChecking() {
        if (!checkEmptySettings() && !checkEmptyCheckingsSettings()) {
            CheckingAccount checking = new CheckingAccount();

            if (checkNegativeChecking()) {
                try {
                    checking.setBalance(Double.parseDouble(textAccountBal.getText()));
                    checking.setMonthlyFee(Double.parseDouble(textMonthlyFee.getText()));
                    checking.setNumber(Integer.parseInt(textAccountNum.getText()));
                }
                catch (NumberFormatException error) {
                    JOptionPane.showMessageDialog(null, "The following must be " +
                            "numbers:\n Account Balance\n Monthly Fee\n Account " +
                            "Number", "Empty Fields", JOptionPane.WARNING_MESSAGE);
                    error.printStackTrace();
                }

                try {
                    checking.setDateOpened(

                    parseTimestamp(textDateOpened.getText())
                    );

                } catch (Exception e) {
                    e.printStackTrace();
                }
                checking.setOwner(textAccountOwner.getText());
                bankModel.addAccount(checking);
                clearAllTextFields();
            } else {
                JOptionPane.showMessageDialog(null,
                        "Do not enter negative numbers",
                        "Empty Fields", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    public void addSavings() {
        if (!checkEmptySettings() && !checkEmptySavingsSettings()) {
            SavingsAccount savings = new SavingsAccount();

            if (checkNegativeSavings()) {
                try {
                    savings.setBalance(Double.parseDouble(textAccountBal.getText()));
                    savings.setInterestRate(Double.parseDouble(textInterestRate.getText()));
                    savings.setMinBalance(Double.parseDouble(textMinBal.getText()));
                    savings.setNumber(Integer.parseInt(textAccountNum.getText()));
                }
                catch (NumberFormatException error) {
                    JOptionPane.showMessageDialog(null, "The following must be" +
                            "numbers:\n Account Balance\n Interest Rate\n " +
                            "Min Balance\n Account Number",
                            "Empty Fields", JOptionPane.WARNING_MESSAGE);
                }

                try {
                    savings.setDateOpened(
                            parseTimestamp(textDateOpened.getText())
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
                savings.setOwner(textAccountOwner.getText());
                bankModel.addAccount(savings);
                clearAllTextFields();
            } else {
                JOptionPane.showMessageDialog(null,
                        "Do not enter negative numbers",
                        "Empty Fields", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Gets source of object clicked
            Object source = e.getSource();
            if (source == quit) {
                System.exit(0);
            } else if (source == checking) {
                textInterestRate.setEditable(false);
                textMinBal.setEditable(false);
                textInterestRate.setText("");
                textMinBal.setText("");
                textMonthlyFee.setEditable(true);
            } else if (source == savings) {
                textMonthlyFee.setEditable(false);
                textMonthlyFee.setText("");
                textInterestRate.setEditable(true);
                textMinBal.setEditable(true);
            }

            else if (source == add) {
                try {
                    if (checking.isSelected()) {
                        addChecking();
                    }
                    else if (savings.isSelected()) {
                        addSavings();
                    }
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            } else if (source == delete) {
                table.getSelectedRow();
                if (table.getSelectedRow() >= 0) {
                    bankModel.removeAccount(table.getSelectedRow());
                    clearAllTextFields();
                }
            } else if (source == update) {
                int sRow = table.getSelectedRow();
                if (bankModel.getAccount(sRow) instanceof CheckingAccount) {
                    CheckingAccount newAccount = (CheckingAccount) bankModel.updateRow(sRow);
                    try {
                        newAccount.setBalance(Double.parseDouble(textAccountBal.getText()));
                        newAccount.setMonthlyFee(Double.parseDouble(textMonthlyFee.getText()));
                        newAccount.setNumber(Integer.parseInt(textAccountNum.getText()));
                    } catch (NumberFormatException error) {
                        JOptionPane.showMessageDialog(null,
                                "The following must be numbers:\n Account Balance\n Monthly Fee\n Account Number",
                                "Empty Fields", JOptionPane.WARNING_MESSAGE);
                    }
                    try {
                        newAccount.setDateOpened(

                                parseTimestamp(textDateOpened.getText())
                        );

                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    newAccount.setOwner(textAccountOwner.getText());
                    bankModel.addAccount(newAccount);
                    clearAllTextFields();
                }

                else if (bankModel.getAccount(sRow) instanceof SavingsAccount) {
                    SavingsAccount newAccount = (SavingsAccount) bankModel.updateRow(sRow);
                    try {
                        newAccount.setBalance(Double.parseDouble(textAccountBal.getText()));
                        newAccount.setInterestRate(Double.parseDouble(textInterestRate.getText()));
                        newAccount.setMinBalance(Double.parseDouble(textMinBal.getText()));
                        newAccount.setNumber(Integer.parseInt(textAccountNum.getText()));
                    } catch (NumberFormatException error) {
                        JOptionPane.showMessageDialog(null,
                                "The following must be numbers:\n Account Balance\n Interest Rate\n Min Balance\n Account Number",
                                "Empty Fields", JOptionPane.WARNING_MESSAGE);
                    }
                    try {
                        newAccount.setDateOpened(

                                parseTimestamp(textDateOpened.getText())
                        );

                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    newAccount.setOwner(textAccountOwner.getText());
                    bankModel.addAccount(newAccount);
                    clearAllTextFields();
                }
            } else if (source == clear) {
                clearAllTextFields();
                bankModel.clearAllAccounts();
            }
            else if(source == saveBinary) {
                bankModel.saveAsBinary("./persist/BankModel.bin");
            }
            else if(source == loadBinary) {
                bankModel.loadFromBinary();
            }
            else if(source == saveXML) {
                bankModel.saveAsXML("./persist/BankModel.xml");
            }
            else if(source == loadXML) {
                bankModel.loadFromXML();
            }
            else if(source == byAccountNum) {
                bankModel.sortByAccountNumber();
            } else if (source == byAccountOwner) {
                bankModel.sortByAccountOwner();
            } else if (source == byDateOpened) {
                bankModel.sortByDateOpened();
            } else if (source == loadText) {
                try {
                    bankModel.loadFromText();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }

            else if (source == saveText) {
                try {
                    bankModel.saveAsText("./persist/BankModel.txt");
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }

        }
    }
}
