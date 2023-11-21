package kb.wgwg.banking.service;

import kb.wgwg.challenge.dto.ChallengeDTO.*;
import kb.wgwg.challenge.repository.ChallengeRepository;
import kb.wgwg.challenge.repository.ChallengeUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {
    private final ChallengeUserRepository challengeUserRepository;
    private final ChallengeRepository challengeRepository;

    public void updateIsSuccess(UpdateIsSuccessRequestDTO dto){
        challengeUserRepository.updateIsSuccess(dto.getUserSeq(), dto.getChallengeId());
    }

    public void updateTotalAsset(UpdateAmountRequestDTO dto){
        challengeRepository.updateTotalAsset(dto.getAmount(), dto.getChallengeId());
    }

    public void updateTotalDeposit(UpdateAmountRequestDTO dto){
        challengeRepository.updateTotalDeposit(dto.getAmount(), dto.getChallengeId());
    }
}
