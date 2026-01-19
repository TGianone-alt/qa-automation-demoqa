package com.accenture.qa.api.client;

import com.accenture.qa.api.model.AuthorizedRequest;
import com.accenture.qa.api.model.CreateUserRequest;
import com.accenture.qa.api.model.GenerateTokenRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class AccountClient extends BaseApiClient {

    public Response createUser(CreateUserRequest req) {
        return RestAssured.given(baseSpec())
                .body(req)
                .post("/Account/v1/User")
                .andReturn();
    }

    public Response generateToken(GenerateTokenRequest req) {
        return RestAssured.given(baseSpec())
                .body(req)
                .post("/Account/v1/GenerateToken")
                .andReturn();
    }

    public Response authorized(AuthorizedRequest req) {
        return RestAssured.given(baseSpec())
                .body(req)
                .post("/Account/v1/Authorized")
                .andReturn();
    }

    public Response getUserDetails(String userId, String token) {
        return RestAssured.given(baseSpecWithAuth(token))
                .get("/Account/v1/User/" + userId)
                .andReturn();
    }
}
