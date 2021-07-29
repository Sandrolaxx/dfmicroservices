package com.github.sandrolaxx.dfmicroservices.dto;

import java.util.List;

public class ListProductCartUpdate {
    
    List<ProductCartUpdate> listToUpdate;

    public List<ProductCartUpdate> getListToUpdate() {
        return listToUpdate;
    }

    public void setListToUpdate(List<ProductCartUpdate> listToUpdate) {
        this.listToUpdate = listToUpdate;
    }
    
}
