package project3;

import java.util.GregorianCalendar;

public class SavingsAccount extends Account{
	private static final long serialVersionUID = 1L;
	private double minBalance;
	private double interestRate;
	
	public SavingsAccount(){
		
	}
	
	public SavingsAccount(int number, String owner, GregorianCalendar dateOpened, double balance) {
		super(number, owner, dateOpened, balance);
	}

	public double getMinBalance() {
		return minBalance;
	}

	public void setMinBalance(double minBalance) {
		this.minBalance = minBalance;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "SavingsAccount [minBalance=" + minBalance + ", interestRate=" + interestRate + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(interestRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(minBalance);
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
		SavingsAccount other = (SavingsAccount) obj;
		if (Double.doubleToLongBits(interestRate) != Double.doubleToLongBits(other.interestRate))
			return false;
		if (Double.doubleToLongBits(minBalance) != Double.doubleToLongBits(other.minBalance))
			return false;
		return true;
	}
}
