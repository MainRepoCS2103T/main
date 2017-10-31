package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.ExitCommand.MESSAGE_CONFIRMATION;
import static seedu.address.logic.commands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.ui.testutil.EventsCollectorRule;

public class ExitCommandTest {
    private ExitCommand exitCommand;
    private CommandHistory history;

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Before
    public void setUp() {
        Model model = new ModelManager();
        history = new CommandHistory();
        exitCommand = new ExitCommand();
        exitCommand.setData(model, history, new UndoRedoStack());
    }

    @Test
    public void execute_exit_stalled() {
        assertCommandResult(exitCommand, MESSAGE_CONFIRMATION);

        String otherCommand = "clear";
        history.add(otherCommand);
        assertCommandResult(exitCommand, MESSAGE_CONFIRMATION);
    }


    @Test
    public void execute_exit_success() {
        history.add("exit");

        assertCommandResult(exitCommand, MESSAGE_EXIT_ACKNOWLEDGEMENT);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ExitAppRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    /**
     * Asserts that the result message from the execution of {@code historyCommand} equals to {@code expectedMessage}
     */
    private void assertCommandResult(ExitCommand exitCommand, String expectedMessage) {
        assertEquals(expectedMessage, exitCommand.execute().feedbackToUser);
    }
}
