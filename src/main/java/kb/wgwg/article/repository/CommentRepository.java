package kb.wgwg.article.repository;

import kb.wgwg.article.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Modifying
    int deleteByCommentSeq(Long commentSeq);

    @Modifying
    @Query("UPDATE Comment c set c.content = :content where c.commentSeq = :commentSeq")
    int updateCommentByCommentSeq(Long commentSeq, String content);

    @Query(value = "SELECT COMMENT_SEQ, WRITER_SEQ, u.NICK_NAME AS NICK_NAME, CONTENT, UPDATE_DATE AS \"date\" " +
            "FROM \"comment\" c " +
            "join USER_ENTITY u " +
            "on c.writer_seq = u.user_seq " +
            "WHERE ARTICLE_SEQ = :articleSeq " +
            "order by \"date\" desc"
            , nativeQuery = true)
    List<Object[]> findCommentsByArticleSeq(@Param("articleSeq") Long articleSeq);

}
