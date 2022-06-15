package banking;


import java.util.concurrent.ThreadLocalRandom;

public class LuhnAlgorithm {
	
	private final Utils utils = new Utils();
	
	
	public String getCheckDigit() {
		String bin = "400000";
		String number = bin + generateCustomerNumber();
		int checkDigit = luhnAlgorithm(number);
		return number + checkDigit;
	}
	
	
	 int luhnAlgorithm(String number) {
		int sumOddNumbers = 0;
		int sumEvenNmbers = 0;
		for (int i = 0; i < number.length(); i += 2) {
			int multiByTwo = Integer.parseInt(String.valueOf(number.charAt(i))) * 2;
			int checkIfBiggerThenNine = multiByTwo > 9 ? (multiByTwo - 9) : multiByTwo;
			sumOddNumbers += checkIfBiggerThenNine;
		 }
		for (int i = 1; i < number.length(); i += 2) {
			sumEvenNmbers += Integer.parseInt(String.valueOf(number.charAt(i)));
		}
		int sum = sumEvenNmbers + sumOddNumbers;
		int rest = sum % 10;
		return (10 - (rest)) % 10;
	}
	
	
	StringBuilder generateCustomerNumber() {
		StringBuilder customerNumber = new StringBuilder(Long.toString(ThreadLocalRandom.current().nextLong(1000000000)));
		
		return new StringBuilder(utils.addDigits(customerNumber, 8));
	}
}
