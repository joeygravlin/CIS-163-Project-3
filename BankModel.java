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

    /** A collection of all Accounts stored in this model */
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
     * Adds an Account to the end of aList, then notifies view
     * about the insertion.
     *
     * @param account the Account to be added
     *****************************************************************/
    public void addAccount(Account account) {
        aList.add(account);
        fireTableRowsInserted(0, aList.size());
    }

    /*****************************************************************
     * Adds an Account to the specified position in aList,
     * then notifies view of the insertion.
     *
     * @param account   the Account to be added
     * @param row       index at which the specified Account
     *                  is to be inserted
     *****************************************************************/
    public void addAccountAtIndex(Account account, int row) {
        aList.add(row, account);
        fireTableRowsInserted(0, aList.size());
    }

    /*****************************************************************
     * Removes the Account at the specified position in this list.
     * Shifts any subsequent elements to the left (subtracts one from
     * their indices). Then notifies view of removal.
     *
     * @param index the index of the element to be removed
     *****************************************************************/
    public void removeAccount(int index) {
        aList.remove(index);
        fireTableRowsDeleted(0, aList.size());
    }

    /*****************************************************************
     * Returns the Account at the specified position in this list.
     *
     * @param index index of the Account to return
     * @return the Account at the specified position in this list
     *****************************************************************/
    public Account getAccount(int index) {
        return aList.get(index);
    }

    /*****************************************************************
     * Returns the number of Accounts in this list.
     *
     * @return the number of Accounts in this list
     *****************************************************************/
    public int getSize() {
        return aList.size();
    }

    /*****************************************************************
     * Removes all of the Accounts from this list. The list will be
     * empty after this call returns. View is notified also.
     *****************************************************************/
    public void clearAllAccounts() {
        aList.clear();
        fireTableRowsDeleted(0, aList.size());
    }

    /*****************************************************************
     * Updates the Account at the specified row according to the new
     * values provided in the user input fields.
     *
     * @param row row index of the Account to update
     * @return the Account to be stored at the specified position
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
     * Saves current aList state to a binary encoded file fileName.
     *
     * @param fileName the path/filename to write out
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
     * Saves current aList state to a plaintext file fileName.
     *
     * @param fileName the path/filename to write out
     * @throws IOException if unable to write file
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
     * Saves current aList state to an XML encoded file fileName.
     * Uses the following schema: size of aList written to first
     * element, followed by elements for each Account within aList.
     *
     * @param fileName the path/filename to write out
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
     * Loads Accounts into aList from a binary encoded file,
     * then notifies the view.
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
     * Loads Accounts into aList from a plaintext file,
     * then notifies the view.
     *
     * @throws IOException if file does not exist
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
     * Loads Accounts into aList from an XML encoded file,
     * then notifies the view.
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
     * Returns the name of the column at the specified position.
     *
     * @param col the column index who's valued is to be queried
     * @return the name of the column at the specified position
     *****************************************************************/
    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    /*****************************************************************
     * Returns the number of columns in BankModel.
     *
     * @return the number of columns in BankModel
     *****************************************************************/
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    /*****************************************************************
     * Returns the number of rows in BankModel, which also corresponds
     * with the number of Accounts in aList.
     *
     * @return the number of rows in BankModel
     *****************************************************************/
    @Override
    public int getRowCount() {
        return aList.size();
    }

    /*****************************************************************
     * Returns the value for the cell at <code>column</code> and
     * <code>row</code>.
     *
     * @param   row        the row whose value is to be queried
     * @param   column     the column whose value is to be queried
     * @return  the value Object at the specified cell
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
     * Sorts aList numerically by Account Number.
     *****************************************************************/
    public void sortByAccountNumber() {
        aList.sort(new accountNumberComparison());
        fireTableRowsInserted(0, aList.size());
    }

    /*****************************************************************
     * Sorts aList alphabetically by Account Owner.
     *****************************************************************/
    public void sortByAccountOwner() {
        aList.sort(new accountOwnerComparison());
        fireTableRowsInserted(0, aList.size());
    }

    /*****************************************************************
     * Sorts aList chronologically by Date Opened.
     *****************************************************************/
    public void sortByDateOpened() {
        aList.sort(new accountDateComparison());
        fireTableRowsInserted(0, aList.size());
    }
}
