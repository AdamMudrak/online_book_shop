package com.example.onlinebookshop.controllers;

import static com.example.onlinebookshop.TestConstants.ADD_BOOKS_CATEGORIES_SQL;
import static com.example.onlinebookshop.TestConstants.ADD_BOOKS_SQL;
import static com.example.onlinebookshop.TestConstants.ADD_CATEGORIES_SQL;
import static com.example.onlinebookshop.TestConstants.DELETE_BOOKS_CATEGORIES_SQL;
import static com.example.onlinebookshop.TestConstants.DELETE_BOOKS_SQL;
import static com.example.onlinebookshop.TestConstants.DELETE_CATEGORIES_SQL;
import static com.example.onlinebookshop.TestConstants.PATH_TO_SQL_SCRIPTS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.onlinebookshop.dtos.book.request.CreateBookDto;
import com.example.onlinebookshop.dtos.book.request.UpdateBookDto;
import com.example.onlinebookshop.dtos.book.response.BookDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTest {
    protected static MockMvc mockMvc;
    private static final CreateBookDto CREATE_NEW_BOOK_DTO = new CreateBookDto();
    private static final CreateBookDto CREATE_EXISTING_BOOK_DTO = new CreateBookDto();
    private static final CreateBookDto CREATE_INVALID_BOOK_DTO = new CreateBookDto();
    private static final UpdateBookDto UPDATE_VALID_BOOK_DTO = new UpdateBookDto();
    private static final UpdateBookDto UPDATE_INVALID_BOOK_DTO = new UpdateBookDto();
    private static final BookDto EXPECTED_NEW_BOOK_DTO = new BookDto();
    private static final BookDto EXPECTED_GATSBY_BOOK_DTO = new BookDto();
    private static final BookDto EXPECTED_BOOK_DTO_AFTER_UPDATE = new BookDto();
    private static final BookDto EXPECTED_TKAM_BOOK_DTO = new BookDto();
    private static final BookDto EXPECTED_1984_BOOK_DTO = new BookDto();
    private static final long EXPECTED_BOOK_DTO_ID = 4L;
    private static final long CATEGORY_ID = 1L;
    private static final String SOME_TITLE = "Some book";
    private static final String SOME_AUTHOR = "Some author";
    private static final String SOME_ISBN = "0000000000000";
    private static final BigDecimal SOME_PRICE = BigDecimal.valueOf(9.99);
    private static final String SOME_DESCRIPTION = "Some description";
    private static final String SOME_COVER_IMAGE = "https://example.com/updated-cover-image.jpg";

    private static final long RANDOM_ID = 1000L;

    private static final long GATSBY_ID = 1L;
    private static final String GATSBY_TITLE = "The Great Gatsby";
    private static final String GATSBY_AUTHOR = "F. Scott Fitzgerald";
    private static final String GATSBY_ISBN = "9780743273565";
    private static final BigDecimal GATSBY_PRICE = BigDecimal.valueOf(12.99);
    private static final String GATSBY_DESCRIPTION = "A story of the fabulously wealthy Jay Gatsby "
            + "and his love for the beautiful Daisy Buchanan.";
    private static final String GATSBY_COVER_IMAGE = "https://example.com/gatsby.jpg";

    private static final long TKAM_ID = 2L;
    private static final String TKAM_TITLE = "To Kill a Mockingbird";
    private static final String TKAM_AUTHOR = "Harper Lee";
    private static final String TKAM_ISBN = "9780061120084";
    private static final BigDecimal TKAM_PRICE = BigDecimal.valueOf(10.49);
    private static final String TKAM_DESCRIPTION = "A novel that explores the irrationality of "
            + "adult attitudes towards race and class in the Deep South of the 1930s.";
    private static final String TKAM_COVER_IMAGE = "https://example.com/mockingbird.jpg";

    private static final long ID_1984 = 3L;
    private static final String TITLE_1984 = "1984";
    private static final String AUTHOR_1984 = "George Orwell";
    private static final String ISBN_1984 = "9780451524935";
    private static final BigDecimal PRICE_1984 = BigDecimal.valueOf(9.99);
    private static final String DESCRIPTION_1984 = "A dystopian novel set in Airstrip One, "
            + "a province of the superstate Oceania, whose residents are victims of perpetual war, "
            + "omnipresent government surveillance, and public manipulation.";
    private static final String COVER_IMAGE_1984 = "https://example.com/1984.jpg";

    private static final String INVALID_FOR_NOT_BLANK = "";
    private static final String INVALID_ISBN_TOO_SHORT = "123456789";
    private static final String VALID_ISBN = "1234567890";
    private static final long INVALID_CATEGORY_ID_DOES_NOT_EXIST = 2L;
    private static final BigDecimal VALID_PRICE = BigDecimal.valueOf(1);
    private static final BigDecimal INVALID_PRICE_NEGATIVE = BigDecimal.valueOf(-1);
    private static final int REPEAT_DESCRIPTION_COUNT = 100;
    private static final String INVALID_DESCRIPTION =
            "This text contains more than 300 characters.".repeat(REPEAT_DESCRIPTION_COUNT);
    private static final String VALID_DESCRIPTION =
            "This text contains more than 0 and less than 300 characters.";
    private static final String INVALID_COVER_IMAGE_WITHOUT_HTTPS = "example.com/gatsby.jpg";
    private static final String VALID_COVER_IMAGE = "https://example.com/another_gatsby.jpg";
    private static final List<String> CREATE_ERRORS_LIST = List.of(
            "title must not be blank",
            "author must not be blank",
            "isbn size must be between 10 and 17",
            "price must be greater than 0",
            "description size must be between 0 and 3000",
            "coverImage Invalid format filepath"
           );
    private static final List<String> UPDATE_ERRORS_LIST = List.of(
            "isbn size must be between 10 and 17",
            "price must be greater than 0",
            "description size must be between 0 and 3000",
            "coverImage Invalid format filepath"
    );
    private static final int FIRST_PAGE_NUMBER = 0;
    private static final int SECOND_PAGE_NUMBER = 1;
    private static final int UNLIMITED_PAGE_SIZE = 3;
    private static final int LIMITED_PAGE_SIZE = 2;
    private static final String SORT_BY_TITLE_DESC = "title,DESC";
    private static final String SORT_BY_PRICE_ASC = "price,ASC";
    private static final String SORT = "sort";
    private static final String PAGE = "page";
    private static final String SIZE = "size";
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void initVars(@Autowired WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
        CREATE_NEW_BOOK_DTO.setTitle(SOME_TITLE);
        CREATE_NEW_BOOK_DTO.setAuthor(SOME_AUTHOR);
        CREATE_NEW_BOOK_DTO.setCategoryIds(Set.of(CATEGORY_ID));
        CREATE_NEW_BOOK_DTO.setIsbn(SOME_ISBN);
        CREATE_NEW_BOOK_DTO.setPrice(SOME_PRICE);
        CREATE_NEW_BOOK_DTO.setDescription(SOME_DESCRIPTION);
        CREATE_NEW_BOOK_DTO.setCoverImage(SOME_COVER_IMAGE);

        EXPECTED_NEW_BOOK_DTO.setId(EXPECTED_BOOK_DTO_ID);
        EXPECTED_NEW_BOOK_DTO.setTitle(SOME_TITLE);
        EXPECTED_NEW_BOOK_DTO.setAuthor(SOME_AUTHOR);
        EXPECTED_NEW_BOOK_DTO.setIsbn(SOME_ISBN);
        EXPECTED_NEW_BOOK_DTO.setCategoryIds(Set.of(CATEGORY_ID));
        EXPECTED_NEW_BOOK_DTO.setPrice(SOME_PRICE);
        EXPECTED_NEW_BOOK_DTO.setDescription(SOME_DESCRIPTION);
        EXPECTED_NEW_BOOK_DTO.setCoverImage(SOME_COVER_IMAGE);

        CREATE_EXISTING_BOOK_DTO.setTitle(GATSBY_TITLE);
        CREATE_EXISTING_BOOK_DTO.setAuthor(GATSBY_AUTHOR);
        CREATE_EXISTING_BOOK_DTO.setIsbn(GATSBY_ISBN);
        CREATE_EXISTING_BOOK_DTO.setCategoryIds(Set.of(CATEGORY_ID));
        CREATE_EXISTING_BOOK_DTO.setPrice(GATSBY_PRICE);
        CREATE_EXISTING_BOOK_DTO.setDescription(GATSBY_DESCRIPTION);
        CREATE_EXISTING_BOOK_DTO.setCoverImage(GATSBY_COVER_IMAGE);

        EXPECTED_BOOK_DTO_AFTER_UPDATE.setId(GATSBY_ID);
        EXPECTED_BOOK_DTO_AFTER_UPDATE.setTitle(GATSBY_TITLE);
        EXPECTED_BOOK_DTO_AFTER_UPDATE.setAuthor(GATSBY_AUTHOR);
        EXPECTED_BOOK_DTO_AFTER_UPDATE.setCategoryIds(Set.of(CATEGORY_ID));
        EXPECTED_BOOK_DTO_AFTER_UPDATE.setIsbn(VALID_ISBN);
        EXPECTED_BOOK_DTO_AFTER_UPDATE.setPrice(VALID_PRICE);
        EXPECTED_BOOK_DTO_AFTER_UPDATE.setDescription(VALID_DESCRIPTION);
        EXPECTED_BOOK_DTO_AFTER_UPDATE.setCoverImage(VALID_COVER_IMAGE);

        CREATE_INVALID_BOOK_DTO.setTitle(INVALID_FOR_NOT_BLANK);
        CREATE_INVALID_BOOK_DTO.setAuthor(INVALID_FOR_NOT_BLANK);
        CREATE_INVALID_BOOK_DTO.setIsbn(INVALID_ISBN_TOO_SHORT);
        CREATE_INVALID_BOOK_DTO.setCategoryIds(Set.of(INVALID_CATEGORY_ID_DOES_NOT_EXIST));
        CREATE_INVALID_BOOK_DTO.setPrice(INVALID_PRICE_NEGATIVE);
        CREATE_INVALID_BOOK_DTO
                .setDescription(INVALID_DESCRIPTION);
        CREATE_INVALID_BOOK_DTO.setCoverImage(INVALID_COVER_IMAGE_WITHOUT_HTTPS);

        UPDATE_VALID_BOOK_DTO.setIsbn(VALID_ISBN);
        UPDATE_VALID_BOOK_DTO.setPrice(VALID_PRICE);
        UPDATE_VALID_BOOK_DTO.setDescription(VALID_DESCRIPTION);
        UPDATE_VALID_BOOK_DTO.setCoverImage(VALID_COVER_IMAGE);

        UPDATE_INVALID_BOOK_DTO.setIsbn(INVALID_ISBN_TOO_SHORT);
        UPDATE_INVALID_BOOK_DTO.setPrice(INVALID_PRICE_NEGATIVE);
        UPDATE_INVALID_BOOK_DTO.setDescription(INVALID_DESCRIPTION);
        UPDATE_INVALID_BOOK_DTO.setCoverImage(INVALID_COVER_IMAGE_WITHOUT_HTTPS);

        EXPECTED_GATSBY_BOOK_DTO.setId(GATSBY_ID);
        EXPECTED_GATSBY_BOOK_DTO.setTitle(GATSBY_TITLE);
        EXPECTED_GATSBY_BOOK_DTO.setAuthor(GATSBY_AUTHOR);
        EXPECTED_GATSBY_BOOK_DTO.setCategoryIds(Set.of(CATEGORY_ID));
        EXPECTED_GATSBY_BOOK_DTO.setIsbn(GATSBY_ISBN);
        EXPECTED_GATSBY_BOOK_DTO.setPrice(GATSBY_PRICE);
        EXPECTED_GATSBY_BOOK_DTO.setDescription(GATSBY_DESCRIPTION);
        EXPECTED_GATSBY_BOOK_DTO.setCoverImage(GATSBY_COVER_IMAGE);

        EXPECTED_TKAM_BOOK_DTO.setId(TKAM_ID);
        EXPECTED_TKAM_BOOK_DTO.setTitle(TKAM_TITLE);
        EXPECTED_TKAM_BOOK_DTO.setAuthor(TKAM_AUTHOR);
        EXPECTED_TKAM_BOOK_DTO.setCategoryIds(Set.of(CATEGORY_ID));
        EXPECTED_TKAM_BOOK_DTO.setIsbn(TKAM_ISBN);
        EXPECTED_TKAM_BOOK_DTO.setPrice(TKAM_PRICE);
        EXPECTED_TKAM_BOOK_DTO.setDescription(TKAM_DESCRIPTION);
        EXPECTED_TKAM_BOOK_DTO.setCoverImage(TKAM_COVER_IMAGE);

        EXPECTED_1984_BOOK_DTO.setId(ID_1984);
        EXPECTED_1984_BOOK_DTO.setTitle(TITLE_1984);
        EXPECTED_1984_BOOK_DTO.setAuthor(AUTHOR_1984);
        EXPECTED_1984_BOOK_DTO.setCategoryIds(Set.of(CATEGORY_ID));
        EXPECTED_1984_BOOK_DTO.setIsbn(ISBN_1984);
        EXPECTED_1984_BOOK_DTO.setPrice(PRICE_1984);
        EXPECTED_1984_BOOK_DTO.setDescription(DESCRIPTION_1984);
        EXPECTED_1984_BOOK_DTO.setCoverImage(COVER_IMAGE_1984);
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @Sql(scripts = {PATH_TO_SQL_SCRIPTS + ADD_BOOKS_SQL,
            PATH_TO_SQL_SCRIPTS + ADD_CATEGORIES_SQL,
            PATH_TO_SQL_SCRIPTS + ADD_BOOKS_CATEGORIES_SQL},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {PATH_TO_SQL_SCRIPTS + DELETE_BOOKS_CATEGORIES_SQL,
            PATH_TO_SQL_SCRIPTS + DELETE_CATEGORIES_SQL,
            PATH_TO_SQL_SCRIPTS + DELETE_BOOKS_SQL},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Given a CreateDto of a book which is not in DB, successfully saves it")
    public void createBook_IsAbleToSaveBookWhichIsNotInDB_Success() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(CREATE_NEW_BOOK_DTO);
        MvcResult result = mockMvc.perform(
                            post("/books")
                                    .content(jsonRequest)
                                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), BookDto.class);
        assertEquals(EXPECTED_NEW_BOOK_DTO, actual);
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @Sql(scripts = {PATH_TO_SQL_SCRIPTS + ADD_BOOKS_SQL,
            PATH_TO_SQL_SCRIPTS + ADD_CATEGORIES_SQL,
            PATH_TO_SQL_SCRIPTS + ADD_BOOKS_CATEGORIES_SQL},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {PATH_TO_SQL_SCRIPTS + DELETE_BOOKS_CATEGORIES_SQL,
            PATH_TO_SQL_SCRIPTS + DELETE_CATEGORIES_SQL,
            PATH_TO_SQL_SCRIPTS + DELETE_BOOKS_SQL},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Given an invalid CreateDto with a mistake for each field, fails to save it")
    public void createBook_IsNotAbleToSaveBookWhenCreateDtoIsInvalid_Fail() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(CREATE_INVALID_BOOK_DTO);
        MvcResult result = mockMvc.perform(
                        post("/books")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
        String actualExceptionMessage = result.getResponse().getContentAsString();
        for (String error : CREATE_ERRORS_LIST) {
            assertTrue(actualExceptionMessage.contains(error));
        }
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @Sql(scripts = {PATH_TO_SQL_SCRIPTS + ADD_BOOKS_SQL,
            PATH_TO_SQL_SCRIPTS + ADD_CATEGORIES_SQL,
            PATH_TO_SQL_SCRIPTS + ADD_BOOKS_CATEGORIES_SQL},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {PATH_TO_SQL_SCRIPTS + DELETE_BOOKS_CATEGORIES_SQL,
            PATH_TO_SQL_SCRIPTS + DELETE_CATEGORIES_SQL,
            PATH_TO_SQL_SCRIPTS + DELETE_BOOKS_SQL},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Given an UpdateDto of a book which is in DB, successfully updates it")
    public void update_IsAbleToUpdateBookWhenUpdateDtoIsValid_Success() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(UPDATE_VALID_BOOK_DTO);
        MvcResult result = mockMvc.perform(
                        put("/books/" + GATSBY_ID)
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), BookDto.class);
        assertEquals(EXPECTED_BOOK_DTO_AFTER_UPDATE, actual);
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @Sql(scripts = {PATH_TO_SQL_SCRIPTS + ADD_BOOKS_SQL,
            PATH_TO_SQL_SCRIPTS + ADD_CATEGORIES_SQL,
            PATH_TO_SQL_SCRIPTS + ADD_BOOKS_CATEGORIES_SQL},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {PATH_TO_SQL_SCRIPTS + DELETE_BOOKS_CATEGORIES_SQL,
            PATH_TO_SQL_SCRIPTS + DELETE_CATEGORIES_SQL,
            PATH_TO_SQL_SCRIPTS + DELETE_BOOKS_SQL},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Given an invalid UpdateDto with a mistake for each field, fails to update it")
    public void update_IsNotAbleToUpdateBookWhenUpdateDtoIsInvalid_Fail() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(UPDATE_INVALID_BOOK_DTO);
        MvcResult result = mockMvc.perform(
                        put("/books/" + GATSBY_ID)
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
        String actualExceptionMessage = result.getResponse().getContentAsString();
        for (String error : UPDATE_ERRORS_LIST) {
            assertTrue(actualExceptionMessage.contains(error));
        }
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @Sql(scripts = {PATH_TO_SQL_SCRIPTS + ADD_BOOKS_SQL,
            PATH_TO_SQL_SCRIPTS + ADD_CATEGORIES_SQL,
            PATH_TO_SQL_SCRIPTS + ADD_BOOKS_CATEGORIES_SQL},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {PATH_TO_SQL_SCRIPTS + DELETE_BOOKS_CATEGORIES_SQL,
            PATH_TO_SQL_SCRIPTS + DELETE_CATEGORIES_SQL,
            PATH_TO_SQL_SCRIPTS + DELETE_BOOKS_SQL},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Given a valid book ID, successfully deletes book by ID,"
            + " then make sure that this book doesn't exist no more")
    public void delete_IsAbleToDeleteBookWhenBookExistsById_Success() throws Exception {
        mockMvc.perform(
                        delete("/books/" + ID_1984)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        String expectedExceptionMessage = "Can't find and delete book by id " + ID_1984;
        MvcResult result = mockMvc.perform(
                        delete("/books/" + ID_1984)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
        String actualExceptionMessage = result.getResponse().getContentAsString();
        assertEquals(expectedExceptionMessage, actualExceptionMessage);
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @Sql(scripts = {PATH_TO_SQL_SCRIPTS + ADD_BOOKS_SQL,
            PATH_TO_SQL_SCRIPTS + ADD_CATEGORIES_SQL,
            PATH_TO_SQL_SCRIPTS + ADD_BOOKS_CATEGORIES_SQL},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {PATH_TO_SQL_SCRIPTS + DELETE_BOOKS_CATEGORIES_SQL,
            PATH_TO_SQL_SCRIPTS + DELETE_CATEGORIES_SQL,
            PATH_TO_SQL_SCRIPTS + DELETE_BOOKS_SQL},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Given an invalid book ID, fails to delete book by ID")
    public void delete_IsNotAbleToDeleteBookWhenBookDoesNotExistById_Fail() throws Exception {
        String expectedExceptionMessage = "Can't find and delete book by id " + RANDOM_ID;
        MvcResult result = mockMvc.perform(
                        delete("/books/" + RANDOM_ID)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
        String actualExceptionMessage = result.getResponse().getContentAsString();
        assertEquals(expectedExceptionMessage, actualExceptionMessage);
    }

    @WithMockUser(username = "user")
    @Test
    @Sql(scripts = {PATH_TO_SQL_SCRIPTS + ADD_BOOKS_SQL,
            PATH_TO_SQL_SCRIPTS + ADD_CATEGORIES_SQL,
            PATH_TO_SQL_SCRIPTS + ADD_BOOKS_CATEGORIES_SQL},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {PATH_TO_SQL_SCRIPTS + DELETE_BOOKS_CATEGORIES_SQL,
            PATH_TO_SQL_SCRIPTS + DELETE_CATEGORIES_SQL,
            PATH_TO_SQL_SCRIPTS + DELETE_BOOKS_SQL},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Get all three books from DB")
    public void getAll_IsAbleToGetAllThreeBooksFrom_Success() throws Exception {
        List<BookDto> expectedBookDtos = List.of(
                EXPECTED_GATSBY_BOOK_DTO, EXPECTED_TKAM_BOOK_DTO, EXPECTED_1984_BOOK_DTO);
        MvcResult result = mockMvc.perform(
                        get("/books")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        List<BookDto> actualBookDtos = objectMapper.readValue(result
                        .getResponse()
                        .getContentAsString(),
                objectMapper
                        .getTypeFactory()
                        .constructCollectionType(List.class, BookDto.class));
        assertEquals(expectedBookDtos, actualBookDtos);
    }

    @WithMockUser(username = "user")
    @Test
    @Sql(scripts = {PATH_TO_SQL_SCRIPTS + ADD_BOOKS_SQL,
            PATH_TO_SQL_SCRIPTS + ADD_CATEGORIES_SQL,
            PATH_TO_SQL_SCRIPTS + ADD_BOOKS_CATEGORIES_SQL},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {PATH_TO_SQL_SCRIPTS + DELETE_BOOKS_CATEGORIES_SQL,
            PATH_TO_SQL_SCRIPTS + DELETE_CATEGORIES_SQL,
            PATH_TO_SQL_SCRIPTS + DELETE_BOOKS_SQL},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Get books from DB, sort by price,ASC")
    public void getAll_IsAbleToGetThreeBooksFromDbSortByPriceAsc_Success() throws Exception {
        List<BookDto> expectedBookDtos = List.of(
                EXPECTED_1984_BOOK_DTO, EXPECTED_TKAM_BOOK_DTO, EXPECTED_GATSBY_BOOK_DTO);
        MvcResult result = mockMvc.perform(
                        get("/books")
                        .queryParam(PAGE, FIRST_PAGE_NUMBER + "")
                        .queryParam(SIZE, UNLIMITED_PAGE_SIZE + "")
                        .queryParam(SORT, SORT_BY_PRICE_ASC)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn();
        List<BookDto> actualBookDtos = objectMapper.readValue(result
                        .getResponse()
                        .getContentAsString(),
                objectMapper
                        .getTypeFactory()
                        .constructCollectionType(List.class, BookDto.class));
        assertEquals(expectedBookDtos, actualBookDtos);
    }

    @WithMockUser(username = "user")
    @Test
    @Sql(scripts = {PATH_TO_SQL_SCRIPTS + ADD_BOOKS_SQL,
            PATH_TO_SQL_SCRIPTS + ADD_CATEGORIES_SQL,
            PATH_TO_SQL_SCRIPTS + ADD_BOOKS_CATEGORIES_SQL},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {PATH_TO_SQL_SCRIPTS + DELETE_BOOKS_CATEGORIES_SQL,
            PATH_TO_SQL_SCRIPTS + DELETE_CATEGORIES_SQL,
            PATH_TO_SQL_SCRIPTS + DELETE_BOOKS_SQL},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Get books from DB, sort by title,DESC, page size limit 2")
    public void getAll_IsAbleToGetTwoBooksFromDbSortByTitleDescLimitPageSizeToTwo_Success()
            throws Exception {
        List<BookDto> expectedBookDtosForFirstPage = List.of(
                EXPECTED_TKAM_BOOK_DTO, EXPECTED_GATSBY_BOOK_DTO);
        MvcResult resultForFirstPage = mockMvc.perform(
                        get("/books")
                                .queryParam(PAGE, FIRST_PAGE_NUMBER + "")
                                .queryParam(SIZE, LIMITED_PAGE_SIZE + "")
                                .queryParam(SORT, SORT_BY_TITLE_DESC)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        List<BookDto> actualBookDtos = objectMapper.readValue(resultForFirstPage
                        .getResponse()
                        .getContentAsString(),
                objectMapper
                        .getTypeFactory()
                        .constructCollectionType(List.class, BookDto.class));
        assertEquals(expectedBookDtosForFirstPage, actualBookDtos);

        List<BookDto> expectedBookDtoForSecondPage = List.of(EXPECTED_1984_BOOK_DTO);
        MvcResult resultForSecondPage = mockMvc.perform(
                        get("/books")
                                .queryParam(PAGE, SECOND_PAGE_NUMBER + "")
                                .queryParam(SIZE, LIMITED_PAGE_SIZE + "")
                                .queryParam(SORT, SORT_BY_TITLE_DESC)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        List<BookDto> actualBookDto = objectMapper.readValue(resultForSecondPage
                        .getResponse()
                        .getContentAsString(),
                objectMapper
                        .getTypeFactory()
                        .constructCollectionType(List.class, BookDto.class));
        assertEquals(expectedBookDtoForSecondPage, actualBookDto);
    }
}
