package kb.wgwg.banking.domain;

import kb.wgwg.user.domain.User;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
        name = "BANKING_ENTITY_SEQUENCE_GENERATOR",
        sequenceName = "BANKING_ENTITY_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class Banking {

    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BANKING_ENTITY_SEQUENCE_GENERATOR")
    private Long bankingId;


    @ManyToOne(fetch = FetchType.LAZY) // 지연로딩
    @JoinColumn(name = "user_seq")
    private User owner;

    @ColumnDefault("0")
    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String content;

    @Column(name = "BANKING_DATE")
    private LocalDateTime bankingDate;

    @JoinColumn(name = "challenge_id")
    private Long challengeId;

    @Column(nullable = false)
    private String category;

    @Builder
    public Banking(User owner, int amount, String type, LocalDateTime bankingDate, String category, String content, Long challengeId) {
        this.owner = owner;
        this.amount = amount;
        this.type = type;
        this.bankingDate = bankingDate;
        this.category = category;
        this.content = content;
        this.challengeId = challengeId;
    }

    public void updateAmount(int amount){ this.amount = amount; }
    public void updateType(String type){ this.type = type; }
    public void updateBankingDate(LocalDateTime bankingDate){ this.bankingDate = bankingDate; }
    public void updateCategory(String category){ this.category = category; }
    public void updateContent(String content) { this.content = content;
    }
}
