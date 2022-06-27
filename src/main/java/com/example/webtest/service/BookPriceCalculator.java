package com.example.webtest.service;

import com.example.webtest.model.Book;

public class BookPriceCalculator {

    public double calculatePrice(Book book) {
        double price = book.getPrice();

        if (book.getPages() > 300) {
            price += 5;
        }

        // envío
        return price += 2.95;
    }
}
