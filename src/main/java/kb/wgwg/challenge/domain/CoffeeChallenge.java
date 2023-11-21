package kb.wgwg.challenge.domain;

import lombok.*;
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
@AttributeOverride(
        name = "challengeId",
        column = @Column(name = "COFFEE_CHALLENGE_ID")
)
@DiscriminatorValue(value = "COFFEE")
public class CoffeeChallenge extends Challenge {

    private int totalAsset;//모든 참가자의 저축 금액

    private int savingAmount;//하루 저축할 금액

    @Builder
    public CoffeeChallenge(String title, String description, String status,
                           LocalDateTime startDate, LocalDateTime endDate,
                           int savingAmount, int totalAsset, String challengeType)
    {
        super(title, description, status, startDate, endDate, challengeType);

        this.savingAmount = savingAmount;
        this.totalAsset = totalAsset;
    }
}
