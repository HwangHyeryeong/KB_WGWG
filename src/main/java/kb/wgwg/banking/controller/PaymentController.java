package kb.wgwg.banking.controller;

import kb.wgwg.common.ResponseMessage;
import kb.wgwg.common.StatusCode;
import kb.wgwg.common.dto.BaseResponseDTO;
import kb.wgwg.challenge.dto.ChallengeDTO.*;
import kb.wgwg.banking.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/payment")
public class PaymentController {

    private final PaymentService paymentService;
    @PostMapping("/coffee/update/success")
    public ResponseEntity<BaseResponseDTO> updateIsSuccess(@RequestBody UpdateIsSuccessRequestDTO requestDTO){
        BaseResponseDTO result = new BaseResponseDTO<>();
        try {
            paymentService.updateIsSuccess(requestDTO);
            result.setStatus(StatusCode.OK);
            result.setSuccess(true);
            result.setMessage(ResponseMessage.INSERT_PAYMENT_SUCCESS);
        }catch (Exception e){
            result.setStatus(StatusCode.NOT_FOUND);
            result.setMessage(e.getMessage());
            result.setSuccess(false);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/update/totalamount")
    public ResponseEntity<BaseResponseDTO> updateTotalAsset(@RequestBody UpdateAmountRequestDTO requestDTO){
        BaseResponseDTO result = new BaseResponseDTO();
        if(requestDTO.getChallengeType().equals("COFFEE")){
            try{
                paymentService.updateTotalAsset(requestDTO);
                result.setSuccess(true);
                result.setStatus(StatusCode.OK);
                result.setMessage(ResponseMessage.UPDATE_TOTAL_PAYMENT_SUCCESS);
            }catch (Exception e){
                result.setSuccess(false);
                result.setStatus(StatusCode.NOT_FOUND);
                result.setMessage(e.getMessage());
            }
        }else{
            try{
                paymentService.updateTotalDeposit(requestDTO);
                result.setSuccess(true);
                result.setStatus(StatusCode.OK);
                result.setMessage(ResponseMessage.UPDATE_TOTAL_DEPOSIT_SUCCESS);
            }catch (Exception e){
                result.setSuccess(false);
                result.setStatus(StatusCode.NOT_FOUND);
                result.setMessage(e.getMessage());
            }
        }
        return ResponseEntity.ok(result);
    }
}
