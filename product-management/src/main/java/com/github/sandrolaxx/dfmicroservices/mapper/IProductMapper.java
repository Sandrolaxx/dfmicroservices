package com.github.sandrolaxx.dfmicroservices.mapper;

import com.github.sandrolaxx.dfmicroservices.dto.ProductCreateDto;
import com.github.sandrolaxx.dfmicroservices.dto.ProductListDto;
import com.github.sandrolaxx.dfmicroservices.entities.Product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface IProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    public Product productListDtoToProduct(ProductListDto dto);

    public Product productCreateDtoToProduct(ProductCreateDto dto);

    @Mapping(target = "createdAt", dateFormat = "dd/MM/yyyy HH:mm:ss")
    @Mapping(target = "updatedAt", dateFormat = "dd/MM/yyyy HH:mm:ss")
    public ProductListDto toProductListDto(Product product);

}
