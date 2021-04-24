package com.github.sandrolaxx.dfmicroservices.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid; 
import javax.ws.rs.NotFoundException;

import com.github.sandrolaxx.dfmicroservices.dto.ProductCreateDto;
import com.github.sandrolaxx.dfmicroservices.dto.ProductListDto;
import com.github.sandrolaxx.dfmicroservices.entities.Product;
import com.github.sandrolaxx.dfmicroservices.mapper.IProductMapper;

import org.eclipse.microprofile.opentracing.Traced;

@ApplicationScoped
@Traced(operationName = "ProductService")
public class ProductService {
  
  @Inject
  IProductMapper mapper;

  public List<ProductListDto> findAll() {
    List<Product> productList = Product.listAll();

    return productList.stream()
                      .map(p -> mapper.toProductListDto(p))
                      .collect(Collectors.toList());
  }

  @Transactional()
  public Product persistProduct(@Valid ProductCreateDto dto) {
    Product product = mapper.productCreateDtoToProduct(dto);

    product.persist();

    return product;
  }

  @Transactional()
  public void updateProduct(Integer id,@Valid ProductCreateDto dto) {
    Optional<Product> oldProduct = Product.findByIdOptional(id);

    if (!oldProduct.isPresent()) {
      throw new NotFoundException();
    }

    var newProduct = oldProduct.get();
    newProduct.name = dto.name;
    newProduct.price = dto.price;
    newProduct.description = dto.description;
    newProduct.imageUri = dto.imageUri;
    newProduct.active = dto.active;

    newProduct.persist();
  }

  @Transactional()
  public void deleteProduct(Integer id) {
    Optional<Product> productToDelete = Product.findByIdOptional(id);

    productToDelete.ifPresentOrElse(Product::delete, () -> {
      throw new NotFoundException();
    });
  }
}
