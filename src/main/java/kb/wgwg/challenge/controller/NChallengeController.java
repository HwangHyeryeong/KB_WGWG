package kb.wgwg.challenge.controller;

import kb.wgwg.common.ResponseMessage;
import kb.wgwg.common.StatusCode;
import kb.wgwg.common.dto.BaseResponseDTO;
import kb.wgwg.challenge.dto.ChallengeDTO.*;
import kb.wgwg.challenge.service.NChallengeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

import static kb.wgwg.common.ResponseMessage.INTERNAL_SERVER_ERROR;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/challenges")
public class NChallengeController {

    private final NChallengeService nChallengeService;

    @PostMapping(value = "/insert/n")
    public ResponseEntity<BaseResponseDTO> createChallenge(@RequestBody NChallengeInsertRequestDTO dto) {
        BaseResponseDTO<NChallengeInsertResponseDTO> response = new BaseResponseDTO<>();

        try {
            NChallengeInsertResponseDTO result = nChallengeService.insertNChallenge(dto);
            response.setMessage(ResponseMessage.CREATED_CHALLENGE_SUCCESS);
            response.setStatus(StatusCode.OK);
            response.setSuccess(true);
            response.setData(result);

            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            response.setMessage(e.getMessage());
            response.setStatus(StatusCode.NOT_FOUND);
            response.setSuccess(false);

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage(INTERNAL_SERVER_ERROR);
            response.setStatus(StatusCode.INTERNAL_SERVER_ERROR);
            response.setSuccess(false);

            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PostMapping(value = "/participate/n")
    public ResponseEntity<BaseResponseDTO> participateNChallenge(@RequestBody ChallengeParticipateRequestDTO dto) {
        BaseResponseDTO<Void> response = new BaseResponseDTO<>();
        try {
            nChallengeService.participateNChallenge(dto);
            response.setMessage(ResponseMessage.INSERT_CHALLENGE_USER_SUCCESS);
            response.setStatus(StatusCode.OK);
            response.setSuccess(true);

            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            response.setMessage(e.getMessage());
            response.setStatus(StatusCode.NOT_FOUND);
            response.setSuccess(false);

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            response.setMessage(ResponseMessage.ALREADY_PARTICIPATED_CHALLENGE);
            response.setStatus(StatusCode.BAD_REQUEST);
            response.setSuccess(false);

            return ResponseEntity.badRequest().body(response);
        } catch (IndexOutOfBoundsException e) {
            response.setMessage(ResponseMessage.OVER_CAPACITY_CHALLENGE);
            response.setStatus(StatusCode.BAD_REQUEST);
            response.setSuccess(false);

            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.setMessage(INTERNAL_SERVER_ERROR);
            response.setStatus(StatusCode.INTERNAL_SERVER_ERROR);
            response.setSuccess(false);

            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PostMapping(value = "/update/n")
    public ResponseEntity<BaseResponseDTO> updateNChallenge(@RequestBody NChallengeUpdateDTO dto) {
        BaseResponseDTO response = new BaseResponseDTO();
        try {
            int updateRows = nChallengeService.updateNChallenge(dto);
            response.setStatus(StatusCode.OK);
            response.setMessage(ResponseMessage.UPDATE_CHALLENGE_SUCCESS);
            response.setSuccess(true);
            response.setData(updateRows);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e){
            response.setStatus(StatusCode.BAD_REQUEST);
            response.setMessage(ResponseMessage.NOT_FOUND_CHALLENGE);
            response.setSuccess(false);
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e){
            response.setStatus(StatusCode.INTERNAL_SERVER_ERROR);
            response.setMessage(ResponseMessage.INTERNAL_SERVER_ERROR);
            response.setSuccess(false);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PostMapping(value = "/read/n")
    public ResponseEntity<BaseResponseDTO> readNChallengeByStatus(@RequestBody ChallengeListRequestDTO requestDTO, @PageableDefault(size = 8) Pageable pageable) {
        BaseResponseDTO<Page<NChallengeListResponseDTO>> response = new BaseResponseDTO<>();
        try {
            Page<NChallengeListResponseDTO> result = nChallengeService.findNChallengeByStatus(requestDTO, pageable);
            response.setMessage(ResponseMessage.READ_CHALLENGE_LIST_SUCCESS);
            response.setStatus(StatusCode.OK);
            response.setSuccess(true);
            response.setData(result);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            response.setMessage(e.getMessage());
            response.setStatus(StatusCode.NOT_FOUND);
            response.setSuccess(false);
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.setMessage(ResponseMessage.INTERNAL_SERVER_ERROR);
            response.setStatus(StatusCode.INTERNAL_SERVER_ERROR);
            response.setSuccess(false);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @DeleteMapping(value = "/delete/n/{id}")
    public ResponseEntity deleteNChallenge(@PathVariable Long id) {
        BaseResponseDTO result = new BaseResponseDTO<>();
        nChallengeService.deleteNChallenge(id);
        result.setMessage(ResponseMessage.DELETE_CHALLENGE_SUCCESS);
        result.setStatus(StatusCode.OK);
        result.setSuccess(true);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/read/n/{id}")
    public ResponseEntity<BaseResponseDTO> readNChallenge(@PathVariable Long id) {
        BaseResponseDTO<NChallengeReadResponseDTO> response = new BaseResponseDTO<>();

        try {
            NChallengeReadResponseDTO result = nChallengeService.findNChallengeById(id);
            response.setMessage(ResponseMessage.READ_CHALLENGE_INFO_SUCCESS);
            response.setStatus(StatusCode.OK);
            response.setSuccess(true);
            response.setData(result);
        } catch (EntityNotFoundException e) {
            response.setMessage(e.getMessage());
            response.setStatus(StatusCode.NOT_FOUND);
            response.setSuccess(false);
        } catch (Exception e) {
            response.setMessage(INTERNAL_SERVER_ERROR);
            response.setStatus(StatusCode.INTERNAL_SERVER_ERROR);
            response.setSuccess(false);
        }
        return ResponseEntity.ok(response);
    }
}
