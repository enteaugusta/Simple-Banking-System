package banking;


import java.util.Objects;
import java.util.Random;

import static banking.UserActionsController.getUserAction;


public class Controller {
	
	private final Database cardDB;
	private final Random random = new Random();
	private final Utils utils = new Utils();
	
	public Controller(Database database) {
		cardDB = database;
	}
	
	public void runBankingSystem() {
		boolean loopState = true;
		int userAction;
		UserActionsController userActions = new UserActionsController(cardDB);
		
		while (loopState) {
			userAction = getUserAction();
			Banking banking = Banking.getEnumFromId(userAction);
			switch (Objects.requireNonNull(banking)) {
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
		
		System.out.println("Your card number is:");
		System.out.println(accountNumber);
		System.out.println("Your card PIN is:");
		System.out.println(cardDB.getAccountInformation(accountNumber).getPin());
	}
	
	
	private String generatePin() {
		StringBuilder pin = new StringBuilder(Integer.toString(random.nextInt(9999)));
		return utils.addDigits(pin, 3);
	}
	
}
