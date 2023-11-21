package kb.wgwg.challenge.controller;

import kb.wgwg.common.ResponseMessage;
import kb.wgwg.common.StatusCode;
import kb.wgwg.common.dto.BaseResponseDTO;
import kb.wgwg.challenge.dto.ChallengeDTO.*;
import kb.wgwg.challenge.service.CoffeeChallengeService;
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
public class CoffeeChallengeController {

    private final CoffeeChallengeService coffeeChallengeService;

    @PostMapping(value = "/update/coffee")
    public ResponseEntity<BaseResponseDTO> updateCoffeeChallenge(@RequestBody CoffeeChallengeUpdateDTO dto) {
        BaseResponseDTO response = new BaseResponseDTO();
        try {
            int updateRows = coffeeChallengeService.updateCoffeeChallenge(dto);
            response.setStatus(StatusCode.OK);
            response.setMessage(ResponseMessage.CHALLENGE_UPDATE_SUCCESS);
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

    @DeleteMapping(value = "/delete/coffee/{id}")
    public ResponseEntity deleteCoffeeChallenge(@PathVariable Long id) {
        BaseResponseDTO result = new BaseResponseDTO<>();
        coffeeChallengeService.deleteCoffeeChallenge(id);
        result.setMessage(ResponseMessage.CHALLENGE_UPDATE_SUCCESS);
        result.setStatus(StatusCode.OK);
        result.setSuccess(true);
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/insert/coffee")
    public ResponseEntity<BaseResponseDTO> createChallenge(@RequestBody CoffeeChallengeInsertRequestDTO dto) {
        BaseResponseDTO<CoffeeChallengeInsertResponseDTO> response = new BaseResponseDTO<>();

        try {
            CoffeeChallengeInsertResponseDTO result = coffeeChallengeService.insertCoffeeChallenge(dto);
            response.setMessage("성공적으로 챌린지가 생성되었습니다.");
            response.setStatus(200);
            response.setSuccess(true);
            response.setData(result);

            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            response.setMessage(e.getMessage());
            response.setStatus(404);
            response.setSuccess(false);

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage(INTERNAL_SERVER_ERROR);
            response.setStatus(500);
            response.setSuccess(false);

            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PostMapping(value = "/participate/coffee")
    public ResponseEntity<BaseResponseDTO> participateNChallenge(@RequestBody ChallengeParticipateRequestDTO dto) {
        BaseResponseDTO<Void> response = new BaseResponseDTO<>();
        try {
            coffeeChallengeService.participateCoffeeChallenge(dto);
            response.setMessage("성공적으로 챌린지에 참여하었습니다.");
            response.setStatus(200);
            response.setSuccess(true);

            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            response.setMessage(e.getMessage());
            response.setStatus(404);
            response.setSuccess(false);

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            response.setMessage("이미 참여중인 챌린지입니다.");
            response.setStatus(400);
            response.setSuccess(false);

            return ResponseEntity.badRequest().body(response);
        } catch (IndexOutOfBoundsException e) {
            response.setMessage("참여자가 가득 차 더 이상 참여가 불가능합니다.");
            response.setStatus(400);
            response.setSuccess(false);

            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.setMessage(INTERNAL_SERVER_ERROR);
            response.setStatus(500);
            response.setSuccess(false);

            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PostMapping(value = "/read/coffee")
    public ResponseEntity<BaseResponseDTO> readNChallengeByStatus(@RequestBody ChallengeListRequestDTO requestDTO, @PageableDefault(size = 8) Pageable pageable) {
        BaseResponseDTO<Page<CoffeeChallengeListResponseDTO>> response = new BaseResponseDTO<>();
        try {
            Page<CoffeeChallengeListResponseDTO> result = coffeeChallengeService.findNChallengeByStatus(requestDTO, pageable);
            response.setMessage(ResponseMessage.READ_CHALLENGELIST_SUCCESS);
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

    @GetMapping(value = "/read/coffee/{id}")
    public ResponseEntity<BaseResponseDTO> readNChallenge(@PathVariable Long id) {
        BaseResponseDTO<CoffeeChallengeReadResponseDTO> response = new BaseResponseDTO<>();

        try {
            CoffeeChallengeReadResponseDTO result = coffeeChallengeService.findCoffeeChallengeById(id);
            response.setMessage("성공적으로 챌린지를 불러왔습니다.");
            response.setStatus(200);
            response.setSuccess(true);
            response.setData(result);
        } catch (EntityNotFoundException e) {
            response.setMessage(e.getMessage());
            response.setStatus(404);
            response.setSuccess(false);
        } catch (Exception e) {
            response.setMessage(INTERNAL_SERVER_ERROR);
            response.setStatus(500);
            response.setSuccess(false);
        }
        return ResponseEntity.ok(response);
    }

}
