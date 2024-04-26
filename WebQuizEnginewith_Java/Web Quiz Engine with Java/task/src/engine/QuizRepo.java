package engine;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepo extends JpaRepository<Quiz, Long>, PagingAndSortingRepository<Quiz, Long> {

    @Query("SELECT q FROM Quiz q")
    Page<Quiz> findAllPerPage(Pageable pageable);
}
