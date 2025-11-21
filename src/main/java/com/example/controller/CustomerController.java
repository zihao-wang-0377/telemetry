package com.example.controller;

import com.example.model.Customer;
import com.example.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController {

    private final CustomerService service;
    private final Logger LOG = LoggerFactory.getLogger(CustomerController.class);

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    private String fmt(String level, String action, String detail) {
        return String.format(
                "[BACKEND-LOG] level=%s action=%s time=%s detail=%s",
                level,
                action,
                Instant.now().toString(),
                detail
        );
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        List<Customer> customers = service.getAllCustomers();

        LOG.info(fmt(
                "INFO",
                "getAllCustomers()",
                "Returning " + customers.size() + " customers"
        ));

        return customers;
    }

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {

        LOG.info(fmt(
                "INFO",
                "createCustomer()",
                "Request payload=" + customer
        ));

        Customer newCustomer = service.createCustomer(customer);

        LOG.info(fmt(
                "INFO",
                "createCustomer()",
                "Created ID=" + newCustomer.getId()
        ));

        return newCustomer;
    }

    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable("id") Long id, @RequestBody Customer customer) {

        LOG.info(fmt(
                "INFO",
                "updateCustomer()",
                "Updating ID=" + id + " data=" + customer
        ));

        Customer updatedCustomer = service.updateCustomer(id, customer);

        LOG.info(fmt(
                "INFO",
                "updateCustomer()",
                "Updated=" + updatedCustomer
        ));

        return updatedCustomer;
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable("id") Long id) {

        LOG.warn(fmt(
                "WARN",
                "deleteCustomer()",
                "Deleting ID=" + id
        ));

        service.deleteCustomer(id);

        LOG.info(fmt(
                "INFO",
                "deleteCustomer()",
                "Deleted ID=" + id
        ));
    }
}
