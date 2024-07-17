package com.example.onlinebookshop.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.example.onlinebookshop.dto.book.request.CreateBookDto;
import com.example.onlinebookshop.dto.book.response.BookDto;
import com.example.onlinebookshop.entities.Book;
import com.example.onlinebookshop.exceptions.ParameterAlreadyExistsException;
import com.example.onlinebookshop.mapper.BookMapper;
import com.example.onlinebookshop.repositories.book.BookRepository;
import com.example.onlinebookshop.repositories.book.bookspecs.BookSpecificationBuilder;
import com.example.onlinebookshop.repositories.category.CategoryRepository;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    private static final int PAGE_NUMBER = 0;
    private static final int PAGE_SIZE = 3;
    private static CreateBookDto createNewBookDto;
    private static CreateBookDto createExistingBookDto;

    private static Book bookFromCreateNewBookDto;

    private static Book expectedBook1;
    private static Book expectedBook2;
    private static Book expectedBook3;

    private static BookDto expectedBookDto1;
    private static BookDto expectedBookDto2;
    private static BookDto expectedBookDto3;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private BookMapper bookMapper;
    @Mock
    private BookSpecificationBuilder bookSpecificationBuilder;
    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeAll
    static void initVars() {
        createNewBookDto = new CreateBookDto();
        createNewBookDto.setTitle("Some book");
        createNewBookDto.setAuthor("Some author");
        createNewBookDto.setIsbn("9780451524935");
        createNewBookDto.setPrice(BigDecimal.valueOf(9.99));
        createNewBookDto.setDescription("Some description");
        createNewBookDto.setCoverImage("some_picture.jpg");

        createExistingBookDto = new CreateBookDto();
        createExistingBookDto.setTitle("1984");
        createExistingBookDto.setAuthor("George Orwell");
        createExistingBookDto.setIsbn("9780451524935");
        createExistingBookDto.setPrice(BigDecimal.valueOf(9.99));
        createExistingBookDto.setDescription(
                "A dystopian novel set in Airstrip One, a province of the "
                + "superstate Oceania, whose residents are victims of perpetual war, omnipresent "
                + "government surveillance, and public manipulation.");
        createExistingBookDto.setCoverImage("1984.jpg");

        bookFromCreateNewBookDto = new Book();
        bookFromCreateNewBookDto.setTitle("Some book");
        bookFromCreateNewBookDto.setAuthor("Some author");
        bookFromCreateNewBookDto.setIsbn("9780451524935");
        bookFromCreateNewBookDto.setPrice(BigDecimal.valueOf(9.99));
        bookFromCreateNewBookDto.setDescription("Some description");
        bookFromCreateNewBookDto.setCoverImage("some_picture.jpg");

        expectedBook1 = new Book();
        expectedBook1.setId(1L);
        expectedBook1.setTitle("The Great Gatsby");
        expectedBook1.setAuthor("F. Scott Fitzgerald");
        expectedBook1.setIsbn("9780743273565");
        expectedBook1.setPrice(BigDecimal.valueOf(12.99));
        expectedBook1.setDescription("A story of the fabulously wealthy Jay Gatsby and his love for"
                + " the beautiful Daisy Buchanan.");
        expectedBook1.setCoverImage("gatsby.jpg");

        expectedBook2 = new Book();
        expectedBook2.setId(2L);
        expectedBook2.setTitle("To Kill a Mockingbird");
        expectedBook2.setAuthor("Harper Lee");
        expectedBook2.setIsbn("9780061120084");
        expectedBook2.setPrice(BigDecimal.valueOf(10.49));
        expectedBook2.setDescription("A novel that explores the irrationality of adult attitudes"
                + " towards race and class in the Deep South of the 1930s.");
        expectedBook2.setCoverImage("mockingbird.jpg");

        expectedBook3 = new Book();
        expectedBook3.setId(3L);
        expectedBook3.setTitle("1984");
        expectedBook3.setAuthor("George Orwell");
        expectedBook3.setIsbn("9780451524935");
        expectedBook3.setPrice(BigDecimal.valueOf(9.99));
        expectedBook3.setDescription("A dystopian novel set in Airstrip One, a province of the "
                + "superstate Oceania, whose residents are victims of perpetual war, omnipresent "
                + "government surveillance, and public manipulation.");
        expectedBook3.setCoverImage("1984.jpg");

        expectedBookDto1 = new BookDto();
        expectedBookDto1.setId(1L);
        expectedBookDto1.setTitle("The Great Gatsby");
        expectedBookDto1.setAuthor("F. Scott Fitzgerald");
        expectedBookDto1.setIsbn("9780743273565");
        expectedBookDto1.setPrice(BigDecimal.valueOf(12.99));
        expectedBookDto1.setDescription("A story of the fabulously wealthy Jay Gatsby and his love "
                + "for the beautiful Daisy Buchanan.");
        expectedBookDto1.setCoverImage("gatsby.jpg");

        expectedBookDto2 = new BookDto();
        expectedBookDto2.setId(2L);
        expectedBookDto2.setTitle("To Kill a Mockingbird");
        expectedBookDto2.setAuthor("Harper Lee");
        expectedBookDto2.setIsbn("9780061120084");
        expectedBookDto2.setPrice(BigDecimal.valueOf(10.49));
        expectedBookDto2.setDescription("A novel that explores the irrationality of adult attitudes"
                + " towards race and class in the Deep South of the 1930s.");
        expectedBookDto2.setCoverImage("mockingbird.jpg");

        expectedBookDto3 = new BookDto();
        expectedBookDto3.setId(3L);
        expectedBookDto3.setTitle("1984");
        expectedBookDto3.setAuthor("George Orwell");
        expectedBookDto3.setIsbn("9780451524935");
        expectedBookDto3.setPrice(BigDecimal.valueOf(9.99));
        expectedBookDto3.setDescription("A dystopian novel set in Airstrip One, a province of the "
                + "superstate Oceania, whose residents are victims of perpetual war, omnipresent "
                + "government surveillance, and public manipulation.");
        expectedBookDto3.setCoverImage("1984.jpg");
    }

    @Test
    void save_IsAbleToSaveBookWhichIsNotInDb_Success() {
        when(bookMapper.toCreateModel(createNewBookDto)).thenReturn(bookFromCreateNewBookDto);
        when(bookRepository.save(bookFromCreateNewBookDto)).thenReturn(bookFromCreateNewBookDto);
        when(bookMapper.toDto(bookFromCreateNewBookDto)).thenReturn(expectedBookDto1);
        BookDto actualBookDto = bookService.save(createNewBookDto);
        assertEquals(expectedBookDto1, actualBookDto);
    }

    @Test
    void save_IsNotAbleToSaveBookWhichIsInDb_Fail() {
        when(bookRepository.existsByIsbn(createExistingBookDto.getIsbn()))
                .thenThrow(new ParameterAlreadyExistsException("Another book with ISBN "
                        + createExistingBookDto.getIsbn() + " already exists in DB"));

        Exception parameterAlreadyExistsException =
                assertThrows(ParameterAlreadyExistsException.class, () ->
                        bookService.save(createExistingBookDto));
        String expectedMessage = "Another book with ISBN "
                + createExistingBookDto.getIsbn() + " already exists in DB";

        assertEquals(expectedMessage, parameterAlreadyExistsException.getMessage());
    }

    @Test
    void findAll() {
        Pageable pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
        List<Book> books = List.of(expectedBook1, expectedBook2, expectedBook3);
        Page<Book> bookPage = new PageImpl<>(books, pageable, books.size());

        when(bookRepository.findAll(pageable)).thenReturn(bookPage);
        when(bookMapper.toDto(expectedBook1)).thenReturn(expectedBookDto1);
        when(bookMapper.toDto(expectedBook2)).thenReturn(expectedBookDto2);
        when(bookMapper.toDto(expectedBook3)).thenReturn(expectedBookDto3);

        List<BookDto> expectedBookDtos =
                List.of(expectedBookDto1, expectedBookDto2, expectedBookDto3);
        List<BookDto> actualBookDtos = bookService.findAll(pageable);

        assertEquals(expectedBookDtos, actualBookDtos);
    }

    @Test
    void findAllWithoutCategoryIds() {
    }

    @Test
    void findById() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void search() {
    }
}
