package layer8project;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class MultipleChoiceQuestionTest {
    @Test
    void testValidateAnswer() {
        ArrayList<String> options = new ArrayList<>();
        options.add("Firewall");
        options.add("Router");
        options.add("Switch");
        options.add("Server");

        MultipleChoiceQuestion question =
                new MultipleChoiceQuestion("Q001", "Which device filters network traffic?", options, 0);
        assertTrue(question.validateAnswer(0));
        assertFalse(question.validateAnswer(1));
    }

    @Test
    void testChangeCorrectAnswer() {

        ArrayList<String> options = new ArrayList<>();
        options.add("TCP");
        options.add("UDP");
        options.add("HTTP");
        options.add("FTP");

        MultipleChoiceQuestion question = new MultipleChoiceQuestion("Q002", "Which protocol should be the correct answer?", options, 0);
        question.changeCorrectAnswer(1);
        assertEquals(1, question.getCorrectAnswerIndex());
        assertTrue(question.validateAnswer(1));
        assertFalse(question.validateAnswer(0));
    }

}
