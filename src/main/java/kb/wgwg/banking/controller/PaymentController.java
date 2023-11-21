package kb.wgwg.banking.controller;

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
            result.setStatus(200);
            result.setSuccess(true);
            result.setMessage("납입금 입금 확인 되었습니다.");
        }catch (Exception e){
            result.setStatus(404);
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
                result.setStatus(200);
                result.setMessage("총 납입금이 수정되었습니다.");
            }catch (Exception e){
                result.setSuccess(false);
                result.setStatus(404);
                result.setMessage(e.getMessage());
            }
        }else{
            try{
                paymentService.updateTotalDeposit(requestDTO);
                result.setSuccess(true);
                result.setStatus(200);
                result.setMessage("총 보증금이 수정되었습니다.");
            }catch (Exception e){
                result.setSuccess(false);
                result.setStatus(404);
                result.setMessage(e.getMessage());
            }
        }
        return ResponseEntity.ok(result);
    }
}
