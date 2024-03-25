package com.excite.taskmanager.integrationTest;

import java.io.FileInputStream;
import java.time.LocalDate;

import javax.sql.DataSource;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.excite.taskmanager.application.resource.gen.org.openapitools.model.TaskPostBody;
import com.excite.taskmanager.application.resource.gen.org.openapitools.model.TaskPutBody;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    public void setUp() throws Exception {

        IDatabaseTester databaseTester = new DataSourceDatabaseTester(dataSource);

        IDataSet dataSet = new FlatXmlDataSetBuilder().build(
                new FileInputStream("src/test/java/com/excite/taskmanager/integrationTest/resource/testData.xml"));
        databaseTester.setDataSet(dataSet);

        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.onSetup();

    }

    @Test
    public void testGetTasks() throws Exception {
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].title").value("Task1"))
                .andExpect(jsonPath("$[1].title").value("Task2"))
                .andExpect(jsonPath("$[2].title").value("Task3"));
    }

    @Test
    public void testGetTaskByID_Success() throws Exception {
        mockMvc.perform(get("/tasks/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value("3"));
    }

    @Test
    public void testGetTaskByID_TaskNotExistException() throws Exception {
        mockMvc.perform(get("/tasks/617"))
                .andExpect(status().isNotFound());
    }

    @Test
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    public void testCreateTask_Success() throws Exception {
        TaskPostBody task = new TaskPostBody();
        task.setTitle("結合テスト");
        task.setDescription("説明");
        task.setDeadline(LocalDate.of(2025, 12, 31));

        String taskJson = objectMapper.writeValueAsString(task);

        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(taskJson))
                .andExpect(status().isOk());
    }

    @Test
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    public void testCreateTask_ValidationException() throws Exception {
        TaskPostBody task = new TaskPostBody();
        task.setDescription("説明");
        task.setDeadline(LocalDate.of(2025, 12, 31));

        String taskJson = objectMapper.writeValueAsString(task);

        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(taskJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    public void testUpdateTask_Success() throws Exception {
        TaskPutBody task = new TaskPutBody();
        int id = 1;
        task.setId(id);
        task.setTitle("update");
        task.setDescription("説明");
        task.setDeadline(LocalDate.of(2025, 12, 31));
        task.setStatus(TaskPutBody.StatusEnum.NUMBER_1);

        String taskJson = objectMapper.writeValueAsString(task);

        mockMvc.perform(put("/tasks/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(taskJson))
                .andExpect(status().isOk());
    }

    @Test
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    public void testUpdateTask_ValidationException() throws Exception {
        TaskPutBody task = new TaskPutBody();
        int id = 1;
        task.setId(id);
        task.setTitle("");
        task.setDescription("説明");
        task.setDeadline(LocalDate.of(2025, 12, 31));
        task.setStatus(TaskPutBody.StatusEnum.NUMBER_1);

        String taskJson = objectMapper.writeValueAsString(task);

        mockMvc.perform(put("/tasks/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(taskJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    public void testUpdateTask_TaskNotExistException() throws Exception {
        TaskPutBody task = new TaskPutBody();
        int id = 10;
        task.setId(id);
        task.setTitle("どれおん");
        task.setDescription("説明");
        task.setDeadline(LocalDate.of(2025, 12, 31));
        task.setStatus(TaskPutBody.StatusEnum.NUMBER_1);

        String taskJson = objectMapper.writeValueAsString(task);

        mockMvc.perform(put("/tasks/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(taskJson))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteTask_Success() throws Exception {
        mockMvc.perform(delete("/tasks/3"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteTask_TaskNotExistException() throws Exception {
        mockMvc.perform(delete("/tasks/48"))
                .andExpect(status().isNotFound());
    }

}
