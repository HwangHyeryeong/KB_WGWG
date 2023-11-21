package kb.wgwg.article.domain;

import kb.wgwg.user.domain.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
        name = "ARTICLE_ENTITY_SEQUENCE_GENERATOR",
        sequenceName = "ARTICLE_ENTITY_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class Article {
    // 게시글번호
    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ARTICLE_ENTITY_SEQUENCE_GENERATOR")
    private Long articleSeq;

    // 제목
    @Column(nullable = false)
    private String title;

    // 내용
    @Column(nullable = false)
    private String content;

    // 카테고리
    private String category;

    // 등록일
    @CreationTimestamp
    @Column(name = "INSERT_DATE")
    private LocalDateTime insertDate;

    // 수정일
    @UpdateTimestamp
    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    // 작성자
    @ManyToOne(fetch = FetchType.LAZY) // 지연로딩
    @JoinColumn(name = "writer_seq") // 외래키 컬럼 이름 writer_seq
    private User writer;

    // 댓글
    @OneToMany(mappedBy = "parentArticle", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Comment> comments = new ArrayList<>();

    @Builder
    public Article(String title, String content, String category, User writer) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.writer = writer;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void updateCategory(String category) {
        this.category = category;
    }

}