package project3;

import java.util.Comparator;

public class accountDateComparison implements Comparator<Account>{

    @Override
    public int compare(Account o1, Account o2) {
        return o1.getDateOpened().compareTo(o2.getDateOpened());
    }

}
