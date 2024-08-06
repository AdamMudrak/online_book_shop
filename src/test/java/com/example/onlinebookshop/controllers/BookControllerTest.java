package com.example.onlinebookshop.controllers;

import static com.example.onlinebookshop.BookCategoryConstants.ADD_BOOKS_CATEGORIES_SQL;
import static com.example.onlinebookshop.BookCategoryConstants.ADD_BOOKS_SQL;
import static com.example.onlinebookshop.BookCategoryConstants.ADD_CATEGORIES_SQL;
import static com.example.onlinebookshop.BookCategoryConstants.ADMIN_NAME;
import static com.example.onlinebookshop.BookCategoryConstants.ADMIN_ROLE;
import static com.example.onlinebookshop.BookCategoryConstants.AUTHOR_1984;
import static com.example.onlinebookshop.BookCategoryConstants.BOOKS_URL;
import static com.example.onlinebookshop.BookCategoryConstants.CATEGORY_ID;
import static com.example.onlinebookshop.BookCategoryConstants.COVER_IMAGE_1984;
import static com.example.onlinebookshop.BookCategoryConstants.CREATE_ERRORS_LIST;
import static com.example.onlinebookshop.BookCategoryConstants.DELETE_BOOKS_CATEGORIES_SQL;
import static com.example.onlinebookshop.BookCategoryConstants.DELETE_BOOKS_SQL;
import static com.example.onlinebookshop.BookCategoryConstants.DELETE_CATEGORIES_SQL;
import static com.example.onlinebookshop.BookCategoryConstants.DESCRIPTION_1984;
import static com.example.onlinebookshop.BookCategoryConstants.EXPECTED_BOOK_DTO_ID;
import static com.example.onlinebookshop.BookCategoryConstants.FIRST_PAGE_NUMBER;
import static com.example.onlinebookshop.BookCategoryConstants.GATSBY_AUTHOR;
import static com.example.onlinebookshop.BookCategoryConstants.GATSBY_COVER_IMAGE;
import static com.example.onlinebookshop.BookCategoryConstants.GATSBY_DESCRIPTION;
import static com.example.onlinebookshop.BookCategoryConstants.GATSBY_ID;
import static com.example.onlinebookshop.BookCategoryConstants.GATSBY_ISBN;
import static com.example.onlinebookshop.BookCategoryConstants.GATSBY_PRICE;
import static com.example.onlinebookshop.BookCategoryConstants.GATSBY_TITLE;
import static com.example.onlinebookshop.BookCategoryConstants.ID_1984;
import static com.example.onlinebookshop.BookCategoryConstants.INVALID_CATEGORY_ID_DOES_NOT_EXIST;
import static com.example.onlinebookshop.BookCategoryConstants.INVALID_COVER_IMAGE_WITHOUT_HTTPS;
import static com.example.onlinebookshop.BookCategoryConstants.INVALID_DESCRIPTION_TOO_LONG;
import static com.example.onlinebookshop.BookCategoryConstants.INVALID_FOR_NOT_BLANK;
import static com.example.onlinebookshop.BookCategoryConstants.INVALID_ISBN_TOO_SHORT;
import static com.example.onlinebookshop.BookCategoryConstants.INVALID_PRICE_NEGATIVE;
import static com.example.onlinebookshop.BookCategoryConstants.ISBN_1984;
import static com.example.onlinebookshop.BookCategoryConstants.LIMITED_PAGE_SIZE;
import static com.example.onlinebookshop.BookCategoryConstants.PAGE;
import static com.example.onlinebookshop.BookCategoryConstants.PATH_TO_SQL_SCRIPTS;
import static com.example.onlinebookshop.BookCategoryConstants.PRICE_1984;
import static com.example.onlinebookshop.BookCategoryConstants.RANDOM_ID;
import static com.example.onlinebookshop.BookCategoryConstants.SECOND_PAGE_NUMBER;
import static com.example.onlinebookshop.BookCategoryConstants.SIZE;
import static com.example.onlinebookshop.BookCategoryConstants.SOME_AUTHOR;
import static com.example.onlinebookshop.BookCategoryConstants.SOME_COVER_IMAGE;
import static com.example.onlinebookshop.BookCategoryConstants.SOME_DESCRIPTION;
import static com.example.onlinebookshop.BookCategoryConstants.SOME_ISBN;
import static com.example.onlinebookshop.BookCategoryConstants.SOME_PRICE;
import static com.example.onlinebookshop.BookCategoryConstants.SOME_TITLE;
import static com.example.onlinebookshop.BookCategoryConstants.SORT;
import static com.example.onlinebookshop.BookCategoryConstants.SORT_BY_PRICE_ASC;
import static com.example.onlinebookshop.BookCategoryConstants.SORT_BY_TITLE_DESC;
import static com.example.onlinebookshop.BookCategoryConstants.TITLE_1984;
import static com.example.onlinebookshop.BookCategoryConstants.TKAM_AUTHOR;
import static com.example.onlinebookshop.BookCategoryConstants.TKAM_COVER_IMAGE;
import static com.example.onlinebookshop.BookCategoryConstants.TKAM_DESCRIPTION;
import static com.example.onlinebookshop.BookCategoryConstants.TKAM_ID;
import static com.example.onlinebookshop.BookCategoryConstants.TKAM_ISBN;
import static com.example.onlinebookshop.BookCategoryConstants.TKAM_PRICE;
import static com.example.onlinebookshop.BookCategoryConstants.TKAM_TITLE;
import static com.example.onlinebookshop.BookCategoryConstants.UNLIMITED_PAGE_SIZE;
import static com.example.onlinebookshop.BookCategoryConstants.UPDATE_ERRORS_LIST;
import static com.example.onlinebookshop.BookCategoryConstants.USER_NAME;
import static com.example.onlinebookshop.BookCategoryConstants.VALID_COVER_IMAGE;
import static com.example.onlinebookshop.BookCategoryConstants.VALID_DESCRIPTION;
import static com.example.onlinebookshop.BookCategoryConstants.VALID_ISBN;
import static com.example.onlinebookshop.BookCategoryConstants.VALID_PRICE;
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
                .setDescription(INVALID_DESCRIPTION_TOO_LONG);
        CREATE_INVALID_BOOK_DTO.setCoverImage(INVALID_COVER_IMAGE_WITHOUT_HTTPS);

        UPDATE_VALID_BOOK_DTO.setIsbn(VALID_ISBN);
        UPDATE_VALID_BOOK_DTO.setPrice(VALID_PRICE);
        UPDATE_VALID_BOOK_DTO.setDescription(VALID_DESCRIPTION);
        UPDATE_VALID_BOOK_DTO.setCoverImage(VALID_COVER_IMAGE);

        UPDATE_INVALID_BOOK_DTO.setIsbn(INVALID_ISBN_TOO_SHORT);
        UPDATE_INVALID_BOOK_DTO.setPrice(INVALID_PRICE_NEGATIVE);
        UPDATE_INVALID_BOOK_DTO.setDescription(INVALID_DESCRIPTION_TOO_LONG);
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

    @WithMockUser(username = ADMIN_NAME, roles = {ADMIN_ROLE})
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
                            post(BOOKS_URL)
                                    .content(jsonRequest)
                                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), BookDto.class);
        assertEquals(EXPECTED_NEW_BOOK_DTO, actual);
    }

    @WithMockUser(username = ADMIN_NAME, roles = {ADMIN_ROLE})
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
                        post(BOOKS_URL)
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
        String actualExceptionMessage = result.getResponse().getContentAsString();
        for (String error : CREATE_ERRORS_LIST) {
            assertTrue(actualExceptionMessage.contains(error));
        }
    }

    @WithMockUser(username = ADMIN_NAME, roles = {ADMIN_ROLE})
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
                        put(BOOKS_URL + "/" + GATSBY_ID)
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), BookDto.class);
        assertEquals(EXPECTED_BOOK_DTO_AFTER_UPDATE, actual);
    }

    @WithMockUser(username = ADMIN_NAME, roles = {ADMIN_ROLE})
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
                        put(BOOKS_URL + "/" + GATSBY_ID)
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
        String actualExceptionMessage = result.getResponse().getContentAsString();
        for (String error : UPDATE_ERRORS_LIST) {
            assertTrue(actualExceptionMessage.contains(error));
        }
    }

    @WithMockUser(username = ADMIN_NAME, roles = {ADMIN_ROLE})
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
                        delete(BOOKS_URL + "/" + ID_1984)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        String expectedExceptionMessage = "Can't find and delete book by id " + ID_1984;
        MvcResult result = mockMvc.perform(
                        delete(BOOKS_URL + "/" + ID_1984)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
        String actualExceptionMessage = result.getResponse().getContentAsString();
        assertEquals(expectedExceptionMessage, actualExceptionMessage);
    }

    @WithMockUser(username = ADMIN_NAME, roles = {ADMIN_ROLE})
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
                        delete(BOOKS_URL + "/" + RANDOM_ID)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
        String actualExceptionMessage = result.getResponse().getContentAsString();
        assertEquals(expectedExceptionMessage, actualExceptionMessage);
    }

    @WithMockUser(username = USER_NAME)
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
                        get(BOOKS_URL)
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

    @WithMockUser(username = USER_NAME)
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
                        get(BOOKS_URL)
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

    @WithMockUser(username = USER_NAME)
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
                        get(BOOKS_URL)
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
                        get(BOOKS_URL)
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
