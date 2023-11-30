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
        result2.setMessage(ResponseMessage.LOGIN_SUCCESS);
        result2.setStatus(StatusCode.OK);
        result2.setSuccess(true);
        result2.setData(result);
        return ResponseEntity.ok(result2);
    }

    @PostMapping(value = "/read")
    public ResponseEntity<BaseResponseDTO> readById(@RequestBody UserReadRequestDTO requestDTO) {
        BaseResponseDTO<UserReadResponseDTO> result2 = new BaseResponseDTO<>();
        UserReadResponseDTO result = userService.readById(requestDTO);
        result2.setMessage(ResponseMessage.READ_USER_SUCCESS);
        result2.setStatus(StatusCode.OK);
        result2.setSuccess(true);
        result2.setData(result);
        return ResponseEntity.ok(result2);
    }

    @GetMapping("/{email}/check/email")
    public ResponseEntity<BaseResponseDTO> checkEmailDup(@PathVariable @Email @NotEmpty String email){
        BaseResponseDTO result = new BaseResponseDTO<>();

        if(userService.checkEmailDup(email)){
            result.setStatus(StatusCode.NOT_FOUND);
            result.setSuccess(false);
            result.setMessage(ResponseMessage.CHECK_EMAIL_DUPLICATED);

        } else{
            result.setStatus(StatusCode.OK);
            result.setSuccess(true);
            result.setMessage(ResponseMessage.CHECK_EMAIL_AVAILABLE);

            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

    @GetMapping("/{nickName}/check/nickname")
    public ResponseEntity<BaseResponseDTO> checkNickNameDup(@PathVariable String nickName){
        BaseResponseDTO result = new BaseResponseDTO<>();
        if(userService.checkNickNameDup(nickName)){
            result.setStatus(StatusCode.NOT_FOUND);
            result.setSuccess(false);
            result.setMessage(ResponseMessage.CHECK_NICKNAME_DUPLICATED);

        } else{
            result.setStatus(StatusCode.OK);
            result.setSuccess(true);
            result.setMessage(ResponseMessage.CHECK_NICKNAME_AVAILABLE);

            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

    @PostMapping("/insert")
    public ResponseEntity<BaseResponseDTO> insert(@RequestBody UserInsertRequestDTO dto){
        BaseResponseDTO<UserReadResponseDTO> result = new BaseResponseDTO<>();

        try{
            UserReadResponseDTO insertResult = userService.insertUser(dto);
            result.setMessage(ResponseMessage.CREATED_USER_SUCCESS);
            result.setStatus(StatusCode.OK);
            result.setSuccess(true);
            result.setData(insertResult);

            return ResponseEntity.ok(result);
        } catch (Exception e){
            result.setMessage(e.getMessage());
            result.setSuccess(false);
            result.setStatus(StatusCode.INTERNAL_SERVER_ERROR);

            return ResponseEntity.internalServerError().body(result);
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        BaseResponseDTO<UserReadResponseDTO> result = new BaseResponseDTO<>();
        userService.deleteUser(id);
        result.setMessage(ResponseMessage.DELETE_USER_SUCCESS);
        result.setStatus(StatusCode.OK);
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
            result.setMessage(ResponseMessage.UPDATE_USER_SUCCESS);
            result.setData(map);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            result.setStatus(StatusCode.INTERNAL_SERVER_ERROR);
            result.setSuccess(false);
            result.setMessage(ResponseMessage.INTERNAL_SERVER_ERROR);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }
}
