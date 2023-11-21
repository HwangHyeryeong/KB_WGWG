package kb.wgwg.user.controller;

import kb.wgwg.common.ResponseMessage;
import kb.wgwg.common.StatusCode;
import kb.wgwg.common.dto.BaseResponseDTO;
import kb.wgwg.user.dto.UserDTO.*;
import kb.wgwg.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/login")
    public ResponseEntity<BaseResponseDTO> login(@RequestBody UserLoginRequestDTO requestDTO) {
        BaseResponseDTO<UserLoginResponseDTO> result2 = new BaseResponseDTO<>();
        UserLoginResponseDTO result = userService.login(requestDTO);
        result2.setMessage("로그인 성공");
        result2.setStatus(200);
        result2.setSuccess(true);
        result2.setData(result);
        return ResponseEntity.ok(result2);
    }

    @PostMapping(value = "/read")
    public ResponseEntity<BaseResponseDTO> readById(@RequestBody UserReadRequestDTO requestDTO) {
        BaseResponseDTO<UserReadResponseDTO> result2 = new BaseResponseDTO<>();
        UserReadResponseDTO result = userService.readById(requestDTO);
        result2.setMessage("성공");
        result2.setStatus(200);
        result2.setSuccess(true);
        result2.setData(result);
        return ResponseEntity.ok(result2);
    }

    @GetMapping("/{email}/check/email")
    public ResponseEntity<BaseResponseDTO> checkEmailDup(@PathVariable @Email @NotEmpty String email){
        BaseResponseDTO result = new BaseResponseDTO<>();

        if(userService.checkEmailDup(email)){
            result.setStatus(404);
            result.setSuccess(false);
            result.setMessage("이메일 중복.");

        } else{
            result.setStatus(200);
            result.setSuccess(true);
            result.setMessage("이메일 사용 가능.");

            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

    @GetMapping("/{nickName}/check/nickname")
    public ResponseEntity<BaseResponseDTO> checkNickNameDup(@PathVariable String nickName){
        BaseResponseDTO result = new BaseResponseDTO<>();
        if(userService.checkNickNameDup(nickName)){
            result.setStatus(404);
            result.setSuccess(false);
            result.setMessage("닉네임 중복.");

        } else{
            result.setStatus(200);
            result.setSuccess(true);
            result.setMessage("닉네임 사용 가능.");

            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

    @PostMapping("/insert")
    public ResponseEntity<BaseResponseDTO> insert(@RequestBody UserInsertRequestDTO dto){
        BaseResponseDTO<UserReadResponseDTO> result = new BaseResponseDTO<>();

        try{
            UserReadResponseDTO insertResult = userService.insertUser(dto);
            result.setMessage("유저 등록 완료.");
            result.setStatus(200);
            result.setSuccess(true);
            result.setData(insertResult);

            return ResponseEntity.ok(result);
        } catch (Exception e){
            result.setMessage(e.getMessage());
            result.setSuccess(false);
            result.setStatus(500);

            return ResponseEntity.internalServerError().body(result);
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        BaseResponseDTO<UserReadResponseDTO> result = new BaseResponseDTO<>();
        userService.deleteUser(id);
        result.setMessage("check");
        result.setStatus(200);
        result.setSuccess(true);
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/update")
    public ResponseEntity updateUserInfo(@RequestBody UserUpdateDTO dto) {
        BaseResponseDTO result = new BaseResponseDTO();

        try {
            userService.updatePassword(dto);
            Map<String, Object> map = new HashMap<>();
            map.put("userSeq", dto.getUserSeq());

            result.setStatus(StatusCode.OK);
            result.setSuccess(true);
            result.setMessage(ResponseMessage.UPDATE_USER);
            result.setData(map);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            result.setStatus(StatusCode.INTERNAL_SERVER_ERROR);
            result.setSuccess(false);
            result.setMessage(ResponseMessage.INTERNAL_SERVER_ERROR);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @PostMapping(value = "/read/challenges/process")
    public ResponseEntity<BaseResponseDTO> readMyProcessingChallengeList(@RequestBody UserReadMyChallengeListRequestDTO dto) {
        BaseResponseDTO<List<ReadMyProcessingChallengeResponseDTO>> result = new BaseResponseDTO<>();

        try {
            List<ReadMyProcessingChallengeResponseDTO> response = userService.readMyProcessingChallenge(dto);

            result.setStatus(StatusCode.OK);
            result.setSuccess(true);
            result.setMessage("성공적으로 챌린지 목록을 반환했습니다.");
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

    @PostMapping(value = "/read/challenges/complete")
    public ResponseEntity<BaseResponseDTO> readMyCompleteChallengeList(@RequestBody UserReadMyChallengeListRequestDTO dto) {
        BaseResponseDTO<List<ReadMyCompleteChallengeResponseDTO>> result = new BaseResponseDTO<>();

        try {
            List<ReadMyCompleteChallengeResponseDTO> response = userService.readMyCompleteChallenge(dto);

            result.setStatus(StatusCode.OK);
            result.setSuccess(true);
            result.setMessage("성공적으로 챌린지 목록을 반환했습니다.");
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
