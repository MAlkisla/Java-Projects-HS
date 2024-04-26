package engine.quiz_completed;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizCompletedRepo extends CrudRepository<QuizCompleted, Long> {
    @Query("SELECT quizCompleted FROM QuizCompleted quizCompleted WHERE quizCompleted.username = :username")
    Page<QuizCompleted> findAllByUsernamePerPage(@Param("username") String username, Pageable pageable);
}
