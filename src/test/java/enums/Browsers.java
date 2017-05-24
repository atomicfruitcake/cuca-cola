package enums;

public enum Browsers {
	FIREFOX,
	IE, 
	CHROME;

	public static Browsers getBrowser(String browser)
			throws IllegalArgumentException {
		for (Browsers b : values()) {
			if (b.toString().equalsIgnoreCase(browser)) {
				return b;
			}
		}
		throw browserNotFound(browser);
	}

	private static IllegalArgumentException browserNotFound(String outcome) {
		return new IllegalArgumentException(
				("Invalid browser [" + outcome + "]"));
	}

}
