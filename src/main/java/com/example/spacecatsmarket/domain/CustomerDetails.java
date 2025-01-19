package com.example.spacecatsmarket.domain;

import com.example.spacecatsmarket.common.CommunicationChannel;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class  CustomerDetails {

    Long id;
    String name;
    String address;
    String phoneNumber;
    String email;
    List<CommunicationChannel> preferredChannel;
}
