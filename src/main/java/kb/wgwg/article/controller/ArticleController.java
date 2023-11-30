package kb.wgwg.article.controller;

import kb.wgwg.common.ResponseMessage;
import kb.wgwg.common.StatusCode;
import kb.wgwg.article.dto.ArticleDTO.*;
import kb.wgwg.common.dto.BaseResponseDTO;
import kb.wgwg.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/articles")
public class ArticleController {
    private final ArticleService service;

    @PostMapping("/insert")
    public ResponseEntity<BaseResponseDTO> insertArticle(@RequestBody ArticleInsertRequestDTO requestDTO){
        BaseResponseDTO<ArticleInsertResponseDTO> result2 = new BaseResponseDTO<>();
        ArticleInsertResponseDTO result = service.insertArticle(requestDTO);
        result2.setMessage(ResponseMessage.CREATED_ARTICLE_SUCCESS);
        result2.setStatus(StatusCode.OK);
        result2.setSuccess(true);
        result2.setData(result);
        return ResponseEntity.ok(result2);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteArticle(@PathVariable Long id){
        BaseResponseDTO result = new BaseResponseDTO();
        try {
            if(service.deleteArticle(id) > 0){
                result.setStatus(StatusCode.OK);
                result.setMessage(ResponseMessage.DELETE_ARTICLE_SUCCESS);
                result.setSuccess(true);
                return ResponseEntity.ok(result);
            }
            else{
                result.setStatus(StatusCode.BAD_REQUEST);
                result.setMessage(ResponseMessage.NOT_FOUND_ARTICLE);
                result.setSuccess(false);
                return ResponseEntity.badRequest().body(result);
            }
        } catch (Exception e){
            result.setStatus(StatusCode.INTERNAL_SERVER_ERROR);
            result.setSuccess(false);
            result.setMessage(ResponseMessage.INTERNAL_SERVER_ERROR);

            return ResponseEntity.internalServerError().body(result);
        }
    }

    @PostMapping(value = "/update")
    public ResponseEntity updateArticle(@RequestBody ArticleUpdateDTO dto) {
        BaseResponseDTO<ArticleUpdateDTO> result2 = new BaseResponseDTO<>();
        ArticleUpdateDTO result = service.updateArticle(dto);
        result2.setMessage(ResponseMessage.UPDATE_ARTICLE_SUCCESS);
        result2.setStatus(StatusCode.OK);
        result2.setSuccess(true);
        result2.setData(result);
        return ResponseEntity.ok(result2);
    }

    @GetMapping(value = "/read/{id}")
    public ResponseEntity<BaseResponseDTO> readArticle(@PathVariable Long id) {
        BaseResponseDTO<ArticleReadResponseDTO> response = new BaseResponseDTO<>();

        try {
            ArticleReadResponseDTO result = service.findArticleById(id);
            response.setMessage(ResponseMessage.READ_ARTICLE_SUCCESS);
            response.setStatus(StatusCode.OK);
            response.setSuccess(true);
            response.setData(result);
        } catch (EntityNotFoundException e) {
            response.setMessage(e.getMessage());
            response.setStatus(StatusCode.NOT_FOUND);
            response.setSuccess(false);
        } catch (Exception e) {
            response.setMessage(ResponseMessage.INTERNAL_SERVER_ERROR);
            response.setStatus(StatusCode.INTERNAL_SERVER_ERROR);
            response.setSuccess(false);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/read")
    public ResponseEntity<BaseResponseDTO> readArticlesByCategory(@RequestBody ArticleListRequestDTO dto,
                                                                  @PageableDefault(size = 10) Pageable pageable) {
        BaseResponseDTO<Page<ArticleListResponseDTO>> response = new BaseResponseDTO<>();

        try {
            pageable = PageRequest.of(pageable.getPageNumber(), 10, Sort.by("updateDate").descending().and(Sort.by("title")));
            Page<ArticleListResponseDTO> result = service.findArticlesByCategory(dto.getCategory(), pageable);
            response.setMessage(ResponseMessage.READ_ARTICLE_LIST_SUCCESS);
            response.setStatus(StatusCode.OK);
            response.setSuccess(true);
            response.setData(result);
        } catch (EntityNotFoundException e) {
            response.setMessage(e.getMessage());
            response.setStatus(StatusCode.NOT_FOUND);
            response.setSuccess(false);
        } catch (Exception e) {
            response.setMessage(ResponseMessage.INTERNAL_SERVER_ERROR);
            response.setStatus(StatusCode.INTERNAL_SERVER_ERROR);
            response.setSuccess(false);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/read/my/{userSeq}")
    public ResponseEntity<BaseResponseDTO> readArticleByUserSeq(@PathVariable Long userSeq, @PageableDefault(size = 10) Pageable pageable) {
        BaseResponseDTO<Page<ArticleListUserResponseDTO>> response = new BaseResponseDTO<>();
        try {
            Page<ArticleListUserResponseDTO> result = service.findArticleByUser(userSeq, pageable);
            response.setMessage(ResponseMessage.READ_ARTICLE_LIST_SUCCESS);
            response.setStatus(StatusCode.OK);
            response.setSuccess(true);
            response.setData(result);
        } catch (EntityNotFoundException e) {
            response.setMessage(e.getMessage());
            response.setStatus(StatusCode.BAD_REQUEST);
            response.setSuccess(false);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage(ResponseMessage.INTERNAL_SERVER_ERROR);
            response.setStatus(StatusCode.INTERNAL_SERVER_ERROR);
            response.setSuccess(false);
        }
        return ResponseEntity.ok(response);
    }
}
