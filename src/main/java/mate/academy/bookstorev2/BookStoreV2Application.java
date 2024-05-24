package mate.academy.bookstorev2;

import java.math.BigDecimal;
import mate.academy.bookstorev2.model.Book;
import mate.academy.bookstorev2.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookStoreV2Application {
    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(BookStoreV2Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Book bookAlice = new Book();
            bookAlice.setAuthor("Luis Carol");
            bookAlice.setTitle("Alice in Wonderland");
            bookAlice.setDescription("""
                    The famous Alice from Wonderland" is a little girl,
                    about seven years old, with straight bangs and hair 
                    that always climbs into her eyes.
                    """);
            bookAlice.setPrice(BigDecimal.valueOf(10));
            bookAlice.setIsbn("978-1-2345-678-9-0");

            Book bookJava = new Book();
            bookJava.setAuthor("Allen B. Downey, Chris Mayfield");
            bookJava.setTitle("Think Java: How to Think Like a Computer Scientist");
            bookJava.setDescription("""
                    Think Java is a hands-on introduction to computer science and 
                    programming used by many universities and high schools around the world.
                    """);
            bookJava.setPrice(BigDecimal.valueOf(20));
            bookJava.setIsbn("978-123-4567-89-0");

            bookService.save(bookAlice);
            bookService.save(bookJava);
            System.out.println(bookService.findAll());
        };
    }
}
