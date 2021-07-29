package com.github.sandrolaxx.dfmicroservices.services;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.github.sandrolaxx.dfmicroservices.dto.ListProductCartUpdate;
import com.github.sandrolaxx.dfmicroservices.dto.ProductCartUpdate;
import com.github.sandrolaxx.dfmicroservices.dto.ProductDto;
import com.github.sandrolaxx.dfmicroservices.entities.Cart;
import com.github.sandrolaxx.dfmicroservices.entities.Product;
import com.github.sandrolaxx.dfmicroservices.entities.ProductCart;
import com.github.sandrolaxx.dfmicroservices.entities.enums.EnumErrorCode;
import com.github.sandrolaxx.dfmicroservices.utils.FrostException;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class MpService {
    
    public Multi<ProductDto> listProducts() {

        Multi<Object> productList = Product.listAll()
                                            .onItem()
                                            .transformToMulti(m -> Multi.createFrom().iterable(m));

        return productList.onItem()
                            .transform(m -> new ProductDto((Product) m));

    }
    
    public Uni<Response> addProductToCart(Integer idCart, 
        Integer idProduct, Integer quantity) throws FrostException {

        return Product.<Product>findById(idProduct)
                    .onItem()
                    .ifNotNull()
                    .call(p -> {
                        return Cart.<Cart>findById(idCart)
                            .onItem()
                            .ifNotNull()
                            .call(c -> {
                                var productCart = new ProductCart();
                                productCart.setCart(c);
                                productCart.setProduct(p);
                                productCart.setQuantity(quantity);
                    
                                return Panache.withTransaction(productCart::persist);       
                            })
                            .onItem().ifNull().failWith(new FrostException(EnumErrorCode.CARRINHO_NAO_ENCONTRADO)); 
                        })
                        .onItem().ifNull().failWith(new FrostException(EnumErrorCode.PRODUTO_NAO_ENCONTRADO))
                        .onItem().ifNotNull().transform(p -> Response.ok().status(Status.CREATED).build());
        
    }

    public Uni<Cart> listAllCart(Integer idCart) {
        return Cart.findById(idCart);
    }

    public Uni<Response> updateCart(Integer idCart, ListProductCartUpdate listToUpdate) {

        return Cart.<Cart>findById(idCart)
            .onItem()
            .ifNotNull()
            .call(c -> {
                Multi<ProductCartUpdate> productsCartUpdate = Multi.createFrom().items(listToUpdate.getListToUpdate().stream());
                Multi<ProductCart> cartProducts = Multi.createFrom().items(c.getProductCartList().stream());

                    return cartProducts.call(cp -> {
                        
                        Uni<ProductCartUpdate> productToUpdate = productsCartUpdate.filter(item -> item.getProductId() == cp.getProduct().getId()).toUni();

                        return productToUpdate.call(pcu -> {
                            
                            if (!pcu.isRemoved()) {
                                if (pcu.getQuantityToAdd() != null 
                                && pcu.getQuantityToAdd() != 0) {
                                    cp.setQuantity(cp.getQuantity() + pcu.getQuantityToAdd());
                                }
                                
                                if (pcu.getQuantityToRemove() != null 
                                && pcu.getQuantityToRemove() != 0) {
                                    cp.setQuantity(cp.getQuantity() + pcu.getQuantityToAdd());
                                }
                                
                            } else {
                                cp.setRemoved(true);
                            }
    
                        System.out.println(cp.getQuantity());
                        return Panache.withTransaction(cp::persistAndFlush);

                        });
                            
                    }).toUni();
                
            })
            .onItem().ifNull().failWith(new FrostException(EnumErrorCode.CARRINHO_NAO_ENCONTRADO))
            .onItem().ifNotNull().transform(c -> Response.ok().status(Status.NO_CONTENT).build());

    }

}
