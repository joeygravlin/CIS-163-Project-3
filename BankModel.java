package project3;

import javax.swing.table.AbstractTableModel;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;


/*****************************************************************
 * BankModel is responsible for encapsulating the runtime state of
 * BankGUI, along with providing methods by which the GUI can
 * change the model's state. BankModel is also responsible for
 * persistence of the current runtime state via binary, text, and
 * XML formats.
 *
 * @author Taylor Cargill
 * @author Chris Deneef
 * @author Joe Gravlin
 * @version 11/04/2015
 *****************************************************************/
public class BankModel extends AbstractTableModel {

    /** A collection of all Accounts stored */
    private ArrayList<Account> aList;

    /** Names of columns in GUI table, corresponding to properties of
     * Accounts stored within aList */
    private String[] columnNames;

    /*****************************************************************
     * Default BankModel constructor
     *****************************************************************/
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

    /*****************************************************************
     * description
     *
     * @param aValue
     * @param rowIndex
     * @param columnIndex
     *****************************************************************/
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        aList.get(rowIndex);
    }

    /*****************************************************************
     * description
     *
     * @param e
     *****************************************************************/
    public void addAccount(Account e) {
        aList.add(e);
        fireTableRowsInserted(0, aList.size());
    }

    /*****************************************************************
     * description
     *
     * @param e
     * @param row
     *****************************************************************/
    public void addAccountAtIndex(Account e, int row) {
        aList.add(row, e);
        fireTableRowsInserted(0, aList.size());
    }

    /*****************************************************************
     * description
     *
     * @param e
     *****************************************************************/
    public void removeAccount(int e) {
        aList.remove(e);
        fireTableRowsDeleted(0, aList.size());
    }

    /*****************************************************************
     * description
     *
     * @param i
     * @return
     *****************************************************************/
    public Account getAccount(int i) {
        return aList.get(i);
    }

    /*****************************************************************
     * description
     *
     * @return
     *****************************************************************/
    public int getSize() {
        return aList.size();
    }

    /*****************************************************************
     * description
     *****************************************************************/
    public void clearAllAccounts() {
        aList.clear();
        fireTableRowsDeleted(0, aList.size());
    }

    /*****************************************************************
     * description
     *
     * @param row
     * @return
     *****************************************************************/
    public Account updateRow(int row) {
        if (aList.get(row) instanceof CheckingAccount) {
            aList.remove(row);
            fireTableRowsDeleted(0, aList.size());
            CheckingAccount newChecking = new CheckingAccount();
            return newChecking;
        } else if (aList.get(row) instanceof SavingsAccount) {
            aList.remove(row);
            fireTableRowsDeleted(0, aList.size());
            SavingsAccount newSavings = new SavingsAccount();
            return newSavings;
        } else
            return null;
    }

    /*****************************************************************
     * description
     *
     * @param fileName
     *****************************************************************/
    public void saveAsBinary(String fileName) {
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(aList);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*****************************************************************
     * description
     *
     * @param filename
     * @throws IOException
     *****************************************************************/
    public void saveAsText(String filename) throws IOException {
        PrintWriter out = new PrintWriter(
                new BufferedWriter(new FileWriter(filename))
        );
        for (int i = 0; i < aList.size(); i++) {
            out.println("1");
            out.println(aList.get(i).getNumber());
            out.println(aList.get(i).getOwner());
            // out.println(aList.get(i).getDateOpened());
            out.println(aList.get(i).getBalance());
            // ugh...
            out.println(aList.get(i).getMonthlyFee());
            out.println(aList.get(i).getInterestRate());
            out.println(aList.get(i).getMinBalance());
        }
        out.close();

    }

    /*****************************************************************
     * description
     *
     * @param fileName
     *****************************************************************/
    public void saveAsXML(String fileName) {
        try {
            XMLEncoder encoder = new XMLEncoder(
                new BufferedOutputStream(
                    new FileOutputStream(fileName)
            ));

            encoder.writeObject(aList.size());
            aList.forEach((Account a) -> {
                encoder.writeObject(a);
            });
            encoder.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*****************************************************************
     * description
     *****************************************************************/
    @SuppressWarnings("unchecked")
    public void loadFromBinary() {
        try {
            FileInputStream fin = new FileInputStream(
                    "./persist/BankModel.bin"
            );
            ObjectInputStream ois = new ObjectInputStream(fin);
            aList.clear();
            aList = (ArrayList<Account>) ois.readObject();

            System.out.println(aList);
            fireTableRowsInserted(0, aList.size());
            ois.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /*****************************************************************
     * description
     *
     * @throws IOException
     *****************************************************************/
    public void loadFromText() throws IOException {
        BufferedReader reader = new BufferedReader(
                new FileReader("./persist/BankModel.txt")
        );
        String line = reader.readLine();
        fireTableRowsDeleted(0, aList.size());
        while (line != null) {
            {
                CheckingAccount accountItems;
                String num = (reader.readLine());
                String accountOwner = (reader.readLine());
                // GregorianCalendar openDate; /*= reader.readLine();
                String currentBalance = (reader.readLine());
                String monthlyFee = (reader.readLine());
                String interestRate = (reader.readLine());
                String minBalance = (reader.readLine());
                accountItems = new CheckingAccount(
                        Integer.parseInt(num),
                        accountOwner,
                        Double.parseDouble(currentBalance),
                        Double.parseDouble(monthlyFee),
                        Double.parseDouble(interestRate),
                        Double.parseDouble(minBalance)
                );
                aList.add(accountItems);
            }

        }
        fireTableRowsInserted(0, aList.size());
        reader.close();
    }

    /*****************************************************************
     * description
     *****************************************************************/
    public void loadFromXML() {
        try {
            XMLDecoder decoder = new XMLDecoder(
                new BufferedInputStream(
                    new FileInputStream("./persist/BankModel.xml")
            ));

            int listSize = (int) decoder.readObject();
            while (listSize > 0) {
                Account a = (Account) decoder.readObject();
                aList.add(a);
                listSize--;
            }
            fireTableRowsInserted(0, aList.size());
            decoder.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*****************************************************************
     * description
     *****************************************************************/
    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    /*****************************************************************
     * description
     *****************************************************************/
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    /*****************************************************************
     * description
     *****************************************************************/
    @Override
    public int getRowCount() {
        return aList.size();
    }

    /*****************************************************************
     * description
     *****************************************************************/
    @Override
    public Object getValueAt(int row, int col) {
        switch (col) {
        case 0:
            return (aList.get(row).getNumber());

        // TODO: Fix me
        case 1:
            return (aList.get(row).getDateOpened());
        // if (DateFormat.getDateInstance(DateFormat.SHORT).format(
        // aList.get(row).getBought())) {
        // }

        case 2:
            return (aList.get(row).getOwner());

        case 3:
            return (aList.get(row).getBalance());

        case 4:
            if (aList.get(row) instanceof CheckingAccount) {
                return ((CheckingAccount) aList.get(row)).getMonthlyFee();
            } else
                return "";
        case 5:
            if (aList.get(row) instanceof SavingsAccount) {
                return ((SavingsAccount) aList.get(row)).getInterestRate();
            } else
                return "";
        case 6:
            if (aList.get(row) instanceof SavingsAccount) {
                return ((SavingsAccount) aList.get(row)).getMinBalance();
            } else
                return "";
        }
        return col;
    }

    /*****************************************************************
     * description
     *****************************************************************/
    public void sortByAccountNumber() {
        aList.sort(new accountNumberComparison());
        fireTableRowsInserted(0, aList.size());
    }

    /*****************************************************************
     * description
     *****************************************************************/
    public void sortByAccountOwner() {
        aList.sort(new accountOwnerComparison());
        fireTableRowsInserted(0, aList.size());
    }

    /*****************************************************************
     * description
     *****************************************************************/
    public void sortByDateOpened() {
        aList.sort(new accountDateComparison());
        fireTableRowsInserted(0, aList.size());
    }
}
