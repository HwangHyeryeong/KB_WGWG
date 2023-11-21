package kb.wgwg.article.controller;

import kb.wgwg.common.dto.BaseResponseDTO;
import kb.wgwg.article.dto.CommentDTO.*;
import kb.wgwg.article.service.CommentService;
import kb.wgwg.common.ResponseMessage;
import kb.wgwg.common.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/insert")
    public ResponseEntity<BaseResponseDTO> insertComment(@RequestBody CommentInsertRequestDTO dto) {
        BaseResponseDTO<CommentInsertResponseDTO> result2 = new BaseResponseDTO<>();
        CommentInsertResponseDTO result = commentService.insertComment(dto);
        result2.setMessage("댓글 등록 완료");
        result2.setStatus(200);
        result2.setSuccess(true);
        result2.setData(result);
        return ResponseEntity.ok(result2);
    }


    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity deleteComment(@PathVariable Long id) {

        BaseResponseDTO result = new BaseResponseDTO();
        try {
            if(commentService.deleteComment(id) > 0) {
                result.setMessage(ResponseMessage.COMMENT_DELETE_SUCCESS);
                result.setStatus(StatusCode.OK);
                result.setSuccess(true);
                return ResponseEntity.ok(result);
            } else {
                result.setMessage(ResponseMessage.NOT_FOUND_COMMENT);
                result.setStatus(StatusCode.BAD_REQUEST);
                result.setSuccess(false);
                return ResponseEntity.ok(result);
            }
        }catch (Exception e){
            result.setMessage(ResponseMessage.INTERNAL_SERVER_ERROR);
            result.setStatus(StatusCode.INTERNAL_SERVER_ERROR);
            result.setSuccess(false);
            return ResponseEntity.ok(result);
        }
    }

    @PostMapping("/update")
    public ResponseEntity updateComment(@RequestBody CommentUpdateDTO dto){
        BaseResponseDTO result = new BaseResponseDTO();

        try {
            int updateRows = commentService.updateComment(dto);
            result.setStatus(StatusCode.OK);
            result.setMessage(ResponseMessage.COMMENT_UPDATE_SUCCESS);
            result.setSuccess(true);
            result.setData(updateRows);
            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException e){
            result.setStatus(StatusCode.BAD_REQUEST);
            result.setMessage(ResponseMessage.NOT_FOUND_COMMENT);
            result.setSuccess(false);
            return ResponseEntity.badRequest().body(result);
        } catch (Exception e){
            result.setStatus(StatusCode.INTERNAL_SERVER_ERROR);
            result.setMessage(ResponseMessage.INTERNAL_SERVER_ERROR);
            result.setSuccess(false);
            return ResponseEntity.internalServerError().body(result);
        }
    }

    @GetMapping("/read/{articleSeq}")
    public ResponseEntity readComments(@PathVariable Long articleSeq){
        BaseResponseDTO result = new BaseResponseDTO();
        try {
            List<CommentReadResponseDTO> comments = commentService.readComment(articleSeq);
            result.setStatus(StatusCode.OK);
            result.setMessage(ResponseMessage.READ_COMMENT_SUCCESS);
            result.setSuccess(true);
            result.setData(comments);
            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException e){
            result.setStatus(StatusCode.BAD_REQUEST);
            result.setMessage(ResponseMessage.NOT_FOUND_ARTICLE);
            result.setSuccess(false);
            return ResponseEntity.badRequest().body(result);
        } catch (Exception e){
            e.printStackTrace();
            result.setStatus(StatusCode.INTERNAL_SERVER_ERROR);
            result.setMessage(ResponseMessage.INTERNAL_SERVER_ERROR);
            result.setSuccess(false);
            return ResponseEntity.internalServerError().body(result);
        }
    }
}
