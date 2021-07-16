package com.github.sandrolaxx.dfmicroservices.entities.enums;


public enum EnumPlateCategory {

    LOW_CARB("LOW_CARB", "Low Carb"),    
    TRANS_FATS_FREE("TRANS_FATS_FREE", "Sem Gorduras Trans"),
    LACTOSE_FREE("LACTOSE_FREE", "Sem Lactose"),
    HIGH_PROTEIN("HIGH_PROTEIN", "Rico em Proteína"),    
    NO_GLUTEN("NO_GLUTEN", "Sem Glúten"),    
    VEGETARIAN("VEGETARIAN", "Vegerariano"); 

    private final String key;
    private final String value;

    private EnumPlateCategory(String key, String value) {
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
        
        for (EnumPlateCategory enumValue : EnumPlateCategory.values()) {
            if (enumValue.getKey().equalsIgnoreCase(size)) {
                return true;
            }
        }
        
        return false;
    }

    public static EnumPlateCategory fromString(String srtEnum) {

        for (EnumPlateCategory enumValue : EnumPlateCategory.values()) {
            if (enumValue.getKey().equalsIgnoreCase(srtEnum)) {
                return enumValue;
            }
        }

        return null;

    }
    
}
