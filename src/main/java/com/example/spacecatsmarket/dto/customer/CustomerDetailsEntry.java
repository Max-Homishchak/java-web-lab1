package com.example.spacecatsmarket.dto.customer;

import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class CustomerDetailsEntry {
    Long id;
    String name;
    String address;
    String phoneNumber;
    String email;
    List<String> preferredChannel;
}