package at.irsigler.parser;

public enum CountryCode {
    BURGENLAND("1"), KAERNTEN("2"), NIEDEROESTERREICH("3"), OBEROESTERREICH("4"), SALZBURG("5"), STEIERMARK("6"), TIROL("7"), VORARLBERG("8"), WIEN("9");

    private String code;

    CountryCode(String code) {
        this.code = code;
    }

    public static CountryCode lookup(String codeString) {
        for (CountryCode countryCode : values()) {
            if (countryCode.code.equals(codeString)) {
                return countryCode;
            }
        }
        throw new IllegalArgumentException("CountryCode " + codeString + " unknown.");
    }
}
