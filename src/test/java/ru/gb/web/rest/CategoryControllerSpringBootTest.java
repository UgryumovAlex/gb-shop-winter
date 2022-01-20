package ru.gb.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.gb.web.dto.CategoryDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryControllerSpringBootTest {

    final private static String CATEGORY_TITLE = "SPORT";
    final private static String CATEGORY_TITLE_TO_CHANGE = "SPORT & FITNESS";
    final private static String CATEGORY_TITLE2 = "ELECTRONICS";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @Order(1)
    public void saveCategoryTest() throws Exception {


        mockMvc.perform(post("/api/v1/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper
                                .writeValueAsString(CategoryDto.builder()
                                        .name(CATEGORY_TITLE)
                                        .build()
                                )
                        ))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/v1/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper
                                .writeValueAsString(CategoryDto.builder()
                                        .name(CATEGORY_TITLE2)
                                        .build()
                                )
                        ))
                .andExpect(status().isCreated());
    }


    @Test
    @Order(2)
    public void findAllTest() throws Exception {
        mockMvc.perform(get("/api/v1/category"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.[1].id").value("2"))
                .andExpect(jsonPath("$.[1].name").value(CATEGORY_TITLE2));
    }

    @Test
    @Order(3)
    public void changeCategoryTest() throws Exception {

        mockMvc.perform(put("/api/v1/category/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper
                                .writeValueAsString(CategoryDto.builder()
                                        .name(CATEGORY_TITLE_TO_CHANGE)
                                        .build()
                                )
                        ))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(4)
    public void findAllAfterChangeTest() throws Exception {
        mockMvc.perform(get("/api/v1/category"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").value(CATEGORY_TITLE_TO_CHANGE));
    }

    @Test
    @Order(5)
    public void deleteCategoryTest() throws Exception {
        mockMvc.perform(delete("/api/v1/category/2"))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(6)
    public void findAllAfterDeleteTest() throws Exception {
        mockMvc.perform(get("/api/v1/category"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }
}
