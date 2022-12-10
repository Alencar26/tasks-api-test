package io.al3ncar.tasks.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class ApiTest {

	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost:8001/tasks-backend";
	}
	
	@Test
	public void deveRetornarTarefas() {
		RestAssured.given()
			.log().all()
		.when()
			.get("/todo")
		.then()
			.statusCode(200)
			.log().all()
		;
	}
	
	@Test
	public void deveAdicionarTarefaComSucesso() {
		RestAssured
			.given()
				.body("{\n"
						+ "	\"task\":\"tarefa 3\",\n"
						+ "	\"dueDate\":\"2022-12-25\"\n"
						+ "}")
				.contentType(ContentType.JSON)
			.when()
				.post("/todo")
			.then()
				.statusCode(201)
			;
	}
	
	@Test
	public void naoDeveAdicionarTarefaInvalida() {
		RestAssured
		.given()
			.body("{\n"
					+ "	\"task\":\"tarefa 3\",\n"
					+ "	\"dueDate\":\"2010-12-25\"\n"
					+ "}")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.log().all()
			.statusCode(400)
			.body("message", CoreMatchers.is("Due date must not be in past"))
		;
	}
}




