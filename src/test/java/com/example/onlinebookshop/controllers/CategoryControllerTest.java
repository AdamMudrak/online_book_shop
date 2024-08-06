package com.example.onlinebookshop.controllers;

import static com.example.onlinebookshop.BookCategoryConstants.ADD_BOOKS_CATEGORIES_SQL;
import static com.example.onlinebookshop.BookCategoryConstants.ADD_BOOKS_SQL;
import static com.example.onlinebookshop.BookCategoryConstants.ADD_CATEGORIES_SQL;
import static com.example.onlinebookshop.BookCategoryConstants.ADMIN_NAME;
import static com.example.onlinebookshop.BookCategoryConstants.ADMIN_ROLE;
import static com.example.onlinebookshop.BookCategoryConstants.ANOTHER_CATEGORY_DESCRIPTION;
import static com.example.onlinebookshop.BookCategoryConstants.ANOTHER_CATEGORY_NAME;
import static com.example.onlinebookshop.BookCategoryConstants.AUTHOR_1984;
import static com.example.onlinebookshop.BookCategoryConstants.BOOKS_URL;
import static com.example.onlinebookshop.BookCategoryConstants.CATEGORIES_URL;
import static com.example.onlinebookshop.BookCategoryConstants.CATEGORY_DTO_ERROR;
import static com.example.onlinebookshop.BookCategoryConstants.COVER_IMAGE_1984;
import static com.example.onlinebookshop.BookCategoryConstants.DELETE_BOOKS_CATEGORIES_SQL;
import static com.example.onlinebookshop.BookCategoryConstants.DELETE_BOOKS_SQL;
import static com.example.onlinebookshop.BookCategoryConstants.DELETE_CATEGORIES_SQL;
import static com.example.onlinebookshop.BookCategoryConstants.DESCRIPTION_1984;
import static com.example.onlinebookshop.BookCategoryConstants.FICTION_CATEGORY_DESCRIPTION;
import static com.example.onlinebookshop.BookCategoryConstants.FICTION_CATEGORY_ID;
import static com.example.onlinebookshop.BookCategoryConstants.FICTION_CATEGORY_NAME;
import static com.example.onlinebookshop.BookCategoryConstants.FIRST_PAGE_NUMBER;
import static com.example.onlinebookshop.BookCategoryConstants.GATSBY_AUTHOR;
import static com.example.onlinebookshop.BookCategoryConstants.GATSBY_COVER_IMAGE;
import static com.example.onlinebookshop.BookCategoryConstants.GATSBY_DESCRIPTION;
import static com.example.onlinebookshop.BookCategoryConstants.GATSBY_ID;
import static com.example.onlinebookshop.BookCategoryConstants.GATSBY_ISBN;
import static com.example.onlinebookshop.BookCategoryConstants.GATSBY_PRICE;
import static com.example.onlinebookshop.BookCategoryConstants.GATSBY_TITLE;
import static com.example.onlinebookshop.BookCategoryConstants.ID_1984;
import static com.example.onlinebookshop.BookCategoryConstants.INVALID_FOR_NOT_BLANK;
import static com.example.onlinebookshop.BookCategoryConstants.ISBN_1984;
import static com.example.onlinebookshop.BookCategoryConstants.LIMITED_PAGE_SIZE;
import static com.example.onlinebookshop.BookCategoryConstants.NEW_CATEGORY_ID;
import static com.example.onlinebookshop.BookCategoryConstants.NON_FICTION_CATEGORY_DESCRIPTION;
import static com.example.onlinebookshop.BookCategoryConstants.NON_FICTION_CATEGORY_ID;
import static com.example.onlinebookshop.BookCategoryConstants.NON_FICTION_CATEGORY_NAME;
import static com.example.onlinebookshop.BookCategoryConstants.PAGE;
import static com.example.onlinebookshop.BookCategoryConstants.PATH_TO_SQL_SCRIPTS;
import static com.example.onlinebookshop.BookCategoryConstants.PRICE_1984;
import static com.example.onlinebookshop.BookCategoryConstants.RANDOM_ID;
import static com.example.onlinebookshop.BookCategoryConstants.SCIENCE_FICTION_CATEGORY_DESCRIPTION;
import static com.example.onlinebookshop.BookCategoryConstants.SCIENCE_FICTION_CATEGORY_ID;
import static com.example.onlinebookshop.BookCategoryConstants.SCIENCE_FICTION_CATEGORY_NAME;
import static com.example.onlinebookshop.BookCategoryConstants.SECOND_PAGE_NUMBER;
import static com.example.onlinebookshop.BookCategoryConstants.SIZE;
import static com.example.onlinebookshop.BookCategoryConstants.SORT;
import static com.example.onlinebookshop.BookCategoryConstants.SORT_BY_NAME_ASC;
import static com.example.onlinebookshop.BookCategoryConstants.SORT_BY_NAME_DESC;
import static com.example.onlinebookshop.BookCategoryConstants.SPLITERATOR;
import static com.example.onlinebookshop.BookCategoryConstants.TITLE_1984;
import static com.example.onlinebookshop.BookCategoryConstants.TKAM_AUTHOR;
import static com.example.onlinebookshop.BookCategoryConstants.TKAM_COVER_IMAGE;
import static com.example.onlinebookshop.BookCategoryConstants.TKAM_DESCRIPTION;
import static com.example.onlinebookshop.BookCategoryConstants.TKAM_ID;
import static com.example.onlinebookshop.BookCategoryConstants.TKAM_ISBN;
import static com.example.onlinebookshop.BookCategoryConstants.TKAM_PRICE;
import static com.example.onlinebookshop.BookCategoryConstants.TKAM_TITLE;
import static com.example.onlinebookshop.BookCategoryConstants.UNLIMITED_PAGE_SIZE;
import static com.example.onlinebookshop.BookCategoryConstants.UPDATED_CATEGORY_DESCRIPTION;
import static com.example.onlinebookshop.BookCategoryConstants.UPDATED_CATEGORY_NAME;
import static com.example.onlinebookshop.BookCategoryConstants.USER_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.onlinebookshop.dtos.book.response.BookDtoWithoutCategoryIds;
import com.example.onlinebookshop.dtos.category.request.CreateCategoryDto;
import com.example.onlinebookshop.dtos.category.request.UpdateCategoryDto;
import com.example.onlinebookshop.dtos.category.response.CategoryDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
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
public class CategoryControllerTest {
    protected static MockMvc mockMvc;
    private static final CreateCategoryDto EXISTING_CREATE_CATEGORY_DTO =
            new CreateCategoryDto(FICTION_CATEGORY_NAME, FICTION_CATEGORY_DESCRIPTION);
    private static final CreateCategoryDto NEW_CREATE_CATEGORY_DTO =
            new CreateCategoryDto(ANOTHER_CATEGORY_NAME, ANOTHER_CATEGORY_DESCRIPTION);
    private static final CreateCategoryDto INVALID_CREATE_CATEGORY_DTO =
            new CreateCategoryDto(INVALID_FOR_NOT_BLANK, ANOTHER_CATEGORY_DESCRIPTION);
    private static final UpdateCategoryDto UPDATE_VALID_CATEGORY_DTO =
            new UpdateCategoryDto(UPDATED_CATEGORY_NAME, UPDATED_CATEGORY_DESCRIPTION);
    private static final UpdateCategoryDto UPDATE_INVALID_CATEGORY_DTO =
            new UpdateCategoryDto(INVALID_FOR_NOT_BLANK, ANOTHER_CATEGORY_DESCRIPTION);
    private static final CategoryDto CATEGORY_DTO =
            new CategoryDto(NEW_CATEGORY_ID, ANOTHER_CATEGORY_NAME, ANOTHER_CATEGORY_DESCRIPTION);
    private static final BookDtoWithoutCategoryIds GATSBY_EXPECTED_DTO =
            new BookDtoWithoutCategoryIds(
            GATSBY_ID,
            GATSBY_TITLE,
            GATSBY_AUTHOR,
            GATSBY_ISBN,
            GATSBY_PRICE,
            GATSBY_DESCRIPTION,
            GATSBY_COVER_IMAGE
    );

