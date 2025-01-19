package com.example.spacecatsmarket.web;

import com.example.spacecatsmarket.dto.customer.CustomerDetailsDto;
import com.example.spacecatsmarket.service.CustomerService;
import com.example.spacecatsmarket.service.mapper.CustomDetailsMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
@AutoConfigureMockMvc
class CustomerControllerTest {

    static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomerService customerService;

    @MockBean
    CustomDetailsMapper customDetailsMapper;

    @Test
    void createCustomer_whenNameNotProvided() throws Exception {
        // given
        CustomerDetailsDto customerDetailsDto = CustomerDetailsDto.builder()
                .email("example@example.com")
                .address("Sector 5, Planet Zeta, Quadrant 12")
                .phoneNumber("+1 (555) 123-4567")
                .build();

        // when
        mockMvc.perform(
                        post("/api/v1/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(customerDetailsDto))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.invalidParams[0].fieldName").value("name"))
                .andExpect(jsonPath("$.invalidParams[0].reason").value("Name is mandatory"))
                .andDo(print());

        // then
    }

    @Test
    void createCustomer_whenNameExceedsMaximumLength() throws Exception {
        // given
        CustomerDetailsDto customerDetailsDto = CustomerDetailsDto.builder()
                .email("example@example.com")
                .address("Sector 5, Planet Zeta, Quadrant 12")
                .phoneNumber("+1 (555) 123-4567")
                .name("ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssfdfdfdfdfdfddddssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssfdfdfdfdfdfdddd")
                .build();

        // when
        mockMvc.perform(
                        post("/api/v1/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(customerDetailsDto))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.invalidParams[0].fieldName").value("name"))
                .andExpect(jsonPath("$.invalidParams[0].reason").value("Name cannot exceed 100 characters"))
                .andDo(print());

        // then
    }

    @Test
    void createCustomer_whenAddressNotProvided() throws Exception {
        // given
        CustomerDetailsDto customerDetailsDto = CustomerDetailsDto.builder()
                .email("example@example.com")
                .phoneNumber("+1 (555) 123-4567")
                .name("sasaasasa")
                .build();

        // when
        mockMvc.perform(
                        post("/api/v1/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(customerDetailsDto))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.invalidParams[0].fieldName").value("address"))
                .andExpect(jsonPath("$.invalidParams[0].reason").value("Address is mandatory"))
                .andDo(print());

        // then
    }

    @Test
    void createCustomer_whenAddressExceedsMaximumLength() throws Exception {
        // given
        CustomerDetailsDto customerDetailsDto = CustomerDetailsDto.builder()
                .email("example@example.com")
                .phoneNumber("+1 (555) 123-4567")
                .address("sssddsdsdsdsdssssddsdsdsdsdssssddsdsdsdsdssssddsdsdsdsdssssddsdsdsdsdssssddsdsdsdsdssssddsdsdsdsdssssddsdsdsdsdssssddsdsdsdsdssssddsdsdsdsdssssddsdsdsdsdssssddsdsdsdsdssssddsdsdsdsdssssddsdsdsdsdssssddsdsdsdsdssssddsdsdsdsdssssddsdsdsdsdssssddsdsdsdsdssssddsdsdsdsdssssddsdsdsdsdssssddsdsdsdsdssssddsdsdsdsdssssddsdsdsdsdssssddsdsdsdsdssssddsdsdsdsdssssddsdsdsdsdssssddsdsdsdsdssssddsdsdsdsdssssddsdsdsdsdssssddsdsdsdsds")
                .name("sasaasasa")
                .build();

        // when
        mockMvc.perform(
                        post("/api/v1/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(customerDetailsDto))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.invalidParams[0].fieldName").value("address"))
                .andExpect(jsonPath("$.invalidParams[0].reason").value("Address cannot exceed 255 characters"))
                .andDo(print());

        // then
    }

    @Test
    void createCustomer_whenAddressIsNotSpace() throws Exception {
        // given
        CustomerDetailsDto customerDetailsDto = CustomerDetailsDto.builder()
                .email("example@example.com")
                .phoneNumber("+1 (555) 123-4567")
                .address("fdfd fdfdfd fdfd")
                .name("sasaasasa")
                .build();

        // when
        mockMvc.perform(
                        post("/api/v1/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(customerDetailsDto))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.invalidParams[0].fieldName").value("address"))
                .andExpect(jsonPath("$.invalidParams[0].reason").value("\"Invalid Space Address: The provided address does not conform to the required format. Please ensure that it includes valid coordinates, sector, and planet information. Example: 'Sector 5, Planet Zeta, Quadrant 12'.\"\n"))
                .andDo(print());

        // then
    }

    @Test
    void createCustomer_whenPhoneNumberNotProvided() throws Exception {
        // given
        CustomerDetailsDto customerDetailsDto = CustomerDetailsDto.builder()
                .email("example@example.com")
                .address("Sector 5, Planet Zeta, Quadrant 12")
                .name("sasaasasa")
                .build();

        // when
        mockMvc.perform(
                        post("/api/v1/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(customerDetailsDto))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.invalidParams[0].fieldName").value("phoneNumber"))
                .andExpect(jsonPath("$.invalidParams[0].reason").value("Phone number is mandatory"))
                .andDo(print());

        // then
    }

    @Test
    void createCustomer_whenInvalidPhoneNumber() throws Exception {
        // given
        CustomerDetailsDto customerDetailsDto = CustomerDetailsDto.builder()
                .email("example@example.com")
                .address("Sector 5, Planet Zeta, Quadrant 12")
                .phoneNumber("dsdsdsd")
                .name("sasaasasa")
                .build();

        // when
        mockMvc.perform(
                        post("/api/v1/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(customerDetailsDto))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.invalidParams[0].fieldName").value("phoneNumber"))
                .andExpect(jsonPath("$.invalidParams[0].reason").value("Phone number must be valid"))
                .andDo(print());

        // then
    }

    @Test
    void createCustomer_whenEmailNotProvided() throws Exception {
        // given
        CustomerDetailsDto customerDetailsDto = CustomerDetailsDto.builder()
                .address("Sector 5, Planet Zeta, Quadrant 12")
                .phoneNumber("+1 (555) 123-4567")
                .name("sasaasasa")
                .build();

        // when
        mockMvc.perform(
                        post("/api/v1/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(customerDetailsDto))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.invalidParams[0].fieldName").value("email"))
                .andExpect(jsonPath("$.invalidParams[0].reason").value("Email is mandatory"))
                .andDo(print());

        // then
    }

    @Test
    void createCustomer_whenInvalidEmail() throws Exception {
        // given
        CustomerDetailsDto customerDetailsDto = CustomerDetailsDto.builder()
                .address("Sector 5, Planet Zeta, Quadrant 12")
                .phoneNumber("+1 (555) 123-4567")
                .email("sasasasa")
                .name("sasaasasa")
                .build();

        // when
        mockMvc.perform(
                        post("/api/v1/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(customerDetailsDto))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.invalidParams[0].fieldName").value("email"))
                .andExpect(jsonPath("$.invalidParams[0].reason").value("Email should be valid"))
                .andDo(print());

        // then
    }

}