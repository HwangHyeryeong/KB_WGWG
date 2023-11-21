package kb.wgwg.challenge.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

public class ChallengeDTO {

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class NChallengeInsertRequestDTO {

        private Long ownerId;

        private String title;

        private String description;

        private String status;

        private LocalDateTime startDate;

        private int deposit;

        private int limitAmount;

        private String challengeType;

        private String account;

        private String bankName;

        @Builder
        public NChallengeInsertRequestDTO(String title, String description, String status,
                                          LocalDateTime startDate, int deposit,
                                          int limitAmount, String challengeType)
        {
            this.title = title;
            this.description = description;
            this.status = status;
            this.startDate = startDate;
            this.deposit = deposit;
            this.limitAmount = limitAmount;
            this.challengeType = challengeType;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class NChallengeInsertEndDateRequestDTO {

        private Long ownerId;

        private String title;

        private String description;

        private String status;

        private LocalDateTime startDate;

        private LocalDateTime endDate;

        private int deposit;

        private int limitAmount;

        private String challengeType;

        @Builder
        public NChallengeInsertEndDateRequestDTO(Long ownerId, String title, String description, String status,
                                          LocalDateTime startDate, LocalDateTime endDate, int deposit,
                                          int limitAmount, String challengeType)
        {
            this.ownerId = ownerId;
            this.title = title;
            this.description = description;
            this.status = status;
            this.startDate = startDate;
            this.endDate = endDate;
            this.deposit = deposit;
            this.limitAmount = limitAmount;
            this.challengeType = challengeType;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class NChallengeInsertResponseDTO {

        private Long challengeId;

        private String title;

        private String description;

        private String status;

        private LocalDateTime startDate;

        private LocalDateTime endDate;

        private int deposit;

        private int limitAmount;

        private String challengeType;

        @Builder
        public NChallengeInsertResponseDTO(Long challengeId, String title, String description, String status,
                                           LocalDateTime startDate, LocalDateTime endDate, int deposit, int limitAmount, String challengeType)
        {
            this.challengeId = challengeId;
            this.title = title;
            this.description = description;
            this.status = status;
            this.startDate = startDate;
            this.endDate = endDate;
            this.deposit = deposit;
            this.limitAmount = limitAmount;
            this.challengeType = challengeType;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class CoffeeChallengeInsertRequestDTO {

        private Long ownerId;

        private String title;

        private String description;

        private String status;

        private LocalDateTime startDate;

        private LocalDateTime endDate;

        private String account;

        private String bankName;

        private int savingAmount;

        private String challengeType;

        @Builder
        public CoffeeChallengeInsertRequestDTO(Long ownerId, String title, String description, String status,
                                          LocalDateTime startDate, LocalDateTime endDate, int savingAmount,
                                          String account, String bankName, String challengeType)
        {
            this.ownerId = ownerId;
            this.title = title;
            this.description = description;
            this.status = status;
            this.startDate = startDate;
            this.endDate = endDate;
            this.savingAmount = savingAmount;
            this.account = account;
            this.bankName = bankName;
            this.challengeType = challengeType;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class CoffeeChallengeInsertResponseDTO {

        private Long challengeId;

        private String title;

        private String description;

        private String status;

        private LocalDateTime startDate;

        private LocalDateTime endDate;

        private int savingAmount;

        private String challengeType;

        @Builder
        public CoffeeChallengeInsertResponseDTO(Long challengeId, String title, String description, String status,
                                           LocalDateTime startDate, LocalDateTime endDate, int savingAmount, String challengeType)
        {
            this.challengeId = challengeId;
            this.title = title;
            this.description = description;
            this.status = status;
            this.startDate = startDate;
            this.endDate = endDate;
            this.savingAmount = savingAmount;
            this.challengeType = challengeType;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class ChallengeParticipateRequestDTO {

        private Long challengeId;

        private Long userSeq;

        private String account;

        private String bankName;

        private String challengeType;
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class UpdateIsSuccessRequestDTO{
        private Long challengeId;

        private Long userSeq;
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class UpdateAmountRequestDTO{
        private Long challengeId;
        private String challengeType;
        private int amount;
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    public static final class NChallengeUpdateDTO{
        private Long challengeId;
        private String title;
        private String description;
        private LocalDateTime startDate;
        private int limitAmount;
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    public static final class CoffeeChallengeUpdateDTO{
        private Long challengeId;
        private String title;
        private String description;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private int savingAmount;
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class ChallengeListRequestDTO {
        private String status;
        private String challengeType;

        @Builder
        public ChallengeListRequestDTO(String status){
            this.status = status;
            this.challengeType = challengeType;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class CoffeeChallengeListResponseDTO {
        private Long challengeId;
        private String title;
        private String status;
        private int savingAmount;
        private LocalDateTime startDate;
        private LocalDateTime endDate;

        @Builder
        public CoffeeChallengeListResponseDTO(Long challengeId, String title, String status, int savingAmount, LocalDateTime startDate, LocalDateTime endDate) {
            this.challengeId = challengeId;
            this.title = title;
            this.status = status;
            this.savingAmount = savingAmount;
            this.startDate = startDate;
            this.endDate = endDate;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class NChallengeListResponseDTO {
        private Long challengeId;
        private String title;
        private String status;
        private int deposit;
        private LocalDateTime startDate;
        private LocalDateTime endDate;

        @Builder
        public NChallengeListResponseDTO(Long challengeId, String title, String status, int deposit, LocalDateTime startDate, LocalDateTime endDate) {
            this.challengeId = challengeId;
            this.title = title;
            this.status = status;
            this.deposit = deposit;
            this.startDate = startDate;
            this.endDate = endDate;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    public static final class NChallengeReadResponseDTO {
        private String title;
        private String description;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private int deposit;
        private int limitAmount;
        private String challengeType;
        private String status;
        private Map<String, Integer> isSuccessList;
        private int reward;
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    public static final class CoffeeChallengeReadResponseDTO {
        private String title;
        private String description;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private String challengeType;
        private int totalAsset;
        private int savingAmount;
        private String status;
        private Map<String, Integer> isSuccessList;
        private int reward;
    }
}
