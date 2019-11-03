package com.thoughtworks.customer;

public class CustomerRepresentation {

  private String firstName;
  private String lastName;

  public CustomerRepresentation(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

//  public CustomerRepresentation() {
//
//  }

  public static CustomerRepresentation from(Customer customer) {
    return new CustomerRepresentation(customer.getFirstName(), customer.getLastName());
  }
}
