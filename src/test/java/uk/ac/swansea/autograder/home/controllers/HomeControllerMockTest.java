package uk.ac.swansea.autograder.home.controllers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import uk.ac.swansea.autograder.home.services.HomeService;

@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HomeService service;

    @Test
    void indexShouldReturnMessageFromService() throws Exception {
        given(service.getWelcomeMessage())
                .willReturn("Hello, Mock");

        mockMvc.perform(get("/home"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, Mock"));
    }
}