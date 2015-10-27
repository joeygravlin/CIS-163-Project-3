package project3;

import java.util.GregorianCalendar;

public class CheckingAccount extends Account{
	private static final long serialVersionUID = 1L;
	private double monthlyFee;

	public CheckingAccount(int number, String owner, GregorianCalendar dateOpened, double balance) {
		super(number, owner, dateOpened, balance);
		// TODO Auto-generated constructor stub
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
	
	
	//need equals and toString
}
