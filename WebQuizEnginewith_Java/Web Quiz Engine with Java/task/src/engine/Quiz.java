package engine;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import engine.user.AppUser;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    @JsonProperty(required = true)
    @NotBlank
    private String title;
    @JsonProperty(required = true)
    @NotBlank
    private String text;
    @JsonProperty(required = true)
    @NotNull
    @Size(min = 2)
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> options;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Fetch(value = FetchMode.SUBSELECT)
    @ElementCollection(targetClass=Integer.class)
    private List<Integer> answer = new ArrayList<>();

//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    @Fetch(value = FetchMode.SUBSELECT)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String username;
    public final static Quiz quizQuestion = new Quiz(100L, "The Java Logo",
            "What is depicted on the Java logo?",
            Arrays.asList("Robot","Tea leaf","Cup of coffee","Bug"));

    public Quiz() {
    }

    public Quiz(Long id, String title, String text, List<String> options) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.options = options;
    }

    public Quiz(Long id, String title, String text, List<String> options, List<Integer> answer) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Integer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Integer> answer) {
        this.answer = answer;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
