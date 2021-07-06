package com.github.sandrolaxx.dfmicroservices.entities.enums;

public enum EnumOrderStatus {

    INDIVIDUAL("INDIVIDUAL", "Individual"),    
    THREE_PEOPLE("THREE_PEOPLE", "3 Pessoas");    
    
    private final String key;
    private final String value;

    private EnumOrderStatus(String key, String value) {
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
        
        for (EnumOrderStatus enumValue : EnumOrderStatus.values()) {
            if (enumValue.getKey().equalsIgnoreCase(size)) {
                return true;
            }
        }
        
        return false;
    }
    
}
