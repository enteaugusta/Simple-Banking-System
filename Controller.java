package banking;


import java.util.Random;

import static banking.Banking.CHOOSING_AN_ACTION;
import static banking.Banking.CREATE_ACCOUNT;
import static banking.Banking.EXIT;
import static banking.Banking.LOGIN;
import static banking.UserActions.getUserAction;


public class Controller {
	
	private final Database cardDB;
	private final Random random = new Random();
	private final Utils utils = new Utils();
	private Banking banking = CHOOSING_AN_ACTION;
	
	public Controller(Database database) {
		cardDB = database;
	}
	
	public void runBankingSystem() {
		boolean loopState = true;
		int userAction;
		UserActions userActions = new UserActions(cardDB);
		
		while (loopState) {
			userAction = getUserAction();
			if (userAction == 1){
				 banking = CREATE_ACCOUNT;
			} else if (userAction == 2)
			{ banking = LOGIN;
			} else banking = EXIT;
			
			switch (banking) {
				case CREATE_ACCOUNT:
					createUserAccount();
					break;
				case LOGIN:
					userActions.logInActions();
					break;
				case EXIT:
					System.out.println();
					System.out.println("Bye!");
					loopState = false;
					break;
			}
		}
	}
	
	
	private void createUserAccount() {
		System.out.println("Your card has been created.");
		LuhnAlgorithm number = new LuhnAlgorithm();
		String accountNumber = number.getCheckDigit();
		String pin = generatePin();
		double balance = 0.0;
		Account card = new Account(accountNumber, pin, balance);
		cardDB.add(card);
		
		String outputNumbers = accountNumber;
		System.out.println("Your card number is:");
		System.out.println(outputNumbers);
		System.out.println("Your card PIN is:");
		System.out.println(cardDB.getAccountInformation(outputNumbers).getPin());
	}
	
	
	private String generatePin() {
		StringBuilder pin = new StringBuilder(Integer.toString(random.nextInt(9999)));
		return utils.addDigits(pin, 3);
	}
	
}
