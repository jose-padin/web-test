package com.example.webtest.controller;

import com.example.webtest.model.Laptop;
import com.example.webtest.repository.LaptopRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class LaptopController {
    @Value("${app.message}")
    String message;

    private LaptopRepository repository;

    public LaptopController(LaptopRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/laptops")
    public List<Laptop> findAll() {
        System.out.println(message);
        return this.repository.findAll();
    }

    @GetMapping("/laptops/{id}")
    public ResponseEntity<Laptop> findOne(@PathVariable Long id) {
        Optional<Laptop> laptop = repository.findById(id);

        if (laptop.isPresent()) {
            return ResponseEntity.ok(laptop.get());
        }

        return ResponseEntity.notFound().build();
    }
    @PostMapping("laptops")
    public Laptop create(@RequestBody Laptop laptop) {
        return this.repository.save(laptop);
    }

    @PutMapping("laptops/{id}")
    public ResponseEntity<Laptop> update(@PathVariable Long id, @RequestBody Laptop laptop) {
        Optional<Laptop> optLaptop = repository.findById(id);

        if (!optLaptop.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Laptop updatedLaptop = optLaptop.get();
        updatedLaptop.setName(laptop.getName());
        this.repository.save(updatedLaptop);
        return ResponseEntity.ok(updatedLaptop);
    }

    @DeleteMapping("/laptops/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        Optional<Laptop> optLaptop = repository.findById(id);

        if (!optLaptop.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        this.repository.delete(optLaptop.get());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("laptops")
    public ResponseEntity deleteAll() {
        this.repository.deleteAll();
        return ResponseEntity.notFound().build();
    }
}
