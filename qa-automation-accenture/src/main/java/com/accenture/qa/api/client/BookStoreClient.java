package com.accenture.qa.api.client;

import com.accenture.qa.api.model.AddBooksRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.List;
import java.util.stream.Collectors;

public class BookStoreClient extends BaseApiClient {

    public Response listBooks() {
        return RestAssured.given(baseSpec())
                .get("/BookStore/v1/Books")
                .andReturn();
    }

    public Response addBooks(String userId, String token, List<String> isbns) {

        AddBooksRequest payload = new AddBooksRequest(
                userId,
                isbns.stream()
                        .map(AddBooksRequest.CollectionOfIsbns::new)
                        .collect(Collectors.toList())
        );

        return RestAssured.given(baseSpecWithAuth(token))
                .body(payload)
                .post("/BookStore/v1/Books")
                .andReturn();
    }
}
