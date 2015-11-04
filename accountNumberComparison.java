package project3;

import java.util.Comparator;

public class accountNumberComparison implements Comparator<Account>{

    @Override
    public int compare(Account o1, Account o2) {
        // TODO Auto-generated method stub
        //return o1.getOwner() - o2.getOwner();
        return o1.getNumber() - o2.getNumber();
    }

}
