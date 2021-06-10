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
    public void updateProduct(Integer id, @Valid ProductCreateDto dto) {
        
        Optional<Product> existsProduct = Product.findByIdOptional(id);

        if (!existsProduct.isPresent()) {
            throw new NotFoundException();
        }

        var oldProduct = existsProduct.get();
        var updatedProduct = existsProduct.get();

        updatedProduct.setName(dto.getName() == null ? oldProduct.getName() : dto.getName());
        updatedProduct.setPrice(dto.getPrice() == null ? oldProduct.getPrice() : dto.getPrice());
        updatedProduct.setDiscount(dto.getDiscount() == null ? oldProduct.getDiscount() : dto.getDiscount());
        updatedProduct.setDescription(dto.getDescription() == null ? oldProduct.getDescription() : dto.getDescription());
        updatedProduct.setImageUri(dto.getImageUri() == null ? oldProduct.getImageUri() : dto.getDescription());
        updatedProduct.setActive(dto.getActive() == null ? oldProduct.getActive() : dto.getActive());

        updatedProduct.persist();

    }

    @Transactional()
    public void deleteProduct(Integer id) {
        
        Optional<Product> productToDelete = Product.findByIdOptional(id);

        productToDelete.ifPresentOrElse(Product::delete, () -> {
            throw new NotFoundException();
        });

    }
    
}
