package io.microprofile.showcase.client;

import java.io.IOException;
import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;

public class AuthHeadersRequestFilter implements ClientRequestFilter {

    private final String authToken;

    public AuthHeadersRequestFilter(String credentials) {
        authToken = credentials;
    }

    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {

        System.out.println("Inside Filter");

        JsonReader jsonReader = Json.createReader(new StringReader(authToken));
        JsonObject jsonObject  = jsonReader.readObject();
        jsonReader.close();
        String token = jsonObject.getString("id_token");
        System.out.println("token:" + token);
        requestContext.getHeaders().add("Authorization", "Bearer " + token);
    }
}