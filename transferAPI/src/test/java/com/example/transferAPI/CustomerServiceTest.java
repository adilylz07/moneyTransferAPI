package com.example.transferAPI;

import com.example.transferAPI.model.Customer;
import com.example.transferAPI.repository.CustomerRepository;
import com.example.transferAPI.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCustomers() {
        Customer customer1 = new Customer(1L, "John Doe", "john.doe@example.com", null);
        Customer customer2 = new Customer(2L, "Jane Doe", "jane.doe@example.com", null);

        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));

        assertEquals(2, customerService.getAllCustomers().size());
    }

    @Test
    void getCustomerById() {
        Customer customer = new Customer(1L, "John Doe", "john.doe@example.com", null);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        assertEquals(customer, customerService.getCustomerById(1L));
    }

    @Test
    void createCustomer() {
        Customer customer = new Customer(null, "John Doe", "john.doe@example.com", null);

        when(customerRepository.save(customer)).thenReturn(customer);

        assertEquals(customer, customerService.createCustomer(customer));
    }

    @Test
    void updateCustomer() {
        Customer customer = new Customer(1L, "John Doe", "john.doe@example.com", null);
        Customer updatedCustomer = new Customer(1L, "John Smith", "john.smith@example.com", null);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.save(customer)).thenReturn(updatedCustomer);

        Customer result = customerService.updateCustomer(1L, updatedCustomer);
        assertEquals("John Smith", result.getName());
        assertEquals("john.smith@example.com", result.getEmail());
    }

    @Test
    void deleteCustomer() {
        Customer customer = new Customer(1L, "John Doe", "john.doe@example.com", null);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        doNothing().when(customerRepository).delete(customer);

        assertTrue(customerService.deleteCustomer(1L));
    }
}
