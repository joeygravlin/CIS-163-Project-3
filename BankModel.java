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

    /**
     *  Our data model is editable, so let's implement this thaaaang!
     *
     *  @param  aValue   value to assign to cell
     *  @param  rowIndex   row of cell
     *  @param  columnIndex  column of cell
     */
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        aList.get(rowIndex);
    }

    public void addAccount(Account e) {
        aList.add(e);
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

    public void updateRow(int row) {

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

    public void saveAsText() {

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

    public void loadFromText() {

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
