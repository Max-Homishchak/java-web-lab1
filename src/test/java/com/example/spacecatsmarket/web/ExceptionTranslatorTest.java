package com.example.spacecatsmarket.web;

import com.example.spacecatsmarket.service.exception.CustomerNotFoundException;
import com.example.spacecatsmarket.web.exception.CatNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ProblemDetail;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class ExceptionTranslatorTest {

    ExceptionTranslator exceptionTranslator = new ExceptionTranslator();

    @Test
    void handleStoreConfigurationNotFoundException_whenCustomerNotFoundException() {
        // given
        CustomerNotFoundException customerNotFoundException = new CustomerNotFoundException(1L);

        // when
        ProblemDetail problemDetail = exceptionTranslator.handleStoreConfigurationNotFoundException(customerNotFoundException);

        // then
        assertNotNull(problemDetail);
        assertEquals("Customer Not Found", problemDetail.getTitle());
        assertEquals(404, problemDetail.getStatus());
        assertEquals(customerNotFoundException.getMessage(), problemDetail.getDetail());
    }

    @Test
    void handleStoreConfigurationNotFoundException_whenCatNotFoundException() {
        // given
        CatNotFoundException catNotFoundException = new CatNotFoundException("Meow");

        // when
        ProblemDetail problemDetail = exceptionTranslator.handleStoreConfigurationNotFoundException(catNotFoundException);

        // then
        assertNotNull(problemDetail);
        assertEquals("Cat Not Found", problemDetail.getTitle());
        assertEquals(404, problemDetail.getStatus());
        assertEquals(catNotFoundException.getMessage(), problemDetail.getDetail());
    }

}