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
    var id = repository.save(new Customer(customer.getFirstName(), customer.getLastName()));
    return String.format("{ id: %s }", id);
  }

  @GetMapping()
  public List<CustomerRepresentation> findAll(){
    List<CustomerRepresentation> customerRepresentation = new ArrayList<>();
      repository.findAll().forEach(customer ->
          customerRepresentation
              .add(new CustomerRepresentation(customer.getFirstName(),customer.getLastName())));
    return customerRepresentation;
  }

  @GetMapping("/{id}")
  public CustomerRepresentation find(@PathVariable long id){
    var customer = repository.findById(id);
    return CustomerRepresentation.from(customer.orElseGet(() -> new Customer()));
  }
}
