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
		// TODO Auto-generated constructor stub
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

	
	//add equals and toString
}
