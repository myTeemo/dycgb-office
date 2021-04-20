package com.dycgb.office.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.dycgb.office.*"})
@EntityScan("com.dycgb.office.common.model")
@EnableJpaRepositories("com.dycgb.office.common.repository")
@EnableJpaAuditing
public class OfficeAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(OfficeAdminApplication.class, args);
    }

}
