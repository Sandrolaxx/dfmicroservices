package com.github.sandrolaxx.dfmicroservices.entities.enums;

public enum EnumErrorCode {
    
    PRODUTO_NAO_ENCONTRADO("01", "Id do produto informado não existe na base de dados!", 404),
    CARRINHO_NAO_ENCONTRADO("02", "Id do carinho informado não existe na base de dados!", 404),
    ERRO_AO_CADASTRAR_PRODUTO("15", "Ocorreu um erro interno e não foi possível cadastrar o produto!", 502);

    private String key;

    private String value;

    private int httpStatus;

    EnumErrorCode(String key, String value, int httpStatus) {
        this.key = key;
        this.value = value;
        this.httpStatus = httpStatus;
    }

    public String getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }
    
    public int getHttpStatus() {
        return this.httpStatus;
    }

    public static EnumErrorCode parseByKey(String key) {

        for (EnumErrorCode enumError : EnumErrorCode.values()) {
            if (enumError.getKey().equals(key)) {
                return enumError;
            }
        }

        return null;
        
    }

}