    private static final BookDtoWithoutCategoryIds TKAM_EXPECTED_DTO =
            new BookDtoWithoutCategoryIds(
            TKAM_ID,
            TKAM_TITLE,
            TKAM_AUTHOR,
            TKAM_ISBN,
            TKAM_PRICE,
            TKAM_DESCRIPTION,
            TKAM_COVER_IMAGE
    );

    private static final BookDtoWithoutCategoryIds EXPECTED_DTO_1984 =
            new BookDtoWithoutCategoryIds(
                    ID_1984,
                    TITLE_1984,
                    AUTHOR_1984,
                    ISBN_1984,
                    PRICE_1984,
                    DESCRIPTION_1984,
                    COVER_IMAGE_1984
            );

    private static final CategoryDto EXPECTED_EXISTING_FICTION_CATEGORY_DTO =
            new CategoryDto(FICTION_CATEGORY_ID,
                    FICTION_CATEGORY_NAME,
                    FICTION_CATEGORY_DESCRIPTION);

    private static final CategoryDto EXPECTED_EXISTING_NON_FICTION_CATEGORY_DTO =
            new CategoryDto(NON_FICTION_CATEGORY_ID,
                    NON_FICTION_CATEGORY_NAME,
                    NON_FICTION_CATEGORY_DESCRIPTION);

