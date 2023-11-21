package kb.wgwg.challenge.domain;

import kb.wgwg.user.domain.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
        name = "CHALLENGE_USER_ENTITY_SEQUENCE_GENERATOR",
        sequenceName = "CHALLENGE_USER_ENTITY_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class ChallengeUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CHALLENGE_USER_ENTITY_SEQUENCE_GENERATOR")
    @Column(name = "challenge_user_id")
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User participant;

    @Column(nullable = false)
    private String account;

    @Column(nullable = false)
    private String bankName;

    // Coffee Challenge
    // 탈락 0
    // 입금전 1
    // 입금 완료 2
    // ===============
    // N Challenge
    // 탈락 0
    // 생존 1
    @Column(name = "is_success")
    private int isSuccess;

    @Column(nullable = false)
    private String challengeType;

    public void addParticipant(Challenge theChallenge) {
        this.challenge = theChallenge;
        theChallenge.getParticipants().add(this);
    }

    @Builder
    public ChallengeUser(String account, String bankName, int isSuccess, String challengeType) {
        this.account = account;
        this.bankName = bankName;
        this.isSuccess = isSuccess;
        this.challengeType = challengeType;
    }
}
