package kb.wgwg.article.dto;

import lombok.*;

import java.time.LocalDateTime;

public class ArticleDTO {

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class ArticleInsertRequestDTO {
        private Long userSeq;
        private String title;
        private String content;
        private String category;

        @Builder
        public ArticleInsertRequestDTO(Long userSeq) {
            this.userSeq = userSeq;
            this.title = title;
            this.content = content;
            this.category = category;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class ArticleInsertResponseDTO {

        private Long articleSeq;

        @Builder
        public ArticleInsertResponseDTO(long articleSeq) {
            this.articleSeq = articleSeq;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    public static final class ArticleUpdateDTO{
        private Long articleSeq;
        private String title;
        private String content;
        private String category;
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class ArticleReadResponseDTO {

        private Long articleSeq;

        private String title;

        private String content;

        private String category;

        private LocalDateTime insertDate;

        private String updateDate;

        private String writer;

        @Builder
        public ArticleReadResponseDTO(Long articleSeq, String title, String content, String category,
                                      LocalDateTime insertDate, String updateDate, String writer)
        {
            this.articleSeq = articleSeq;
            this.title = title;
            this.content = content;
            this.category = category;
            this.insertDate = insertDate;
            this.updateDate = updateDate;
            this.writer = writer;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class ArticleListRequestDTO {

        private String category;

        @Builder
        public ArticleListRequestDTO(String category) {
            this.category = category;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class ArticleListResponseDTO {

        private Long articleSeq;

        private String title;

        private String writer;

        private LocalDateTime insertDate;

        private LocalDateTime updateDate;

        private int commentSize;

        @Builder
        public ArticleListResponseDTO(Long articleSeq, String title, String writer,
                                      LocalDateTime insertDate, LocalDateTime updateDate, int commentSize) {
            this.articleSeq = articleSeq;
            this.title = title;
            this.writer = writer;
            this.insertDate = insertDate;
            this.updateDate = updateDate;
            this.commentSize = commentSize;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class ArticleListUserResponseDTO {
        private Long articleSeq;
        private LocalDateTime insertDate;
        private String title;
        private String category;

        @Builder
        public ArticleListUserResponseDTO(Long articleSeq, LocalDateTime insertDate, String title, String category) {
            this.articleSeq = articleSeq;
            this.insertDate = insertDate;
            this.title = title;
            this.category = category;
        }
    }
}