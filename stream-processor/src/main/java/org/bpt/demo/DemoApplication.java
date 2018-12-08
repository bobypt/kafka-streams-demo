package org.bpt.demo;

import org.bpt.demo.config.StreamBinding;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.schema.client.EnableSchemaRegistryClient;

@SpringBootApplication
@EnableBinding(StreamBinding.class)
@EnableSchemaRegistryClient
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
