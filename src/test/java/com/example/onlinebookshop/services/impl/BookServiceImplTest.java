package com.example.onlinebookshop.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.example.onlinebookshop.dto.book.request.CreateBookDto;
import com.example.onlinebookshop.dto.book.response.BookDto;
import com.example.onlinebookshop.dto.book.response.BookDtoWithoutCategoryIds;
import com.example.onlinebookshop.entities.Book;
import com.example.onlinebookshop.entities.Category;
import com.example.onlinebookshop.exceptions.EntityNotFoundException;
import com.example.onlinebookshop.exceptions.ParameterAlreadyExistsException;
import com.example.onlinebookshop.mapper.BookMapper;
import com.example.onlinebookshop.repositories.book.BookRepository;
import com.example.onlinebookshop.repositories.book.bookspecs.BookSpecificationBuilder;
import com.example.onlinebookshop.repositories.category.CategoryRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
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
    private static final Category CATEGORY = new Category();
    private static final CreateBookDto CREATE_NEW_BOOK_DTO = new CreateBookDto();
    private static final CreateBookDto CREATE_EXISTING_BOOK_DTO = new CreateBookDto();

    private static final Book BOOK_FROM_CREATE_NEW_BOOK_DTO = new Book();

    private static final Book EXPECTED_BOOK_1 = new Book();
    private static final Book EXPECTED_BOOK_2 = new Book();
    private static final Book EXPECTED_BOOK_3 = new Book();

    private static final BookDto EXPECTED_BOOK_DTO_1 = new BookDto();
    private static final BookDto EXPECTED_BOOK_DTO_2 = new BookDto();
    private static final BookDto EXPECTED_BOOK_DTO_3 = new BookDto();

    private static final long CATEGORY_ID = 1L;
    private static final long RANDOM_CATEGORY_ID = 1000L;
    private static final String CATEGORY_NAME = "Fiction";
    private static final String CATEGORY_DESCRIPTION =
            "Interesting books about imaginary though possible events";

    private static final long GATSBY_ID = 1L;
    private static final String GATSBY_TITLE = "The Great Gatsby";
    private static final String GATSBY_AUTHOR = "F. Scott Fitzgerald";
    private static final String GATSBY_ISBN = "9780743273565";
    private static final BigDecimal GATSBY_PRICE = BigDecimal.valueOf(12.99);
    private static final String GATSBY_DESCRIPTION = "A story of the fabulously wealthy Jay Gatsby "
            + "and his love for the beautiful Daisy Buchanan.";
    private static final String GATSBY_COVER_IMAGE = "gatsby.jpg";

    private static final long TKAM_ID = 2L;
    private static final String TKAM_TITLE = "To Kill a Mockingbird";
    private static final String TKAM_AUTHOR = "Harper Lee";
    private static final String TKAM_ISBN = "9780061120084";
    private static final BigDecimal TKAM_PRICE = BigDecimal.valueOf(10.49);
    private static final String TKAM_DESCRIPTION = "A novel that explores the irrationality of "
            + "adult attitudes towards race and class in the Deep South of the 1930s.";
    private static final String TKAM_COVER_IMAGE = "mockingbird.jpg";

    private static final long ID_1984 = 3L;
    private static final String TITLE_1984 = "1984";
    private static final String AUTHOR_1984 = "George Orwell";
    private static final String ISBN_1984 = "9780451524935";
    private static final BigDecimal PRICE_1984 = BigDecimal.valueOf(9.99);
    private static final String DESCRIPTION_1984 = "A dystopian novel set in Airstrip One, "
            + "a province of the superstate Oceania, whose residents are victims of perpetual war, "
            + "omnipresent government surveillance, and public manipulation.";
    private static final String COVER_IMAGE_1984 = "1984.jpg";

    private static final String SOME_TITLE = "Some book";
    private static final String SOME_AUTHOR = "Some author";
    private static final String SOME_ISBN = "0000000000000";
    private static final BigDecimal SOME_PRICE = BigDecimal.valueOf(9.99);
    private static final String SOME_DESCRIPTION = "Some description";
    private static final String SOME_COVER_IMAGE = "some_picture.jpg";

    private static final BookDtoWithoutCategoryIds EXPECTED_BOOK_DTO_WITHOUT_CATEGORY_ID_1 =
            new BookDtoWithoutCategoryIds(GATSBY_ID,
                    GATSBY_TITLE,
                    GATSBY_AUTHOR,
                    GATSBY_ISBN,
                    GATSBY_PRICE,
                    GATSBY_DESCRIPTION,
                    GATSBY_COVER_IMAGE);

    private static final BookDtoWithoutCategoryIds EXPECTED_BOOK_DTO_WITHOUT_CATEGORY_ID_2 =
            new BookDtoWithoutCategoryIds(TKAM_ID,
                    TKAM_TITLE,
                    TKAM_AUTHOR,
                    TKAM_ISBN,
                    TKAM_PRICE,
                    TKAM_DESCRIPTION,
                    TKAM_COVER_IMAGE);

    private static final BookDtoWithoutCategoryIds EXPECTED_BOOK_DTO_WITHOUT_CATEGORY_ID_3 =
            new BookDtoWithoutCategoryIds(ID_1984,
                    TITLE_1984,
                    AUTHOR_1984,
                    ISBN_1984,
                    PRICE_1984,
                    DESCRIPTION_1984,
                    COVER_IMAGE_1984);
    private static final int PAGE_NUMBER = 0;
    private static final int PAGE_SIZE = 3;

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
        CATEGORY.setId(CATEGORY_ID);
        CATEGORY.setName(CATEGORY_NAME);
        CATEGORY.setDescription(CATEGORY_DESCRIPTION);

        CREATE_NEW_BOOK_DTO.setTitle(SOME_TITLE);
        CREATE_NEW_BOOK_DTO.setAuthor(SOME_AUTHOR);
        CREATE_NEW_BOOK_DTO.setIsbn(SOME_ISBN);
        CREATE_NEW_BOOK_DTO.setPrice(SOME_PRICE);
        CREATE_NEW_BOOK_DTO.setDescription(SOME_DESCRIPTION);
        CREATE_NEW_BOOK_DTO.setCoverImage(SOME_COVER_IMAGE);

        CREATE_EXISTING_BOOK_DTO.setTitle(TITLE_1984);
        CREATE_EXISTING_BOOK_DTO.setAuthor(AUTHOR_1984);
        CREATE_EXISTING_BOOK_DTO.setIsbn(ISBN_1984);
        CREATE_EXISTING_BOOK_DTO.setPrice(PRICE_1984);
        CREATE_EXISTING_BOOK_DTO.setDescription(DESCRIPTION_1984);
        CREATE_EXISTING_BOOK_DTO.setCoverImage(COVER_IMAGE_1984);

        BOOK_FROM_CREATE_NEW_BOOK_DTO.setTitle(SOME_TITLE);
        BOOK_FROM_CREATE_NEW_BOOK_DTO.setAuthor(SOME_AUTHOR);
        BOOK_FROM_CREATE_NEW_BOOK_DTO.setIsbn(SOME_ISBN);
        BOOK_FROM_CREATE_NEW_BOOK_DTO.setPrice(SOME_PRICE);
        BOOK_FROM_CREATE_NEW_BOOK_DTO.setDescription(SOME_DESCRIPTION);
        BOOK_FROM_CREATE_NEW_BOOK_DTO.setCoverImage(SOME_COVER_IMAGE);

        EXPECTED_BOOK_1.setId(GATSBY_ID);
        EXPECTED_BOOK_1.setTitle(GATSBY_TITLE);
        EXPECTED_BOOK_1.setAuthor(GATSBY_AUTHOR);
        EXPECTED_BOOK_1.setIsbn(GATSBY_ISBN);
        EXPECTED_BOOK_1.setPrice(GATSBY_PRICE);
        EXPECTED_BOOK_1.setDescription(GATSBY_DESCRIPTION);
        EXPECTED_BOOK_1.setCoverImage(GATSBY_COVER_IMAGE);

        EXPECTED_BOOK_2.setId(TKAM_ID);
        EXPECTED_BOOK_2.setTitle(TKAM_TITLE);
        EXPECTED_BOOK_2.setAuthor(TKAM_AUTHOR);
        EXPECTED_BOOK_2.setIsbn(TKAM_ISBN);
        EXPECTED_BOOK_2.setPrice(TKAM_PRICE);
        EXPECTED_BOOK_2.setDescription(TKAM_DESCRIPTION);
        EXPECTED_BOOK_2.setCoverImage(TKAM_COVER_IMAGE);

        EXPECTED_BOOK_3.setId(ID_1984);
        EXPECTED_BOOK_3.setTitle(TITLE_1984);
        EXPECTED_BOOK_3.setAuthor(AUTHOR_1984);
        EXPECTED_BOOK_3.setIsbn(ISBN_1984);
        EXPECTED_BOOK_3.setPrice(PRICE_1984);
        EXPECTED_BOOK_3.setDescription(DESCRIPTION_1984);
        EXPECTED_BOOK_3.setCoverImage(COVER_IMAGE_1984);

        EXPECTED_BOOK_DTO_1.setId(GATSBY_ID);
        EXPECTED_BOOK_DTO_1.setTitle(GATSBY_TITLE);
        EXPECTED_BOOK_DTO_1.setAuthor(GATSBY_AUTHOR);
        EXPECTED_BOOK_DTO_1.setIsbn(GATSBY_ISBN);
        EXPECTED_BOOK_DTO_1.setPrice(GATSBY_PRICE);
        EXPECTED_BOOK_DTO_1.setDescription(GATSBY_DESCRIPTION);
        EXPECTED_BOOK_DTO_1.setCoverImage(GATSBY_COVER_IMAGE);

        EXPECTED_BOOK_DTO_2.setId(TKAM_ID);
        EXPECTED_BOOK_DTO_2.setTitle(TKAM_TITLE);
        EXPECTED_BOOK_DTO_2.setAuthor(TKAM_AUTHOR);
        EXPECTED_BOOK_DTO_2.setIsbn(TKAM_ISBN);
        EXPECTED_BOOK_DTO_2.setPrice(TKAM_PRICE);
        EXPECTED_BOOK_DTO_2.setDescription(TKAM_DESCRIPTION);
        EXPECTED_BOOK_DTO_2.setCoverImage(TKAM_COVER_IMAGE);

        EXPECTED_BOOK_DTO_3.setId(ID_1984);
        EXPECTED_BOOK_DTO_3.setTitle(TITLE_1984);
        EXPECTED_BOOK_DTO_3.setAuthor(AUTHOR_1984);
        EXPECTED_BOOK_DTO_3.setIsbn(ISBN_1984);
        EXPECTED_BOOK_DTO_3.setPrice(PRICE_1984);
        EXPECTED_BOOK_DTO_3.setDescription(DESCRIPTION_1984);
        EXPECTED_BOOK_DTO_3.setCoverImage(COVER_IMAGE_1984);
    }

    @Test
    void save_IsAbleToSaveBookWhichIsNotInDb_Success() {
        when(bookMapper.toCreateModel(CREATE_NEW_BOOK_DTO))
                .thenReturn(BOOK_FROM_CREATE_NEW_BOOK_DTO);
        when(bookRepository.save(BOOK_FROM_CREATE_NEW_BOOK_DTO))
                .thenReturn(BOOK_FROM_CREATE_NEW_BOOK_DTO);
        when(bookMapper.toDto(BOOK_FROM_CREATE_NEW_BOOK_DTO)).thenReturn(EXPECTED_BOOK_DTO_1);
        BookDto actualBookDto = bookService.save(CREATE_NEW_BOOK_DTO);
        assertEquals(EXPECTED_BOOK_DTO_1, actualBookDto);
    }

    @Test
    void save_IsNotAbleToSaveBookWhichIsInDb_Fail() {
        when(bookRepository.existsByIsbn(CREATE_EXISTING_BOOK_DTO.getIsbn()))
                .thenThrow(new ParameterAlreadyExistsException("Another book with ISBN "
                        + CREATE_EXISTING_BOOK_DTO.getIsbn() + " already exists in DB"));

        Exception parameterAlreadyExistsException =
                assertThrows(ParameterAlreadyExistsException.class, () ->
                        bookService.save(CREATE_EXISTING_BOOK_DTO));
        String expectedMessage = "Another book with ISBN "
                + CREATE_EXISTING_BOOK_DTO.getIsbn() + " already exists in DB";

        assertEquals(expectedMessage, parameterAlreadyExistsException.getMessage());
    }

    @Test
    void findAll_IsAbleToFindThreeBooksFromDb_Success() {
        Pageable pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
        List<Book> books = List.of(EXPECTED_BOOK_1, EXPECTED_BOOK_2, EXPECTED_BOOK_3);
        Page<Book> bookPage = new PageImpl<>(books, pageable, books.size());

        when(bookRepository.findAll(pageable)).thenReturn(bookPage);
        when(bookMapper.toDto(EXPECTED_BOOK_1)).thenReturn(EXPECTED_BOOK_DTO_1);
        when(bookMapper.toDto(EXPECTED_BOOK_2)).thenReturn(EXPECTED_BOOK_DTO_2);
        when(bookMapper.toDto(EXPECTED_BOOK_3)).thenReturn(EXPECTED_BOOK_DTO_3);

        List<BookDto> expectedBookDtos =
                List.of(EXPECTED_BOOK_DTO_1, EXPECTED_BOOK_DTO_2, EXPECTED_BOOK_DTO_3);
        List<BookDto> actualBookDtos = bookService.findAll(pageable);

        assertEquals(expectedBookDtos, actualBookDtos);
    }

    @Test
    void findAll_IsNotAbleToFindRandomBook_Fail() {
        Pageable pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
        List<Book> books = List.of();
        Page<Book> bookPage = new PageImpl<>(books, pageable, 0);

        when(bookRepository.findAll(pageable)).thenReturn(bookPage);

        List<BookDto> expectedBookDtos = List.of();
        List<BookDto> actualBookDtos = bookService.findAll(pageable);

        assertEquals(expectedBookDtos, actualBookDtos);
    }

    @Test
    void findAllWithoutCategoryIds_IsAbleToFindThreeBooksFromDb_Success() {
        List<Book> books = List.of(EXPECTED_BOOK_1, EXPECTED_BOOK_2, EXPECTED_BOOK_3);

        when(categoryRepository.findById(CATEGORY_ID)).thenReturn(Optional.of(CATEGORY));
        when(bookRepository.findAllByCategoryId(CATEGORY_ID)).thenReturn(books);
        when(bookMapper.toDtoWithoutCategories(EXPECTED_BOOK_1))
                .thenReturn(EXPECTED_BOOK_DTO_WITHOUT_CATEGORY_ID_1);
        when(bookMapper.toDtoWithoutCategories(EXPECTED_BOOK_2))
                .thenReturn(EXPECTED_BOOK_DTO_WITHOUT_CATEGORY_ID_2);
        when(bookMapper.toDtoWithoutCategories(EXPECTED_BOOK_3))
                .thenReturn(EXPECTED_BOOK_DTO_WITHOUT_CATEGORY_ID_3);

        List<BookDtoWithoutCategoryIds> expectedBookDtos =
                List.of(EXPECTED_BOOK_DTO_WITHOUT_CATEGORY_ID_1,
                        EXPECTED_BOOK_DTO_WITHOUT_CATEGORY_ID_2,
                        EXPECTED_BOOK_DTO_WITHOUT_CATEGORY_ID_3);
        List<BookDtoWithoutCategoryIds> actualBookDtos =
                bookService.findAllWithoutCategoryIds(CATEGORY_ID);

        assertEquals(expectedBookDtos, actualBookDtos);
    }

    @Test
    void findAllWithoutCategoryIds_IsNotAbleToFindByRandomCategoryId_Fail() {
        when(categoryRepository.findById(RANDOM_CATEGORY_ID)).thenThrow(
                new EntityNotFoundException("Can't find category by id " + RANDOM_CATEGORY_ID));

        Exception entityNotFoundException = assertThrows(EntityNotFoundException.class, () ->
                bookService.findAllWithoutCategoryIds(RANDOM_CATEGORY_ID));
        String expectedMessage = "Can't find category by id " + RANDOM_CATEGORY_ID;

        assertEquals(expectedMessage, entityNotFoundException.getMessage());
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
