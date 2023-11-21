package kb.wgwg.user.dto;

import lombok.*;

import java.time.LocalDateTime;

public class UserDTO {

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class UserLoginRequestDTO {
        private String email;
        private String password;

        @Builder
        public UserLoginRequestDTO(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class UserLoginResponseDTO {
        private Long userSeq;
        private String nickName;

        @Builder
        public UserLoginResponseDTO(Long userSeq, String nickName){
            this.userSeq = userSeq;
            this.nickName = nickName;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class UserReadRequestDTO {

        private Long userSeq;

        @Builder
        public UserReadRequestDTO(Long userSeq) {
            this.userSeq = userSeq;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class UserReadResponseDTO {

        private String email;

        private String nickName;

        private String userName;

        @Builder
        public UserReadResponseDTO(String email, String nickName, String userName) {
            this.email = email;
            this.nickName = nickName;
            this.userName = userName;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class UserInsertRequestDTO {
        private String userName;

        private String email;

        private String nickName;

        private String password;
        @Builder
        public UserInsertRequestDTO(String userName, String email, String nickName, String password) {
            this.email = email;
            this.nickName = nickName;
            this.userName = userName;
            this.password = password;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    public static final class UserUpdateDTO{
        private Long userSeq;
        private String password;
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class UserReadMyChallengeListRequestDTO {

        private Long userSeq;

        private String status;

        @Builder
        public UserReadMyChallengeListRequestDTO(Long userSeq, String status)
        {
            this.userSeq = userSeq;
            this.status = status;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class ReadMyProcessingChallengeResponseDTO {

        private String title;

        private LocalDateTime startDate;

        private LocalDateTime endDate;

        private String status;

        private String challengeType;

        @Builder

        public ReadMyProcessingChallengeResponseDTO(String title, LocalDateTime startDate, LocalDateTime endDate,
                                                    String status, String challengeType)
        {
            this.title = title;
            this.startDate = startDate;
            this.endDate = endDate;
            this.status = status;
            this.challengeType = challengeType;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class ReadMyCompleteChallengeResponseDTO {

        private String title;

        private LocalDateTime startDate;

        private LocalDateTime endDate;

        private int isSuccess;

        private int reward;

        private String challengeType;

        @Builder

        public ReadMyCompleteChallengeResponseDTO(String title, LocalDateTime startDate, LocalDateTime endDate,
                                                    int isSuccess, String challengeType)
        {
            this.title = title;
            this.startDate = startDate;
            this.endDate = endDate;
            this.isSuccess = isSuccess;
            this.challengeType = challengeType;
        }
    }
}
