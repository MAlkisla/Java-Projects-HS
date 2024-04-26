package engine;

import java.util.List;

public class UserAnswer {
    private List<Integer> answer;

    public UserAnswer() {
    }

    public UserAnswer(List<Integer> answer) {
        this.answer = answer;
    }

    public List<Integer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Integer> answer) {
        this.answer = answer;
    }
}
