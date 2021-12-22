package com.github.sandrolaxx.dfmicroservices.utils;

import com.github.sandrolaxx.dfmicroservices.entities.enums.EnumErrorCode;
import com.github.sandrolaxx.dfmicroservices.entities.enums.EnumPaymentType;

public class ValidateUtil {
    
    public static void validateIdUser(Integer idUser) {
        
        if (idUser == null
            || !(idUser instanceof Integer)) {
            throw new FrostException(EnumErrorCode.ID_USUARIO_INVALIDO);
        }

    }

    public static void validateIdProductCart(Integer idProductCart) {
        
        if (idProductCart == null
            || !(idProductCart instanceof Integer)) {
            throw new FrostException(EnumErrorCode.ID_PRODUTO_CARRINHO_INVALIDO);
        }

    }

    public static void validateIdProduct(Integer idProduct) {
        
        if (idProduct == null
            || !(idProduct instanceof Integer)) {
            throw new FrostException(EnumErrorCode.ID_PRODUTO_INVALIDO);
        }

    }

    public static void validateQuantity(Integer quantity) {
        
        if (quantity == null
            || quantity <= 0
            || !(quantity instanceof Integer)) {
            throw new FrostException(EnumErrorCode.QUANTIDADE_INFORMADA_INVALIDA);
        }

    }

    public static void validateNewProductOnCart(Integer idProduct, Integer quantity) {
        validateIdProduct(idProduct);
        validateQuantity(quantity);
    }

    public static void validateAddQuantity(Integer idProductCart, Integer quantity) {
        
        validateIdProductCart(idProductCart);
        validateQuantity(quantity);

    }

    public static void validateRemoveQuantity(Integer idProductCart, Integer quantity) {
        
        validateIdProductCart(idProductCart);

        if (quantity != null
                && quantity <= 0) {
            throw new FrostException(EnumErrorCode.QUANTIDADE_INFORMADA_INVALIDA);
        }

    }

    public static void validateNewOrder(EnumPaymentType payType) {
        
        if (payType == null
            || !(payType instanceof EnumPaymentType)) {
            throw new FrostException(EnumErrorCode.TIPO_PAGAMENTO_INFORMADO_INVALIDO);
        }

    }

}
