package kb.wgwg.challenge.dto;

import lombok.*;

import java.time.LocalDateTime;

public class ChallengeUserDTO {

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    public static final class ReadChallengeUserRequestDTO{
        private Long challengeId;
    }

    public interface ReadChallengeUserResponseDTO{
        Integer getIsSuccess();
        String getNickName();
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    public static final class CheckChallengeUserRequestDTO{
        private Long challengeId;
        private Long userSeq;
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    public static final class CheckChallengeUserResponseDTO{
        private String participantType;
        private String challengeType;
    }


    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    public static final class challengeUserCntResponse{
        private int allParticipantCnt;
        private int survivorCnt;
        private int failureCnt;
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class ChallengeSuccessRateRequestDTO {
        private Long userSeq;

        @Builder
        public ChallengeSuccessRateRequestDTO(Long userSeq) {
            this.userSeq = userSeq;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class ChallengeSuccessRateResponseDTO {
        private int allChallengeCount;

        private int successChallengeCount;

        private int failedChallengeCount;

        @Builder
        public ChallengeSuccessRateResponseDTO(int allChallengeCount, int successChallengeCount, int failedChallengeCount) {
            this.allChallengeCount = allChallengeCount;
            this.successChallengeCount = successChallengeCount;
            this.failedChallengeCount = failedChallengeCount;
        }
    }
}
