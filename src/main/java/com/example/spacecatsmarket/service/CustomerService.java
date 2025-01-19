package com.example.spacecatsmarket.service;

import com.example.spacecatsmarket.domain.CustomerDetails;
import java.util.List;

public interface CustomerService {

    List<CustomerDetails> getAllCustomerDetails();

    CustomerDetails getCustomerDetailsById(Long customerId);
}
