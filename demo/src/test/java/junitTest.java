

import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.entity.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class junitTest {
    private Task task;

    @BeforeEach
    public void setUp() {
        task = new Task("Write JUnit tests", false, false, 1234L);
    }

    @Test
    public void testTaskCreation() {
        assertNotNull(task);
        assertEquals("Write JUnit tests", task.getTask());
        assertFalse(task.isCompleted());
        assertFalse(task.isDeleted());
    }

    @Test
    public void testSetTask() {
        task.setTask("Update JUnit tests");
        assertEquals("Update JUnit tests", task.getTask());
    }

    @Test
    public void testSetCompleted() {
        task.setCompleted(true);
        assertTrue(task.isCompleted());
    }

    @Test
    public void testSetDeleted() {
        task.setDeleted(true);
        assertTrue(task.isDeleted());
    }

    @Test
    public void testDateAdded() {
        assertNotNull(task.getDateAdded());
        assertTrue(task.getDateAdded().before(new Date()));
    }
}

