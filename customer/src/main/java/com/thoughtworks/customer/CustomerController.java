package com.thoughtworks.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

  @Autowired  CustomerRepository repository;

  @PostMapping("/bulk")
  public String bulkcreate(){
    repository.save(new Customer("Ganesan", "Arunachalam"));
    repository.saveAll(Arrays.asList(new Customer("Salim", "Khan")
        , new Customer("Rajesh", "Parihar")
        , new Customer("Rahul", "Dravid")
        , new Customer("Dharmendra", "Bhojwani")));
    return "Customers are created";
  }

  @PostMapping()
  public String enroll(@RequestBody CustomerRepresentation customer){
    repository.save(new Customer(customer.getFirstName(), customer.getLastName()));
    return "Customer is created";
  }

  @GetMapping()
  public List<CustomerRepresentation> findAll(){
    Iterable<Customer> customers = repository.findAll();
    List<CustomerRepresentation> customerRepresentation = new ArrayList<>();
    for (Customer customer : customers) {
      customerRepresentation.add(new CustomerRepresentation(customer.getFirstName(),customer.getLastName()));
    }
    return customerRepresentation;
  }

  @GetMapping("/{id}")
  public CustomerRepresentation find(@PathVariable long id){
    var customer = repository.findById(id);
    return CustomerRepresentation.from(customer.orElseGet(() -> new Customer()));
  }

//  @GetMapping
//  public List<CustomerRepresentation> fetchDataByFirstName(@RequestParam(required = true) String firstName){
//    List<Customer> customers = repository.findByFirstName(firstName);
//    List<CustomerRepresentation> customerRepresentation = new ArrayList<>();
//    for (Customer customer : customers) {
//      customerRepresentation.add(new CustomerRepresentation(customer.getFirstName(),customer.getLastName()));
//    }
//    return customerRepresentation;
//  }
}
