package kb.wgwg.domain;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(
        name = "challengeId",
        column = @Column(name = "N_CHALLENGE_ID")
)
@DiscriminatorValue(value = "N")
public class NChallenge extends Challenge {

    private int deposit;

    private int limitAmount;

    private int totalDeposit;

    @Builder
    public NChallenge(String title, String description, String status,
                           LocalDateTime startDate, LocalDateTime endDate, int limitAmount, int deposit)
    {
        super(title, description, status, startDate, endDate);

        this.limitAmount = limitAmount;
        this.deposit = deposit;
    }
}
