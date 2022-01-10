package com.github.sandrolaxx.dfmicroservices.entities.enums;

import org.apache.http.HttpStatus;

public enum EnumErrorCode {
    
    USUARIO_NAO_ENCONTRADO("01", "Id do usuário informado não existe na base de dados!", HttpStatus.SC_NOT_FOUND),
    ERRO_AO_CADASTRAR_USUARIO("02", "Ocorreu um erro interno e não foi possível cadastrar o usuário!", HttpStatus.SC_INTERNAL_SERVER_ERROR),
    ERRO_AO_DELETAR_ENDERECO("03", "O usuário deve possuir pelo menos um endereço! Impossível deletar.", HttpStatus.SC_BAD_REQUEST),
    ERRO_AO_DELETAR_USUARIO("04", "Não foi possível remover o usuário do sistema.", HttpStatus.SC_BAD_REQUEST),
    USUARIO_SEM_CREDENCIAIS("05", "Rota não disponível para as credenciais informadas.", HttpStatus.SC_FORBIDDEN),
    EMAIL_JA_CADASTRADO("06", "E-mail já utilizado, insira outro ou realize o login.", HttpStatus.SC_BAD_REQUEST),
    ENDERECO_NAO_ENCONTRADO("07", "Id do endereço não existe na base de dados!", HttpStatus.SC_NOT_FOUND);

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
