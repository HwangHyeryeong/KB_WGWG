package kb.wgwg.challenge.controller;

import kb.wgwg.common.ResponseMessage;
import kb.wgwg.common.StatusCode;
import kb.wgwg.common.dto.BaseResponseDTO;
import kb.wgwg.challenge.dto.ChallengeUserDTO.*;
import kb.wgwg.challenge.service.ChallengeUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/challengeuser")
public class ChallengeUserController {

    private final ChallengeUserService challengeUserService;

    @PostMapping("/read")
    public ResponseEntity<BaseResponseDTO> readChallengeUserByChallenge(@RequestBody ReadChallengeUserRequestDTO dto, @PageableDefault(size = 15) Pageable pageable){
        BaseResponseDTO<Page<ReadChallengeUserResponseDTO>> response = new BaseResponseDTO<>();
        try{
            Page<ReadChallengeUserResponseDTO> result = challengeUserService.readChallengeUserByChallengeId(dto, pageable);
            response.setMessage(ResponseMessage.READ_CHALLENGE_USER_INFO_SUCCESS);
            response.setStatus(StatusCode.OK);
            response.setSuccess(true);
            response.setData(result);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            e.printStackTrace();
            response.setMessage(e.getMessage());
            response.setStatus(StatusCode.INTERNAL_SERVER_ERROR);
            response.setSuccess(false);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PostMapping("/checkuser")
    public ResponseEntity<BaseResponseDTO> checkChallengeUser(@RequestBody CheckChallengeUserRequestDTO dto){
        BaseResponseDTO<CheckChallengeUserResponseDTO> response = new BaseResponseDTO<>();
        try {
            CheckChallengeUserResponseDTO result = challengeUserService.checkChallengeUser(dto);
            response.setMessage(ResponseMessage.IDENTIFY_CHALLENGE_USER_INFO_SUCCESS);
            response.setStatus(StatusCode.OK);
            response.setSuccess(true);
            response.setData(result);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            e.printStackTrace();
            response.setMessage(e.getMessage());
            response.setStatus(StatusCode.INTERNAL_SERVER_ERROR);
            response.setSuccess(false);
            return ResponseEntity.internalServerError().body(response);
        }

    }

    @PostMapping("/cnt")
    public ResponseEntity<BaseResponseDTO> countUser(@RequestBody ReadChallengeUserRequestDTO dto){
        BaseResponseDTO<challengeUserCntResponse> response = new BaseResponseDTO<>();
        try {
            challengeUserCntResponse result = challengeUserService.countUser(dto);
            response.setMessage(ResponseMessage.COUNT_CHALLENGE_USER_INFO_SUCCESS);
            response.setStatus(StatusCode.OK);
            response.setSuccess(true);
            response.setData(result);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            e.printStackTrace();
            response.setMessage(e.getMessage());
            response.setStatus(StatusCode.INTERNAL_SERVER_ERROR);
            response.setSuccess(false);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PostMapping("/read/challenge/success")
    public ResponseEntity<BaseResponseDTO> readSuccessChallengeRate(@RequestBody ChallengeSuccessRateRequestDTO dto) {
        BaseResponseDTO<ChallengeSuccessRateResponseDTO> response = new BaseResponseDTO<>();

        try {
            ChallengeSuccessRateResponseDTO result = challengeUserService.readSuccessChallengeRate(dto);
            response.setStatus(StatusCode.OK);
            response.setMessage(ResponseMessage.CHALLENGE_UPDATE_SUCCESS);
            response.setSuccess(true);
            response.setData(result);
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
}