    private static final CategoryDto EXPECTED_EXISTING_SCIENCE_FICTION_CATEGORY_DTO =
            new CategoryDto(SCIENCE_FICTION_CATEGORY_ID,
                    SCIENCE_FICTION_CATEGORY_NAME,
                    SCIENCE_FICTION_CATEGORY_DESCRIPTION);

    private static final CategoryDto EXPECTED_CATEGORY_DTO_AFTER_UPDATE =
            new CategoryDto(FICTION_CATEGORY_ID,
                    UPDATED_CATEGORY_NAME,
                    UPDATED_CATEGORY_DESCRIPTION);
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void initVars(@Autowired WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(username = ADMIN_NAME, roles = {ADMIN_ROLE})
    @Test
    @Sql(scripts = {PATH_TO_SQL_SCRIPTS + ADD_CATEGORIES_SQL},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {PATH_TO_SQL_SCRIPTS + DELETE_CATEGORIES_SQL},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Given a CreateDto of a category which is not in DB, successfully saves it")
    public void createCategory_IsAbleToSaveCategoryWhichIsNotInDB_Success() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(NEW_CREATE_CATEGORY_DTO);
        MvcResult result = mockMvc.perform(
                        post(CATEGORIES_URL)
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        CategoryDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), CategoryDto.class);
        assertEquals(CATEGORY_DTO, actual);
    }

