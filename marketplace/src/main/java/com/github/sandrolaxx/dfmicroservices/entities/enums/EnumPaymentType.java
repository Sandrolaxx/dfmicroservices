package com.github.sandrolaxx.dfmicroservices.entities.enums;

public enum EnumPaymentType {

    INDIVIDUAL("INDIVIDUAL", "Individual"),    
    THREE_PEOPLE("THREE_PEOPLE", "3 Pessoas");    
    
    private final String key;
    private final String value;

    private EnumPaymentType(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
 
    public static Boolean containsSize(String size) {
        
        for (EnumPaymentType enumValue : EnumPaymentType.values()) {
            if (enumValue.getKey().equalsIgnoreCase(size)) {
                return true;
            }
        }
        
        return false;
    }
    
}
