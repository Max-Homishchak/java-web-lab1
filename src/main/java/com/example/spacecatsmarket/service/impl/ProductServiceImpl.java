package com.example.spacecatsmarket.service.impl;

import com.example.spacecatsmarket.domain.product.Product;
import com.example.spacecatsmarket.service.ProductService;
import com.example.spacecatsmarket.service.exception.product.ProductClientException;
import com.example.spacecatsmarket.service.exception.product.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final RestClient productRestClient;
    private final String productServiceEndpoint;

    public ProductServiceImpl(@Qualifier("productRestClient") RestClient productRestClient,
            @Value("${application.product-service.products}") String productServiceEndpoint) {
        this.productRestClient = productRestClient;
        this.productServiceEndpoint = productServiceEndpoint;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRestClient
                .get()
                .uri(productServiceEndpoint)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    logProductClientException(response);
                    throw new ProductClientException("Server response failed to fetch products. Response Code " + response.getStatusCode());
                })
                .body(new ParameterizedTypeReference<List<Product>>() {});
    }

    @Override
    public Product getProductById(Long id) {
        return productRestClient
                .get()
                .uri(productServiceEndpoint + "/" + id)
                .retrieve()
                .onStatus(httpStatusCode -> httpStatusCode.isSameCodeAs(HttpStatusCode.valueOf(404)), ((request, response) -> {
                    logProductClientException(response);
                    throw new ProductNotFoundException(id);
                }))
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    logProductClientException(response);
                    throw new ProductClientException("Server response failed to fetch product by id: " + id + ". Response Code " + response.getStatusCode());
                })
                .body(Product.class);
    }

    private void logProductClientException(ClientHttpResponse response) throws IOException {
        log.error("Products client request failed. Response Code {}", response.getStatusCode());
    }

    @Override
    public Product createProduct(Product product) {
        return productRestClient
                .post()
                .uri(productServiceEndpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .body(product)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    logProductClientException(response);
                    throw new ProductClientException("Server response failed to create product. Response Code " + response.getStatusCode());
                })
                .body(Product.class);
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        return productRestClient
                .put()
                .uri(productServiceEndpoint + "/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(product)
                .retrieve()
                .onStatus(httpStatusCode -> httpStatusCode.isSameCodeAs(HttpStatusCode.valueOf(404)), ((request, response) -> {
                    logProductClientException(response);
                    throw new ProductNotFoundException(id);
                }))
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    logProductClientException(response);
                    throw new ProductClientException("Server response failed to update product. Response Code " + response.getStatusCode());
                })
                .body(Product.class);
    }

    @Override
    public void deleteProduct(Long id) {
        productRestClient
                .delete()
                .uri(productServiceEndpoint + "/" + id)
                .retrieve()
                .onStatus(httpStatusCode -> httpStatusCode.isSameCodeAs(HttpStatusCode.valueOf(404)), ((request, response) -> {
                    logProductClientException(response);
                    throw new ProductNotFoundException(id);
                }))
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    logProductClientException(response);
                    throw new ProductClientException("Server response failed to update product. Response Code " + response.getStatusCode());
                })
                .body(Void.class);
    }
}
