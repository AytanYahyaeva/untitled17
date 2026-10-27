package org.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.List;
import org.testng.Assert;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class RestTask {
    @BeforeMethod
    public void setup(){
        RestAssured.baseURI="https://jsonplaceholder.typicode.com";
        RestAssured.useRelaxedHTTPSValidation();
    }
// Tapşırıq 1 — Blog Post API-nin test edilməsi
// Bütün postlar (https://jsonplaceholder.typicode.com/posts?utm_source=chatgpt.com) · ID-si 1 olan post (https://jsonplaceholder.typicode.com/posts/1?utm_source=chatgpt.com) · User ID-yə görə postlar (https://jsonplaceholder.typicode.com/posts?userId=1&utm_source=chatgpt.com)
//• GET /posts sorğusu göndər:
// • Status code 200 olmalıdır.
// • Response body JSON array olmalıdır.
// • Array-də 100 post olduğunu yoxla.
// • Hər postun id, userId, titlevə body sahələri olmalıdır.
    @Test
    public void firstGet(){
        given()
        .when()
                .get("/posts")
        .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()",equalTo(100))
                .body("id",everyItem(notNullValue()))
                .body("userId",everyItem(notNullValue()))
                .body("title",everyItem(notNullValue()))
                .body("body",everyItem(notNullValue()));
    }
// • GET /posts/1 sorğusu göndər:
// • Status code 200 olmalıdır.
// •  id dəyərinin 1 olduğunu yoxla.
// • title və body boş olmamalıdır.
// •  Content-Type header-inin JSON formatına uyğun olduğunu yoxla.
    @Test
    public void secondGet(){
        given()
        .when()
        .get("/posts/1")
        .then()
                .statusCode(200)
                .body("id",equalTo(1))
                .body("title",notNullValue())
                .body("body",notNullValue())
                .contentType(ContentType.JSON);

    }

//    • GET /posts?userId=1 sorğusu göndər:
//    • Yalnız userId = 1 olan postların qaytarıldığını yoxla.
//    • Nəticələrin boş olmadığını yoxla.
    @Test
    public void thirdGet(){
        given()
                .queryParam("userId",1)
        .when()
                .get("/posts")
        .then()
                .body("userId",everyItem(equalTo(1)))
                .body("size()", greaterThan(0))
                .body("userId",notNullValue())
                .body("id",notNullValue())
                .body("title",notNullValue())
                .body("body",notNullValue());
    }

//    Tapşırıq 2 — Post və Comment əlaqələrinin yoxlanılması
//    Post 1-in şərhləri (https://jsonplaceholder.typicode.com/posts/1/comments?utm_source=chatgpt.com) · postId ilə şərhlər (https://jsonplaceholder.typicode.com/comments?postId=1&utm_source=chatgpt.com) · User məlumatları (https://jsonplaceholder.typicode.com/users/1?utm_source=chatgpt.com)
//    GET /posts/1/commentssorğusu göndər:
//    Status code 200 olmalıdır.
//    Response boş olmamalıdır.
//    Hər şərhdə postId, id, name, email və body sahələri olmalıdır.
//    Bütün şərhlərin postId dəyərinin 1 olduğunu yoxla.
    @Test
     public void getCommentsByGet(){
        given()
        .when()
                .get("/posts/1/comments")
        .then()
                .statusCode(200)
                .body("size()",greaterThan(0))
                .body("postId",everyItem(notNullValue()))
                .body("id",everyItem(notNullValue()))
                .body("name",everyItem(notNullValue()))
                .body("email",everyItem(notNullValue()))
                .body("body",everyItem(notNullValue()))
                .body("postId",everyItem(equalTo(1)));
    }
//    • Eyni məlumatı GET /comments?postId=1 vasitəsilə əldə et:
//    • Hər iki endpoint-in eyni sayda şərh qaytardığını yoxla.
//    • Hər iki cavabın eyni şərh ID-lərini ehtiva etdiyini yoxla.
    @Test
    public void compareEndpoints(){
        List <Integer> id1 =
        given()
                .queryParam("postId",1)
        .when()
                .get("/comments")
        .then()
                .statusCode(200)
                .extract().path("id");

        List <Integer> id2 =
        given()
        .when()
                .get("/posts/1/comments")
        .then()
                .statusCode(200)
                .extract().path("id");

        Assert.assertEquals(id1.size(),id2.size());
        Assert.assertEquals(id1,id2);

    }
//    • GET /posts/1 sorğusundan userId dəyərini çıxar və həmin istifadəçinin məlumatlarını GET /users/{id} vasitəsilə əldə et:
//    • İstifadəçinin id dəyəri post-un userId dəyərinə bərabər olmalıdır.
//    • name, email, usernamesahələrinin boş olmadığını yoxla.
    @Test
    public void getUserId(){
        int userId1 =
                given()
                .when()
                        .get("/posts/1")
                .then()
                        .statusCode(200)
                        .extract().path("userId");

        given()
                .pathParam("id",userId1)
        .when()
                .get("/users/{id}")
        .then()
                .statusCode(200)
                .body("id", equalTo(userId1))
                .body("name", notNullValue())
                .body("email", notNullValue())
                .body("username", notNullValue());
    }
//    Tapşırıq 3 — CRUD əməliyyatlarının test edilməsi
//    Posts endpoint-i (https://jsonplaceholder.typicode.com/posts?utm_source=chatgpt.com) · API sənədləri (https://jsonplaceholder.typicode.com/?utm_source=chatgpt.com)
//            • Create — POST
//            • Yeni post yarat.
//            • userId, title və body göndər.
//                              • Status code 201 olmalıdır.
//                              • Response-dakı title, body və userId göndərilən dəyərlərlə eyni olmalıdır.
//                              • Response-da id mövcud olmalıdır.
    @Test
    public void createPost(){
        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "userId": 101,
                          "title": "this is a title",
                          "body": "this is a body u know"
                        }
                        """)
        .when()
                .post("/posts")
        .then()
                .statusCode(201)
                .body("userId",equalTo(101))
                .body("title",equalTo("this is a title"))
                .body("body",equalTo("this is a body u know"))
                .body("id",notNullValue());

    }



//                              • Update — PATCH
//                              • Mövcud post-un yalnız titlesahəsini dəyiş.
//                              • Status code 200 olmalıdır.
//                              • Response-da yeni title və dəyişməyən digər sahələri yoxla.
    @Test
    public void taskPatch(){
        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "title": "this is a new title"
                        }
""")
        .when()
                .patch("posts/1")
        .then()
                .statusCode(200)
                .body("title",equalTo("this is a new title"))
                .body("userId",equalTo(1))
                .body("body",equalTo("quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"));
    }

