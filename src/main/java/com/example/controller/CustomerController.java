package com.example.controller;

import com.example.model.Customer;
import com.example.service.CustomerService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return service.getAllCustomers();
    }

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return service.createCustomer(customer);
    }

    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable("id") Long id, @RequestBody Customer customer) {
        return service.updateCustomer(id, customer);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable("id") Long id) {
        service.deleteCustomer(id);
    }
}
