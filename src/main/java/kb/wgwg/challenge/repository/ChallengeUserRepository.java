package kb.wgwg.challenge.repository;

import kb.wgwg.challenge.domain.ChallengeUser;
import kb.wgwg.user.domain.User;
import kb.wgwg.challenge.dto.ChallengeUserDTO.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChallengeUserRepository extends JpaRepository<ChallengeUser, Long> {
    @Modifying
    @Query(value = "UPDATE CHALLENGE_USER cu " +
            "SET IS_SUCCESS = 0 " +
            "WHERE EXISTS ( " +
            "SELECT 1 " +
            " FROM (SELECT c.CHALLENGE_ID, n.LIMIT_AMOUNT FROM NCHALLENGE n JOIN CHALLENGE c ON n.CHALLENGE_ID = c.CHALLENGE_ID) ci " +
            " JOIN (SELECT b.USER_SEQ, cu.CHALLENGE_ID, sum(b.AMOUNT) AS spending FROM BANKING b JOIN CHALLENGE_USER cu ON b.USER_SEQ = cu.USER_ID WHERE cu.CHALLENGE_TYPE = 'N' AND b.CATEGORY NOT LIKE '%챌린지%' GROUP BY USER_SEQ, cu.CHALLENGE_ID) ub " +
            " ON ci.CHALLENGE_ID = ub.CHALLENGE_ID " +
            " WHERE ci.LIMIT_AMOUNT < ub.spending " +
            " AND cu.CHALLENGE_ID = ci.CHALLENGE_ID " +
            " AND cu.USER_ID = ub.USER_SEQ)", nativeQuery = true)
    void updateChallengeUserStateOfSuccess();

    @Modifying
    @Query(value = "UPDATE CHALLENGE_USER SET IS_SUCCESS = 2 WHERE USER_ID = ?1 AND CHALLENGE_ID = ?2", nativeQuery = true)
    void updateIsSuccess(Long userSeq, Long challengeId);

    @Modifying
    @Query(value = "UPDATE CHALLENGE_USER SET IS_SUCCESS = 0 WHERE IS_SUCCESS = 1 AND CHALLENGE_TYPE = 'COFFEE' AND  CHALLENGE_ID IN (SELECT c.CHALLENGE_ID FROM CHALLENGE c WHERE c.STATUS LIKE '%진행%')", nativeQuery = true)
    void updateChallengeUserStateOfSuccessToFail();

    @Modifying
    @Query(value = "UPDATE CHALLENGE_USER SET IS_SUCCESS = 1 WHERE IS_SUCCESS = 2 AND CHALLENGE_TYPE = 'COFFEE' AND  CHALLENGE_ID IN (SELECT c.CHALLENGE_ID FROM CHALLENGE c WHERE c.STATUS LIKE '%진행%')", nativeQuery = true)
    void updateChallengeUserStateOfSuccessToNotyet();

    @Query(value = "SELECT USER_ID FROM CHALLENGE_USER cu WHERE IS_SUCCESS = 2 AND CHALLENGE_ID = ?1", nativeQuery = true)
    List<Long> findCoffeeChallengeUsersByChallengeId(Long challengeId);

    @Query(value = "SELECT USER_ID FROM CHALLENGE_USER cu WHERE IS_SUCCESS = 1 AND CHALLENGE_ID = ?1", nativeQuery = true)
    List<Long> findNChallengeUsersByChallengeId(Long challengeId);

    @Query(value = "SELECT CU.IS_SUCCESS as isSuccess, U.NICK_NAME as nickName FROM CHALLENGE_USER CU INNER JOIN USER_ENTITY U ON CU.USER_ID = U.USER_SEQ WHERE CU.CHALLENGE_ID = ?1", nativeQuery = true)
    Page<ReadChallengeUserResponseDTO> findAllByChallenge(Long challengeId, Pageable pageable);

    @Query(value = "select ownerId from Challenge where challengeId = ?1")
    Long findOwnerIdByChallengeId(Long challengeId);

    @Query(value = "select user_id from challenge_user where challenge_id = ?1", nativeQuery = true)
    List<Long> findParticipantIdByChallengeId(Long challengeId);

    @Query(value = "select count(*) from challenge_user where challenge_id = ?1", nativeQuery = true)
    Integer allParticipantCnt(Long challengeId);

    @Query(value = "select count(is_success) from challenge_user where is_success in (1, 2) and challenge_id = ?1", nativeQuery = true)
    Integer survivorCnt(Long challengeId);

    // 참여했었던 모든 챌린지 불러오기
    int countByParticipantAndChallenge_Status(User theUser, String status);

    int countByParticipantAndIsSuccessNotAndChallenge_Status(User theParticipant, int isSuccess, String status);
}
