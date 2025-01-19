package com.example.spacecatsmarket.web;

import com.example.spacecatsmarket.dto.order.PlaceOrderRequestDto;
import com.example.spacecatsmarket.service.OrderService;
import com.example.spacecatsmarket.web.mapper.OrderDtoMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc
class OrderControllerTest {

    static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    MockMvc mockMvc;

    @MockBean
    OrderService orderService;

    @MockBean
    OrderDtoMapper orderDtoMapper;

    @Test
    void placeOrder_whenEntriesNotProvided() throws Exception {
        // given
        PlaceOrderRequestDto placeOrderRequestDto = PlaceOrderRequestDto.builder().totalPrice(5.0).build();

        // when
        mockMvc.perform(
                post("/api/v1/customerReference/orders/cartId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(placeOrderRequestDto))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.invalidParams[0].fieldName").value("entries"))
                .andExpect(jsonPath("$.invalidParams[0].reason").value("Entries cannot be null"))
                .andDo(print());

        // then
    }

    @Test
    void placeOrder_whenPriceNotProvided() throws Exception {
        // given
        PlaceOrderRequestDto placeOrderRequestDto = PlaceOrderRequestDto.builder()
                .entries(Collections.emptyList())
                .build();

        // when
        mockMvc.perform(
                        post("/api/v1/customerReference/orders/cartId")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(placeOrderRequestDto))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.invalidParams[0].fieldName").value("totalPrice"))
                .andExpect(jsonPath("$.invalidParams[0].reason").value("Total price cannot be null"))
                .andDo(print());

        // then
    }

    @Test
    void placeOrder_whenPriceNegative() throws Exception {
        // given
        PlaceOrderRequestDto placeOrderRequestDto = PlaceOrderRequestDto.builder()
                .entries(Collections.emptyList())
                .totalPrice(-5.0)
                .build();

        // when
        mockMvc.perform(
                        post("/api/v1/customerReference/orders/cartId")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(placeOrderRequestDto))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.invalidParams[0].fieldName").value("totalPrice"))
                .andExpect(jsonPath("$.invalidParams[0].reason").value("must be greater than or equal to 0"))
                .andDo(print());

        // then
    }

}