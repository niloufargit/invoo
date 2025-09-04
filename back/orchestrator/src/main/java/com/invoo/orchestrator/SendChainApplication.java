package com.invoo.orchestrator;

import com.invoo.orchestrator.application.config.auditing.ApplicationAuditAware;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SendChainApplication {
	public SendChainApplication(ApplicationAuditAware auditorAware, ApplicationAuditAware auditorAware1) {
	}

	public static void main(String[] args) {
		SpringApplication.run(SendChainApplication.class, args);
	}
}
