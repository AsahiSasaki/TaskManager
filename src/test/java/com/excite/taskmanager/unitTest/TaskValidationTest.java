package com.excite.taskmanager.unitTest;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.excite.taskmanager.domain.service.TaskValidation;

@SpringBootTest
public class TaskValidationTest {
    @InjectMocks
    private TaskValidation taskValidation;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

}
