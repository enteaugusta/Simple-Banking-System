package banking;

public class Account {
	
	private final String accountNumber;
	private final String pin;
	private final double balance;
	private static Database cardDB;
	
	public Account(String accountNumber, String pin, double balance) {
		this(accountNumber, pin, balance, cardDB);
	}
	
	public Account(String accountNumber, String pin, double balance,
			Database database) {
		this.accountNumber = accountNumber;
		this.pin = pin;
		cardDB = database;
		this.balance = 0.0;
	}
	
	public String getAccountNumber() {
		return accountNumber;
	}
	
	public String getPin() {
		return pin;
	}
	
	public double getBalance() {
		return balance;
	}
}

