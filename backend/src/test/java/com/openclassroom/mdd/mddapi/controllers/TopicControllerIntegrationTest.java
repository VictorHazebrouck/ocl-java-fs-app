// package com.openclassroom.mdd.mddapi.controllers;
// import static org.junit.jupiter.api.Assertions.*;
// import com.fasterxml.jackson.databind.JsonNode;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.openclassroom.mdd.mddauth.dtos.AuthSigninReq;
// import com.openclassroom.mdd.mddauth.dtos.AuthSignupReq;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.resttestclient.TestRestTemplate;
// import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.web.server.LocalServerPort;
// import org.springframework.http.*;
// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @AutoConfigureTestRestTemplate
// public class TopicControllerIntegrationTest {
//     @LocalServerPort
//     private int port;
//     @Autowired
//     private TestRestTemplate restTemplate;
//     private final ObjectMapper objectMapper = new ObjectMapper();
//     private final String email = "topic@test.com";
//     private final String username = "topicuser";
//     private final String password = "password";
//     private String baseUrl() {
//         return "http://localhost:" + port;
//     }
//     @BeforeEach
//     void setupUser() {
//         AuthSignupReq signupReq = new AuthSignupReq(email, username, password);
//         HttpHeaders headers = new HttpHeaders();
//         headers.setContentType(MediaType.APPLICATION_JSON);
//         HttpEntity<AuthSignupReq> request = new HttpEntity<>(
//             signupReq,
//             headers
//         );
//         try {
//             restTemplate.postForEntity(
//                 baseUrl() + "/api/auth/signup",
//                 request,
//                 String.class
//             );
//         } catch (Exception ignored) {}
//     }
//     private String loginAndGetToken() {
//         AuthSigninReq loginReq = new AuthSigninReq(email, password);
//         HttpHeaders headers = new HttpHeaders();
//         headers.setContentType(MediaType.APPLICATION_JSON);
//         HttpEntity<AuthSigninReq> request = new HttpEntity<>(loginReq, headers);
//         ResponseEntity<String> response = restTemplate.postForEntity(
//             baseUrl() + "/api/auth/signin",
//             request,
//             String.class
//         );
//         assertEquals(HttpStatus.OK, response.getStatusCode());
//         try {
//             JsonNode json = objectMapper.readTree(response.getBody());
//             return json.get("accessToken").asText();
//         } catch (Exception e) {
//             throw new RuntimeException(e);
//         }
//     }
//     private HttpHeaders authHeaders(String token) {
//         HttpHeaders headers = new HttpHeaders();
//         headers.setContentType(MediaType.APPLICATION_JSON);
//         headers.setBearerAuth(token);
//         return headers;
//     }
//     @Test
//     void should_get_all_topics() {
//         String token = loginAndGetToken();
//         System.out.println("GET ALL TOPICS TOKEN:");
//         System.out.println(token);
//         HttpEntity<Void> request = new HttpEntity<>(authHeaders(token));
//         ResponseEntity<String> response = restTemplate.exchange(
//             baseUrl() + "/api/topic",
//             HttpMethod.GET,
//             request,
//             String.class
//         );
//         assertEquals(HttpStatus.OK, response.getStatusCode());
//         assertNotNull(response.getBody());
//     }
//     @Test
//     void should_get_topics_with_subscription_flag() {
//         String token = loginAndGetToken();
//         HttpEntity<Void> request = new HttpEntity<>(authHeaders(token));
//         ResponseEntity<String> response = restTemplate.exchange(
//             baseUrl() + "/api/topic/with-am-i-subscribed",
//             HttpMethod.GET,
//             request,
//             String.class
//         );
//         assertEquals(HttpStatus.OK, response.getStatusCode());
//         assertNotNull(response.getBody());
//     }
//     @Test
//     void should_get_only_subscribed_topics() {
//         String token = loginAndGetToken();
//         HttpEntity<Void> request = new HttpEntity<>(authHeaders(token));
//         ResponseEntity<String> response = restTemplate.exchange(
//             baseUrl() + "/api/topic/only-subscribed",
//             HttpMethod.GET,
//             request,
//             String.class
//         );
//         assertEquals(HttpStatus.OK, response.getStatusCode());
//         assertNotNull(response.getBody());
//     }
// }
