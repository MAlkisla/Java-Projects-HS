package engine.quiz_completed;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Generated;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
public class QuizCompleted {

    @Id
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quizCompleted_generator")
//    @SequenceGenerator(name="quizCompleted_generator", sequenceName = "quizCompleted_seq", allocationSize=50)
//    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    @JsonProperty("id")
    private Long quizId;
    private LocalDateTime completedAt;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String username;
}
