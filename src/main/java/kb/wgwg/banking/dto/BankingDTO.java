package kb.wgwg.banking.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

import lombok.*;

public class BankingDTO {
    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    public static final class BankingListRequestDTO { //월별로 요청
        private LocalDateTime bankingDate;
        private Long userSeq;
    }

    @Getter
    @ToString
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    public static final class BankingListResponseDTO {
        private Long bankingId;
        private LocalDateTime bankingDate;
        private String type;
        private String category;
        private int amount;
        private String content;
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class BankingReadResponseDTO {
        private Long bankingId;
        private LocalDateTime bankingDate;
        private String type;
        private String category;
        private int amount;
        private String content;

        @Builder
        public BankingReadResponseDTO(Long bankingId, LocalDateTime bankingDate, String type, String category, int amount, String content)
        {
            this.bankingId =bankingId;
            this.bankingDate = bankingDate;
            this.type = type;
            this.category = category;
            this.amount = amount;
            this.content = content;
        }
    }


    @Getter
    @Setter
    @NoArgsConstructor
    public static final class ReadCategoryRequestDTO {
        private Long userSeq;
        private LocalDateTime checkMonth;

        @Builder
        public ReadCategoryRequestDTO(Long userSeq, LocalDateTime checkMonth){
            this.userSeq = userSeq;
            this.checkMonth = checkMonth;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static final class ReadTotalRequestDTO {
        private Long userSeq;
        private LocalDateTime checkMonth;

        @Builder
        public ReadTotalRequestDTO(Long userSeq, LocalDateTime checkMonth) {
            this.userSeq = userSeq;
            this.checkMonth = checkMonth;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static final class ReadTotalResponseDTO {
        private int totalIncome;
        private int totalExpense;

        @Builder
        public ReadTotalResponseDTO(int totalIncome, int totalExpense){
            this.totalIncome = totalIncome;
            this.totalExpense = totalExpense;
        }
    }


    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class BankingUpdateDTO {
        // 고유 식별 번호
        private Long bankingId;

        // 금액
        private int amount;

        // 입금/출금/보증금/적금
        private String type;

        // 입출금 일자
        private LocalDateTime bankingDate;

        // 카테고리
        private String category;

        private String content;


    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static final class BankingInsertRequestDTO {
        private Long userSeq;
        private String type;
        private int amount;
        private String bankingDate;
        private String category;
        private String content;
        private Long challengeId;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static final class GetTotalRewardRequestDTO {
        private Long userSeq;

        @Builder
        public GetTotalRewardRequestDTO(Long userSeq) {
            this.userSeq = userSeq;
        }
    }
}