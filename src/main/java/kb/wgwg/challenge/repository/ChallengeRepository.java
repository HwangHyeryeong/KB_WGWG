package kb.wgwg.challenge.repository;

import kb.wgwg.challenge.domain.Challenge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;
import java.util.List;


public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

    Page<Challenge> findAllByStatusAndChallengeType(String status, String challengeType,Pageable pageable);
    Page<Challenge> findAllByChallengeType(String challengeType,Pageable pageable);

    @Modifying
    @Query("UPDATE NChallenge " +
            "SET title=:title, description=:description, startDate=:startDate, endDate=:startDate + 7, limitAmount=:limitAmount " +
            "WHERE challengeId = :challengeId")
    int updateChallengeByChallengeId(Long challengeId, String title, String description, LocalDateTime startDate, int limitAmount);

    @Modifying
    @Query("UPDATE CoffeeChallenge " +
            "SET title=:title, description=:description, startDate=:startDate, endDate=:endDate, savingAmount=:savingAmount " +
            "WHERE challengeId = :challengeId")
    int updateChallengeByChallengeId(Long challengeId, String title, String description, LocalDateTime startDate, LocalDateTime endDate, int savingAmount);

    @Modifying
    void deleteByChallengeId(Long challengeId);

    @Modifying
    @Query(value = "UPDATE Challenge c SET status = '종료' WHERE TRUNC(END_DATE) = TRUNC(sysdate)", nativeQuery = true)
    void updateChallengeStateToFinish();

    @Modifying
    @Query(value = "UPDATE Challenge c SET status = '진행중' WHERE TRUNC(START_DATE) = TRUNC(sysdate)", nativeQuery = true)
    void updateChallengeStateToOngoing();

    @Modifying
    @Query(value = "UPDATE COFFEE_CHALLENGE SET TOTAL_ASSET = TOTAL_ASSET + ?1 WHERE CHALLENGE_ID = ?2", nativeQuery = true)
    void updateTotalAsset(int amount, Long challengeId);

    @Modifying
    @Query(value = "UPDATE NCHALLENGE SET TOTAL_DEPOSIT = TOTAL_DEPOSIT + ?1 WHERE CHALLENGE_ID = ?2", nativeQuery = true)
    void updateTotalDeposit(int amount, Long challengeId);

    @Modifying
    @Query(value = "UPDATE CHALLENGE SET REWARD = ?1 WHERE CHALLENGE_ID = ?2", nativeQuery = true)
    void updateRewardByChallengeId(int updatedReward, Long challengeId);

    @Query(value = "SELECT c.CHALLENGE_ID as CHALLENGE_ID, TOTAL_ASSET FROM COFFEE_CHALLENGE cc JOIN CHALLENGE c ON cc.CHALLENGE_ID = c.CHALLENGE_ID WHERE TRUNC(c.END_DATE) = TRUNC(?1)", nativeQuery = true)
    List<Object[]> findFinishedCoffeeChallenge(LocalDateTime endDate);

    @Query(value = "SELECT c.CHALLENGE_ID as CHALLENGE_ID, TOTAL_ASSET FROM COFFEE_CHALLENGE cc JOIN CHALLENGE c ON cc.CHALLENGE_ID = c.CHALLENGE_ID WHERE c.STATUS = \'진행중\'", nativeQuery = true)
    List<Object[]> findOngoingCoffeeChallenge();

    @Query(value = "SELECT c.CHALLENGE_ID as CHALLENGE_ID, TOTAL_DEPOSIT FROM NCHALLENGE cc JOIN CHALLENGE c ON cc.CHALLENGE_ID = c.CHALLENGE_ID WHERE TRUNC(c.END_DATE) = TRUNC(?1)", nativeQuery = true)
    List<Object[]> findFinishedNChallenge(LocalDateTime endDate);

    @Query(value = "SELECT c.CHALLENGE_ID as CHALLENGE_ID, TOTAL_DEPOSIT FROM NCHALLENGE cc JOIN CHALLENGE c ON cc.CHALLENGE_ID = c.CHALLENGE_ID WHERE c.STATUS = \'진행중\'", nativeQuery = true)
    List<Object[]> findOngoingNChallenge();

    @Query(value = "select challenge_type from challenge where challenge_id = ?1", nativeQuery = true)
    String findChallengeTypeByChallengeId(Long challengeId);
}
