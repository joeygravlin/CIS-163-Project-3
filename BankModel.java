package project3;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.table.AbstractTableModel;

public class BankModel extends AbstractTableModel{
	
	private ArrayList<Account> aList = new ArrayList<Account>();
	private String[] columnNames = new String[5];
	
	public BankModel(){
		columnNames[0] = "Number";
		columnNames[1] = "Date Opened";
		columnNames[2] = "Account Owner";
		columnNames[3] = "Current Balance";
		columnNames[4] = "Monthly Fee";
	}

	public void addAccount(Account e){
		aList.add(e);
		fireTableRowsInserted(0, aList.size());
	}
	
	public void removeAccount(int e){
		aList.remove(e);
		fireTableRowsDeleted(0, aList.size());
	}
	
	public Account getAccount(int i){
		return aList.get(i);
	}
	
	public int getSize(){
		return aList.size();
	}
	
	public void clearAllAccounts(){
		aList.clear();
		fireTableRowsDeleted(0, aList.size());
	}
	
	public void updateRow(int row){
		
	}
	
	public void isChecking(){
		String[] columnNames = new String[5];
		columnNames[0] = "Number";
		columnNames[1] = "Date Opened";
		columnNames[2] = "Account Owner";
		columnNames[3] = "Current Balance";
		columnNames[3] = "Monthly Fee";
	}
	
	public void isSavings(){
		String[] columnNames = new String[6];
		columnNames[0] = "Number";
		columnNames[1] = "Date Opened";
		columnNames[2] = "Account Owner";
		columnNames[3] = "Current Balance";
		columnNames[4] = "Interest Rate";
		columnNames[5] = "Min Balance";
		//getColumnCount();
		//System.out.println(getColumnCount());
	}
	
	
	//fix me
	public void saveAsBinary(String fileName){
		try{
			FileOutputStream fos = new FileOutputStream(fileName);
			ObjectOutputStream os = new ObjectOutputStream(fos);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void saveAsText(){
		
	}
	
	public void saveAsXML(){
		
	}
	public void loadFromBinary(){
		
	}
	
	public void loadFromText(){
		
	}
	
	public void loadFromXML(){
		
	}
	
	@Override
	public String getColumnName(int col) {
	    return columnNames[col];
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	@Override
	public int getRowCount() {
		return aList.size();
	}
	@Override
	public Object getValueAt(int row, int col) {
		// TODO Auto-generated method stub
		switch(col){
		case 0:
		return(aList.get(row).getNumber());
		
		case 1:
			return(aList.get(row).getDateOpened());
			/*
			if(DateFormat.getDateInstance(DateFormat.SHORT).format(aList.get(row).getBought()){
				
			}*/
		
		case 2:
			return(aList.get(row).getOwner());
			
			
		case 3:
			return(aList.get(row).getBalance());
			
		case 4:
			if(aList.get(row) instanceof CheckingAccount){
				return ((CheckingAccount) aList.get(row)).getMonthlyFee();
			}
			if(aList.get(row) instanceof SavingsAccount){
				return ((SavingsAccount) aList.get(row)).getInterestRate();
			}
		case 5:
			if(aList.get(row) instanceof SavingsAccount){
				return ((SavingsAccount) aList.get(row)).getMinBalance();
			}
		}
		return col;
	}
}
