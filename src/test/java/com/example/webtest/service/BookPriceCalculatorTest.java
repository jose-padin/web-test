package com.example.webtest.service;

import com.example.webtest.model.Book;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BookPriceCalculatorTest {

    @Test
    void calculatePrice() {
        LocalDate date = LocalDate.of(1848, 1, 1);
        BookPriceCalculator calculator = new BookPriceCalculator();
        Book book = new Book(
                1L, "El manifiesto comunista", "Karl Marx", 100, date, true, 10.90
        );
        double price = calculator.calculatePrice(book);

        assertTrue(price > 0);
        assertTrue(price > 2.95);
    }
}