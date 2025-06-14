package com.example.onlinebookshop.services.impl;

import static com.example.onlinebookshop.BookCategoryConstants.AUTHOR_1984;
import static com.example.onlinebookshop.BookCategoryConstants.CEILING_PRICE;
import static com.example.onlinebookshop.BookCategoryConstants.COVER_IMAGE_1984;
import static com.example.onlinebookshop.BookCategoryConstants.DESCRIPTION_1984;
import static com.example.onlinebookshop.BookCategoryConstants.FICTION_CATEGORY_ID;
import static com.example.onlinebookshop.BookCategoryConstants.FIRST_PAGE_NUMBER;
import static com.example.onlinebookshop.BookCategoryConstants.FLOOR_PRICE;
import static com.example.onlinebookshop.BookCategoryConstants.GATSBY_AUTHOR;
import static com.example.onlinebookshop.BookCategoryConstants.GATSBY_COVER_IMAGE;
import static com.example.onlinebookshop.BookCategoryConstants.GATSBY_DESCRIPTION;
import static com.example.onlinebookshop.BookCategoryConstants.GATSBY_ID;
import static com.example.onlinebookshop.BookCategoryConstants.GATSBY_ISBN;
import static com.example.onlinebookshop.BookCategoryConstants.GATSBY_PRICE;
import static com.example.onlinebookshop.BookCategoryConstants.GATSBY_TITLE;
import static com.example.onlinebookshop.BookCategoryConstants.ID_1984;
import static com.example.onlinebookshop.BookCategoryConstants.ISBN_1984;
import static com.example.onlinebookshop.BookCategoryConstants.PRICE_1984;
import static com.example.onlinebookshop.BookCategoryConstants.RANDOM_ID;
import static com.example.onlinebookshop.BookCategoryConstants.RANDOM_PAGE_NUMBER;
import static com.example.onlinebookshop.BookCategoryConstants.SOME_AUTHOR;
import static com.example.onlinebookshop.BookCategoryConstants.SOME_COVER_IMAGE;
import static com.example.onlinebookshop.BookCategoryConstants.SOME_DESCRIPTION;
import static com.example.onlinebookshop.BookCategoryConstants.SOME_ISBN;
import static com.example.onlinebookshop.BookCategoryConstants.SOME_PRICE;
import static com.example.onlinebookshop.BookCategoryConstants.SOME_TITLE;
import static com.example.onlinebookshop.BookCategoryConstants.TITLE_1984;
import static com.example.onlinebookshop.BookCategoryConstants.TKAM_AUTHOR;
import static com.example.onlinebookshop.BookCategoryConstants.TKAM_COVER_IMAGE;
import static com.example.onlinebookshop.BookCategoryConstants.TKAM_DESCRIPTION;
import static com.example.onlinebookshop.BookCategoryConstants.TKAM_ID;
import static com.example.onlinebookshop.BookCategoryConstants.TKAM_ISBN;
import static com.example.onlinebookshop.BookCategoryConstants.TKAM_PRICE;
import static com.example.onlinebookshop.BookCategoryConstants.TKAM_TITLE;
import static com.example.onlinebookshop.BookCategoryConstants.UNLIMITED_PAGE_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.onlinebookshop.dtos.book.request.BookSearchParametersDto;
import com.example.onlinebookshop.dtos.book.request.CreateBookDto;
import com.example.onlinebookshop.dtos.book.request.UpdateBookDto;
import com.example.onlinebookshop.dtos.book.response.BookDto;
import com.example.onlinebookshop.dtos.book.response.BookDtoWithoutCategoryIds;
import com.example.onlinebookshop.entities.Book;
import com.example.onlinebookshop.entities.Category;
import com.example.onlinebookshop.exceptions.EntityNotFoundException;
import com.example.onlinebookshop.exceptions.ParameterAlreadyExistsException;
import com.example.onlinebookshop.mappers.BookMapper;
import com.example.onlinebookshop.repositories.BookRepository;
import com.example.onlinebookshop.repositories.CategoryRepository;
import com.example.onlinebookshop.repositories.book.bookspecs.BookSpecificationBuilder;
import com.example.onlinebookshop.repositories.book.bookspecs.bookfieldsspecs.AuthorSpecificationProvider;
import com.example.onlinebookshop.repositories.book.bookspecs.bookfieldsspecs.FromPriceSpecificationProvider;
import com.example.onlinebookshop.repositories.book.bookspecs.bookfieldsspecs.TitleSpecificationProvider;
import com.example.onlinebookshop.repositories.book.bookspecs.bookfieldsspecs.ToPriceSpecificationProvider;
import com.example.onlinebookshop.repositories.specifications.SpecificationProviderManager;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
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
        CATEGORY.setId(FICTION_CATEGORY_ID);

        CREATE_NEW_BOOK_DTO.setTitle(SOME_TITLE);
        CREATE_NEW_BOOK_DTO.setAuthor(SOME_AUTHOR);
        CREATE_NEW_BOOK_DTO.setIsbn(SOME_ISBN);
        CREATE_NEW_BOOK_DTO.setPrice(SOME_PRICE);
        CREATE_NEW_BOOK_DTO.getCategoryIds().add(FICTION_CATEGORY_ID);
        CREATE_NEW_BOOK_DTO.setDescription(SOME_DESCRIPTION);
        CREATE_NEW_BOOK_DTO.setCoverImage(SOME_COVER_IMAGE);

        CREATE_EXISTING_1984_BOOK_DTO.setTitle(TITLE_1984);
        CREATE_EXISTING_1984_BOOK_DTO.setAuthor(AUTHOR_1984);
        CREATE_EXISTING_1984_BOOK_DTO.setIsbn(ISBN_1984);
        CREATE_EXISTING_1984_BOOK_DTO.setPrice(PRICE_1984);
        CREATE_NEW_BOOK_DTO.getCategoryIds().add(FICTION_CATEGORY_ID);
        CREATE_EXISTING_1984_BOOK_DTO.setDescription(DESCRIPTION_1984);
        CREATE_EXISTING_1984_BOOK_DTO.setCoverImage(COVER_IMAGE_1984);

        UPDATE_BOOK_DTO.setTitle(SOME_TITLE);
        UPDATE_BOOK_DTO.setAuthor(SOME_AUTHOR);
        UPDATE_BOOK_DTO.getCategoryIds().add(FICTION_CATEGORY_ID);
        UPDATE_BOOK_DTO.setIsbn(SOME_ISBN);
        UPDATE_BOOK_DTO.setPrice(SOME_PRICE);
        UPDATE_BOOK_DTO.setDescription(SOME_DESCRIPTION);
        UPDATE_BOOK_DTO.setCoverImage(SOME_COVER_IMAGE);

        BOOK_FROM_DTO.setTitle(SOME_TITLE);
        BOOK_FROM_DTO.setAuthor(SOME_AUTHOR);
        BOOK_FROM_DTO.setIsbn(SOME_ISBN);
        BOOK_FROM_DTO.setPrice(SOME_PRICE);
        BOOK_FROM_DTO.getCategories().add(CATEGORY);
        BOOK_FROM_DTO.setDescription(SOME_DESCRIPTION);
        BOOK_FROM_DTO.setCoverImage(SOME_COVER_IMAGE);

        EXPECTED_GATSBY_BOOK.setId(GATSBY_ID);
        EXPECTED_GATSBY_BOOK.setTitle(GATSBY_TITLE);
        EXPECTED_GATSBY_BOOK.setAuthor(GATSBY_AUTHOR);
        EXPECTED_GATSBY_BOOK.setIsbn(GATSBY_ISBN);
        EXPECTED_GATSBY_BOOK.setPrice(GATSBY_PRICE);
        EXPECTED_GATSBY_BOOK.getCategories().add(CATEGORY);
        EXPECTED_GATSBY_BOOK.setDescription(GATSBY_DESCRIPTION);
        EXPECTED_GATSBY_BOOK.setCoverImage(GATSBY_COVER_IMAGE);

        EXPECTED_TKAM_BOOK.setId(TKAM_ID);
        EXPECTED_TKAM_BOOK.setTitle(TKAM_TITLE);
        EXPECTED_TKAM_BOOK.setAuthor(TKAM_AUTHOR);
        EXPECTED_TKAM_BOOK.setIsbn(TKAM_ISBN);
        EXPECTED_TKAM_BOOK.setPrice(TKAM_PRICE);
        EXPECTED_TKAM_BOOK.getCategories().add(CATEGORY);
        EXPECTED_TKAM_BOOK.setDescription(TKAM_DESCRIPTION);
        EXPECTED_TKAM_BOOK.setCoverImage(TKAM_COVER_IMAGE);

        EXPECTED_1984_BOOK.setId(ID_1984);
        EXPECTED_1984_BOOK.setTitle(TITLE_1984);
        EXPECTED_1984_BOOK.setAuthor(AUTHOR_1984);
        EXPECTED_1984_BOOK.setIsbn(ISBN_1984);
        EXPECTED_1984_BOOK.setPrice(PRICE_1984);
        EXPECTED_1984_BOOK.getCategories().add(CATEGORY);
        EXPECTED_1984_BOOK.setDescription(DESCRIPTION_1984);
        EXPECTED_1984_BOOK.setCoverImage(COVER_IMAGE_1984);

        EXPECTED_GATSBY_BOOK_DTO.setId(GATSBY_ID);
        EXPECTED_GATSBY_BOOK_DTO.setTitle(GATSBY_TITLE);
        EXPECTED_GATSBY_BOOK_DTO.setAuthor(GATSBY_AUTHOR);
        EXPECTED_GATSBY_BOOK_DTO.setIsbn(GATSBY_ISBN);
        EXPECTED_GATSBY_BOOK_DTO.setPrice(GATSBY_PRICE);
        EXPECTED_GATSBY_BOOK_DTO.getCategoryIds().add(FICTION_CATEGORY_ID);
        EXPECTED_GATSBY_BOOK_DTO.setDescription(GATSBY_DESCRIPTION);
        EXPECTED_GATSBY_BOOK_DTO.setCoverImage(GATSBY_COVER_IMAGE);

        EXPECTED_TKAM_BOOK_DTO.setId(TKAM_ID);
        EXPECTED_TKAM_BOOK_DTO.setTitle(TKAM_TITLE);
        EXPECTED_TKAM_BOOK_DTO.setAuthor(TKAM_AUTHOR);
        EXPECTED_TKAM_BOOK_DTO.setIsbn(TKAM_ISBN);
        EXPECTED_TKAM_BOOK_DTO.setPrice(TKAM_PRICE);
        EXPECTED_TKAM_BOOK_DTO.getCategoryIds().add(FICTION_CATEGORY_ID);
        EXPECTED_TKAM_BOOK_DTO.setDescription(TKAM_DESCRIPTION);
        EXPECTED_TKAM_BOOK_DTO.setCoverImage(TKAM_COVER_IMAGE);

        EXPECTED_1984_BOOK_DTO.setId(ID_1984);
        EXPECTED_1984_BOOK_DTO.setTitle(TITLE_1984);
        EXPECTED_1984_BOOK_DTO.setAuthor(AUTHOR_1984);
        EXPECTED_1984_BOOK_DTO.setIsbn(ISBN_1984);
        EXPECTED_1984_BOOK_DTO.setPrice(PRICE_1984);
        EXPECTED_1984_BOOK_DTO.getCategoryIds().add(FICTION_CATEGORY_ID);
        EXPECTED_1984_BOOK_DTO.setDescription(DESCRIPTION_1984);
        EXPECTED_1984_BOOK_DTO.setCoverImage(COVER_IMAGE_1984);

        EXPECTED_GATSBY_AFTER_UPDATE.setId(GATSBY_ID);
        EXPECTED_GATSBY_AFTER_UPDATE.setTitle(SOME_TITLE);
        EXPECTED_GATSBY_AFTER_UPDATE.setAuthor(SOME_AUTHOR);
        EXPECTED_GATSBY_AFTER_UPDATE.setIsbn(SOME_ISBN);
        EXPECTED_GATSBY_AFTER_UPDATE.setPrice(SOME_PRICE);
        EXPECTED_GATSBY_AFTER_UPDATE.getCategories().add(CATEGORY);
        EXPECTED_GATSBY_AFTER_UPDATE.setDescription(SOME_DESCRIPTION);
        EXPECTED_GATSBY_AFTER_UPDATE.setCoverImage(SOME_COVER_IMAGE);

        EXPECTED_GATSBY_AFTER_UPDATE_DTO.setId(GATSBY_ID);
        EXPECTED_GATSBY_AFTER_UPDATE_DTO.setTitle(SOME_TITLE);
        EXPECTED_GATSBY_AFTER_UPDATE_DTO.setAuthor(SOME_AUTHOR);
        EXPECTED_GATSBY_AFTER_UPDATE_DTO.setIsbn(SOME_ISBN);
        EXPECTED_GATSBY_AFTER_UPDATE_DTO.setPrice(SOME_PRICE);
        EXPECTED_GATSBY_AFTER_UPDATE_DTO.getCategoryIds().add(FICTION_CATEGORY_ID);
        EXPECTED_GATSBY_AFTER_UPDATE_DTO.setDescription(SOME_DESCRIPTION);
        EXPECTED_GATSBY_AFTER_UPDATE_DTO.setCoverImage(SOME_COVER_IMAGE);
    }

    @Test
    @DisplayName("Given a CreateDto of a book which is not in DB, successfully saves it")
    void save_IsAbleToSaveBookWhichIsNotInDb_Success() {
        when(categoryRepository.existsById(FICTION_CATEGORY_ID)).thenReturn(true);
        when(bookMapper.toCreateModel(CREATE_NEW_BOOK_DTO))
                .thenReturn(BOOK_FROM_DTO);
        when(bookRepository.save(BOOK_FROM_DTO))
                .thenReturn(BOOK_FROM_DTO);
        when(bookMapper.toDto(BOOK_FROM_DTO)).thenReturn(EXPECTED_GATSBY_BOOK_DTO);
        BookDto actualBookDto = bookService.save(CREATE_NEW_BOOK_DTO);
        assertEquals(EXPECTED_GATSBY_BOOK_DTO, actualBookDto);

        verify(categoryRepository, times(1)).existsById(FICTION_CATEGORY_ID);
        verify(bookMapper, times(1)).toCreateModel(CREATE_NEW_BOOK_DTO);
        verify(bookRepository, times(1)).save(BOOK_FROM_DTO);
        verify(bookMapper, times(1)).toDto(BOOK_FROM_DTO);
    }

    @Test
    @DisplayName("Given a CreateDto of a book which is already in DB by ISBN, can't save it")
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

        verify(bookRepository, times(1)).existsByIsbn(CREATE_EXISTING_1984_BOOK_DTO.getIsbn());
    }

    @Test
    @DisplayName("Given a pageable of first(zero) page and page sized 3, "
            + "successfully retrieves a list of 3 books from DB")
    void findAll_IsAbleToFindThreeBooksFromDb_Success() {
        Pageable pageable = PageRequest.of(FIRST_PAGE_NUMBER, UNLIMITED_PAGE_SIZE);
        List<Book> books = List.of(EXPECTED_GATSBY_BOOK, EXPECTED_TKAM_BOOK, EXPECTED_1984_BOOK);
        Page<Book> bookPage = new PageImpl<>(books, pageable, books.size());
        when(bookRepository.findAll(pageable)).thenReturn(bookPage);
        when(bookMapper.toDtoList(bookPage.getContent()))
                .thenReturn(List.of(
                        EXPECTED_GATSBY_BOOK_DTO,
                        EXPECTED_TKAM_BOOK_DTO,
                        EXPECTED_1984_BOOK_DTO));

        List<BookDto> expectedBookDtos =
                List.of(EXPECTED_GATSBY_BOOK_DTO,
                        EXPECTED_TKAM_BOOK_DTO,
                        EXPECTED_1984_BOOK_DTO);
        List<BookDto> actualBookDtos = bookService.findAll(pageable);

        assertEquals(expectedBookDtos, actualBookDtos);

        verify(bookRepository, times(1)).findAll(pageable);
        verify(bookMapper, times(1)).toDtoList(bookPage.getContent());
    }

    @Test
    @DisplayName("Given a pageable of 1000th page and page sized 3, "
            + "retrieves an empty list from DB")
    void findAll_IsNotAbleToFindBooksOnRandomPage_Fail() {
        Pageable pageable = PageRequest.of(RANDOM_PAGE_NUMBER, UNLIMITED_PAGE_SIZE);
        List<Book> books = List.of();
        Page<Book> bookPage = new PageImpl<>(books, pageable, 0);

        when(bookRepository.findAll(pageable)).thenReturn(bookPage);

        List<BookDto> expectedBookDtos = List.of();
        List<BookDto> actualBookDtos = bookService.findAll(pageable);

        assertEquals(expectedBookDtos, actualBookDtos);

        verify(bookRepository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Given a category id, if it is present in DB, "
            + "successfully retrieves 3 books from DB having this same category")
    void findAllWithoutCategoryIds_IsAbleToFindThreeBooksFromDb_Success() {
        List<Book> books = List.of(EXPECTED_GATSBY_BOOK, EXPECTED_TKAM_BOOK, EXPECTED_1984_BOOK);

        when(categoryRepository.existsById(FICTION_CATEGORY_ID)).thenReturn(true);
        when(bookRepository.findAllByCategoryId(FICTION_CATEGORY_ID)).thenReturn(books);
        when(bookMapper.toDtoWithoutCategoriesList(books))
                .thenReturn(List.of(
                        EXPECTED_GATSBY_BOOK_DTO_WITHOUT_CATEGORY_ID,
                        EXPECTED_TKAM_BOOK_DTO_WITHOUT_CATEGORY_ID,
                        EXPECTED_1984_BOOK_DTO_WITHOUT_CATEGORY_ID));

        List<BookDtoWithoutCategoryIds> expectedBookDtos =
                List.of(EXPECTED_GATSBY_BOOK_DTO_WITHOUT_CATEGORY_ID,
                        EXPECTED_TKAM_BOOK_DTO_WITHOUT_CATEGORY_ID,
                        EXPECTED_1984_BOOK_DTO_WITHOUT_CATEGORY_ID);
        List<BookDtoWithoutCategoryIds> actualBookDtos =
                bookService.findAllWithoutCategoryIds(FICTION_CATEGORY_ID);

        assertEquals(expectedBookDtos, actualBookDtos);

        verify(categoryRepository, times(1)).existsById(FICTION_CATEGORY_ID);
        verify(bookRepository, times(1)).findAllByCategoryId(FICTION_CATEGORY_ID);
        verify(bookMapper, times(1)).toDtoWithoutCategoriesList(List.of(
                EXPECTED_GATSBY_BOOK,EXPECTED_TKAM_BOOK, EXPECTED_1984_BOOK));
    }

    @Test
    @DisplayName("Given a random category id, throws an exception")
    void findAllWithoutCategoryIds_IsNotAbleToFindByRandomCategoryId_Fail() {
        when(categoryRepository.existsById(RANDOM_ID)).thenThrow(
                new EntityNotFoundException("Can't find category by id " + RANDOM_ID));

        Exception entityNotFoundException = assertThrows(EntityNotFoundException.class, () ->
                bookService.findAllWithoutCategoryIds(RANDOM_ID));
        String expectedMessage = "Can't find category by id " + RANDOM_ID;

        assertEquals(expectedMessage, entityNotFoundException.getMessage());

        verify(categoryRepository, times(1)).existsById(RANDOM_ID);
    }

    @Test
    @DisplayName("Given a book id, successfully retrieves the book by id from DB")
    void findById_IsAbleToFindExistingBook_Success() {
        when(bookRepository.findById(GATSBY_ID)).thenReturn(Optional.of(EXPECTED_GATSBY_BOOK));
        when(bookMapper.toDto(EXPECTED_GATSBY_BOOK)).thenReturn(EXPECTED_GATSBY_BOOK_DTO);
        BookDto byGatsbyId = bookService.findById(GATSBY_ID);
        assertEquals(EXPECTED_GATSBY_BOOK_DTO, byGatsbyId);

        verify(bookRepository, times(1)).findById(GATSBY_ID);
        verify(bookMapper, times(1)).toDto(EXPECTED_GATSBY_BOOK);
    }

    @Test
    @DisplayName("Given a random book id, throws an exception")
    void findById_IsNotAbleToFindBookByRandomId_Fail() {
        String exceptionMessage = "Can't find book by id " + RANDOM_ID;
        when(bookRepository.findById(RANDOM_ID))
                .thenThrow(new EntityNotFoundException("Can't find book by id " + RANDOM_ID));
        Exception exception = assertThrows(EntityNotFoundException.class, () ->
                bookService.findById(RANDOM_ID));
        assertEquals(exceptionMessage, exception.getMessage());

        verify(bookRepository, times(1)).findById(RANDOM_ID);
    }

    @Test
    @DisplayName("Given an UpdateDto, successfully updates the book in DB on condition that "
            + "another book with the same ISBN is not already present")
    void update_CanUpdateBookWhenIsbnDoesNotExist_Success() {
        when(bookRepository.findById(GATSBY_ID)).thenReturn(Optional.of(EXPECTED_GATSBY_BOOK));
        when(bookRepository.findBookIdByIsbn(UPDATE_BOOK_DTO.getIsbn())).thenReturn(
                Optional.empty());
        when(categoryRepository.existsById(FICTION_CATEGORY_ID)).thenReturn(true);
        when(bookRepository.save(EXPECTED_GATSBY_AFTER_UPDATE))
                .thenReturn(EXPECTED_GATSBY_AFTER_UPDATE);
        when(bookMapper.toDto(EXPECTED_GATSBY_AFTER_UPDATE))
                .thenReturn(EXPECTED_GATSBY_AFTER_UPDATE_DTO);
        BookDto actual = bookService.update(UPDATE_BOOK_DTO, GATSBY_ID, true);
        assertEquals(EXPECTED_GATSBY_AFTER_UPDATE_DTO, actual);

        verify(bookRepository, times(1)).findById(GATSBY_ID);
        verify(bookRepository, times(1)).findBookIdByIsbn(UPDATE_BOOK_DTO.getIsbn());
        verify(categoryRepository, times(1)).existsById(FICTION_CATEGORY_ID);
        verify(bookRepository, times(1)).save(EXPECTED_GATSBY_AFTER_UPDATE);
        verify(bookMapper, times(1)).toDto(EXPECTED_GATSBY_AFTER_UPDATE);
    }

    @Test
    @DisplayName("Given a random book id, throws an exception")
    void update_CannotUpdateBookWhenIsNotPresentById_Fail() {
        String exceptionMessage = "Can't find and update book by id " + RANDOM_ID;
        when(bookRepository.findById(RANDOM_ID))
                .thenThrow(new EntityNotFoundException(
                        "Can't find and update book by id " + RANDOM_ID));
        Exception exception = assertThrows(EntityNotFoundException.class, () ->
                bookService.update(UPDATE_BOOK_DTO, RANDOM_ID, true));
        assertEquals(exceptionMessage, exception.getMessage());

        verify(bookRepository, times(1)).findById(RANDOM_ID);
    }

    @Test
    @DisplayName("Throws an exception as another book with the same ISBN is already present")
    void update_CannotUpdateBookWhenAnotherIsPresentByIsbn_Fail() {
        UPDATE_BOOK_DTO.setIsbn(EXPECTED_TKAM_BOOK.getIsbn());

        String exceptionMessage = "Book with ISBN " + UPDATE_BOOK_DTO.getIsbn()
                + " already exists in DB";
        when(bookRepository.findById(TKAM_ID)).thenReturn(Optional.of(EXPECTED_TKAM_BOOK));
        when(bookRepository.findBookIdByIsbn(UPDATE_BOOK_DTO.getIsbn()))
                .thenReturn(Optional.of(EXPECTED_GATSBY_BOOK.getId()));
        Exception exception = assertThrows(ParameterAlreadyExistsException.class, () ->
                bookService.update(UPDATE_BOOK_DTO, TKAM_ID, true));
        assertEquals(exceptionMessage, exception.getMessage());

        verify(bookRepository, times(1)).findById(TKAM_ID);
        verify(bookRepository, times(1)).findBookIdByIsbn(UPDATE_BOOK_DTO.getIsbn());

        UPDATE_BOOK_DTO.setIsbn(SOME_ISBN);
    }

    @Test
    @DisplayName("Throws an exception because category from UpdateDto is not present by id")
    void update_CannotUpdateBookWhenCategoryIsNotPresentById_Fail() {
        when(bookRepository.findById(GATSBY_ID)).thenReturn(Optional.of(EXPECTED_GATSBY_BOOK));
        when(bookRepository.findBookIdByIsbn(UPDATE_BOOK_DTO.getIsbn()))
                .thenReturn(Optional.empty());
        String exceptionMessage = "Can't find category by id " + FICTION_CATEGORY_ID;
        when(categoryRepository.existsById(FICTION_CATEGORY_ID))
                .thenThrow(new EntityNotFoundException(
                        "Can't find category by id " + FICTION_CATEGORY_ID));
        Exception exception = assertThrows(EntityNotFoundException.class, () ->
                bookService.update(UPDATE_BOOK_DTO, GATSBY_ID, true));
        assertEquals(exceptionMessage, exception.getMessage());

        verify(bookRepository, times(1)).findById(GATSBY_ID);
        verify(bookRepository, times(1)).findBookIdByIsbn(UPDATE_BOOK_DTO.getIsbn());
        verify(categoryRepository, times(1)).existsById(FICTION_CATEGORY_ID);
    }

    @Test
    @DisplayName("Successfully deletes a book by id. For making sure the book is now gone, "
            + "when findById is engaged, throws an exception because book is not present "
            + "by id anymore")
    void delete_IsAbleToDeleteBookById_Success() {
        when(bookRepository.findById(GATSBY_ID)).thenReturn(Optional.of(EXPECTED_GATSBY_BOOK));
        bookService.delete(GATSBY_ID);
        String exceptionMessage = "Can't find and delete book by id " + GATSBY_ID;
        when(bookRepository.findById(GATSBY_ID))
                .thenThrow(new EntityNotFoundException(
                        "Can't find and delete book by id " + GATSBY_ID));
        Exception exception = assertThrows(EntityNotFoundException.class, () ->
                bookService.delete(GATSBY_ID));
        assertEquals(exceptionMessage, exception.getMessage());

        verify(bookRepository, times(2)).findById(GATSBY_ID);
        verify(bookRepository, times(1)).deleteById(GATSBY_ID);
    }

    @Test
    @DisplayName("When findById is engaged, throws an exception because book is not present by id")
    void delete_IsNotAbleToDeleteBookByRandomId_Fail() {
        String exceptionMessage = "Can't find and delete book by id " + RANDOM_ID;
        when(bookRepository.findById(RANDOM_ID))
                .thenThrow(new EntityNotFoundException(
                        "Can't find and delete book by id " + RANDOM_ID));
        Exception exception = assertThrows(EntityNotFoundException.class, () ->
                bookService.delete(RANDOM_ID));
        assertEquals(exceptionMessage, exception.getMessage());

        verify(bookRepository, times(1)).findById(RANDOM_ID);
        verify(bookRepository, times(0)).deleteById(RANDOM_ID);
    }

    @Test
    @DisplayName("Given specs of books which are actually in DB, can find these books")
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

        verify(bookSpecificationBuilder, times(1)).build(REAL_BOOK_SEARCH_PARAMETERS_DTO);
        verify(bookRepository, times(1)).findAll(specification);
        verify(bookMapper, times(1)).toDto(EXPECTED_TKAM_BOOK);
        verify(bookMapper, times(1)).toDto(EXPECTED_1984_BOOK);
        verifyWhensForSearch();
    }

    @Test
    @DisplayName("Fail-check test - make sure doesn't find"
            + " unwanted list of books by real specs of other books")
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

        verify(bookSpecificationBuilder, times(1)).build(REAL_BOOK_SEARCH_PARAMETERS_DTO);
        verify(bookRepository, times(1)).findAll(specification);
        verify(bookMapper, times(1)).toDto(EXPECTED_TKAM_BOOK);
        verify(bookMapper, times(1)).toDto(EXPECTED_1984_BOOK);
        verifyWhensForSearch();
    }

    @Test
    @DisplayName("Given specs of books which are not in DB, find nothing")
    void search_IsNotAbleToSearchBooksWithNonExistingSpecifications_Fail() {
        prepareWhensForSearch();

        Specification<Book> specification = getRandomSpecification();

        when(bookSpecificationBuilder.build(RANDOM_BOOK_SEARCH_PARAMETERS_DTO))
                .thenReturn(specification);
        List<Book> expectedBooks = List.of();

        when(bookRepository.findAll(specification)).thenReturn(expectedBooks);

        List<BookDto> actualBookDtos = bookService.search(RANDOM_BOOK_SEARCH_PARAMETERS_DTO);

        assertTrue(actualBookDtos.isEmpty());

        verify(bookSpecificationBuilder, times(1)).build(RANDOM_BOOK_SEARCH_PARAMETERS_DTO);
        verify(bookRepository, times(1)).findAll(specification);
        verifyWhensForSearch();
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

    private void verifyWhensForSearch() {
        verify(bookSpecificationProviderManager, times(1)).getSpecificationProvider("title");
        verify(bookSpecificationProviderManager, times(1)).getSpecificationProvider("author");
        verify(bookSpecificationProviderManager, times(1)).getSpecificationProvider("fromPrice");
        verify(bookSpecificationProviderManager, times(1)).getSpecificationProvider("toPrice");
    }
}
