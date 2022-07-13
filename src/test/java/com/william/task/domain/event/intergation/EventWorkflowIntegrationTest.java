package com.william.task.domain.event.intergation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.william.task.domain.event.intergation.configuration.IntegrationTestConfig;
import com.william.task.domain.event.model.EventDetailedView;
import com.william.task.domain.event.model.EventInitData;
import com.william.task.domain.event.model.EventView;
import com.william.task.domain.event.model.ScoreView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.william.task.domain.event.intergation.TestUtils.asJsonString;
import static com.william.task.domain.event.intergation.TestUtils.asObject;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = IntegrationTestConfig.class)
@ActiveProfiles("test")
public class EventWorkflowIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void eventCompleteWorkflowTest() throws Exception {
        EventInitData data = new EventInitData();
        data.setTeam1("test team 1");
        data.setTeam2("test team 2");
        String response = mvc.perform(MockMvcRequestBuilders
                        .post("/v1/event")
                        .content(asJsonString(data))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse().getContentAsString();
        EventView saveResult  = asObject(response, EventView.class);

        assertAfterSave(saveResult);

        response = mvc.perform(MockMvcRequestBuilders
                        .get("/v1/event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse().getContentAsString();
        List<EventView> loadResult  = asObject(response, new TypeReference<List<EventView>>() {});

        assertLoad(loadResult);

        response = mvc.perform(MockMvcRequestBuilders
                        .get("/v1/event/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse().getContentAsString();
        EventDetailedView loadOneResult  = asObject(response, EventDetailedView.class);

        assertLoadOne(loadOneResult);

        ScoreView scoreData = new ScoreView();
        scoreData.setScore1(1);
        scoreData.setScore2(0);
        response = mvc.perform(MockMvcRequestBuilders
                        .patch("/v1/event/1")
                        .content(asJsonString(scoreData))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse().getContentAsString();
        Assertions.assertTrue(StringUtils.hasText(response));
        response = mvc.perform(MockMvcRequestBuilders
                        .get("/v1/event/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse().getContentAsString();
        EventDetailedView updateResult  = asObject(response, EventDetailedView.class);

        assertUpdate(updateResult);

        mvc.perform(MockMvcRequestBuilders
                        .delete("/v1/event/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        response = mvc.perform(MockMvcRequestBuilders
                        .get("/v1/event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse().getContentAsString();
        loadResult  = asObject(response, new TypeReference<List<EventView>>() {});
        Assertions.assertTrue(loadResult.isEmpty());
    }

    private void assertLoad(List<EventView> loadResult) {
        Assertions.assertEquals(1, loadResult.size());
        Assertions.assertEquals(1L, loadResult.get(0).getId());
        Assertions.assertEquals("test team 1", loadResult.get(0).getTeam1());
        Assertions.assertEquals("test team 2", loadResult.get(0).getTeam2());
        Assertions.assertEquals(0, loadResult.get(0).getScore1());
        Assertions.assertEquals(0, loadResult.get(0).getScore2());
    }

    private void assertLoadOne(EventDetailedView loadOneResult) {
        Assertions.assertEquals(1L, loadOneResult.getId());
        Assertions.assertEquals("test team 1", loadOneResult.getTeam1());
        Assertions.assertEquals("test team 2", loadOneResult.getTeam2());
        Assertions.assertEquals(0, loadOneResult.getScore1());
        Assertions.assertEquals(0, loadOneResult.getScore2());
        Assertions.assertTrue(loadOneResult.getScoreHistory().isEmpty());
    }

    private void assertUpdate(EventDetailedView loadOneResult) {
        Assertions.assertEquals(1L, loadOneResult.getId());
        Assertions.assertEquals("test team 1", loadOneResult.getTeam1());
        Assertions.assertEquals("test team 2", loadOneResult.getTeam2());
        Assertions.assertEquals(1, loadOneResult.getScore1());
        Assertions.assertEquals(0, loadOneResult.getScore2());
        Assertions.assertFalse(loadOneResult.getScoreHistory().isEmpty());
        Assertions.assertEquals(1, loadOneResult.getScoreHistory().size());
        ScoreView item = loadOneResult.getScoreHistory().get(0);
        Assertions.assertEquals(1, item.getScore1());
        Assertions.assertEquals(0, item.getScore2());
    }

    private void assertAfterSave(EventView saveResult) {
        Assertions.assertEquals(1L, saveResult.getId());
        Assertions.assertEquals("test team 1", saveResult.getTeam1());
        Assertions.assertEquals("test team 2", saveResult.getTeam2());
        Assertions.assertEquals(0, saveResult.getScore1());
        Assertions.assertEquals(0, saveResult.getScore2());
    }

    private JsonPathResultMatchers getData(String id) {
        return MockMvcResultMatchers.jsonPath("$." + id);
    }
}
