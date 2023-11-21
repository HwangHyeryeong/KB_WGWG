package kb.wgwg.challenge.service;

import kb.wgwg.banking.dto.BankingDTO.BankingInsertRequestDTO;
import kb.wgwg.banking.service.BankingService;
import kb.wgwg.challenge.repository.ChallengeRepository;
import kb.wgwg.challenge.repository.ChallengeUserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional
public class ChallengeSchedulerService {
    private final ModelMapper modelMapper;
    private final ChallengeRepository challengeRepository;
    private final ChallengeUserRepository challengeUserRepository;
    private final BankingService bankingService;

    /**
     * 챌린지 진행 상태 업데이트
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void updateChallengeStatus(){
        //진행 -> 종료
        challengeRepository.updateChallengeStateToFinish();

        //모집 -> 진행
        challengeRepository.updateChallengeStateToOngoing();
    }

    /**
     * 챌린지 참여자의 진행 상태 업데이트
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void updateChallengeUserStatusOfSuccess(){
        //N챌린지
        challengeUserRepository.updateChallengeUserStateOfSuccess();
        
        //coffee 챌린지
        challengeUserRepository.updateChallengeUserStateOfSuccessToFail();
        challengeUserRepository.updateChallengeUserStateOfSuccessToNotyet();
    }

    /**
     * N챌린지 리워드 값 업데이트
     */
    @Scheduled(cron = "1 0 0 * * *")
    public void updateNReward(){
        List<Object[]> ongoingChallenge = challengeRepository.findOngoingNChallenge();

        for(Object[] challenge : ongoingChallenge){
            Long challengeID = ((BigDecimal) challenge[0]).longValue();
            int numOfSuccess = challengeUserRepository.findNChallengeUsersByChallengeId(challengeID).size();
            int reward = ((BigDecimal) challenge[1]).intValue() / numOfSuccess;

            challengeRepository.updateRewardByChallengeId(reward, challengeID);
        }
    }

    /**
     * Coffee챌린지 리워드 값 업데이트
     */
    @Scheduled(cron = "1 0 0 * * *")
    public void updateCoffeeReward(){
        List<Object[]> ongoingChallenge = challengeRepository.findOngoingCoffeeChallenge();

        for(Object[] challenge : ongoingChallenge){
            Long challengeID = ((BigDecimal) challenge[0]).longValue();
            int numOfSuccess = challengeUserRepository.findNChallengeUsersByChallengeId(challengeID).size();
            int reward = ((BigDecimal) challenge[1]).intValue() / numOfSuccess;

            challengeRepository.updateRewardByChallengeId(reward, challengeID);
        }
    }

    /**
     * 커피 챌린지 종료시, 챌린지 성공한 사용자에게 리워드 지급
     */
    @Scheduled(cron = "2 0 0 * * *")
    public void provideRewardToCoffeeChallengeSuccessUser(){
        LocalDateTime updateDay = LocalDateTime.now();
        System.out.println(updateDay);

        List<Object[]> coffeeChallenges = challengeRepository.findFinishedCoffeeChallenge(updateDay);

        for(Object[] o : coffeeChallenges){
             List<Long> successUser = challengeUserRepository.findCoffeeChallengeUsersByChallengeId(((BigDecimal) o[0]).longValue());

            insertRewardToBankingHistory(o, successUser);
        }
    }

    /**
     * N 챌린지 종료시, 챌린지 성공한 사용자에게 리워드 지급
     */
    @Scheduled(cron = "2 0 0 * * *")
    public void provideRewardToNChallengeSuccessUser(){
        LocalDateTime updateDay = LocalDateTime.now();
        System.out.println(updateDay);

        List<Object[]> NChallenges = challengeRepository.findFinishedNChallenge(updateDay);

        for(Object[] o : NChallenges){
            List<Long> successUser = challengeUserRepository.findNChallengeUsersByChallengeId(((BigDecimal) o[0]).longValue());

            insertRewardToBankingHistory(o, successUser);
        }
    }

    private void insertRewardToBankingHistory(Object[] o, List<Long> successUser) {
        if(successUser.size() != 0){
            int reward = ((BigDecimal) o[1]).intValue() / successUser.size();

            for(Long user : successUser) {
                BankingInsertRequestDTO dto = new BankingInsertRequestDTO();
                dto.setUserSeq(user);
                dto.setType("입금");
                dto.setAmount(reward);
                dto.setBankingDate(String.valueOf(LocalDateTime.now()));
                dto.setCategory("챌린지");
                dto.setChallengeId(((BigDecimal) o[0]).longValue());
                dto.setContent("챌린지 리워드 입금");

                bankingService.insertBankingHistory(dto);
            }
        }
    }
}
