package com.accenture.qa.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateUserResponse {

    @JsonProperty("userID")
    private String userId;

    private String username;

    private List<BookItem> books;

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public List<BookItem> getBooks() { return books; }
    public void setBooks(List<BookItem> books) { this.books = books; }
}
