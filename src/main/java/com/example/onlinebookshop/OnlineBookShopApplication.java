package com.example.onlinebookshop;

import com.example.onlinebookshop.entities.Book;
import com.example.onlinebookshop.services.BookService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OnlineBookShopApplication {
    @Autowired
    private BookService bookService;
    private final String title = "Harry Potter and the Philosopher's Stone";
    private final String author = "J. K. Rowling";
    private final String isbn = "978-0-7475-3269-9";
    private final BigDecimal price = BigDecimal.valueOf(390);
    private final String description = """
            Harry Potter and the Philosopher's Stone is a fantasy novel written by British author
            J. K. Rowling. The first novel in the Harry Potter series and Rowling's debut novel,
            it follows Harry Potter, a young wizard who discovers his magical heritage on his
            eleventh birthday, when he receives a letter of acceptance to Hogwarts School of
            Witchcraft and Wizardry. Harry makes close friends and a few enemies during his first
            year at the school and with the help of his friends, Ron Weasley and Hermione Granger,
            he faces an attempted comeback by the dark wizard Lord Voldemort, who killed Harry's
            parents, but failed to kill Harry when he was just 15 months old.
            """;
    private final String coverImage = "https://content2.rozetka.com.ua/goods/images/original/72555843.jpg";

    public static void main(String[] args) {
        SpringApplication.run(OnlineBookShopApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Book harryPotterAndThePhilosophersStone = new Book();
                harryPotterAndThePhilosophersStone.setTitle(title);
                harryPotterAndThePhilosophersStone.setAuthor(author);
                harryPotterAndThePhilosophersStone.setIsbn(isbn);
                harryPotterAndThePhilosophersStone.setPrice(price);
                harryPotterAndThePhilosophersStone.setDescription(description);
                harryPotterAndThePhilosophersStone.setCoverImage(coverImage);

                bookService.save(harryPotterAndThePhilosophersStone);
                System.out.println(bookService.findAll());
            }
        };
    }
}
