package kb.wgwg.user.domain;

import kb.wgwg.article.domain.Article;
import kb.wgwg.article.domain.Comment;
import kb.wgwg.banking.domain.Banking;
import kb.wgwg.challenge.domain.ChallengeUser;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_entity")
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
        name = "USER_ENTITY_SEQUENCE_GENERATOR",
        sequenceName = "USER_ENTITY_SEQ",
        initialValue = 1,
        allocationSize = 1
        )
public class User {

    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_ENTITY_SEQUENCE_GENERATOR")
    private Long userSeq;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String nickName;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Article> articles = new ArrayList<>();

    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Banking> bankingHistory = new ArrayList<>();

    @OneToMany(mappedBy = "participant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChallengeUser> participants = new ArrayList<>();

    @Builder
    public User(String userName, String email, String nickName, String password) {
        this.userName = userName;
        this.email = email;
        this.nickName = nickName;
        this.password = password;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updateNickName(String nickName) {
        this.nickName = nickName;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateUserName(String userName) {
        this.userName = this.userName;
    }

}
