package kb.wgwg.article.domain;

import kb.wgwg.user.domain.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "\"comment\"")
@Getter
@ToString
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
        name = "COMMENT_ENTITY_SEQUENCE_GENERATOR",
        sequenceName = "COMMENT_ENTITY_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class Comment {
    // 댓글 고유 식별 번호
    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMMENT_ENTITY_SEQUENCE_GENERATOR")
    private Long commentSeq;

    // 내용
    @Column(nullable = false)
    private String content;

    // 등록일
    @Column(name = "INSERT_DATE")
    @CreationTimestamp
    private LocalDateTime insertDate;

    // 수정일
    @Column(name = "UPDATE_DATE")
    @UpdateTimestamp
    private LocalDateTime updateDate;

    // 작성자
    @ManyToOne(fetch = FetchType.LAZY) // 지연로딩
    @JoinColumn(name = "writer_seq") // 외래키 컬럼 이름 writer_seq
    private User writer;

    // 게시글
    @ManyToOne(fetch = FetchType.LAZY) // 지연로딩
    @JoinColumn(name = "article_seq") // 외래키 컬럼 이름 article_seq
    private Article parentArticle;

    @Builder
    public Comment(String content, User writer, Article parentArticle) {
        this.content = content;
        this.writer = writer;
        this.parentArticle = parentArticle;
    }
}
