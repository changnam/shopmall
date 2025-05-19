package com.honsoft.shopmall.service;

public enum JwtName {
	JWT("JWT"), REFRESH("Refresh");

	private final String value;

	JwtName(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	// Optional: Convert from string to enum
	public static JwtName fromValue(String value) {
		for (JwtName type : values()) {
			if (type.getValue().equalsIgnoreCase(value)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Unknown ProductType: " + value);
	}

	@Override
	public String toString() {
		return value;
	}
}
