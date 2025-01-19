package com.example.spacecatsmarket.web;

import com.example.spacecatsmarket.common.ProductType;
import com.example.spacecatsmarket.dto.category.CategoryDto;
import com.example.spacecatsmarket.dto.product.ProductDto;
import com.example.spacecatsmarket.service.ProductService;
import com.example.spacecatsmarket.service.mapper.ProductMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc
class ProductControllerTest {

    static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductService productService;

    @MockBean
    ProductMapper productMapper;

    @Test
    void createProduct_whenBlankProductType() throws Exception {
        // given
        ProductDto productDto = ProductDto.builder()
                .price(BigDecimal.valueOf(9.0))
                .name("Young star")
                .category(CategoryDto.builder().id(1L).name("Animal care").build())
                .build();

        // when
        mockMvc.perform(post("/api/v1/products").contentType(MediaType.APPLICATION_JSON).content(MAPPER.writeValueAsString(productDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.invalidParams[0].fieldName").value("productType"))
                .andExpect(jsonPath("$.invalidParams[0].reason").value("Product type is mandatory"))
                .andDo(print());

        // then
    }

    @Test
    void createProduct_whenCategoryNotProvided() throws Exception {
        // given
        ProductDto productDto = ProductDto.builder()
                .price(BigDecimal.valueOf(9.0))
                .productType(ProductType.COSMIC_CATNIP.getDisplayName())
                .name("Young star")
                .build();

        // when
        mockMvc.perform(post("/api/v1/products").contentType(MediaType.APPLICATION_JSON).content(MAPPER.writeValueAsString(productDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.invalidParams[0].fieldName").value("category"))
                .andExpect(jsonPath("$.invalidParams[0].reason").value("Category is mandatory"))
                .andDo(print());

        // then
    }

    @Test
    void createProduct_whenNameNotProvided() throws Exception {
        // given
        ProductDto productDto = ProductDto.builder()
                .price(BigDecimal.valueOf(9.0))
                .productType(ProductType.COSMIC_CATNIP.getDisplayName())
                .category(CategoryDto.builder().id(1L).name("Animal care").build())
                .build();

        // when
        mockMvc.perform(post("/api/v1/products").contentType(MediaType.APPLICATION_JSON).content(MAPPER.writeValueAsString(productDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.invalidParams[0].fieldName").value("name"))
                .andExpect(jsonPath("$.invalidParams[0].reason").value("Name is mandatory"))
                .andDo(print());

        // then
    }

    @Test
    void createProduct_whenNameBiggerThanMaximumAndNonCosmicWord() throws Exception {
        // given
        ProductDto productDto = ProductDto.builder()
                .price(BigDecimal.valueOf(9.0))
                .productType(ProductType.COSMIC_CATNIP.getDisplayName())
                .category(CategoryDto.builder().id(1L).name("Animal care").build())
                .name("nnsansansnansansansanasnnasnsansanasnasnnsansansansannsansanasnsnansnasnsnsansansnansasnansnnansasass")
                .build();

        // when
        mockMvc.perform(post("/api/v1/products").contentType(MediaType.APPLICATION_JSON).content(MAPPER.writeValueAsString(productDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.invalidParams", hasItem(
                        allOf(
                                hasEntry("fieldName", "name"),
                                hasEntry("reason", "Name should contain at least 1 cosmic word")
                        )
                )))
                .andExpect(jsonPath("$.invalidParams", hasItem(
                        allOf(
                                hasEntry("fieldName", "name"),
                                hasEntry("reason", "Name cannot exceed 100 characters")
                        )
                )))
                .andDo(print());

        // then
    }

    @Test
    void createProduct_whenPriceNotProvided() throws Exception {
        // given
        ProductDto productDto = ProductDto.builder()
                .productType(ProductType.COSMIC_CATNIP.getDisplayName())
                .category(CategoryDto.builder().id(1L).name("Animal care").build())
                .name("Young star")
                .build();

        // when
        mockMvc.perform(post("/api/v1/products").contentType(MediaType.APPLICATION_JSON).content(MAPPER.writeValueAsString(productDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.invalidParams[0].fieldName").value("price"))
                .andExpect(jsonPath("$.invalidParams[0].reason").value("Price is mandatory"))
                .andDo(print());

        // then
    }

    @Test
    void createProduct_whenPriceIsNegative() throws Exception {
        // given
        ProductDto productDto = ProductDto.builder()
                .productType(ProductType.COSMIC_CATNIP.getDisplayName())
                .category(CategoryDto.builder().id(1L).name("Animal care").build())
                .name("Young star")
                .price(BigDecimal.valueOf(-1))
                .build();

        // when
        mockMvc.perform(post("/api/v1/products").contentType(MediaType.APPLICATION_JSON).content(MAPPER.writeValueAsString(productDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.invalidParams[0].fieldName").value("price"))
                .andExpect(jsonPath("$.invalidParams[0].reason").value("Price must be positive"))
                .andDo(print());

        // then
    }
}