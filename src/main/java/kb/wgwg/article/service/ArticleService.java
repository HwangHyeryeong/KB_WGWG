package kb.wgwg.article.service;

import kb.wgwg.article.domain.Article;
import kb.wgwg.user.domain.User;
import kb.wgwg.article.dto.ArticleDTO.*;
import kb.wgwg.article.repository.ArticleRepository;
import kb.wgwg.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService {
    private final ModelMapper modelMapper;
    private final ArticleRepository repository;
    private final UserRepository userRepository;
    private final EntityManager entityManager;

    public ArticleInsertResponseDTO insertArticle(ArticleInsertRequestDTO dto){
        User user  = userRepository.findById(dto.getUserSeq()).orElseThrow(
                () -> new EntityNotFoundException("해당 유저를 찾을 수 없습니다.")
        );

        Article article = Article.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .category(dto.getCategory())
                .writer(user).build();

        Article savedArticle = repository.save(article);
        entityManager.flush(); //insertDate
        ArticleInsertResponseDTO result = modelMapper.map(savedArticle, ArticleInsertResponseDTO.class);
        return result;
    }


    public int deleteArticle(Long id){
        return repository.deleteByArticleSeq(id);
    }

    public ArticleUpdateDTO updateArticle(ArticleUpdateDTO dto) {
        Article article = repository.findById(dto.getArticleSeq()).orElseThrow(
                () -> new EntityNotFoundException("해당 게시글을 찾을 수 없습니다.")
        );
        article.updateTitle(dto.getTitle());
        article.updateContent(dto.getContent());
        article.updateCategory(dto.getCategory());

        dto = modelMapper.map(article, ArticleUpdateDTO.class);
        return dto;
    }

    @Transactional(readOnly = true)
    public ArticleReadResponseDTO findArticleById(Long id) {
        Article theArticle = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("해당 게시글을 찾을 수 없습니다.")
        );

        return ArticleReadResponseDTO.builder()
                                        .articleSeq(theArticle.getArticleSeq())
                                        .title(theArticle.getTitle())
                                        .content(theArticle.getContent())
                                        .category(theArticle.getCategory())
                                        .insertDate(theArticle.getInsertDate())
                                        .updateDate(theArticle.getUpdateDate().format(DateTimeFormatter.ISO_DATE))
                                        .writer(theArticle.getWriter().getNickName())
                                    .build();
    }

    @Transactional(readOnly = true)
    public Page<ArticleListResponseDTO> findArticlesByCategory(String category, Pageable pageable) {
        Page<Article> page = null;

        if (category.equals("전체보기")) {
            page = repository.findAll(pageable);
        } else {
            page = repository.findAllByCategory(category, pageable);
        }
        Page<ArticleListResponseDTO> dtoPage = page.map(new Function<Article, ArticleListResponseDTO>() {
            @Override
            public ArticleListResponseDTO apply(Article article) {
                ArticleListResponseDTO dto = ArticleListResponseDTO.builder()
                        .articleSeq(article.getArticleSeq())
                        .title(article.getTitle())
                        .insertDate(article.getInsertDate())
                        .updateDate(article.getUpdateDate())
                        .writer(article.getWriter().getNickName())
                        .commentSize(article.getComments().size())
                        .build();
                return dto;
            }
        });
        return dtoPage;
    }

    @Transactional(readOnly = true)
    public Page<ArticleListUserResponseDTO> findArticleByUser(Long userSeq, Pageable pageable) {
        Page<Article> page = repository.findAllByUserSeq(userSeq, pageable);
        Page<ArticleListUserResponseDTO> dtoPage = page.map(new Function<Article, ArticleListUserResponseDTO>() {
            @Override
            public ArticleListUserResponseDTO apply(Article article) {
                ArticleListUserResponseDTO dto = ArticleListUserResponseDTO.builder()
                        .articleSeq(article.getArticleSeq())
                        .insertDate(article.getInsertDate())
                        .title(article.getTitle())
                        .category(article.getCategory())
                        .build();

                return dto;
            }
        });
        return dtoPage;
    }
}