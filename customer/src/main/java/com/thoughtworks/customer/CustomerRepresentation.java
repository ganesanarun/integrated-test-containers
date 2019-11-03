package com.thoughtworks.customer;

public class CustomerRepresentation {

  private final String firstName;
  private final String lastName;

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

  public static CustomerRepresentation from(Customer customer) {
    return new CustomerRepresentation(customer.getFirstName(), customer.getLastName());
  }
}
