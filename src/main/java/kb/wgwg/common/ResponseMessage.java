package kb.wgwg.common;

public class ResponseMessage {
    public static final String INTERNAL_SERVER_ERROR = "서버 내부 에러";
    public static final String DB_ERROR = "데이터베이스 에러";

    //회원
    public static final String LOGIN_SUCCESS = "로그인 성공";
    public static final String LOGIN_FAIL = "로그인 실패";
    public static final String READ_USER_SUCCESS = "회원 정보 조회 성공";
    public static final String NOT_FOUND_USER = "회원을 찾을 수 없습니다.";
    public static final String CREATED_USER_SUCCESS = "회원 가입 성공";
    public static final String UPDATE_USER_SUCCESS = "회원 정보 수정 성공";
    public static final String DELETE_USER_SUCCESS = "회원 탈퇴 성공";
    public static final String CHECK_EMAIL_AVAILABLE = "이메일 사용 가능";
    public static final String CHECK_EMAIL_DUPLICATED = "이메일 사용 불가";
    public static final String CHECK_NICKNAME_AVAILABLE = "닉네임 사용 가능";
    public static final String CHECK_NICKNAME_DUPLICATED = "닉네임 사용 불가";


    //게시판
    public static final String CREATED_ARTICLE_SUCCESS = "게시글 등록 성공";
    public static final String READ_ARTICLE_SUCCESS = "게시글 조회 성공";
    public static final String READ_ARTICLE_LIST_SUCCESS = "게시글 목록 조회 성공";
    public static final String UPDATE_ARTICLE_SUCCESS = "게시글 수정 성공";
    public static final String DELETE_ARTICLE_SUCCESS = "게시글 삭제 성공";
    public static final String NOT_FOUND_ARTICLE = "게시글을 찾을 수 없습니다.";
    public static final String CREATED_COMMENT_SUCCESS = "댓글 등록 성공";
    public static final String READ_COMMENT_SUCCESS = "댓글 조회 성공";
    public static final String UPDATE_COMMENT_SUCCESS = "댓글 수정 성공";
    public static final String DELETE_COMMENT_SUCCESS = "댓글 삭제 성공";
    public static final String NOT_FOUND_COMMENT = "댓글을 찾을 수 없습니다.";

    //챌린지
    public static final String CREATED_CHALLENGE_SUCCESS = "챌린지 생성 성공";
    public static final String READ_CHALLENGE_LIST_SUCCESS = "챌린지 목록 조회 성공";
    public static final String READ_CHALLENGE_INFO_SUCCESS = "챌린지 조회 성공";
    public static final String UPDATE_CHALLENGE_SUCCESS = "챌린지 수정 성공";
    public static final String DELETE_CHALLENGE_SUCCESS = "챌린지 삭제 성공";
    public static final String INSERT_CHALLENGE_USER_SUCCESS = "챌린지 참여 성공";
    public static final String READ_CHALLENGE_USER_INFO_SUCCESS = "참가자 정보 읽기 완료";
    public static final String COUNT_CHALLENGE_USER_INFO_SUCCESS = "참여자수 카운트 성공";
    public static final String IDENTIFY_CHALLENGE_USER_INFO_SUCCESS = "유저 식별 완료";
    public static final String NOT_FOUND_CHALLENGE = "챌린지를 찾을 수 없습니다.";
    public static final String ALREADY_PARTICIPATED_CHALLENGE = "이미 참여중인 챌린지입니다.";
    public static final String OVER_CAPACITY_CHALLENGE = "참여자가 가득 차 더 이상 참여가 불가능합니다.";

    //가계부
    public static final String UPDATE_BANKING_HISTORY_SUCCESS = "입출금 내역 수정 성공";
    public static final String DELETE_BANKING_HISTORY_SUCCESS = "입출금 내역 삭제 성공";
    public static final String READ_CATEGORY_SUCCESS = "카테고리 차트 조회 성공";
    public static final String READ_TOTAL_SUCCESS = "가계부 수입/지출 누적 금액 조회 성공";
    public static final String INSERT_BANKING_SUCCESS = "입출금 등록 성공";
    public static final String INSERT_PAYMENT_SUCCESS = "납입금 등록 성공";
    public static final String UPDATE_TOTAL_PAYMENT_SUCCESS = "납입금 총액 갱신 성공";
    public static final String UPDATE_TOTAL_DEPOSIT_SUCCESS = "보증금 총액 갱신 성공";
    public static final String READ_BANKING_HISTORY_SUCCESS = "입출금 내역 조회 성공";
    public static final String BANKING_INSERT_FAIL = "입출금 등록 실패";
}
