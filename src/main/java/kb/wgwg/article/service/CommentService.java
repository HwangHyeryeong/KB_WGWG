package kb.wgwg.article.service;

import kb.wgwg.article.domain.Article;
import kb.wgwg.article.domain.Comment;
import kb.wgwg.user.domain.User;
import kb.wgwg.article.dto.CommentDTO.*;
import kb.wgwg.article.repository.ArticleRepository;
import kb.wgwg.article.repository.CommentRepository;
import kb.wgwg.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final ModelMapper modelMapper;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final EntityManager entityManager;

    public CommentInsertResponseDTO insertComment(CommentInsertRequestDTO dto) {
        User user = userRepository.findById(dto.getUserSeq()).orElseThrow(
                () -> new EntityNotFoundException("해당 사용자를 찾을 수 없습니다.")
        );

        Article article = articleRepository.findById(dto.getArticleSeq()).orElseThrow(
                () -> new EntityNotFoundException("해당 게시글을 찾을 수 없습니다.")
        );

        Comment comment = Comment.builder()
                .content(dto.getContent())
                .writer(user)
                .parentArticle(article).build();

        Comment savedComment = commentRepository.save(comment);
        entityManager.flush();
        CommentInsertResponseDTO result = modelMapper.map(savedComment, CommentInsertResponseDTO.class);
        return result;
    }

    public int updateComment(CommentUpdateDTO dto) {
        Comment comment = commentRepository.findById(dto.getCommentSeq()).orElseThrow(
                () -> new EntityNotFoundException("해당 댓글을 찾을 수 없습니다.")
        );

        return commentRepository.updateCommentByCommentSeq(dto.getCommentSeq(), dto.getContent());
    }

    public int deleteComment(Long id){
        return commentRepository.deleteByCommentSeq(id);
    }

    public List<CommentReadResponseDTO> readComment(Long articleSeq){
        Article article = articleRepository.findById(articleSeq).orElseThrow(
                () -> new EntityNotFoundException("해당 게시글을 찾을 수 없습니다.")
        );

         List<Object[]> rawComments = commentRepository.findCommentsByArticleSeq(articleSeq);

         List<CommentReadResponseDTO> result = new ArrayList<>();
         for(Object[] r : rawComments){
             CommentReadResponseDTO dto = new CommentReadResponseDTO();
             dto.setCommentSeq(((BigDecimal) r[0]).longValue());
             dto.setWriterSeq(((BigDecimal) r[1]).longValue());
             dto.setNickName((String) r[2]);
             dto.setContent((String) r[3]);
             dto.setDate(((Timestamp) r[4]).toLocalDateTime().withNano(0));
             result.add(dto);
         }
         return result;
    }

}
