package com.codegym.controllers;

import com.codegym.models.Customer;
import com.codegym.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

//    -----------------------get customers list--------------------------
    @RequestMapping(value = "/user/customer/", method = RequestMethod.GET)
    public ResponseEntity<List<Customer>> listCustomer(){
        List<Customer> customers = customerService.findAll();
        if(customers.isEmpty()){
            return new ResponseEntity<List<Customer>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Customer>>(customers, HttpStatus.OK);
    }

//    -------------------------create a new customer--------------------------
    @RequestMapping(value = "/admin/customer/", method = RequestMethod.POST)
    public ResponseEntity<Void>  createCustomer(
            @RequestBody Customer customer, UriComponentsBuilder uriComponentsBuilder){
            customerService.save(customer);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(uriComponentsBuilder.path("/admin/customer/{id}"
            ).buildAndExpand(customer.getId()).toUri());
            return new ResponseEntity<Void>(headers, HttpStatus.CREATED);

    }

//    ------------------------------get an infomation customer----------------------

    @RequestMapping(value = "/admin/customer/{id}", method = RequestMethod.GET)
    public ResponseEntity<Customer> getCustomer(@PathVariable("id") Long id){
        Customer customer = customerService.findById(id);
        if(customer == null){
            return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
        }
        return  new ResponseEntity<Customer>(customer, HttpStatus.OK);

    }
//    ----------------------------Edit a customer---------------------------------
    @RequestMapping(value = "/admin/customer/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Customer> editCustomer(@PathVariable("id") Long id, @RequestBody Customer customer){
        Customer currentCustomer = customerService.findById(id);
        if(currentCustomer == null){
            return  new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
        }
        currentCustomer.setFirstName(customer.getFirstName());
        currentCustomer.setLastName(customer.getLastName());
        currentCustomer.setId(customer.getId());

        customerService.save(currentCustomer);
        return new ResponseEntity<Customer>(currentCustomer, HttpStatus.OK);
    }

//    -----------------------------Remove a customer----------------------------
    @RequestMapping(value = "/admin/customer/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Customer> removeCustomer(@PathVariable ("id") Long id){
        Customer customer = customerService.findById(id);
        if(customer == null){
            return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
        }
        customerService.remove(id);
        return new ResponseEntity<Customer>(HttpStatus.NO_CONTENT);
    }


}