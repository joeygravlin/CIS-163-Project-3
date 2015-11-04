package project3;

import java.util.Comparator;

public class accountOwnerComparison implements Comparator<Account> {

    @Override
    public int compare(Account o1, Account o2) {
        String name1 = o1.getOwner();
        String name2 = o2.getOwner();
        return name1.compareTo(name2);
    }

}
