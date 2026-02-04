package ru.netology.inheritance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TasksTest {

    @Test
    public void shouldThereAreSeveralTasks() {
        Todos todos = new Todos();
        SimpleTask newtask = new SimpleTask(1, "Звонок клиенту");
        Epic newepic = new Epic(2, new String[]{
            "Звонок коллеге", "Закрыть задачу"
        });

        Meeting newmeeting = new Meeting(
          4,
          "Звонок руководителю",
          "BB Банк",
          "В пятницу"
        );

        todos.add(newtask);
        todos.add(newepic);
        todos.add(newmeeting);

        Task[] expected = {newtask, newepic, newmeeting};
        Task[] actual = todos.search("Звонок");

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldFindOneTask() {
        Todos todos = new Todos();
        SimpleTask task = new SimpleTask(1, "Позвонить");
        todos.add(task);
        todos.add(new Epic(2, new String[]{
                "Молоко"
        }));

        Task[] expected = {task};
        Task[] actual = todos.search("Позвонить");

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldNotFoundTask() {
        Todos todos = new Todos();
        SimpleTask task = new SimpleTask(1, "Позвонить");
        todos.add(task);
        todos.add(new Epic(2, new String[]{
                "Молоко"
        }));

        Task[] expected = new Task[0];
        Task[] actual = todos.search("В пятницу");

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldMatchSimpleTaskWhenQueryInTitle() {
        SimpleTask task = new SimpleTask(1, "Позвонить родителям");

        boolean expected = true;
        boolean actual = task.matches("Позвонить");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldNotMatchSimpleTaskWhenQueryNotInTitle() {
        SimpleTask task = new SimpleTask(1, "Позвонить родителям");

        boolean expected = false;
        boolean actual = task.matches("Написать");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldMatchEpicWhenQueryInSubtasks() {
        String[] subtasks = {"Молоко", "Яйца", "Хлеб"};
        Epic epic = new Epic(2, subtasks);

        boolean expected = true;
        boolean actual = epic.matches("Яйца");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldNotMatchEpicWhenQueryNotInSubtasks() {
        String[] subtasks = {"Молоко", "Яйца", "Хлеб"};
        Epic epic = new Epic(2, subtasks);

        boolean expected = false;
        boolean actual = epic.matches("Сыр");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldMatchMeetingByTopic() {
        Meeting meeting = new Meeting(
                3,
                "Выкатка приложения",
                "НетоБанк",
                "Во вторник"
        );

        boolean expected = true;
        boolean actual = meeting.matches("Выкатка");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldMatchMeetingNotFound() {
        Meeting meeting = new Meeting(
                3,
                "Выкатка приложения",
                "НетоБанк",
                "Во вторник"
        );

        boolean expected = false;
        boolean actual = meeting.matches("В пятницу");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldMatchMeetingByProject() {
        Meeting meeting = new Meeting(
                3,
                "Выкатка приложения",
                "НетоБанк",
                "Во вторник"
        );

        boolean expected = true;
        boolean actual = meeting.matches("НетоБанк");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldAddThreeTasksOfDifferentType() {
        SimpleTask simpleTask = new SimpleTask(5, "Позвонить родителям");

        String[] subtasks = {"Молоко", "Яйца", "Хлеб"};
        Epic epic = new Epic(55, subtasks);

        Meeting meeting = new Meeting(
                555,
                "Выкатка 3й версии приложения",
                "Приложение НетоБанка",
                "Во вторник после обеда"
        );

        Todos todos = new Todos();

        todos.add(simpleTask);
        todos.add(epic);
        todos.add(meeting);

        Task[] expected = {simpleTask, epic, meeting};
        Task[] actual = todos.findAll();

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldCallBaseTaskMatches() {
        Task task = new Task(10);

        boolean expected = false;
        boolean actual = task.matches("любое");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldGetIdFromBaseTask() {
        Task task = new Task(10);
        int expected = 10;
        int actual = task.getId();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnTitleSimpleTask() {
        SimpleTask simpleTask = new SimpleTask(1, "Позвонить родителям");
        String expected = "Позвонить родителям";
        String actual = simpleTask.getTitle();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnSubtasksEpic() {
        String[] subtasks = {"Молоко", "Яйца", "Хлеб"};
        Epic epic = new Epic(2, subtasks);
        String[] actual = epic.getSubtasks();
        Assertions.assertArrayEquals(subtasks, actual);
    }

    @Test
    public void shouldReturnTopicProjectStartFromMeeting() {
        Meeting meeting = new Meeting(3, "Встреча", "Проект X", "Во вторник");

        Assertions.assertEquals("Встреча", meeting.getTopic());
        Assertions.assertEquals("Проект X", meeting.getProject());
        Assertions.assertEquals("Во вторник", meeting.getStart());
    }
}