    @WithMockUser(username = ADMIN_NAME, roles = {ADMIN_ROLE})
    @Test
    @Sql(scripts = {PATH_TO_SQL_SCRIPTS + ADD_CATEGORIES_SQL},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {PATH_TO_SQL_SCRIPTS + DELETE_CATEGORIES_SQL},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Given a CreateDto of a category with a name which is in DB, fails to save it")
    public void createCategory_IsNotAbleToSaveCategoryWhichIsInDbByName_Fail() throws Exception {
        String expectedExceptionMessage = "Category with name " + FICTION_CATEGORY_NAME
                + " already exists in DB";
        String jsonRequest = objectMapper.writeValueAsString(EXISTING_CREATE_CATEGORY_DTO);
        MvcResult result = mockMvc.perform(
                        post(CATEGORIES_URL)
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andReturn();
        String actualExceptionMessage = result.getResponse().getContentAsString();
        assertEquals(expectedExceptionMessage, actualExceptionMessage);
    }

    @WithMockUser(username = ADMIN_NAME, roles = {ADMIN_ROLE})
    @Test
    @Sql(scripts = {PATH_TO_SQL_SCRIPTS + ADD_CATEGORIES_SQL},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {PATH_TO_SQL_SCRIPTS + DELETE_CATEGORIES_SQL},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Given an invalid CreateDto with a mistake for the name field, fails to save it")
    public void createCategory_IsNotAbleToSaveCategoryWithNoName_Fail() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(INVALID_CREATE_CATEGORY_DTO);
        MvcResult result = mockMvc.perform(
                        post(CATEGORIES_URL)
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
        String actualExceptionMessage = result.getResponse().getContentAsString();
        assertTrue(actualExceptionMessage.contains(CATEGORY_DTO_ERROR));
    }

    @WithMockUser(username = ADMIN_NAME, roles = {ADMIN_ROLE})
    @Test
    @Sql(scripts = {PATH_TO_SQL_SCRIPTS + ADD_CATEGORIES_SQL},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {PATH_TO_SQL_SCRIPTS + DELETE_CATEGORIES_SQL},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Given an UpdateDto of a category which is in DB, successfully updates it")
    public void update_IsAbleToUpdateCategoryWhenUpdateDtoIsValid_Success() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(UPDATE_VALID_CATEGORY_DTO);
        MvcResult result = mockMvc.perform(
                        put(CATEGORIES_URL + SPLITERATOR + FICTION_CATEGORY_ID)
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        CategoryDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), CategoryDto.class);
        assertEquals(EXPECTED_CATEGORY_DTO_AFTER_UPDATE, actual);
    }

    @WithMockUser(username = ADMIN_NAME, roles = {ADMIN_ROLE})
    @Test
    @Sql(scripts = {PATH_TO_SQL_SCRIPTS + ADD_CATEGORIES_SQL},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {PATH_TO_SQL_SCRIPTS + DELETE_CATEGORIES_SQL},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Given an invalid UpdateDto with a mistake for each field, fails to update it")
    public void update_IsNotAbleToUpdateCategoryWhenUpdateDtoIsInvalid_Fail() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(UPDATE_INVALID_CATEGORY_DTO);
        MvcResult result = mockMvc.perform(
                        put(CATEGORIES_URL + SPLITERATOR + FICTION_CATEGORY_ID)
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
        String actualExceptionMessage = result.getResponse().getContentAsString();
        assertTrue(actualExceptionMessage.contains(CATEGORY_DTO_ERROR));
    }

    @WithMockUser(username = ADMIN_NAME, roles = {ADMIN_ROLE})
    @Test
    @Sql(scripts = {PATH_TO_SQL_SCRIPTS + ADD_CATEGORIES_SQL},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {PATH_TO_SQL_SCRIPTS + DELETE_CATEGORIES_SQL},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Given a valid category ID, successfully deletes category by ID,"
            + " then make sure that this category doesn't exist no more")
    public void delete_IsAbleToDeleteCategoryWhenCategoryExistsById_Success() throws Exception {
        mockMvc.perform(
                        delete(CATEGORIES_URL + SPLITERATOR + FICTION_CATEGORY_ID)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        String expectedExceptionMessage = "Can't find category by id " + FICTION_CATEGORY_ID;
        MvcResult result = mockMvc.perform(
                        delete(CATEGORIES_URL + SPLITERATOR + FICTION_CATEGORY_ID)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
        String actualExceptionMessage = result.getResponse().getContentAsString();
        assertEquals(expectedExceptionMessage, actualExceptionMessage);
    }

    @WithMockUser(username = ADMIN_NAME, roles = {ADMIN_ROLE})
    @Test
    @Sql(scripts = {PATH_TO_SQL_SCRIPTS + ADD_CATEGORIES_SQL},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {PATH_TO_SQL_SCRIPTS + DELETE_CATEGORIES_SQL},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Given an invalid category ID, fails to delete category by ID")
    public void delete_IsNotAbleToDeleteCategoryWhenCategoryDoesNotExistById_Fail()
            throws Exception {
        String expectedExceptionMessage = "Can't find category by id " + RANDOM_ID;
        MvcResult result = mockMvc.perform(
                        delete(CATEGORIES_URL + SPLITERATOR + RANDOM_ID)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
        String actualExceptionMessage = result.getResponse().getContentAsString();
        assertEquals(expectedExceptionMessage, actualExceptionMessage);
    }

    @WithMockUser(username = USER_NAME)
    @Test
    @Sql(scripts = {PATH_TO_SQL_SCRIPTS + ADD_CATEGORIES_SQL},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {PATH_TO_SQL_SCRIPTS + DELETE_CATEGORIES_SQL},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Get category \"Fiction\" from DB by id")
    public void getById_IsAbleToFindACategoryByExistingIdFromDb_Success() throws Exception {
        MvcResult result = mockMvc.perform(
                        get(CATEGORIES_URL + SPLITERATOR + FICTION_CATEGORY_ID)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        CategoryDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), CategoryDto.class);
        assertEquals(EXPECTED_EXISTING_FICTION_CATEGORY_DTO, actual);
    }

    @WithMockUser(username = USER_NAME)
    @Test
    @Sql(scripts = {PATH_TO_SQL_SCRIPTS + ADD_CATEGORIES_SQL},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {PATH_TO_SQL_SCRIPTS + DELETE_CATEGORIES_SQL},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Get nothing from DB")
    public void getById_IsNotAbleToFindACategoryByNonExistingIdFromDb_Fail() throws Exception {
        String expectedExceptionMessage = "Can't find category by id " + RANDOM_ID;
        MvcResult result = mockMvc.perform(
                        get(CATEGORIES_URL + SPLITERATOR + RANDOM_ID)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
        String actualExceptionMessage = result.getResponse().getContentAsString();
        assertEquals(expectedExceptionMessage, actualExceptionMessage);
    }

    @WithMockUser(username = USER_NAME)
    @Test
    @Sql(scripts = {PATH_TO_SQL_SCRIPTS + ADD_CATEGORIES_SQL},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {PATH_TO_SQL_SCRIPTS + DELETE_CATEGORIES_SQL},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Get all three categories from DB")
    public void getAll_IsAbleToGetAllThreeCategoriesFromDb_Success() throws Exception {
        List<CategoryDto> expectedCategoryDtos = List.of(
                EXPECTED_EXISTING_FICTION_CATEGORY_DTO,
                EXPECTED_EXISTING_SCIENCE_FICTION_CATEGORY_DTO,
                EXPECTED_EXISTING_NON_FICTION_CATEGORY_DTO);
        MvcResult result = mockMvc.perform(
                        get(CATEGORIES_URL)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        List<CategoryDto> actualCategoryDtos = objectMapper.readValue(result
                        .getResponse()
                        .getContentAsString(),
                objectMapper
                        .getTypeFactory()
                        .constructCollectionType(List.class, CategoryDto.class));
        assertEquals(expectedCategoryDtos, actualCategoryDtos);
    }

    @WithMockUser(username = USER_NAME)
    @Test
    @Sql(scripts = {PATH_TO_SQL_SCRIPTS + ADD_CATEGORIES_SQL},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {PATH_TO_SQL_SCRIPTS + DELETE_CATEGORIES_SQL},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Get categories from DB, sort by name,DESC")
    public void getAll_IsAbleToGetThreeCategoriesFromDbSortByNameDesc_Success() throws Exception {
        List<CategoryDto> expectedCategoryDtos = List.of(
                EXPECTED_EXISTING_SCIENCE_FICTION_CATEGORY_DTO,
                EXPECTED_EXISTING_NON_FICTION_CATEGORY_DTO,
                EXPECTED_EXISTING_FICTION_CATEGORY_DTO);
        MvcResult result = mockMvc.perform(
                        get(CATEGORIES_URL)
                                .queryParam(PAGE, FIRST_PAGE_NUMBER + "")
                                .queryParam(SIZE, UNLIMITED_PAGE_SIZE + "")
                                .queryParam(SORT, SORT_BY_NAME_DESC)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        List<CategoryDto> actualCategoryDtos = objectMapper.readValue(result
                        .getResponse()
                        .getContentAsString(),
                objectMapper
                        .getTypeFactory()
                        .constructCollectionType(List.class, CategoryDto.class));
        assertEquals(expectedCategoryDtos, actualCategoryDtos);
    }

    @WithMockUser(username = USER_NAME)
    @Test
    @Sql(scripts = {PATH_TO_SQL_SCRIPTS + ADD_CATEGORIES_SQL},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {PATH_TO_SQL_SCRIPTS + DELETE_CATEGORIES_SQL},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Get categories from DB, sort by name,ASC, page size limit 2")
    public void getAll_IsAbleToGetThreeCategoriesInTwoPortionsDueToPageSizeLimit_Success()
            throws Exception {
        List<CategoryDto> expectedCategoryDtosForFirstPage = List.of(
                EXPECTED_EXISTING_FICTION_CATEGORY_DTO,
                EXPECTED_EXISTING_NON_FICTION_CATEGORY_DTO);
        MvcResult resultForFirstPage = mockMvc.perform(
                        get(CATEGORIES_URL)
                                .queryParam(PAGE, FIRST_PAGE_NUMBER + "")
                                .queryParam(SIZE, LIMITED_PAGE_SIZE + "")
                                .queryParam(SORT, SORT_BY_NAME_ASC)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        List<CategoryDto> actualCategoryDtos = objectMapper.readValue(resultForFirstPage
                        .getResponse()
                        .getContentAsString(),
                objectMapper
                        .getTypeFactory()
                        .constructCollectionType(List.class, CategoryDto.class));
        assertEquals(expectedCategoryDtosForFirstPage, actualCategoryDtos);

        List<CategoryDto> expectedCategoryDtoForSecondPage =
                List.of(EXPECTED_EXISTING_SCIENCE_FICTION_CATEGORY_DTO);
        MvcResult resultForSecondPage = mockMvc.perform(
                        get(CATEGORIES_URL)
                                .queryParam(PAGE, SECOND_PAGE_NUMBER + "")
                                .queryParam(SIZE, LIMITED_PAGE_SIZE + "")
                                .queryParam(SORT, SORT_BY_NAME_ASC)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        List<CategoryDto> actualCategoryDto = objectMapper.readValue(resultForSecondPage
                        .getResponse()
                        .getContentAsString(),
                objectMapper
                        .getTypeFactory()
                        .constructCollectionType(List.class, CategoryDto.class));
        assertEquals(expectedCategoryDtoForSecondPage, actualCategoryDto);
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
    @DisplayName("Get books by category id from DB")
    public void getBooksByCategoryId_IsAbleToGetThreeBooksConnectedToFirstCategory_Success()
            throws Exception {
        MvcResult result = mockMvc.perform(
                        get(CATEGORIES_URL + SPLITERATOR + FICTION_CATEGORY_ID + BOOKS_URL)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
        List<BookDtoWithoutCategoryIds> expectedBookDtosWithoutCategoryIds = List.of(
                GATSBY_EXPECTED_DTO, TKAM_EXPECTED_DTO, EXPECTED_DTO_1984);
        List<BookDtoWithoutCategoryIds> actualBookDtosWithoutCategoryIds = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                objectMapper
                        .getTypeFactory()
                        .constructCollectionType(List.class, BookDtoWithoutCategoryIds.class));
        assertEquals(expectedBookDtosWithoutCategoryIds, actualBookDtosWithoutCategoryIds);
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
    @DisplayName("Get nothing by category id from DB if books are not assigned to this category")
    public void getBooksByCategoryId_IsNotAbleToGetAnythingWhenTryingThirdCategory_Fail()
            throws Exception {
        MvcResult result = mockMvc.perform(
                        get(CATEGORIES_URL + SPLITERATOR + NON_FICTION_CATEGORY_ID + BOOKS_URL)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        List<BookDtoWithoutCategoryIds> expectedBookDtosWithoutCategoryIds = List.of();
        List<BookDtoWithoutCategoryIds> actualBookDtosWithoutCategoryIds = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                objectMapper
                        .getTypeFactory()
                        .constructCollectionType(List.class, BookDtoWithoutCategoryIds.class));
        assertEquals(expectedBookDtosWithoutCategoryIds, actualBookDtosWithoutCategoryIds);
    }
}
