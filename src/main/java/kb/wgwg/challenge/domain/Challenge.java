package kb.wgwg.challenge.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@SequenceGenerator(
        name = "CHALLENGE_ENTITY_SEQUENCE_GENERATOR",
        sequenceName = "CHALLENGE_ENTITY_SEQ",
        initialValue = 1,
        allocationSize = 1
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CHALLENGE_ENTITY_SEQUENCE_GENERATOR")
    private Long challengeId;

    @Column(nullable = false)
    private Long ownerId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Column(nullable = false)
    private String challengeType;//NChallenge인지 CoffeeChallenge인지 구분

    private int reward;

    @OneToMany(mappedBy = "challenge", cascade = CascadeType.REMOVE)
    private List<ChallengeUser> participants = new ArrayList<>();

    public Challenge(String title, String description, String status, LocalDateTime startDate, LocalDateTime endDate, String challengeType) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.challengeType = challengeType;
    }
}
