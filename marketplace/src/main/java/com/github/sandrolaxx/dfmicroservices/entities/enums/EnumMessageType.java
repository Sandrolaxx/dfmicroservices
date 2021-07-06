package com.github.sandrolaxx.dfmicroservices.entities.enums;

public enum EnumMessageType {

    CREATE("CREATE"),    
    UPDATE("UPDATE"), 
    DELETE("DELETE"), 
    CREATE_ADDRESS("CREATE_ADDRESS"),    
    UPDATE_ADDRESS("UPDATE_ADDRESS"),    
    DELETE_ADDRESS("DELETE_ADDRESS"); 

    private final String key;

    private EnumMessageType(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
 
    public static EnumMessageType getEnumIfExists(String e) {
        
        for (EnumMessageType enumValue : EnumMessageType.values()) {
            if (enumValue.getKey().equalsIgnoreCase(e)) {
                return enumValue;
            }
        }
        
        return null;
    }
    
}