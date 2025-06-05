package com.example.task.service;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;


class TodoServiceTestV2 {
  RequestSpecification requestSpecification;
  Response response;
  ValidatableResponse validatableResponse;

  @Test
  void testGetAllTodos() {
    RestAssured.baseURI = "http://localhost:8080/api/v1/todos";
    requestSpecification = RestAssured.given();
    response = requestSpecification.get();
    String resString = response.prettyPrint();
   System.out.println("Response Details : " + resString);

    /*validatableResponse = response.then();
    validatableResponse.statusCode(200);
    validatableResponse.statusLine("HTTP/1.1 200");
*/
    String statusLine = response.getStatusLine();
    Assertions.assertEquals(statusLine, "HTTP/1.1 200 ");

    int statusCode = response.getStatusCode();
    Assertions.assertEquals(statusCode, 200);
  }
 }
