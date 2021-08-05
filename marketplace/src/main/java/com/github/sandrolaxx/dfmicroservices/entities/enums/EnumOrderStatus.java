package com.github.sandrolaxx.dfmicroservices.entities.enums;

public enum EnumOrderStatus {

    AWAITING_PAYMENT("AWAITING_PAYMENT", "Aguardando Pagamento"),    
    AWAITING_DELIVERY("AWAITING_DELIVERY", "Aguardando Entrega"),    
    IN_DELIVERY("IN_DELIVERY", "Realizando Entrega"),    
    FINISHED("FINISHED", "Finalizado");    
    
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
