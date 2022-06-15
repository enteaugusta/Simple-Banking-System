package banking;

public class Utils {
	
	public String addDigits(StringBuilder str, int limit) {
		while (str.length() <= limit) {
			str.insert(0, "0");
		}
		return str.toString();
	}
}
