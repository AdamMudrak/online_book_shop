package com.example.onlinebookshop.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.example.onlinebookshop.dto.book.request.BookSearchParametersDto;
import com.example.onlinebookshop.dto.book.request.CreateBookDto;
import com.example.onlinebookshop.dto.book.request.UpdateBookDto;
import com.example.onlinebookshop.dto.book.response.BookDto;
import com.example.onlinebookshop.dto.book.response.BookDtoWithoutCategoryIds;
import com.example.onlinebookshop.entities.Book;
import com.example.onlinebookshop.entities.Category;
import com.example.onlinebookshop.exceptions.EntityNotFoundException;
import com.example.onlinebookshop.exceptions.ParameterAlreadyExistsException;
import com.example.onlinebookshop.mapper.BookMapper;
import com.example.onlinebookshop.repositories.book.BookRepository;
import com.example.onlinebookshop.repositories.book.bookspecs.BookSpecificationBuilder;
import com.example.onlinebookshop.repositories.book.bookspecs.bookfieldsspecs.AuthorSpecificationProvider;
import com.example.onlinebookshop.repositories.book.bookspecs.bookfieldsspecs.FromPriceSpecificationProvider;
import com.example.onlinebookshop.repositories.book.bookspecs.bookfieldsspecs.TitleSpecificationProvider;
import com.example.onlinebookshop.repositories.book.bookspecs.bookfieldsspecs.ToPriceSpecificationProvider;
import com.example.onlinebookshop.repositories.category.CategoryRepository;
import com.example.onlinebookshop.repositories.specifications.SpecificationProviderManager;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
import org.springframework.data.jpa.domain.Specification;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    private static final Category CATEGORY = new Category();
    private static final CreateBookDto CREATE_NEW_BOOK_DTO = new CreateBookDto();
    private static final CreateBookDto CREATE_EXISTING_1984_BOOK_DTO = new CreateBookDto();

    private static final UpdateBookDto UPDATE_BOOK_DTO = new UpdateBookDto();

    private static final Book BOOK_FROM_DTO = new Book();

    private static final Book EXPECTED_GATSBY_BOOK = new Book();
    private static final Book EXPECTED_GATSBY_AFTER_UPDATE = new Book();
    private static final Book EXPECTED_TKAM_BOOK = new Book();
    private static final Book EXPECTED_1984_BOOK = new Book();

    private static final BookDto EXPECTED_GATSBY_BOOK_DTO = new BookDto();
    private static final BookDto EXPECTED_TKAM_BOOK_DTO = new BookDto();
    private static final BookDto EXPECTED_1984_BOOK_DTO = new BookDto();
    private static final BookDto EXPECTED_GATSBY_AFTER_UPDATE_DTO = new BookDto();

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

    private static final long RANDOM_BOOK_ID = 1000L;
    private static final String SOME_TITLE = "Some book";
    private static final String SOME_AUTHOR = "Some author";
    private static final String SOME_ISBN = "0000000000000";
    private static final BigDecimal SOME_PRICE = BigDecimal.valueOf(9.99);
    private static final String SOME_DESCRIPTION = "Some description";
    private static final String SOME_COVER_IMAGE = "some_picture.jpg";

    private static final BookDtoWithoutCategoryIds EXPECTED_GATSBY_BOOK_DTO_WITHOUT_CATEGORY_ID =
            new BookDtoWithoutCategoryIds(GATSBY_ID,
                    GATSBY_TITLE,
                    GATSBY_AUTHOR,
                    GATSBY_ISBN,
                    GATSBY_PRICE,
                    GATSBY_DESCRIPTION,
                    GATSBY_COVER_IMAGE);

    private static final BookDtoWithoutCategoryIds EXPECTED_TKAM_BOOK_DTO_WITHOUT_CATEGORY_ID =
            new BookDtoWithoutCategoryIds(TKAM_ID,
                    TKAM_TITLE,
                    TKAM_AUTHOR,
                    TKAM_ISBN,
                    TKAM_PRICE,
                    TKAM_DESCRIPTION,
                    TKAM_COVER_IMAGE);

    private static final BookDtoWithoutCategoryIds EXPECTED_1984_BOOK_DTO_WITHOUT_CATEGORY_ID =
            new BookDtoWithoutCategoryIds(ID_1984,
                    TITLE_1984,
                    AUTHOR_1984,
                    ISBN_1984,
                    PRICE_1984,
                    DESCRIPTION_1984,
                    COVER_IMAGE_1984);
    private static final int FLOOR_PRICE = 10;
    private static final int CEILING_PRICE = 12;
    private static final BookSearchParametersDto REAL_BOOK_SEARCH_PARAMETERS_DTO =
            new BookSearchParametersDto(
                    BigDecimal.valueOf(FLOOR_PRICE),
                    BigDecimal.valueOf(CEILING_PRICE),
                    new String[]{TITLE_1984, TKAM_TITLE},
                    new String[]{AUTHOR_1984, TKAM_AUTHOR});

    private static final BookSearchParametersDto RANDOM_BOOK_SEARCH_PARAMETERS_DTO =
            new BookSearchParametersDto(
                    BigDecimal.valueOf(FLOOR_PRICE),
                    BigDecimal.valueOf(CEILING_PRICE),
                    new String[]{SOME_TITLE},
                    new String[]{SOME_AUTHOR});
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
    @Mock
    private SpecificationProviderManager<Book> bookSpecificationProviderManager;
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

        CREATE_EXISTING_1984_BOOK_DTO.setTitle(TITLE_1984);
        CREATE_EXISTING_1984_BOOK_DTO.setAuthor(AUTHOR_1984);
        CREATE_EXISTING_1984_BOOK_DTO.setIsbn(ISBN_1984);
        CREATE_EXISTING_1984_BOOK_DTO.setPrice(PRICE_1984);
        CREATE_EXISTING_1984_BOOK_DTO.setDescription(DESCRIPTION_1984);
        CREATE_EXISTING_1984_BOOK_DTO.setCoverImage(COVER_IMAGE_1984);

        UPDATE_BOOK_DTO.setTitle(SOME_TITLE);
        UPDATE_BOOK_DTO.setAuthor(SOME_AUTHOR);
        UPDATE_BOOK_DTO.setCategoryIds(Set.of(CATEGORY_ID));
        UPDATE_BOOK_DTO.setIsbn(SOME_ISBN);
        UPDATE_BOOK_DTO.setPrice(SOME_PRICE);
        UPDATE_BOOK_DTO.setDescription(SOME_DESCRIPTION);
        UPDATE_BOOK_DTO.setCoverImage(SOME_COVER_IMAGE);

        BOOK_FROM_DTO.setTitle(SOME_TITLE);
        BOOK_FROM_DTO.setAuthor(SOME_AUTHOR);
        BOOK_FROM_DTO.setIsbn(SOME_ISBN);
        BOOK_FROM_DTO.setPrice(SOME_PRICE);
        BOOK_FROM_DTO.setDescription(SOME_DESCRIPTION);
        BOOK_FROM_DTO.setCoverImage(SOME_COVER_IMAGE);

        EXPECTED_GATSBY_BOOK.setId(GATSBY_ID);
        EXPECTED_GATSBY_BOOK.setTitle(GATSBY_TITLE);
        EXPECTED_GATSBY_BOOK.setAuthor(GATSBY_AUTHOR);
        EXPECTED_GATSBY_BOOK.setIsbn(GATSBY_ISBN);
        EXPECTED_GATSBY_BOOK.setPrice(GATSBY_PRICE);
        EXPECTED_GATSBY_BOOK.setDescription(GATSBY_DESCRIPTION);
        EXPECTED_GATSBY_BOOK.setCoverImage(GATSBY_COVER_IMAGE);

        EXPECTED_TKAM_BOOK.setId(TKAM_ID);
        EXPECTED_TKAM_BOOK.setTitle(TKAM_TITLE);
        EXPECTED_TKAM_BOOK.setAuthor(TKAM_AUTHOR);
        EXPECTED_TKAM_BOOK.setIsbn(TKAM_ISBN);
        EXPECTED_TKAM_BOOK.setPrice(TKAM_PRICE);
        EXPECTED_TKAM_BOOK.setDescription(TKAM_DESCRIPTION);
        EXPECTED_TKAM_BOOK.setCoverImage(TKAM_COVER_IMAGE);

        EXPECTED_1984_BOOK.setId(ID_1984);
        EXPECTED_1984_BOOK.setTitle(TITLE_1984);
        EXPECTED_1984_BOOK.setAuthor(AUTHOR_1984);
        EXPECTED_1984_BOOK.setIsbn(ISBN_1984);
        EXPECTED_1984_BOOK.setPrice(PRICE_1984);
        EXPECTED_1984_BOOK.setDescription(DESCRIPTION_1984);
        EXPECTED_1984_BOOK.setCoverImage(COVER_IMAGE_1984);

        EXPECTED_GATSBY_BOOK_DTO.setId(GATSBY_ID);
        EXPECTED_GATSBY_BOOK_DTO.setTitle(GATSBY_TITLE);
        EXPECTED_GATSBY_BOOK_DTO.setAuthor(GATSBY_AUTHOR);
        EXPECTED_GATSBY_BOOK_DTO.setIsbn(GATSBY_ISBN);
        EXPECTED_GATSBY_BOOK_DTO.setPrice(GATSBY_PRICE);
        EXPECTED_GATSBY_BOOK_DTO.setDescription(GATSBY_DESCRIPTION);
        EXPECTED_GATSBY_BOOK_DTO.setCoverImage(GATSBY_COVER_IMAGE);

        EXPECTED_TKAM_BOOK_DTO.setId(TKAM_ID);
        EXPECTED_TKAM_BOOK_DTO.setTitle(TKAM_TITLE);
        EXPECTED_TKAM_BOOK_DTO.setAuthor(TKAM_AUTHOR);
        EXPECTED_TKAM_BOOK_DTO.setIsbn(TKAM_ISBN);
        EXPECTED_TKAM_BOOK_DTO.setPrice(TKAM_PRICE);
        EXPECTED_TKAM_BOOK_DTO.setDescription(TKAM_DESCRIPTION);
        EXPECTED_TKAM_BOOK_DTO.setCoverImage(TKAM_COVER_IMAGE);

        EXPECTED_1984_BOOK_DTO.setId(ID_1984);
        EXPECTED_1984_BOOK_DTO.setTitle(TITLE_1984);
        EXPECTED_1984_BOOK_DTO.setAuthor(AUTHOR_1984);
        EXPECTED_1984_BOOK_DTO.setIsbn(ISBN_1984);
        EXPECTED_1984_BOOK_DTO.setPrice(PRICE_1984);
        EXPECTED_1984_BOOK_DTO.setDescription(DESCRIPTION_1984);
        EXPECTED_1984_BOOK_DTO.setCoverImage(COVER_IMAGE_1984);

        EXPECTED_GATSBY_AFTER_UPDATE.setId(GATSBY_ID);
        EXPECTED_GATSBY_AFTER_UPDATE.setTitle(SOME_TITLE);
        EXPECTED_GATSBY_AFTER_UPDATE.setAuthor(SOME_AUTHOR);
        EXPECTED_GATSBY_AFTER_UPDATE.setIsbn(SOME_ISBN);
        EXPECTED_GATSBY_AFTER_UPDATE.setPrice(SOME_PRICE);
        EXPECTED_GATSBY_AFTER_UPDATE.setDescription(SOME_DESCRIPTION);
        EXPECTED_GATSBY_AFTER_UPDATE.setCoverImage(SOME_COVER_IMAGE);

        EXPECTED_GATSBY_AFTER_UPDATE_DTO.setId(GATSBY_ID);
        EXPECTED_GATSBY_AFTER_UPDATE_DTO.setTitle(SOME_TITLE);
        EXPECTED_GATSBY_AFTER_UPDATE_DTO.setAuthor(SOME_AUTHOR);
        EXPECTED_GATSBY_AFTER_UPDATE_DTO.setIsbn(SOME_ISBN);
        EXPECTED_GATSBY_AFTER_UPDATE_DTO.setPrice(SOME_PRICE);
        EXPECTED_GATSBY_AFTER_UPDATE_DTO.setDescription(SOME_DESCRIPTION);
        EXPECTED_GATSBY_AFTER_UPDATE_DTO.setCoverImage(SOME_COVER_IMAGE);
    }

    @Test
    void save_IsAbleToSaveBookWhichIsNotInDb_Success() {
        when(bookMapper.toCreateModel(CREATE_NEW_BOOK_DTO))
                .thenReturn(BOOK_FROM_DTO);
        when(bookRepository.save(BOOK_FROM_DTO))
                .thenReturn(BOOK_FROM_DTO);
        when(bookMapper.toDto(BOOK_FROM_DTO)).thenReturn(EXPECTED_GATSBY_BOOK_DTO);
        BookDto actualBookDto = bookService.save(CREATE_NEW_BOOK_DTO);
        assertEquals(EXPECTED_GATSBY_BOOK_DTO, actualBookDto);
    }

    @Test
    void save_IsNotAbleToSaveBookWhichIsInDb_Fail() {
        when(bookRepository.existsByIsbn(CREATE_EXISTING_1984_BOOK_DTO.getIsbn()))
                .thenThrow(new ParameterAlreadyExistsException("Another book with ISBN "
                        + CREATE_EXISTING_1984_BOOK_DTO.getIsbn() + " already exists in DB"));

        Exception parameterAlreadyExistsException =
                assertThrows(ParameterAlreadyExistsException.class, () ->
                        bookService.save(CREATE_EXISTING_1984_BOOK_DTO));
        String expectedMessage = "Another book with ISBN "
                + CREATE_EXISTING_1984_BOOK_DTO.getIsbn() + " already exists in DB";

        assertEquals(expectedMessage, parameterAlreadyExistsException.getMessage());
    }

    @Test
    void findAll_IsAbleToFindThreeBooksFromDb_Success() {
        Pageable pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
        List<Book> books = List.of(EXPECTED_GATSBY_BOOK, EXPECTED_TKAM_BOOK, EXPECTED_1984_BOOK);
        Page<Book> bookPage = new PageImpl<>(books, pageable, books.size());

        when(bookRepository.findAll(pageable)).thenReturn(bookPage);
        when(bookMapper.toDto(EXPECTED_GATSBY_BOOK)).thenReturn(EXPECTED_GATSBY_BOOK_DTO);
        when(bookMapper.toDto(EXPECTED_TKAM_BOOK)).thenReturn(EXPECTED_TKAM_BOOK_DTO);
        when(bookMapper.toDto(EXPECTED_1984_BOOK)).thenReturn(EXPECTED_1984_BOOK_DTO);

        List<BookDto> expectedBookDtos =
                List.of(EXPECTED_GATSBY_BOOK_DTO, EXPECTED_TKAM_BOOK_DTO, EXPECTED_1984_BOOK_DTO);
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
        List<Book> books = List.of(EXPECTED_GATSBY_BOOK, EXPECTED_TKAM_BOOK, EXPECTED_1984_BOOK);

        when(categoryRepository.findById(CATEGORY_ID)).thenReturn(Optional.of(CATEGORY));
        when(bookRepository.findAllByCategoryId(CATEGORY_ID)).thenReturn(books);
        when(bookMapper.toDtoWithoutCategories(EXPECTED_GATSBY_BOOK))
                .thenReturn(EXPECTED_GATSBY_BOOK_DTO_WITHOUT_CATEGORY_ID);
        when(bookMapper.toDtoWithoutCategories(EXPECTED_TKAM_BOOK))
                .thenReturn(EXPECTED_TKAM_BOOK_DTO_WITHOUT_CATEGORY_ID);
        when(bookMapper.toDtoWithoutCategories(EXPECTED_1984_BOOK))
                .thenReturn(EXPECTED_1984_BOOK_DTO_WITHOUT_CATEGORY_ID);

        List<BookDtoWithoutCategoryIds> expectedBookDtos =
                List.of(EXPECTED_GATSBY_BOOK_DTO_WITHOUT_CATEGORY_ID,
                        EXPECTED_TKAM_BOOK_DTO_WITHOUT_CATEGORY_ID,
                        EXPECTED_1984_BOOK_DTO_WITHOUT_CATEGORY_ID);
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
    void update_CannotUpdateBookWhenIsNotPresentById_Fail() {
        String exceptionMessage = "Can't find and update book by id " + RANDOM_BOOK_ID;
        when(bookRepository.findById(RANDOM_BOOK_ID))
                .thenThrow(new EntityNotFoundException(
                        "Can't find and update book by id " + RANDOM_BOOK_ID));
        Exception exception = assertThrows(EntityNotFoundException.class, () ->
                bookService.update(UPDATE_BOOK_DTO, RANDOM_BOOK_ID, true));
        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    void update_CannotUpdateBookWhenAnotherIsPresentByIsbn_Fail() {
        String exceptionMessage = "Book with ISBN " + UPDATE_BOOK_DTO.getIsbn()
                + " already exists in DB";
        when(bookRepository.findById(RANDOM_BOOK_ID)).thenReturn(Optional.of(EXPECTED_GATSBY_BOOK));
        when(bookRepository.findBookByIsbn(UPDATE_BOOK_DTO.getIsbn()))
                .thenThrow(new ParameterAlreadyExistsException(
                        "Book with ISBN " + UPDATE_BOOK_DTO.getIsbn()
                                + " already exists in DB"));
        Exception exception = assertThrows(ParameterAlreadyExistsException.class, () ->
                bookService.update(UPDATE_BOOK_DTO, RANDOM_BOOK_ID, true));
        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    void delete() {
    }

    @Test
    void search_IsAbleToSearchBooksWithExistingSpecifications_Success() {
        prepareWhensForSearch();

        Specification<Book> specification = getRealSpecification();

        when(bookSpecificationBuilder.build(REAL_BOOK_SEARCH_PARAMETERS_DTO))
                .thenReturn(specification);
        List<Book> expectedBooks = List.of(EXPECTED_TKAM_BOOK, EXPECTED_1984_BOOK);

        when(bookRepository.findAll(specification)).thenReturn(expectedBooks);
        when(bookMapper.toDto(EXPECTED_TKAM_BOOK)).thenReturn(EXPECTED_TKAM_BOOK_DTO);
        when(bookMapper.toDto(EXPECTED_1984_BOOK)).thenReturn(EXPECTED_1984_BOOK_DTO);

        List<BookDto> expectedBookDtos =
                List.of(EXPECTED_TKAM_BOOK_DTO, EXPECTED_1984_BOOK_DTO);
        List<BookDto> actualBookDtos = bookService.search(REAL_BOOK_SEARCH_PARAMETERS_DTO);

        assertEquals(expectedBookDtos, actualBookDtos);
    }

    @Test
    void search_MakeSureIfOtherBooksListGivenThen_Fail() {
        prepareWhensForSearch();

        Specification<Book> specification = getRealSpecification();

        when(bookSpecificationBuilder.build(REAL_BOOK_SEARCH_PARAMETERS_DTO))
                .thenReturn(specification);
        List<Book> expectedBooks = List.of(EXPECTED_TKAM_BOOK, EXPECTED_1984_BOOK);

        when(bookRepository.findAll(specification)).thenReturn(expectedBooks);
        when(bookMapper.toDto(EXPECTED_TKAM_BOOK)).thenReturn(EXPECTED_TKAM_BOOK_DTO);
        when(bookMapper.toDto(EXPECTED_1984_BOOK)).thenReturn(EXPECTED_1984_BOOK_DTO);

        List<BookDto> unexpectedBookDtos =
                List.of(EXPECTED_GATSBY_BOOK_DTO, EXPECTED_TKAM_BOOK_DTO);
        List<BookDto> actualBookDtos = bookService.search(REAL_BOOK_SEARCH_PARAMETERS_DTO);

        assertEquals(unexpectedBookDtos.size(), actualBookDtos.size());
        assertNotEquals(unexpectedBookDtos, actualBookDtos);
    }

    @Test
    void search_IsNotAbleToSearchBooksWithNonExistingSpecifications_Fail() {
        prepareWhensForSearch();

        Specification<Book> specification = getRandomSpecification();

        when(bookSpecificationBuilder.build(RANDOM_BOOK_SEARCH_PARAMETERS_DTO))
                .thenReturn(specification);
        List<Book> expectedBooks = List.of();

        when(bookRepository.findAll(specification)).thenReturn(expectedBooks);

        List<BookDto> actualBookDtos = bookService.search(RANDOM_BOOK_SEARCH_PARAMETERS_DTO);

        assertTrue(actualBookDtos.isEmpty());
    }

    private Specification<Book> getRealSpecification() {
        return Specification.allOf(bookSpecificationProviderManager
                        .getSpecificationProvider("title")
                        .getSpecification(REAL_BOOK_SEARCH_PARAMETERS_DTO.titles())
                        .and(bookSpecificationProviderManager
                                .getSpecificationProvider("author")
                                .getSpecification(REAL_BOOK_SEARCH_PARAMETERS_DTO.authors())))
                .and(bookSpecificationProviderManager
                        .getSpecificationProvider("fromPrice")
                        .getSpecification(
                                new String[]{String
                                        .valueOf(REAL_BOOK_SEARCH_PARAMETERS_DTO.fromPrice())}))
                .and(bookSpecificationProviderManager
                        .getSpecificationProvider("toPrice")
                        .getSpecification(
                                new String[]{String.valueOf(REAL_BOOK_SEARCH_PARAMETERS_DTO
                                        .toPrice())}));
    }

    private Specification<Book> getRandomSpecification() {
        return Specification.allOf(bookSpecificationProviderManager
                        .getSpecificationProvider("title")
                        .getSpecification(RANDOM_BOOK_SEARCH_PARAMETERS_DTO.titles())
                        .and(bookSpecificationProviderManager
                                .getSpecificationProvider("author")
                                .getSpecification(RANDOM_BOOK_SEARCH_PARAMETERS_DTO.authors())))
                .and(bookSpecificationProviderManager
                        .getSpecificationProvider("fromPrice")
                        .getSpecification(
                                new String[]{String
                                        .valueOf(RANDOM_BOOK_SEARCH_PARAMETERS_DTO.fromPrice())}))
                .and(bookSpecificationProviderManager
                        .getSpecificationProvider("toPrice")
                        .getSpecification(
                                new String[]{String.valueOf(RANDOM_BOOK_SEARCH_PARAMETERS_DTO
                                        .toPrice())}));
    }

    private void prepareWhensForSearch() {
        when(bookSpecificationProviderManager.getSpecificationProvider("title"))
                .thenReturn(new TitleSpecificationProvider());
        when(bookSpecificationProviderManager.getSpecificationProvider("author"))
                .thenReturn(new AuthorSpecificationProvider());
        when(bookSpecificationProviderManager.getSpecificationProvider("fromPrice"))
                .thenReturn(new FromPriceSpecificationProvider());
        when(bookSpecificationProviderManager.getSpecificationProvider("toPrice"))
                .thenReturn(new ToPriceSpecificationProvider());
    }
}
