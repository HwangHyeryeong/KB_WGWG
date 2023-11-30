package kb.wgwg.user.controller;

import kb.wgwg.common.ResponseMessage;
import kb.wgwg.common.StatusCode;
import kb.wgwg.common.dto.BaseResponseDTO;
import kb.wgwg.user.dto.UserDTO;
import kb.wgwg.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users/read/challenges")
public class UserChallengeController {
    private final UserService userService;

    @PostMapping(value = "/process")
    public ResponseEntity<BaseResponseDTO> readMyProcessingChallengeList(@RequestBody UserDTO.UserReadMyChallengeListRequestDTO dto) {
        BaseResponseDTO<List<UserDTO.ReadMyProcessingChallengeResponseDTO>> result = new BaseResponseDTO<>();

        try {
            List<UserDTO.ReadMyProcessingChallengeResponseDTO> response = userService.readMyProcessingChallenge(dto);

            result.setStatus(StatusCode.OK);
            result.setSuccess(true);
            result.setMessage(ResponseMessage.READ_CHALLENGE_LIST_SUCCESS);
            result.setData(response);

            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException e) {
            result.setStatus(StatusCode.BAD_REQUEST);
            result.setSuccess(true);
            result.setMessage(e.getMessage());

            return ResponseEntity.badRequest().body(result);
        } catch (Exception e) {
            result.setStatus(StatusCode.INTERNAL_SERVER_ERROR);
            result.setSuccess(false);
            result.setMessage(ResponseMessage.INTERNAL_SERVER_ERROR);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @PostMapping(value = "/complete")
    public ResponseEntity<BaseResponseDTO> readMyCompleteChallengeList(@RequestBody UserDTO.UserReadMyChallengeListRequestDTO dto) {
        BaseResponseDTO<List<UserDTO.ReadMyCompleteChallengeResponseDTO>> result = new BaseResponseDTO<>();

        try {
            List<UserDTO.ReadMyCompleteChallengeResponseDTO> response = userService.readMyCompleteChallenge(dto);

            result.setStatus(StatusCode.OK);
            result.setSuccess(true);
            result.setMessage(ResponseMessage.READ_CHALLENGE_LIST_SUCCESS);
            result.setData(response);

            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException e) {
            result.setStatus(StatusCode.BAD_REQUEST);
            result.setSuccess(true);
            result.setMessage(e.getMessage());

            return ResponseEntity.badRequest().body(result);
        } catch (Exception e) {
            result.setStatus(StatusCode.INTERNAL_SERVER_ERROR);
            result.setSuccess(false);
            result.setMessage(ResponseMessage.INTERNAL_SERVER_ERROR);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }
}
