package project3;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class BankGUI {
	
	private JButton add, delete, update, clear;
    private JTextField textAccountNum, textAccountOwner, textDateOpened, textAccountBal, textMonthlyFee, textInterestRate, textMinBal;
    private JLabel labelAccountNum, labelAccountOwner, labelDateOpened, labelAccountBal, labelMonthlyFee, labelInterestRate, labelMinBal;
    private JScrollPane scrollPane;
    private JTable table;
    private JFrame frame;
    private JRadioButton checking, savings;
    private JPanel panelTop, panelBottom, panelBottomLeft, panelBottomRight;
    private JMenuBar toolBar;
    private JMenu file, sort;
    private JMenuItem loadBinary, saveBinary, loadText, saveText, loadXML, saveXML, quit, byAccountNum, byAccountOwner, byDateOpened;
    private ButtonListener listener;
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BankGUI bank = new BankGUI();
	}
	
	public BankGUI()
	{
		frame = new JFrame();
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        listener = new ButtonListener();
        
        panelTop = new JPanel();
        panelBottom = new JPanel();
        //Top panel
        scrollPane = new JScrollPane();
        table = new JTable();
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setViewportView(table);        
        frame.add(scrollPane, BorderLayout.CENTER);
        
        //Bottom panel    
        panelBottom = new JPanel();
        panelBottom.setLayout(new BorderLayout());
        
        panelBottomLeft = setUpBottomPanel();      
        panelBottomRight = setUpRightPanel();
        
        panelBottom.add(panelBottomLeft, BorderLayout.CENTER);  
        panelBottom.add(panelBottomRight, BorderLayout.EAST);
        
        //Add toolbar
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
        file.add(loadText);
        file.add(saveText);
        file.add(loadXML);
        file.add(saveXML);
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
	
	private JPanel setUpBottomPanel()
	{
		JPanel panelBottom = new JPanel();
		panelBottom.setLayout(new BoxLayout(panelBottom, BoxLayout.Y_AXIS));
        
        JPanel panel8 = new JPanel();
        ButtonGroup group = new ButtonGroup();
        checking = new JRadioButton("Checking");
        savings = new JRadioButton("Savings");
        group.add(checking);
        group.add(savings);
        panel8.add(checking);
        panel8.add(savings);
        
        JPanel panel1 = new JPanel(); 
        labelAccountNum = new JLabel("Account Number: ");
        textAccountNum = new JTextField(20);
        panel1.add(labelAccountNum);
        panel1.add(Box.createRigidArea(new Dimension(35,0)));
        panel1.add(textAccountNum);

        JPanel panel2 = new JPanel();  
        labelAccountOwner = new JLabel("Account Owner: ");
        textAccountOwner = new JTextField(20); 
        panel2.add(labelAccountOwner);
        panel2.add(Box.createRigidArea(new Dimension(42,0)));
        panel2.add(textAccountOwner);

        JPanel panel3 = new JPanel(); 
        labelDateOpened = new JLabel("Date Opened: ");
        textDateOpened = new JTextField(20);
        panel3.add(labelDateOpened);
        panel3.add(Box.createRigidArea(new Dimension(57,0)));
        panel3.add(textDateOpened);

        JPanel panel4 = new JPanel();
        labelAccountBal = new JLabel("Account Balance: ");
        textAccountBal = new JTextField(20);
        panel4.add(labelAccountBal);
        panel4.add(Box.createRigidArea(new Dimension(35,0)));
        panel4.add(textAccountBal);

        JPanel panel5 = new JPanel();        
        labelMonthlyFee = new JLabel("Monthly Fee: ");
        textMonthlyFee = new JTextField(20);
        panel5.add(labelMonthlyFee);
        panel5.add(Box.createRigidArea(new Dimension(62,0)));
        panel5.add(textMonthlyFee);

        JPanel panel6 = new JPanel();           
        labelInterestRate = new JLabel("Interest Rate: ");
        textInterestRate = new JTextField(20);
        panel6.add(labelInterestRate);
        panel6.add(Box.createRigidArea(new Dimension(57,0)));
        panel6.add(textInterestRate);

        JPanel panel7 = new JPanel();           
        labelMinBal = new JLabel("Minimum Balance: ");
        textMinBal = new JTextField(20);
        panel7.add(labelMinBal);
        panel7.add(Box.createRigidArea(new Dimension(30,0)));
        panel7.add(textMinBal);
        
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

	private JPanel setUpRightPanel()
	{
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
		rightPanel.add(Box.createRigidArea(new Dimension(0,60)));
		rightPanel.add(add);
		rightPanel.add(Box.createRigidArea(new Dimension(0,15)));
		rightPanel.add(delete);
		rightPanel.add(Box.createRigidArea(new Dimension(0,15)));
		rightPanel.add(update);
		rightPanel.add(Box.createRigidArea(new Dimension(0,15)));
		rightPanel.add(clear);
		return rightPanel;
	}

	private class ButtonListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
		//Gets source of object clicked
		Object source = e.getSource();
		
		}
	}
	
}
