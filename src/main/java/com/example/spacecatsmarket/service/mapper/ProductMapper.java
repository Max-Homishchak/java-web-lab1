package com.example.spacecatsmarket.service.mapper;

import com.example.spacecatsmarket.common.ProductType;
import com.example.spacecatsmarket.domain.product.Product;
import com.example.spacecatsmarket.dto.product.ProductDto;
import com.example.spacecatsmarket.dto.product.ProductEntryDto;
import com.example.spacecatsmarket.dto.product.ProductListDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "productType", source = "productDto.productType")
    @Mapping(target = "name", source = "productDto.name")
    @Mapping(target = "price", source = "productDto.price")
    @Mapping(target = "description", source = "productDto.description")
    @Mapping(target = "imageUrl", source = "productDto.imageUrl")
    Product toUpdatedProduct(Long id, ProductDto productDto);

    default ProductType toProductType(String productType) {
        return ProductType.fromDisplayName(productType);
    }

    default String toProductType(ProductType productType) {
        return productType.getDisplayName();
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "productType", source = "productType")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "imageUrl", source = "imageUrl")
    Product toProduct(ProductDto productDto);

    @Mapping(target = "productType", source = "productType")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "imageUrl", source = "imageUrl")
    ProductDto toProductDto(Product product);

    default ProductListDto toProductListDto(List<Product> products) {
        return ProductListDto.builder().productDetailsEntries(toProductEntries(products)).build();
    }

    default List<ProductEntryDto> toProductEntries(List<Product> products) {
        return products.stream().map(this::toProductEntry).collect(Collectors.toList());
    }

    @Mapping(target = "id", source = "id")
    @Mapping(target = "productType", source = "productType")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "imageUrl", source = "imageUrl")
    ProductEntryDto toProductEntry(Product product);

}
