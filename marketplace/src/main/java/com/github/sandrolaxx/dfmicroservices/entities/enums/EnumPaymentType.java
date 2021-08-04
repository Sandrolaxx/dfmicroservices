package com.github.sandrolaxx.dfmicroservices.entities.enums;

public enum EnumPaymentType {

    CARD_ON_WEB("CARD_ON_WEB", "Cartão no Site"),    
    CARD_ON_DELIVERY("CARD_ON_DELIVERY", "Cartão na Entrega"),    
    LIVE_MONEY("LIVE_MONEY", "Dinheiro"),    
    PIX("PIX", "Pagamento Instantâneo(PIX)");    
    
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
