package kb.wgwg.article.repository;

import kb.wgwg.article.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Modifying
    int deleteByArticleSeq(Long articleSeq);

    Page<Article> findAllByCategory(String category, Pageable pageable);

    @Query(value = "select * from article a where a.writer_seq = :userSeq order by a.insert_date", nativeQuery = true)
    Page<Article> findAllByUserSeq(Long userSeq,Pageable pageable);
}
