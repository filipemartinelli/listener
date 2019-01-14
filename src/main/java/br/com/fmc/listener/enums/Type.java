package br.com.fmc.listener.enums;

import java.util.stream.Stream;


public enum Type {

    SALESMAN("001"),
    CLIENT("002"),
    SALE("003");

    private final String code;

    Type(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

	public static boolean isValid(String string) {
		return Stream.of(values()).filter(x -> x.code.equalsIgnoreCase(string)).findAny().orElse(null) != null;
	}
	
    public static Type getValue(String code) {
        return Stream.of(values()).filter(e -> e.code.equals(code)).findFirst()
                .orElse(null);
    }
}
