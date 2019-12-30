package com.codegym.controllers;

import com.codegym.models.Customer;
import com.codegym.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @RequestMapping(value = "/user/customer/", method = RequestMethod.GET)
    public ResponseEntity<List<Customer>> listCustomer(){
        List<Customer> customers = customerService.findAll();
        if(customers.isEmpty()){
            return new ResponseEntity<List<Customer>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Customer>>(customers, HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/customer/", method = RequestMethod.POST)
    public ResponseEntity<Void>  createCustomer(
            @RequestBody Customer customer, UriComponentsBuilder uriComponentsBuilder){
            customerService.save(customer);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(uriComponentsBuilder.path("/admin/customer/{id}"
            ).buildAndExpand(customer.getId()).toUri());
            return new ResponseEntity<Void>(headers, HttpStatus.CREATED);

    }


}