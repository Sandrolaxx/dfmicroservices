package com.github.sandrolaxx.dfmicroservices.entities.enums;

public enum EnumErrorCode {
    
    //User Error's
    PRODUTO_NAO_ENCONTRADO("01", "Id do produto informado não existe na base de dados!", 404),
    CARRINHO_NAO_ENCONTRADO("02", "Id do carinho informado não existe na base de dados!", 404),
    PRODUTO_JA_EXISTENTE_NO_CARRINHO("03", "Produto já está no carrinho adicione outro!", 403),
    ID_CARRINHO_INVALIDO("04", "Informe um identificador de carrinho valido!", 400),
    ID_PEDIDO_INVALIDO("05", "Informe um identificador de pedido valido!", 400),
    ID_USUARIO_INVALIDO("06", "Informe um identificador de usuário valido!", 400),
    ID_PRODUTO_INVALIDO("07", "Informe um identificador de Produto valido!", 400),
    ID_PRODUTO_CARRINHO_INVALIDO("08", "Informe um identificador de Produto Carrinho valido!", 400),
    QUANTIDADE_INFORMADA_INVALIDA("09", "Informe uma quantidade válida para o produto!", 400),
    TIPO_PAGAMENTO_INFORMADO_INVALIDO("10", "Informe uma tipo de pagamento valido para o pedido!", 400),
    NENHUM_PEDIDO_ENCONTRADO("11", "Nenhum pedido foi encontrado para o cliente informado!", 404),
    
    //Aplication Error's
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
