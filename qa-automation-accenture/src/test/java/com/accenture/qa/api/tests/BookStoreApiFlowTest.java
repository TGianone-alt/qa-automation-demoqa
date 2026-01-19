package com.accenture.qa.api.tests;

import com.accenture.qa.api.client.AccountClient;
import com.accenture.qa.api.client.BookStoreClient;
import com.accenture.qa.api.model.*;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class BookStoreApiFlowTest {

    private final AccountClient account = new AccountClient();
    private final BookStoreClient store = new BookStoreClient();

    @Test
    void should_create_user_auth_list_books_reserve_two_and_validate_user_details() {

        String username = "user_" + System.currentTimeMillis();
        String password = "Test@1234";

        // 1) criar usuário
        Response createResp = account.createUser(new CreateUserRequest(username, password));
        assertEquals(201, createResp.statusCode(), "Falha ao criar usuário: " + createResp.asString());

        CreateUserResponse created = createResp.as(CreateUserResponse.class);
        assertNotNull(created.getUserId(), "userId veio nulo");
        assertEquals(username, created.getUsername(), "username não bate");

        // 2) gerar token
        Response tokenResp = account.generateToken(new GenerateTokenRequest(username, password));
        assertEquals(200, tokenResp.statusCode(), "Falha ao gerar token: " + tokenResp.asString());

        GenerateTokenResponse token = tokenResp.as(GenerateTokenResponse.class);
        assertNotNull(token.getToken(), "token veio nulo");
        assertFalse(token.getToken().trim().isEmpty(), "token veio vazio");

        // 3) authorized
        Response authResp = account.authorized(new AuthorizedRequest(username, password));
        assertEquals(200, authResp.statusCode(), "Falha no authorized: " + authResp.asString());
        assertTrue(authResp.as(Boolean.class), "Usuário não está autorizado");

        // 4) listar livros
        Response booksResp = store.listBooks();
        assertEquals(200, booksResp.statusCode(), "Falha ao listar livros: " + booksResp.asString());

        BooksResponse books = booksResp.as(BooksResponse.class);
        assertNotNull(books.getBooks(), "Lista de livros veio nula");
        assertTrue(books.getBooks().length >= 2, "Precisa ter pelo menos 2 livros disponíveis");

        // 5) reservar 2 livros
        List<String> isbns = Arrays.asList(
                books.getBooks()[0].getIsbn(),
                books.getBooks()[1].getIsbn()
        );

        Response addResp = store.addBooks(created.getUserId(), token.getToken(), isbns);
        assertTrue(addResp.statusCode() == 200 || addResp.statusCode() == 201,
                "Falha ao reservar livros. Status: " + addResp.statusCode() + " Body: " + addResp.asString());

        // 6) buscar detalhes e validar
        Response detailsResp = account.getUserDetails(created.getUserId(), token.getToken());
        assertEquals(200, detailsResp.statusCode(), "Falha ao buscar detalhes: " + detailsResp.asString());

        UserDetailsResponse details = detailsResp.as(UserDetailsResponse.class);

        assertEquals(created.getUserId(), details.getUserId(), "userId retornado não bate");
        assertNotNull(details.getBooks(), "books veio nulo no details");
        assertEquals(2, details.getBooks().size(), "Quantidade de livros reservados deveria ser 2");

        List<String> reserved = details.getBooks()
                .stream()
                .map(BookItem::getIsbn)
                .collect(Collectors.toList());

        assertTrue(reserved.containsAll(isbns),
                "Livros reservados não batem. Esperado: " + isbns + " Atual: " + reserved);
    }
}
