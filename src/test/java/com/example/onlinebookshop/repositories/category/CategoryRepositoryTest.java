package com.example.onlinebookshop.repositories.category;

import static com.example.onlinebookshop.BookCategoryConstants.ADD_CATEGORIES_SQL;
import static com.example.onlinebookshop.BookCategoryConstants.DELETE_CATEGORIES_SQL;
import static com.example.onlinebookshop.BookCategoryConstants.PATH_TO_SQL_SCRIPTS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.onlinebookshop.entities.Category;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategoryRepositoryTest {
    private static final String CATEGORY_NAME = "Fiction";
    private static final String CATEGORY_DESCRIPTION =
            "Interesting books about imaginary though possible events";
    private static final String NON_EXISTING_CATEGORY_NAME = "I don't even exist";
    private static Category expectedCategory;
    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeAll
    static void initVars() {
        expectedCategory = new Category();
        expectedCategory.setId(1L);
        expectedCategory.setName(CATEGORY_NAME);
        expectedCategory.setDescription(CATEGORY_DESCRIPTION);
    }

    @Sql(scripts = PATH_TO_SQL_SCRIPTS + ADD_CATEGORIES_SQL,
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = PATH_TO_SQL_SCRIPTS + DELETE_CATEGORIES_SQL,
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Given a name of an existing category, successfully retrieve it from DB")
    @Test
    void findByName_IsAbleToFindExistingCategoryByName_Success() {
        if (categoryRepository.findByName(CATEGORY_NAME).isPresent()) {
            Category actualCategory = categoryRepository.findByName(CATEGORY_NAME).get();
            assertEquals(expectedCategory, actualCategory);
        }
    }

    @Sql(scripts = PATH_TO_SQL_SCRIPTS + ADD_CATEGORIES_SQL,
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = PATH_TO_SQL_SCRIPTS + DELETE_CATEGORIES_SQL,
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Given a name of a non-existing category, retrieve nothing from DB")
    @Test
    void findByName_IsNotAbleToFindNonExistingCategoryByName_Fail() {
        assertTrue(categoryRepository.findByName(NON_EXISTING_CATEGORY_NAME).isEmpty());
    }

    @Sql(scripts = PATH_TO_SQL_SCRIPTS + ADD_CATEGORIES_SQL,
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = PATH_TO_SQL_SCRIPTS + DELETE_CATEGORIES_SQL,
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Given a name of an existing category, successfully make sure it exists in DB")
    @Test
    void existsByName_IsAbleToCheckIfExistingCategoryExistsByName_Success() {
        assertTrue(categoryRepository.existsByName(CATEGORY_NAME));
    }

    @Sql(scripts = PATH_TO_SQL_SCRIPTS + ADD_CATEGORIES_SQL,
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = PATH_TO_SQL_SCRIPTS + DELETE_CATEGORIES_SQL,
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Given a name of a non-existing category, "
            + "successfully make sure it doesn't exist in DB")
    @Test
    void existsByName_IsNotAbleToCheckIfNonExistingCategoryExistsByName_Fail() {
        assertFalse(categoryRepository.existsByName(NON_EXISTING_CATEGORY_NAME));
    }
}
