package project3;

import java.io.Serializable;
import java.util.GregorianCalendar;

/**
 * Account serves as a base class for shared implementation between
 * CheckingAccount and SavingsAccount.
 */
public abstract class Account implements Serializable {
	private static final long serialVersionUID = 1L;
	private int number;
	private String owner;
	private GregorianCalendar dateOpened;
	private double balance;
	
	public Account(){
		
	}
	
	public Account(int number, String owner, GregorianCalendar dateOpened, double balance) {
		this.number = number;
		this.owner = owner;
		this.dateOpened = dateOpened;
		this.balance = balance;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public GregorianCalendar getDateOpened() {
		return dateOpened;
	}
	public void setDateOpened(GregorianCalendar dateOpened) {
		this.dateOpened = dateOpened;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	//TODO: fix this toString()
	@Override
	public String toString() {
		return "Account [number=" + number + ", owner=" + owner + ", dateOpened=" + dateOpened + ", balance=" + balance
				+ "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(balance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((dateOpened == null) ? 0 : dateOpened.hashCode());
		result = prime * result + number;
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		return result;
	}
	
	//FIX THIS EQUALS
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (Double.doubleToLongBits(balance) != Double.doubleToLongBits(other.balance))
			return false;
		if (dateOpened == null) {
			if (other.dateOpened != null)
				return false;
		} else if (!dateOpened.equals(other.dateOpened))
			return false;
		if (number != other.number)
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		return true;
	}
}
