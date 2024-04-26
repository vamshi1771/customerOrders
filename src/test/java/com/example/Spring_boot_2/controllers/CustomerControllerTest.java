package com.example.Spring_boot_2.controllers;


import com.example.Spring_boot_2.dto.Customerdto;
import com.example.Spring_boot_2.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(controllers = CustomerController.class)
@ActiveProfiles("test")
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockmvc;
    @MockBean
    private CustomerService customerService;
    @Autowired
    private ObjectMapper objectMapper;


    private List<Customerdto> customersList;


    @Before
    public void setUp() throws Exception {
        this.customersList=new ArrayList<>();
        this.customersList.add(new Customerdto(10,"vamshi","kodad","male"));
        this.customersList.add(new Customerdto(12,"Monika","kodad","Female"));
        this.customersList.add(new Customerdto(11,"Yogesh","Kodad","male"));
        this.customersList.add(new Customerdto(13,"Uday","kodad","male"));

    }

    @Test
    public void getAllCustomer_success() throws Exception{

        given(customerService.getAllCusotmers()).willReturn(customersList);

        this.mockmvc.perform(get("/getAllCustomers"))
                .andExpect(status().isOk());
    }

    private StatusResultMatchers status() {
        return null;
    }


}