package com.example.service;

import com.example.model.Customer;
import com.example.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }

    public Optional<Customer> getCustomer(Long id) {
        return repository.findById(id);
    }

    public Customer createCustomer(Customer customer) {
        return repository.save(customer);
    }

    public Customer updateCustomer(Long id, Customer customer) {
        customer.setId(id);
        return repository.save(customer);
    }

    public void deleteCustomer(Long id) {
        repository.deleteById(id);
    }
}
