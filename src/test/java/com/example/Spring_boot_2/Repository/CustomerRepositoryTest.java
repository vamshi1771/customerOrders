package com.example.Spring_boot_2.Repository;



import com.example.Spring_boot_2.entity.Customers;
import com.example.Spring_boot_2.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerRepositoryTest {
 @Autowired
 private  CustomerRepository customerRepo;
private  Customers customer;
    @BeforeEach
    void setUp() {
         customer =Customers.builder()
                .customername("shiva")
                .customerid(13)
                .region("Kodad")
                .gender("Male")
                .build();
    }


    @Test
    public void GivenCustomerObject_WhenSaveCustomer_thenReturnSavedCustomer() {
Customers customer =Customers.builder()
        .customername("vamshi")
        .customerid(10)
        .region("Kodad")
        .gender("Male")
        .build();

        Customers savedCustomer= customerRepo.save(customer);
        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getCustomerid()).isGreaterThan(0);
    }


    @Test
    public void GivenCustomerObject_WhenFindall_thenReturnCustomerList() {
        Customers customer =Customers.builder()
                .customername("vamshi")
                .customerid(10)
                .region("Kodad")
                .gender("Male")
                .build();
        Customers customer1 =Customers.builder()
                .customername("Karthik")
                .customerid(12)
                .region("Kodad")
                .gender("Male")
                .build();

        customerRepo.save(customer);
        customerRepo.save(customer1);

        List<Customers> customersList =customerRepo.findAll();

        assertThat(customersList).isNotEmpty();
        assertThat(customersList.size()).isEqualTo(2);
    }
}
