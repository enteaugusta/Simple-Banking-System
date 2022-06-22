package banking;

public enum Banking {
    CREATE_ACCOUNT(1),
	LOGIN(2),
	LOG_OUT(5),
	CLOSE_ACCOUNT(4),
	DO_TRANSFER(3),
	ADD_INCOME(2),
	BALANCE(1),
	EXIT(0);
	
	private final int id;
	
	Banking(int id) {
		this.id = id;
	}
	
	public static Banking getEnumfromId(int id) {
		for (Banking type : values()) {
			if (type.getId() == id) {
				return type;
			}
		}
		return null;
	}
	
	public int getId() {
		return id;
	}
}

