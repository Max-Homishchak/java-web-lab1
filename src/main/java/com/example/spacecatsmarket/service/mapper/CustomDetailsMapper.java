package com.example.spacecatsmarket.service.mapper;

import com.example.spacecatsmarket.common.CommunicationChannel;
import com.example.spacecatsmarket.domain.CustomerDetails;
import com.example.spacecatsmarket.dto.customer.CustomerDetailsDto;
import com.example.spacecatsmarket.dto.customer.CustomerDetailsEntry;
import com.example.spacecatsmarket.dto.customer.CustomerDetailsListDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CustomDetailsMapper {

    @Mapping(target = "name", source = "name")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "preferredChannel", source = "preferredChannel", qualifiedByName = "toPreferredChannelString")
    CustomerDetailsDto toCustomerDetailsDto(CustomerDetails customerDetails);

    default CustomerDetailsListDto toCustomerDetailsListDto(List<CustomerDetails> customerDetails) {
        return CustomerDetailsListDto.builder().customerDetailsEntries(toCustomerDetailsEntry(customerDetails)).build();
    }

    List<CustomerDetailsEntry> toCustomerDetailsEntry(List<CustomerDetails> customerDetails);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "preferredChannel", source = "preferredChannel", qualifiedByName = "toPreferredChannelString")
    CustomerDetailsEntry toCustomerDetailsEntry(CustomerDetails customerDetails);

    @Named("toPreferredChannelString")
    default List<String> toPreferredChannelString(List<CommunicationChannel> preferredChannel) {
        return preferredChannel.stream().map(channel -> channel.name().toLowerCase()).toList();
    }

}
