package com.example.spacecatsmarket.service.mapper;

import com.example.spacecatsmarket.common.CommunicationChannel;
import com.example.spacecatsmarket.domain.CustomerDetails;
import com.example.spacecatsmarket.dto.customer.CustomerDetailsDto;
import com.example.spacecatsmarket.dto.customer.CustomerDetailsEntry;
import com.example.spacecatsmarket.dto.customer.CustomerDetailsListDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class CustomDetailsMapperTest {

    CustomDetailsMapper customDetailsMapper = Mappers.getMapper(CustomDetailsMapper.class);

    @Test
    void toCustomerDetailsDto() {
        // given
        CustomerDetails customerDetails = buildCustomerDetails();

        // when
        CustomerDetailsDto dto = customDetailsMapper.toCustomerDetailsDto(customerDetails);

        // then
        assertNotNull(dto);
        assertEquals(customerDetails.getName(), dto.getName());
        assertEquals(customerDetails.getAddress(), dto.getAddress());
        assertEquals(customerDetails.getPhoneNumber(), dto.getPhoneNumber());
        assertEquals(customerDetails.getEmail(), dto.getEmail());
        assertEquals(customerDetails.getPreferredChannel().get(0).name().toLowerCase(), dto.getPreferredChannel().get(0));
    }

    @Test
    void toCustomerDetailsListDto() {
        // given
        CustomerDetails customerDetails = buildCustomerDetails();

        // when
        CustomerDetailsListDto customerDetailsListDto = customDetailsMapper.toCustomerDetailsListDto(List.of(customerDetails));

        // then
        assertNotNull(customerDetailsListDto);

        List<CustomerDetailsEntry> customerDetailsEntries = customerDetailsListDto.getCustomerDetailsEntries();
        assertFalse(CollectionUtils.isEmpty(customerDetailsEntries));
        CustomerDetailsEntry customerDetailsEntry = customerDetailsEntries.get(0);

        assertCustomerDetailsEqualCustomerDetailsEntry(customerDetails, customerDetailsEntry);
    }

    @Test
    void toCustomerDetailsEntry() {
        // given
        CustomerDetails customerDetails = buildCustomerDetails();

        // when
        CustomerDetailsEntry customerDetailsEntry = customDetailsMapper.toCustomerDetailsEntry(customerDetails);

        // then
        assertCustomerDetailsEqualCustomerDetailsEntry(customerDetails, customerDetailsEntry);
    }

    private void assertCustomerDetailsEqualCustomerDetailsEntry(CustomerDetails customerDetails, CustomerDetailsEntry customerDetailsEntry) {
        assertNotNull(customerDetailsEntry);
        assertEquals(customerDetails.getId(), customerDetailsEntry.getId());
        assertEquals(customerDetails.getName(), customerDetailsEntry.getName());
        assertEquals(customerDetails.getAddress(), customerDetailsEntry.getAddress());
        assertEquals(customerDetails.getPhoneNumber(), customerDetailsEntry.getPhoneNumber());
        assertEquals(customerDetails.getEmail(), customerDetailsEntry.getEmail());
        assertEquals(customerDetails.getPreferredChannel().get(0).name().toLowerCase(), customerDetailsEntry.getPreferredChannel().get(0));
    }

    CustomerDetails buildCustomerDetails() {
        return CustomerDetails.builder()
                .id(1L)
                .name("Customer Details")
                .address("Customer Address")
                .phoneNumber("+44000000000")
                .email("example@example.com")
                .preferredChannel(List.of(CommunicationChannel.MOBILE))
                .build();
    }

}