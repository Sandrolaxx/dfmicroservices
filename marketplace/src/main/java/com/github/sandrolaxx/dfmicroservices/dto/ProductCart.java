package com.github.sandrolaxx.dfmicroservices.dto;

public class ProductCart {
    
    private Integer productId;

    private Integer quantityToRemove;

    private Integer quantityToAdd;

    private boolean remove;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantityToRemove() {
        return quantityToRemove;
    }

    public void setQuantityToRemove(Integer quantityToRemove) {
        this.quantityToRemove = quantityToRemove;
    }

    public Integer getQuantityToAdd() {
        return quantityToAdd;
    }

    public void setQuantityToAdd(Integer quantityToAdd) {
        this.quantityToAdd = quantityToAdd;
    }

    public boolean isRemove() {
        return remove;
    }

    public void setRemove(boolean remove) {
        this.remove = remove;
    }

}
