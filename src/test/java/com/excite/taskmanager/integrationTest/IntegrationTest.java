package com.excite.taskmanager.integrationTest;

import java.io.FileInputStream;
import java.time.LocalDate;

import javax.sql.DataSource;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.dbunit.Assertion;

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

    IDatabaseConnection connection;


    @BeforeEach
    public void setUp() throws Exception {

        IDatabaseTester databaseTester = new DataSourceDatabaseTester(dataSource);

        IDataSet dataSet = new FlatXmlDataSetBuilder().build(
                new FileInputStream("src/test/java/com/excite/taskmanager/integrationTest/resource/testData.xml"));
        databaseTester.setDataSet(dataSet);

        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.onSetup();
        connection = databaseTester.getConnection();

    }

    @AfterEach
    public void tearDown() throws Exception {
        connection.close();
    }

    @Nested
    class TestGetTasks {
        @Test
        public void testGetTasks() throws Exception {
            mockMvc.perform(get("/tasks"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(3)))
                    .andExpect(jsonPath("$[0].title").value("Task1"))
                    .andExpect(jsonPath("$[1].title").value("Task2"))
                    .andExpect(jsonPath("$[2].title").value("Task3"));
        }
    }

    @Nested
    class TestGetTaskById {
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
    }

    @Nested
    class TestCreateTask {
        @Test
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    public void testCreateTask_Success() throws Exception {
        TaskPostBody task = new TaskPostBody();
        task.setTitle("Task4");
        task.setDescription("説明４");
        task.setDeadline(LocalDate.of(2025, 12, 31));

        String taskJson = objectMapper.writeValueAsString(task);

        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(taskJson))
                .andExpect(status().isOk());

        IDataSet actual = connection.createDataSet();
        ITable actualTaskTable = actual.getTable("task");
        ITable filteredActualTable = DefaultColumnFilter.excludedColumnsTable(actualTaskTable,
                                                                         new String[]{"id"});
        IDataSet expected = new FlatXmlDataSetBuilder().build(
                new FileInputStream("src/test/java/com/excite/taskmanager/integrationTest/resource/expectedDatas/createTaskSuccessData.xml"));
        ITable expectedTaskTable = expected.getTable("task");
        ITable filteredExpectedTable = DefaultColumnFilter.excludedColumnsTable(expectedTaskTable,
                                                                         new String[]{"id"});
       
        Assertion.assertEquals(filteredExpectedTable, filteredActualTable);
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
            
            IDataSet actual = connection.createDataSet();
            ITable actualTaskTable = actual.getTable("task");

            IDataSet expected = new FlatXmlDataSetBuilder().build(
                new FileInputStream("src/test/java/com/excite/taskmanager/integrationTest/resource/expectedDatas/createTaskFailedData.xml"));
            ITable expectedTaskTable = expected.getTable("task");
            Assertion.assertEquals(expectedTaskTable, actualTaskTable);
        }
    }

    @Nested
    class TestUpdateTask {
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

            IDataSet actual = connection.createDataSet();
            ITable actualTaskTable = actual.getTable("task");

            IDataSet expected = new FlatXmlDataSetBuilder().build(
                new FileInputStream("src/test/java/com/excite/taskmanager/integrationTest/resource/expectedDatas/updateTaskSuccessData.xml"));
            ITable expectedTaskTable = expected.getTable("task");
            Assertion.assertEquals(expectedTaskTable, actualTaskTable);
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

            IDataSet actual = connection.createDataSet();
            ITable actualTaskTable = actual.getTable("task");

            IDataSet expected = new FlatXmlDataSetBuilder().build(
                new FileInputStream("src/test/java/com/excite/taskmanager/integrationTest/resource/expectedDatas/updateTaskFailedData.xml"));
            ITable expectedTaskTable = expected.getTable("task");
            Assertion.assertEquals(expectedTaskTable, actualTaskTable);
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
            
            IDataSet actual = connection.createDataSet();
            ITable actualTaskTable = actual.getTable("task");

            IDataSet expected = new FlatXmlDataSetBuilder().build(
                new FileInputStream("src/test/java/com/excite/taskmanager/integrationTest/resource/expectedDatas/updateTaskFailedData.xml"));
            ITable expectedTaskTable = expected.getTable("task");
            Assertion.assertEquals(expectedTaskTable, actualTaskTable);
        }
    }

    @Nested
    class TestDeleteTask {
        @Test
        public void testDeleteTask_Success() throws Exception {
            mockMvc.perform(delete("/tasks/3"))
                    .andExpect(status().isOk());

            IDataSet actual = connection.createDataSet();
            ITable actualTaskTable = actual.getTable("task");

            IDataSet expected = new FlatXmlDataSetBuilder().build(
                new FileInputStream("src/test/java/com/excite/taskmanager/integrationTest/resource/expectedDatas/deleteTaskSuccessData.xml"));
            ITable expectedTaskTable = expected.getTable("task");
            Assertion.assertEquals(expectedTaskTable, actualTaskTable);
        }

        @Test
        public void testDeleteTask_TaskNotExistException() throws Exception {
            mockMvc.perform(delete("/tasks/48"))
                    .andExpect(status().isNotFound());

            IDataSet actual = connection.createDataSet();
            ITable actualTaskTable = actual.getTable("task");

            IDataSet expected = new FlatXmlDataSetBuilder().build(
                new FileInputStream("src/test/java/com/excite/taskmanager/integrationTest/resource/expectedDatas/deleteTaskFailedData.xml"));
            ITable expectedTaskTable = expected.getTable("task");
            Assertion.assertEquals(expectedTaskTable, actualTaskTable);
        }
    }
}
