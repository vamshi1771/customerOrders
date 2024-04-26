package com.example.Spring_boot_2.Repository;

import com.example.Spring_boot_2.entity.Orders;
import com.example.Spring_boot_2.repository.OrderRepository;
import lombok.Data;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

class OrderRepositoryTest {


    @Autowired
 private    OrderRepository orderRepo;

    private  Orders oder;

    @BeforeEach
    void setUp() {
        Orders oder = Orders.builder()
                .price(1000)
                .ordername("bike")
                .cust_id(9262)
                .build();
    }

    @Test
    public void SaveOrder(){
        Orders order= Orders.builder()
                .price(1000)
                .ordername("car")
                .cust_id(4253)
                .build();
        Orders savedOrder = orderRepo.save(order);
        assertThat(savedOrder).isNotNull();
        assertThat(savedOrder.getOrderid()).isGreaterThan(0);


    }

}