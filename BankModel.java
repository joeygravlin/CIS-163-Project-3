package project3;

import javax.swing.table.AbstractTableModel;
import java.io.*;
import java.util.ArrayList;

public class BankModel extends AbstractTableModel {

    private ArrayList<Account> aList;
    private String[] columnNames;

    public BankModel() {
        aList = new ArrayList<>();
        columnNames = new String[7];

        columnNames[0] = "Number";
        columnNames[1] = "Date Opened";
        columnNames[2] = "Account Owner";
        columnNames[3] = "Current Balance";
        columnNames[4] = "Monthly Fee";
        columnNames[5] = "Interest Rate";
        columnNames[6] = "Min Balance";
    }

    
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        aList.get(rowIndex);
    }

    public void addAccount(Account e) {
        aList.add(e);
        fireTableRowsInserted(0, aList.size());
    }
    
    public void addAccountAtIndex(Account e, int row){
    	aList.add(row, e);
    	fireTableRowsInserted(0, aList.size());
    }

    public void removeAccount(int e) {
        aList.remove(e);
        fireTableRowsDeleted(0, aList.size());
    }

    public Account getAccount(int i) {
        return aList.get(i);
    }

    public int getSize() {
        return aList.size();
    }
    
    public void clearAllAccounts() {
        aList.clear();
        fireTableRowsDeleted(0, aList.size());
    }

    public Account updateRow(int row) {
    	if(aList.get(row) instanceof CheckingAccount){
    		aList.remove(row);
    		fireTableRowsDeleted(0, aList.size());
    		CheckingAccount newChecking = new CheckingAccount();
    		return newChecking;
    	}
    	else if(aList.get(row) instanceof SavingsAccount){
    		aList.remove(row);
    		fireTableRowsDeleted(0, aList.size());
    		SavingsAccount newSavings = new SavingsAccount();
    		return newSavings;
    	}
    	else return null;
    }

    // TODO: fix me
    public void saveAsBinary(String fileName) {
        try{
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(aList);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveAsText(String filename) throws IOException{
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename + ".txt")));
		for (int i=0; i<aList.size(); i++){
			out.println("1");
			out.println(aList.get(i).getNumber());
			out.println(aList.get(i).getOwner());
			//out.println(aList.get(i).getDateOpened());
			out.println(aList.get(i).getBalance()); 
			out.println(aList.get(i).getMonthlyFee());
			out.println(aList.get(i).getInterestRate());
			out.println(aList.get(i).getMinBalance());
		}
		out.close();

	}

    public void saveAsXML() {

    }

    @SuppressWarnings("unchecked")
    public void loadFromBinary() {
           try {
               FileInputStream fin = new FileInputStream("C:/Users/Taylor/Desktop/tester/hello.ser");
               ObjectInputStream ois = new ObjectInputStream(fin);
               aList = (ArrayList<Account>) ois.readObject();

               System.out.println(aList);
               fireTableRowsInserted(0, aList.size());
               ois.close();

           } catch(Exception ex) {
               ex.printStackTrace();
           }
    }

    public void loadFromText(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename + ".txt"));
        String line = reader.readLine();
            fireTableRowsDeleted(0, aList.size());
            while (line != null){
                {
                    CheckingAccount accountItems;
                    String num = (reader.readLine());
					String accountOwner = (reader.readLine());	
					//GregorianCalendar  openDate; /*= reader.readLine();
					String currentBalance = (reader.readLine());
					String monthlyFee = (reader.readLine());
					String interestRate = (reader.readLine());
					String minBalance = (reader.readLine());
                    accountItems = new CheckingAccount(Integer.parseInt(num), accountOwner, Double.parseDouble(currentBalance), Double.parseDouble(monthlyFee), Double.parseDouble(interestRate), Double.parseDouble(minBalance));
                    aList.add(accountItems);
                }
            
                
            }
            fireTableRowsInserted(0, aList.size());
            reader.close();
    }

    public void loadFromXML() {

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
        switch(col) {
        case 0:
            return(aList.get(row).getNumber());

        case 1:
            return(aList.get(row).getDateOpened());
//            if (DateFormat.getDateInstance(DateFormat.SHORT).format(
//                    aList.get(row).getBought())) {
//            }

        case 2:
            return(aList.get(row).getOwner());

        case 3:
            return(aList.get(row).getBalance());

        case 4:
            if (aList.get(row) instanceof CheckingAccount) {
                return ((CheckingAccount) aList.get(row)).getMonthlyFee();
            }
            else return "";
        case 5:
            if (aList.get(row) instanceof SavingsAccount) {
                return ((SavingsAccount) aList.get(row)).getInterestRate();
            }
            else return "";
        case 6:
            if (aList.get(row) instanceof SavingsAccount) {
                return ((SavingsAccount) aList.get(row)).getMinBalance();
            }
            else return "";
        }
        return col;
    }

    public void sortByAccountNumber() {
        aList.sort(new accountNumberComparison());
        fireTableRowsInserted(0, aList.size());
    }

    public void sortByAccountOwner() {
        aList.sort(new accountOwnerComparison());
        fireTableRowsInserted(0, aList.size());
    }

    //fix me
    public void sortByDateOpened() {
        aList.sort(new accountDateComparison());
        fireTableRowsInserted(0, aList.size());
    }
}
