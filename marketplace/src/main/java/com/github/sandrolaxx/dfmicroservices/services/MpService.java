package com.github.sandrolaxx.dfmicroservices.services;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import com.github.sandrolaxx.dfmicroservices.entities.Cart;
import com.github.sandrolaxx.dfmicroservices.entities.Product;
import com.github.sandrolaxx.dfmicroservices.entities.ProductCart;
import com.github.sandrolaxx.dfmicroservices.entities.enums.EnumErrorCode;
import com.github.sandrolaxx.dfmicroservices.utils.FrostException;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class MpService {
    
    public List<ProductCart> addProductToCart(Integer idCart, 
        Integer idProduct, Integer quantity) throws FrostException {

        List<ProductCart> cartItems = new ArrayList<>();

        Uni<Cart> cart = Cart.findById(idCart);

        if (cart == null) {
            throw new FrostException(EnumErrorCode.ERRO_AO_CADASTRAR_PRODUTO);
        }
        
        Uni<Product> product = Product.findById(idProduct);
        
        if (product == null) {
            throw new FrostException(EnumErrorCode.ERRO_AO_CADASTRAR_PRODUTO);
        }
        
        cart.onItem().invoke(item -> {
            
            if (item.getProductCartList().isEmpty()) {
                
                ProductCart newCart = new ProductCart();
                newCart.setCart(item);
                newCart.setQuantity(quantity);
                product.onItem().invoke(p -> newCart.setProduct(p));
                
                cartItems.add(newCart);
                item.setProductCartList(cartItems);
                
                Panache.withTransaction(item::persist);

            }

        });

        return cartItems;
        
    }

}
