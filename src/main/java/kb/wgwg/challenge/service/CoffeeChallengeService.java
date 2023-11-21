package kb.wgwg.challenge.service;

import kb.wgwg.challenge.domain.Challenge;
import kb.wgwg.challenge.domain.ChallengeUser;
import kb.wgwg.challenge.domain.CoffeeChallenge;
import kb.wgwg.challenge.dto.ChallengeDTO.*;
import kb.wgwg.challenge.repository.ChallengeRepository;
import kb.wgwg.user.repository.UserRepository;
import kb.wgwg.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Transactional
public class CoffeeChallengeService {

    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;
    private final EntityManager entityManager;

    private static final int LIMIT_CHALLENGE_USER_SIZE = 30;

    public int updateCoffeeChallenge(CoffeeChallengeUpdateDTO dto) {
        Challenge challenge = challengeRepository.findById(dto.getChallengeId()).orElseThrow(
                () -> new EntityNotFoundException("해당 챌린지를 찾을 수 없습니다.")
        );

        return challengeRepository.updateChallengeByChallengeId(dto.getChallengeId(), dto.getTitle(), dto.getDescription(), dto.getStartDate(), dto.getEndDate(), dto.getSavingAmount());
    }

    public CoffeeChallengeInsertResponseDTO insertCoffeeChallenge(CoffeeChallengeInsertRequestDTO dto) {
        User theUser = userRepository.findById(dto.getOwnerId()).orElseThrow(
                () -> new EntityNotFoundException("해당 사용자를 찾을 수 없습니다.")
        );

        LocalDateTime currentDate = LocalDateTime.now();

        String status;

        // 현재 날짜와 startDate, endDate 비교하여 상태 설정
        if (currentDate.isBefore(dto.getStartDate())) {
            status = "모집중";
        } else if (currentDate.isEqual(dto.getStartDate()) ||
                (currentDate.isAfter(dto.getStartDate()) && currentDate.isBefore(dto.getEndDate()))) {
            status = "진행중";
        } else {
            status = "종료";
        }

        CoffeeChallengeInsertRequestDTO finalDTO = CoffeeChallengeInsertRequestDTO.builder()
                .ownerId(dto.getOwnerId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .status(status)
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .savingAmount(dto.getSavingAmount())
                .challengeType(dto.getChallengeType())
                .build();

        CoffeeChallenge theChallenge = challengeRepository.save(modelMapper.map(finalDTO, CoffeeChallenge.class));

        ChallengeUser theParticipant = ChallengeUser.builder()
                .isSuccess(1)
                .account(dto.getAccount())
                .bankName(dto.getBankName())
                .challengeType(dto.getChallengeType())
                .build();

        theParticipant.addParticipant(theChallenge);
        theParticipant.setParticipant(theUser);
        entityManager.persist(theParticipant);

        return modelMapper.map(theChallenge, CoffeeChallengeInsertResponseDTO.class);
    }

    public void participateCoffeeChallenge(ChallengeParticipateRequestDTO dto) {
        User theUser = userRepository.findById(dto.getUserSeq()).orElseThrow(
                () -> new EntityNotFoundException("해당 사용자를 찾을 수 없습니다.")
        );

        Challenge theChallenge = challengeRepository.findById(dto.getChallengeId()).orElseThrow(
                () -> new EntityNotFoundException("해당 챌린지를 찾을 수 없습니다.")
        );

        theChallenge.getParticipants().stream().forEach(
                participant -> {
                    if (participant.getParticipant().getUserSeq() == theUser.getUserSeq()) {
                        throw new IllegalArgumentException();
                    }
                }
        );

        if (theChallenge.getParticipants().size() == LIMIT_CHALLENGE_USER_SIZE) {
            throw new ArrayIndexOutOfBoundsException();
        }

        ChallengeUser theParticipant = ChallengeUser.builder()
                .isSuccess(1)
                .account(dto.getAccount())
                .bankName(dto.getBankName())
                .challengeType(dto.getChallengeType())
                .build();

        theParticipant.addParticipant(theChallenge);
        theParticipant.setParticipant(theUser);
        entityManager.persist(theParticipant);
    }

    public void deleteCoffeeChallenge(Long id) {
        challengeRepository.deleteByChallengeId(id);
    }


    @Transactional(readOnly = true)
    public Page<CoffeeChallengeListResponseDTO> findNChallengeByStatus(ChallengeListRequestDTO requestDTO, Pageable pageable) {
        Page<Challenge> page;
        if(requestDTO.getStatus().equals("전체보기")) {
            page = challengeRepository.findAllByChallengeType(requestDTO.getChallengeType(), pageable);
        } else {
            page = challengeRepository.findAllByStatusAndChallengeType(requestDTO.getStatus(), requestDTO.getChallengeType(), pageable);
        }

        // Page<Challenge>을 Page<CoffeeChallenge>로 변환
        Page<CoffeeChallenge> coffeeChallengePage = page.map(challenge -> (CoffeeChallenge) challenge);
        Page<CoffeeChallengeListResponseDTO> dtoPage = coffeeChallengePage.map(new Function<CoffeeChallenge, CoffeeChallengeListResponseDTO>() {
            @Override
            public CoffeeChallengeListResponseDTO apply(CoffeeChallenge ch) {
                CoffeeChallengeListResponseDTO dto = CoffeeChallengeListResponseDTO.builder()
                        .challengeId(ch.getChallengeId())
                        .title(ch.getTitle())
                        .status(ch.getStatus())
                        .savingAmount(ch.getSavingAmount())
                        .startDate(ch.getStartDate())
                        .endDate(ch.getEndDate())
                        .build();
                return dto;
            }
        });
        return dtoPage;
    }
    @Transactional(readOnly = true)
    public CoffeeChallengeReadResponseDTO findCoffeeChallengeById(Long id) {
        CoffeeChallenge coffeechallenge = (CoffeeChallenge) challengeRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("해당 챌린지를 찾을 수 없습니다.")
        );

        Map<String, Integer> isSuccessList = new HashMap<>();
        for (ChallengeUser participant : coffeechallenge.getParticipants()) {
            isSuccessList.put(
                    participant.getParticipant().getNickName(),
                    participant.getIsSuccess()
            );
        }
        System.out.println("isSuccessList = " + isSuccessList);
        return CoffeeChallengeReadResponseDTO.builder()
                .title(coffeechallenge.getTitle())
                .description(coffeechallenge.getDescription())
                .startDate(coffeechallenge.getStartDate())
                .endDate(coffeechallenge.getEndDate())
                .challengeType(coffeechallenge.getChallengeType())
                .totalAsset(coffeechallenge.getTotalAsset())
                .savingAmount(coffeechallenge.getSavingAmount())
                .status(coffeechallenge.getStatus())
                .isSuccessList(isSuccessList)
                .reward(coffeechallenge.getReward())
                .build();
    }
}
