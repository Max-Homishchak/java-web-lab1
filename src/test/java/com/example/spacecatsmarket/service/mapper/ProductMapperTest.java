package com.example.spacecatsmarket.service.mapper;

import com.example.spacecatsmarket.common.ProductType;
import com.example.spacecatsmarket.domain.category.Category;
import com.example.spacecatsmarket.domain.product.Product;
import com.example.spacecatsmarket.dto.product.ProductDto;
import com.example.spacecatsmarket.dto.product.ProductEntryDto;
import com.example.spacecatsmarket.dto.product.ProductListDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static com.example.spacecatsmarket.util.ProductTestUtil.PRODUCT_ID;
import static com.example.spacecatsmarket.util.ProductTestUtil.buildProduct;
import static com.example.spacecatsmarket.util.ProductTestUtil.buildProductDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class ProductMapperTest {

    ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    @Test
    void toUpdatedProduct() {
        // given
        ProductDto productDto = buildProductDto();

        // when
        Product product = productMapper.toUpdatedProduct(PRODUCT_ID, productDto);

        // then
        assertEquals(PRODUCT_ID, product.getId());

        assertProductEqualsProductDto(productDto, product);
    }

    @Test
    void toProductTypeString() {
        // given

        // when
        String productType = productMapper.toProductType(ProductType.COSMIC_CATNIP);

        // then
        assertEquals(ProductType.COSMIC_CATNIP.getDisplayName(), productType);
    }

    @Test
    void toProductType() {
        // given
        String displayName = ProductType.COSMIC_CATNIP.getDisplayName();

        // when
        ProductType productType = productMapper.toProductType(displayName);

        // then
        assertEquals(ProductType.fromDisplayName(displayName), productType);
    }

    @Test
    void toProduct() {
        // given
        ProductDto productDto = buildProductDto();

        // when
        Product product = productMapper.toProduct(productDto);

        // then
        assertProductEqualsProductDto(productDto, product);
    }

    @Test
    void toProductListDto() {
        // given
        Product product = buildProduct();

        // when
        ProductListDto productListDto = productMapper.toProductListDto(List.of(product));

        // then
        assertNotNull(productListDto);
        List<ProductEntryDto> productDetailsEntries = productListDto.getProductDetailsEntries();
        assertProductEqualsProductEntries(product, productDetailsEntries);
    }

    @Test
    void toProductEntries() {
        // given
        Product product = buildProduct();

        // when
        List<ProductEntryDto> productEntries = productMapper.toProductEntries(List.of(product));

        // then
        assertProductEqualsProductEntries(product, productEntries);
    }

    private void assertProductEqualsProductEntries(Product product, List<ProductEntryDto> productEntryDtos) {
        assertFalse(CollectionUtils.isEmpty(productEntryDtos));
        ProductEntryDto productEntryDto = productEntryDtos.get(0);
        assertNotNull(productEntryDto);

        assertEquals(product.getId(), productEntryDto.getId());
        assertEquals(product.getName(), productEntryDto.getName());
        assertEquals(product.getProductType().getDisplayName(), productEntryDto.getProductType());
        assertEquals(product.getDescription(), productEntryDto.getDescription());
        assertEquals(product.getImageUrl(), productEntryDto.getImageUrl());
    }

    @Test
    void toProductEntry() {
        // given
        Product product = buildProduct();

        // when
        ProductEntryDto productEntry = productMapper.toProductEntry(product);

        // then
        assertNotNull(productEntry);
        assertEquals(product.getId(), productEntry.getId());
        assertEquals(product.getName(), productEntry.getName());
        assertEquals(product.getProductType().getDisplayName(), productEntry.getProductType());
        assertEquals(product.getPrice(), productEntry.getPrice());
        assertEquals(product.getDescription(), productEntry.getDescription());
        assertEquals(product.getImageUrl(), productEntry.getImageUrl());
    }

    @Test
    void toProductDto() {
        // given
        Product product = buildProduct();

        // when
        ProductDto productDto = productMapper.toProductDto(product);

        // then
        assertProductEqualsProductDto(productDto, product);
    }

    void assertProductEqualsProductDto(ProductDto productDto, Product product) {
        Category category = product.getCategory();
        assertNotNull(category);
        assertEquals(productDto.getCategory().getId(), category.getId());
        assertEquals(productDto.getCategory().getName(), category.getName());

        assertEquals(productDto.getName(), product.getName());
        assertEquals(productDto.getPrice(), product.getPrice());
        assertEquals(productDto.getDescription(), product.getDescription());
        assertEquals(productDto.getImageUrl(), product.getImageUrl());
    }

}