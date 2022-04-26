package org.quickstart.unirest.example.konghq;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class UnirestTest {

    @Test
    public void testPost() throws UnirestException {
        String URL = "";
        String result = Unirest.post(URL + "/generateUser")//
            .header("Auth", "demo")//
            .queryString("page", 2)//
            .queryString("rows", 5)//
            .asString()//
            .getBody();


        JsonNode result2 = Unirest.post(URL + "/generateUser")//
            .header("Auth", "demo")//
            .queryString("page", 2)//
            .queryString("rows", 5)//
            .asJson()//
            .getBody();
        log.info(result2.getObject().getString("total"));


        HttpResponse<JsonNode> response = Unirest.post("http://localhost/post")//
            .header("accept", "application/json")//
            .queryString("apiKey", "123")//
            .field("parameter", "value")//
            .field("firstname", "Gary")//
            .asJson();
    }

    @Test
    public void testGet() throws UnirestException {
        String URL = "";

        String result = Unirest.get(URL + "user/{id}")//
            .header("Auth", "demo")//
            .routeParam("id", "1")//
            .asString()//
            .getBody();
        log.info(result);



        /*Unirest.config().setObjectMapper(new JacksonObjectMapper());
        UserEntity result2 = Unirest.get(URL + "user/{id}")
            .header("Auth", "demo")
            .routeParam("id", "1")
            .asObject(UserEntity.class)
            .getBody();
        log.info(result2.getName());*/

    }

}
