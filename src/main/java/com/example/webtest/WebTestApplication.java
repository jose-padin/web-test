package com.example.webtest;

import com.example.webtest.model.Laptop;
import com.example.webtest.repository.BookRepository;
import com.example.webtest.repository.LaptopRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class WebTestApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(WebTestApplication.class, args);
		LaptopRepository lpRepo = ctx.getBean(LaptopRepository.class);
		Laptop lp1 = new Laptop(null, "Laptop1");
		Laptop lp2 = new Laptop(null, "Laptop2");
		lpRepo.save(lp1);
		lpRepo.save(lp2);
		System.out.println("Total: " + lpRepo.count());
	}

}
