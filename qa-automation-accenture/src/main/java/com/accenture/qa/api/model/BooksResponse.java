package com.accenture.qa.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BooksResponse {
    private BookItem[] books;

    public BookItem[] getBooks() { return books; }
    public void setBooks(BookItem[] books) { this.books = books; }
}
