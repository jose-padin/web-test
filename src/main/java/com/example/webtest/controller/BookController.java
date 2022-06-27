package com.example.webtest.controller;

import com.example.webtest.model.Book;
import com.example.webtest.repository.BookRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {
    BookRepository repository;

    public BookController(BookRepository repository) {
        this.repository = repository;
    }

    // GET
    @GetMapping("/books")
    public List<Book> findAll() {
        return this.repository.findAll();
    }

    // POST
    @PostMapping("/books")
    public Book create(@RequestBody Book book) {
        return (this.repository.save(book));
    }

    // GET
    @GetMapping("/books/{id}")
    public ResponseEntity<Book> findById(@PathVariable Long id) {
        Optional<Book> optBook = this.repository.findById(id);
        if (optBook.isPresent()) {
            return ResponseEntity.ok(optBook.get());
        }

        return ResponseEntity.notFound().build();
    }

    // PATCH
//    @PatchMapping("/books/{id}")
//    public ResponseEntity<Book> partialUpdate(@PathVariable Long id, @RequestBody Book book) {
//        Book book = this.repository.findById(id);
//
//    }
    // PUT
    @PutMapping("/books")
    public ResponseEntity<Book> update(@RequestBody Book book) {
        if (book.getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        if (!this.repository.existsById(book.getId())) {
            return ResponseEntity.notFound().build();
        }

        Book savedBook = this.repository.save(book);
        return ResponseEntity.ok(savedBook);
    }

    // DELETE
    @DeleteMapping("/books")
//    public ResponseEntity delete(@PathVariable Long id) {
    public ResponseEntity delete(@RequestBody Book book) {
        if (book.getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        if (!this.repository.existsById(book.getId())) {
            return ResponseEntity.notFound().build();
        }

        this.repository.delete(book);
//        this.repository.deleteById(id);

        return (ResponseEntity) ResponseEntity.noContent().build();
    }
}
