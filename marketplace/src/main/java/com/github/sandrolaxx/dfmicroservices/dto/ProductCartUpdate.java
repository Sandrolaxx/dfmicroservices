package com.github.sandrolaxx.dfmicroservices.dto;

public class ProductCartUpdate {
    
    private Integer productId;

    private Integer quantityToRemove;

    private Integer quantityToAdd;

    private boolean removed;

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

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

}
