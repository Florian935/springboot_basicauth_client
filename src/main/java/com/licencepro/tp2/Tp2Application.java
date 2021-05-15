package com.licencepro.tp2;

import com.licencepro.tp2.domain.Book;
import com.licencepro.tp2.web.BookClient;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootApplication
public class Tp2Application {

	private final BookClient bookClient;

	public Tp2Application(BookClient bookClient) {
		this.bookClient = bookClient;
	}

	private static final Logger log = LoggerFactory.getLogger(Tp2Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Tp2Application.class, args);
	}

	@Bean
	public CommandLineRunner run() throws Exception {
		return args -> {
			bookClient.save();
			bookClient.getAll();
		};
	}
}
