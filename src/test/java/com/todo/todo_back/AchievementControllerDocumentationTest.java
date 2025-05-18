package com.todo.todo_back;
import com.todo.todo_back.services.AchievementService;
import com.todo.todo_back.services.PlaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AchieveementController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AchievementControllerDocumentationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AchievementService achievementService;

    @MockBean
    private UserService userService;

    @MockBean
    private PlaceService placeService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testGetAchievementsByLocation() throws Exception {
        Achievement achievement1 = new Achievement();
        achievement1.setId(1L);
        achievement1.setName("Visit Central Park");

        Achievement achievement2 = new Achievement();
        achievement2.setId(2L);
        achievement2.setName("Find the Statue");

        when(achievementService.getAchievements(eq(40.7128), eq(-74.0060), any()))
                .thenReturn(List.of(achievement1, achievement2));


        mockMvc.perform(RestDocumentationRequestBuilders
                        .post("/loocationInfo/{longitude}/{latitude}", -74.0060, 40.7128))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Visit Central Park"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Find the Statue"));
    }

    @Test
    void testGetCompletedAchievements() throws Exception {
        Achievement achievement = new Achievement();
        achievement.setId(1L);
        achievement.setName("City Explorer");

        when(achievementService.getAchievementsCompleted(any()))
                .thenReturn(List.of(achievement));

        mockMvc.perform(RestDocumentationRequestBuilders.get("/achieveementsCompleted"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("City Explorer"));
    }
}