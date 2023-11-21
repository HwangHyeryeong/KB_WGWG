package kb.wgwg.challenge.service;

import kb.wgwg.user.domain.User;
import kb.wgwg.challenge.dto.ChallengeUserDTO.*;
import kb.wgwg.challenge.repository.ChallengeRepository;
import kb.wgwg.challenge.repository.ChallengeUserRepository;
import kb.wgwg.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChallengeUserService {

    private final ChallengeUserRepository challengeUserRep;
    private final ChallengeRepository challengeRep;
    private final UserRepository userRepository;

    public Page<ReadChallengeUserResponseDTO> readChallengeUserByChallengeId(ReadChallengeUserRequestDTO dto, Pageable pageable){
        Page<ReadChallengeUserResponseDTO> page = challengeUserRep.findAllByChallenge(dto.getChallengeId(), pageable);
        System.out.println(page);
        return page;
    }

    public CheckChallengeUserResponseDTO checkChallengeUser(CheckChallengeUserRequestDTO dto){
        Long ownerId = challengeUserRep.findOwnerIdByChallengeId(dto.getChallengeId());
        String challengeType = challengeRep.findChallengeTypeByChallengeId(dto.getChallengeId());
        if(ownerId == dto.getUserSeq()){
            CheckChallengeUserResponseDTO result = CheckChallengeUserResponseDTO.builder()
                    .participantType("개설자")
                    .challengeType(challengeType)
                    .build();
            return result;
        }
        List<Long> list = challengeUserRep.findParticipantIdByChallengeId(dto.getChallengeId());
        for(Long seq : list){
            if(seq == dto.getUserSeq()){
                CheckChallengeUserResponseDTO result = CheckChallengeUserResponseDTO.builder()
                        .participantType("참여자")
                        .challengeType(challengeType)
                        .build();
                return result;
            }
        }
        CheckChallengeUserResponseDTO result = CheckChallengeUserResponseDTO.builder()
                .participantType("비참여자")
                .build();
        return result;
    }

    public challengeUserCntResponse countUser(ReadChallengeUserRequestDTO dto){
        int allParticipantCnt = challengeUserRep.allParticipantCnt(dto.getChallengeId());
        int survivorCnt = challengeUserRep.survivorCnt(dto.getChallengeId());
        challengeUserCntResponse response = challengeUserCntResponse.builder()
                .allParticipantCnt(allParticipantCnt)
                .survivorCnt(survivorCnt)
                .failureCnt(allParticipantCnt - survivorCnt)
                .build();
        return response;
    }

    public ChallengeSuccessRateResponseDTO readSuccessChallengeRate(ChallengeSuccessRateRequestDTO dto) {
        User theUser = userRepository.findById(dto.getUserSeq()).orElseThrow(
                () -> new EntityNotFoundException()
        );

        int allChallengeCount = challengeUserRep.countByParticipantAndChallenge_Status(theUser, "종료");
        int successChallengeCount = challengeUserRep.countByParticipantAndIsSuccessNotAndChallenge_Status(theUser, 0, "종료");
        int failedChallengeCount = allChallengeCount - successChallengeCount;

        return ChallengeSuccessRateResponseDTO.builder()
                                                .allChallengeCount(allChallengeCount)
                                                .successChallengeCount(successChallengeCount)
                                                .failedChallengeCount(failedChallengeCount)
                                                .build();
    }
}
