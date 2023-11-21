package kb.wgwg.article.dto;

import kb.wgwg.user.domain.User;
import lombok.*;

import java.time.LocalDateTime;

public class CommentDTO {
    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    public static final class CommentInsertRequestDTO {
        private Long userSeq;
        private Long articleSeq;
        private String content;
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class CommentInsertResponseDTO {
        private String content;
        private String writer;
        private LocalDateTime insertDate;
        private LocalDateTime updateDate;

        @Builder
        public CommentInsertResponseDTO(String content, User writer, LocalDateTime insertDate, LocalDateTime updateDate) {
            this.content = content;
            this.writer = writer.getNickName();
            this.insertDate = insertDate;
            this.updateDate = updateDate;
        }
    }
    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class CommentUpdateDTO{
        private Long commentSeq;
        private String content;
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class CommentReadResponseDTO{
        private Long commentSeq;
        private Long writerSeq;
        private String nickName;
        private String content;
        private LocalDateTime date;
    }
}
