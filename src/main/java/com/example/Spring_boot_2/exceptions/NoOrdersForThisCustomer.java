package com.example.Spring_boot_2.exceptions;

public class NoOrdersForThisCustomer extends Exception {
    private String error;

    public NoOrdersForThisCustomer() {
    }

    public NoOrdersForThisCustomer(String error) {
        super(error);
        this.error = error;
    }
}