package maburhan.newsfeedapp.repositories;

import maburhan.newsfeedapp.model.NewsArticle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<NewsArticle, Long> {
}