//                              • Replace — PUT
//                              • Post-u yeni məlumatlarla tam əvəz etmək üçün sorğu göndər.
//                              • Status code 200 olmalıdır.
//                              • Response-dakı sahələrin göndərilən məlumatlara uyğunluğunu yoxla.
    @Test
    public void taskPut(){
        given()
                .contentType(ContentType.JSON)
                .body("""
{
                          "userId": 111,
                          "title": "this is a title for put",
                          "body": "this is a body for put"
                        }
""")
                .when()
                .put("posts/1")
                .then()
                .statusCode(200)
                .body("userId",equalTo(111))
                .body("title",equalTo("this is a title for put"))
                .body("body",equalTo("this is a body for put"));
    }
//                              • Delete — DELETE
//                              • Post-u silmək üçün sorğu göndər.
//                              • JSONPlaceholder üçün gözlənilən status code-u yoxla.
//                              • Silmə sorğusunun cavabını yoxla; boş obyekt və ya boş response kimi davranışı API-nin faktiki cavabına uyğun qiymətləndir.
    @Test
    public void taskDelete(){
        given()
                .when()
                .delete("posts/1")
                .then()
                .statusCode(200)
                .body(equalTo("{}"));

    }
//        • Negative testing
// • Yanlış formatlı request body göndər.
// • Mövcud olmayan ID ilə update və delete sorğuları göndər.
// • API-nin faktiki status code və response davranışını sənədləşdir.
    @Test
    public void invalidFormat(){
        given()
                .body("""
                        userId: 1, title
                        """)
                .when()
                .post("posts")
                .then()
                .statusCode(201)
                .log().all();

    }

    @Test
    public void nonExistId(){
        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body("""
 {
                          "title": "this is a new title"
                        }
""")
                .when()
                .patch("posts/9493")
                .then()
                .statusCode(204)
                .log().all();
    }
    @Test
    public void nonExistId2(){
        given()
                .when()
                .delete("posts/133333")
                .then()
                .statusCode(200)
                .body(equalTo("{}"));

    }


}
