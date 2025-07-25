package uk.ac.swansea.autograder.auth.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import uk.ac.swansea.autograder.api.controllers.dto.NewUserDto;
import uk.ac.swansea.autograder.auth.controllers.dto.LoginDto;
import uk.ac.swansea.autograder.auth.controllers.dto.LoginResponseDto;
import uk.ac.swansea.autograder.general.services.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerMethodTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private Flyway flyway;

    @BeforeEach
    void setUp() {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    void adminShouldLoginSuccessfully() throws Exception {
        // Create Admin
        NewUserDto newUserDto = NewUserDto.builder()
                .username("test_user").password("test_pass").build();
        userService.createAdmin(newUserDto);

        // Arrange
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("test_user");
        loginDto.setPassword("test_pass");

        // Act & Assert
        MvcResult result = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // Verify response can be deserialized to LoginResponseDto
        String responseContent = result.getResponse().getContentAsString();
        LoginResponseDto responseDto = objectMapper.readValue(responseContent, LoginResponseDto.class);

        // Assert all fields are present and have correct types
        assertNotNull(responseDto.getToken());
        assertNotNull(responseDto.getRole());
        assertEquals("ADMIN", responseDto.getRole());
        assertEquals("test_user", responseDto.getUsername()); // Fixed assertion to match test_user
    }

    @Test
    void lecturerShouldLoginSuccessfully() throws Exception {
        // Create Lecturer
        NewUserDto newUserDto = NewUserDto.builder()
                .username("test_user").password("test_pass").build();
        userService.createLecturer(newUserDto);

        // Arrange
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("test_user");
        loginDto.setPassword("test_pass");

        // Act & Assert
        MvcResult result = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // Verify response can be deserialized to LoginResponseDto
        String responseContent = result.getResponse().getContentAsString();
        LoginResponseDto responseDto = objectMapper.readValue(responseContent, LoginResponseDto.class);

        // Assert all fields are present and have correct types
        assertNotNull(responseDto.getToken());
        assertNotNull(responseDto.getRole());
        assertEquals("LECTURER", responseDto.getRole());
        assertEquals("test_user", responseDto.getUsername()); // Fixed assertion to match test_user
    }

    @Test
    void studentShouldLoginSuccessfully() throws Exception {
        // Create Student
        NewUserDto newUserDto = NewUserDto.builder()
                .username("test_user").password("test_pass").build();
        userService.createStudent(newUserDto);

        // Arrange
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("test_user");
        loginDto.setPassword("test_pass");

        // Act & Assert
        MvcResult result = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // Verify response can be deserialized to LoginResponseDto
        String responseContent = result.getResponse().getContentAsString();
        LoginResponseDto responseDto = objectMapper.readValue(responseContent, LoginResponseDto.class);

        // Assert all fields are present and have correct types
        assertNotNull(responseDto.getToken());
        assertNotNull(responseDto.getRole());
        assertEquals("STUDENT", responseDto.getRole());
        assertEquals("test_user", responseDto.getUsername()); // Fixed assertion to match test_user
    }
}