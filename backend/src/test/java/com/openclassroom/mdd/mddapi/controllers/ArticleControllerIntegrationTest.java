// package com.openclassroom.mdd.mddapi.controllers;
// import static org.junit.jupiter.api.Assertions.*;
// import com.fasterxml.jackson.databind.JsonNode;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.openclassroom.mdd.mddapi.dtos.ArticleCreateReq;
// import com.openclassroom.mdd.mddauth.dtos.AuthSigninReq;
// import com.openclassroom.mdd.mddauth.dtos.AuthSignupReq;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.resttestclient.TestRestTemplate;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.web.server.LocalServerPort;
// import org.springframework.http.*;
// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// class ArticleControllerIntegrationTest {
//     @LocalServerPort
//     private int port;
//     @Autowired
//     private TestRestTemplate restTemplate;
//     @Autowired
//     private ObjectMapper objectMapper;
//     private final String email = "test@test.com";
//     private final String username = "testuser";
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
//             return json.get("token").asText(); // adjust if needed
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
//     void should_create_article_authenticated() {
//         String token = loginAndGetToken();
//         ArticleCreateReq req = new ArticleCreateReq("title", "content", 1L);
//         HttpEntity<ArticleCreateReq> request = new HttpEntity<>(
//             req,
//             authHeaders(token)
//         );
//         ResponseEntity<String> response = restTemplate.postForEntity(
//             baseUrl() + "/api/article",
//             request,
//             String.class
//         );
//         assertEquals(HttpStatus.OK, response.getStatusCode());
//     }
//     @Test
//     void should_block_create_article_when_not_authenticated() {
//         ArticleCreateReq req = new ArticleCreateReq("title", "content", 1L);
//         HttpEntity<ArticleCreateReq> request = new HttpEntity<>(
//             req,
//             new HttpHeaders()
//         );
//         ResponseEntity<String> response = restTemplate.postForEntity(
//             baseUrl() + "/api/article",
//             request,
//             String.class
//         );
//         assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
//     }
//     @Test
//     void should_get_articles_authenticated() {
//         String token = loginAndGetToken();
//         HttpEntity<Void> request = new HttpEntity<>(authHeaders(token));
//         ResponseEntity<String> response = restTemplate.exchange(
//             baseUrl() + "/api/article",
//             HttpMethod.GET,
//             request,
//             String.class
//         );
//         assertEquals(HttpStatus.OK, response.getStatusCode());
//     }
//     @Test
//     void should_get_article_by_id_authenticated() {
//         String token = loginAndGetToken();
//         HttpEntity<Void> request = new HttpEntity<>(authHeaders(token));
//         ResponseEntity<String> response = restTemplate.exchange(
//             baseUrl() + "/api/article/1",
//             HttpMethod.GET,
//             request,
//             String.class
//         );
//         assertEquals(HttpStatus.OK, response.getStatusCode());
//     }
// }
