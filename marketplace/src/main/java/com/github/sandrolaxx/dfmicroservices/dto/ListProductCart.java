package com.github.sandrolaxx.dfmicroservices.dto;

import java.util.List;

public class ListProductCart {
    
    List<ProductCart> listToUpdate;

    public List<ProductCart> getListToUpdate() {
        return listToUpdate;
    }

    public void setListToUpdate(List<ProductCart> listToUpdate) {
        this.listToUpdate = listToUpdate;
    }
    
}
