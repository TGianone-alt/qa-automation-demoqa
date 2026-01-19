package com.accenture.qa.api.model;

import java.util.List;

public class AddBooksRequest {

    private String userId;
    private List<CollectionOfIsbns> collectionOfIsbns;

    public AddBooksRequest() {}

    public AddBooksRequest(String userId, List<CollectionOfIsbns> collectionOfIsbns) {
        this.userId = userId;
        this.collectionOfIsbns = collectionOfIsbns;
    }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public List<CollectionOfIsbns> getCollectionOfIsbns() { return collectionOfIsbns; }
    public void setCollectionOfIsbns(List<CollectionOfIsbns> collectionOfIsbns) { this.collectionOfIsbns = collectionOfIsbns; }

    public static class CollectionOfIsbns {
        private String isbn;

        public CollectionOfIsbns() {}
        public CollectionOfIsbns(String isbn) { this.isbn = isbn; }

        public String getIsbn() { return isbn; }
        public void setIsbn(String isbn) { this.isbn = isbn; }
    }
}
