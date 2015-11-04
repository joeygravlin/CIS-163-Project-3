package project3;

import java.util.GregorianCalendar;

public class CheckingAccount extends Account {
    private static final long serialVersionUID = 1L;
    private double monthlyFee;

    public CheckingAccount() {
    }
    
    public CheckingAccount(int number, String owner,  double balance, double monthlyFee, double interestRate, double minBalance) {
		super(number, owner, balance, monthlyFee, interestRate, minBalance );

	}
    
    public CheckingAccount(int number, String owner, GregorianCalendar dateOpened, double balance) {
        super(number, owner, dateOpened, balance);
    }

    public double getMonthlyFee() {
        return monthlyFee;
    }

    public void setMonthlyFee(double monthlyFee) {
        this.monthlyFee = monthlyFee;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "CheckingAccount [monthlyFee=" + monthlyFee + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        long temp;
        temp = Double.doubleToLongBits(monthlyFee);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        CheckingAccount other = (CheckingAccount) obj;
        if (Double.doubleToLongBits(monthlyFee) != Double.doubleToLongBits(other.monthlyFee))
            return false;
        return true;
    }
}
