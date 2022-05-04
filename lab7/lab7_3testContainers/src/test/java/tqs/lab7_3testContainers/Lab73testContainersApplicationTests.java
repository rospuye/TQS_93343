package tqs.lab7_3testContainers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.junit.jupiter.api.Order;

@Testcontainers
@SpringBootTest
class Lab73testContainersApplicationTests {

    @Container
    public static PostgreSQLContainer container = new PostgreSQLContainer()
            .withUsername("isabel")
            .withPassword("password")
            .withDatabaseName("booksDB");

    @Autowired
    private BookRepository bookRepository;

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    @Test
    @Order(1)
    void contextLoads() {

        Book book = new Book();
        book.setTitle("Pride And Prejudice");
        book.setAuthor("Jane Austen");
        book.setPage_number(412);

        bookRepository.save(book);

        System.out.println("Context loads!");
    }

}

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertTrue;

// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.List;

// import org.junit.jupiter.api.Order;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.context.DynamicPropertyRegistry;
// import org.springframework.test.context.DynamicPropertySource;
// import org.testcontainers.containers.PostgreSQLContainer;
// import org.testcontainers.junit.jupiter.Container;
// import org.testcontainers.junit.jupiter.Testcontainers;

// @Testcontainers
// @SpringBootTest
// class Lab73testContainersApplicationTests {

// 	@SuppressWarnings("rawtypes")
// 	@Container
// 	static PostgreSQLContainer container = new PostgreSQLContainer("postgres:11.1")
// 		.withUsername("isabel")
// 		.withPassword("password")
// 		.withDatabaseName("tqs_lab7_3");

// 	@Autowired
// 	private BookRepository repository;

// 	@DynamicPropertySource
// 	static void properties(DynamicPropertyRegistry registry) {
// 		registry.add("spring.datasource.url", container::getJdbcUrl);
// 		registry.add("spring.datasource.password", container::getPassword);
// 		registry.add("spring.datasource.username", container::getUsername);
// 	}

// 	@Test
// 	@Order(1)
// 	void insertBook() {
// 		Book book = new Book();
// 		book.setTitle("Pride And Prejudice");
// 		book.setAuthor("Jane Austen");
//         book.setPage_number(367);
// 		repository.saveAndFlush(book);

// 		Book res = repository.findById(book.getId()).get();
// 		assertEquals("Pride And Prejudice", res.getTitle());
// 		assertEquals("Jane Austen", res.getAuthor());
//         assertEquals(367, res.getPage_number());
// 	}

// 	@Test
// 	@Order(2)
// 	void readBooks() {
// 		List<Book> res = repository.findAll();
// 		List<String> names = new ArrayList<>();
// 		res.forEach((Book b) -> names.add(b.getTitle()));

// 		assertEquals(2, res.size());
// 		assertTrue(names.containsAll(Arrays.asList("Pride And Prejudice", "A Sibila")));
// 	}

// }