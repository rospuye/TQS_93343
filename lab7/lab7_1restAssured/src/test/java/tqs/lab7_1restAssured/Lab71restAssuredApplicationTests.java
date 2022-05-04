package tqs.lab7_1restAssured;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

// import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.when;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;

@SpringBootTest
class Lab71restAssuredApplicationTests {

	@Test
	public void whenRequestGet_thenOK() {
		when()
				.get("https://jsonplaceholder.typicode.com/todos")
				.then()
				.statusCode(200);
	}

	@Test
	public void whenRequest4_thenEtPorroTempora() {
		when()
				.get("https://jsonplaceholder.typicode.com/todos/4")
				.then()
				.body("title", is("et porro tempora"));
	}

	@Test
	public void whenRequestAll_then198And199() {
		when()
				.get("https://jsonplaceholder.typicode.com/todos")
				.then()
				.body("id", hasItems(198, 199));
	}

}
