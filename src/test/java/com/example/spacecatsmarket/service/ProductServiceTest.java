package com.example.spacecatsmarket.service;

import com.example.spacecatsmarket.config.MappersTestConfiguration;
import com.example.spacecatsmarket.domain.product.Product;
import com.example.spacecatsmarket.service.exception.product.ProductClientException;
import com.example.spacecatsmarket.service.exception.product.ProductNotFoundException;
import com.example.spacecatsmarket.service.impl.ProductServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestClient;

import java.util.List;

import static com.example.spacecatsmarket.util.ProductTestUtil.PRODUCT_ID;
import static com.example.spacecatsmarket.util.ProductTestUtil.buildProduct;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = {ProductServiceTest.class})
@Import(MappersTestConfiguration.class)
@DisplayName("Product Service Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductServiceTest {

    ObjectMapper objectMapper = new ObjectMapper();

    ProductServiceImpl productService;

    WireMockServer wireMockServer;

    @BeforeEach
    void setUp() {
        wireMockServer = new WireMockServer(8080);
        wireMockServer.start();
        configureFor("localhost", 8080);

        productService = new ProductServiceImpl(RestClient.builder().build(), "http://localhost:" + wireMockServer.port());
    }

    @AfterEach
    void cleanUp() {
        wireMockServer.stop();
    }

    @Test
    void getAllProducts() throws JsonProcessingException {
        // given
        Product product = buildProduct();

        stubFor(
                get(urlEqualTo("/"))
                .willReturn(aResponse()
                    .withStatus(200)
                    .withBody(objectMapper.writeValueAsString(List.of(product)))
                    .withHeader("Content-Type", "application/json")
                )
        );

        // when
        List<Product> products = productService.getAllProducts();

        // then
        assertFalse(CollectionUtils.isEmpty(products));
        Product retrievedProduct = products.get(0);
        assertEquals(product, retrievedProduct);
    }

    @Test
    void getAllProducts_whenProductClientError() throws JsonProcessingException {
        // given
        Product product = buildProduct();

        stubFor(
                get(urlEqualTo("/"))
                        .willReturn(aResponse()
                                .withStatus(500))
        );

        // when
        assertThrows(ProductClientException.class, () -> productService.getAllProducts());

        // then
    }

    @Test
    void getProductById() throws JsonProcessingException {
        // given
        Product product = buildProduct();

        stubFor(
                get(urlEqualTo("/" + PRODUCT_ID))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withBody(objectMapper.writeValueAsString(product))
                                .withHeader("Content-Type", "application/json")
                        )
        );

        // when
        Product retrievedProduct = productService.getProductById(PRODUCT_ID);

        // then
        assertEquals(product, retrievedProduct);
    }

    @Test
    void getProductById_whenProductClientError() throws JsonProcessingException {
        // given

        stubFor(
                get(urlEqualTo("/" + PRODUCT_ID))
                        .willReturn(aResponse()
                                .withStatus(500))
        );

        // when
        assertThrows(ProductClientException.class, () -> productService.getProductById(PRODUCT_ID));

        // then
    }

    @Test
    void getProductById_whenProductNotFound() throws JsonProcessingException {
        // given

        stubFor(
                get(urlEqualTo("/" + PRODUCT_ID))
                        .willReturn(aResponse()
                                .withStatus(404))
        );

        // when
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(PRODUCT_ID));

        // then
    }

    @Test
    void createProduct() throws JsonProcessingException {
        // given
        Product product = buildProduct();

        stubFor(
                post(urlEqualTo("/"))
                        .willReturn(aResponse()
                                .withStatus(201)
                                .withBody(objectMapper.writeValueAsString(product))
                                .withHeader("Content-Type", "application/json")
                        )
        );

        // when
        Product createdProduct = productService.createProduct(product);

        // then
        assertEquals(product, createdProduct);
    }

    @Test
    void createProduct_whenProductClientError() throws JsonProcessingException {
        // given

        stubFor(
                post(urlEqualTo("/"))
                        .willReturn(aResponse()
                                .withStatus(500))
        );

        // when
        assertThrows(ProductClientException.class, () -> productService.createProduct(buildProduct()));

        // then
    }

    @Test
    void updateProduct() throws JsonProcessingException {
        // given
        Product product = buildProduct();

        stubFor(
                put(urlEqualTo("/" + PRODUCT_ID))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withBody(objectMapper.writeValueAsString(product))
                                .withHeader("Content-Type", "application/json")
                        )
        );

        // when
        Product updatedProduct = productService.updateProduct(PRODUCT_ID, product);

        // then
        assertEquals(product, updatedProduct);
    }

    @Test
    void updateProduct_whenProductNotFound() throws JsonProcessingException {
        // given

        stubFor(
                put(urlEqualTo("/" + PRODUCT_ID))
                        .willReturn(aResponse()
                                .withStatus(404))
        );

        // when
        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(PRODUCT_ID, buildProduct()));

        // then
    }

    @Test
    void updateProduct_whenProductClientError() throws JsonProcessingException {
        // given

        stubFor(
                put(urlEqualTo("/" + PRODUCT_ID))
                        .willReturn(aResponse()
                                .withStatus(500))
        );

        // when
        assertThrows(ProductClientException.class, () -> productService.updateProduct(PRODUCT_ID, buildProduct()));

        // then
    }

    @Test
    void deleteProduct() {
        // given

        // when
        stubFor(
                delete(urlEqualTo("/" + PRODUCT_ID))
                        .willReturn(aResponse()
                                .withStatus(200))
        );

        // then
        assertDoesNotThrow(() -> productService.deleteProduct(PRODUCT_ID));
    }

    @Test
    void deleteProduct_whenProductNotFound() throws JsonProcessingException {
        // given

        // when
        stubFor(
                delete(urlEqualTo("/" + PRODUCT_ID))
                        .willReturn(aResponse()
                                .withStatus(404))
        );

        // then
        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(PRODUCT_ID));
    }

    @Test
    void deleteProduct_whenProductClientError() throws JsonProcessingException {
        // given

        // when
        stubFor(
                delete(urlEqualTo("/" + PRODUCT_ID))
                        .willReturn(aResponse()
                                .withStatus(500))
        );

        // then
        assertThrows(ProductClientException.class, () -> productService.deleteProduct(PRODUCT_ID));
    }

}