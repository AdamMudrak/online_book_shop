package com.example.onlinebookshop.repositories;

import static com.example.onlinebookshop.BookCategoryConstants.ADD_CATEGORIES_SQL;
import static com.example.onlinebookshop.BookCategoryConstants.DELETE_CATEGORIES_SQL;
import static com.example.onlinebookshop.BookCategoryConstants.FICTION_CATEGORY_DESCRIPTION;
import static com.example.onlinebookshop.BookCategoryConstants.FICTION_CATEGORY_ID;
import static com.example.onlinebookshop.BookCategoryConstants.FICTION_CATEGORY_NAME;
import static com.example.onlinebookshop.BookCategoryConstants.NON_EXISTING_CATEGORY_NAME;
import static com.example.onlinebookshop.BookCategoryConstants.PATH_TO_SQL_SCRIPTS;
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
    private static final Category EXPECTED_CATEGORY = new Category();
    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeAll
    static void initVars() {
        EXPECTED_CATEGORY.setId(FICTION_CATEGORY_ID);
        EXPECTED_CATEGORY.setName(FICTION_CATEGORY_NAME);
        EXPECTED_CATEGORY.setDescription(FICTION_CATEGORY_DESCRIPTION);
    }

    @Sql(scripts = PATH_TO_SQL_SCRIPTS + ADD_CATEGORIES_SQL,
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = PATH_TO_SQL_SCRIPTS + DELETE_CATEGORIES_SQL,
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Given a name of an existing category, successfully make sure it exists in DB")
    @Test
    void existsByName_IsAbleToCheckIfExistingCategoryExistsByName_Success() {
        assertTrue(categoryRepository.existsByName(FICTION_CATEGORY_NAME));
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
