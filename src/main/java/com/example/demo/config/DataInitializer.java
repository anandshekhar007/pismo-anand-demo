package com.example.demo.config;

import com.example.demo.model.OperationType;
import com.example.demo.repository.OperationTypeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(OperationTypeRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                repo.save(new OperationType(1, "Normal Purchase"));
                repo.save(new OperationType(2, "Purchase with installments"));
                repo.save(new OperationType(3, "Withdrawal"));
                repo.save(new OperationType(4, "Credit Voucher"));
            }
        };
    }
}
