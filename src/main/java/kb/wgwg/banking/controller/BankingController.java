package kb.wgwg.banking.controller;

import kb.wgwg.common.ResponseMessage;
import kb.wgwg.common.StatusCode;
import kb.wgwg.banking.dto.BankingDTO.*;
import kb.wgwg.common.dto.BaseResponseDTO;
import kb.wgwg.banking.service.BankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

import static kb.wgwg.common.ResponseMessage.INTERNAL_SERVER_ERROR;
import java.util.HashMap;
import java.util.Map;
import java.util.List;



@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/banking")
public class BankingController {
    private final BankingService bankingService;

    @PostMapping(value = "/read")
    public ResponseEntity<BaseResponseDTO> readBankingsByBankingDate(@RequestBody BankingListRequestDTO dto, @PageableDefault(size = 10) Pageable pageable) {
        BaseResponseDTO<Page<BankingListResponseDTO>> response = new BaseResponseDTO<>();
        try {
            Page<BankingListResponseDTO> result = bankingService.findBankingByYearAndMonth(dto.getBankingDate().getYear(), dto.getBankingDate().getMonthValue(), dto.getUserSeq(), pageable);
            response.setMessage("성공적으로 입출금 내역을 불러왔습니다.");
            response.setStatus(200);
            response.setSuccess(true);
            response.setData(result);
        } catch (EntityNotFoundException e) {
            response.setMessage(e.getMessage());
            response.setStatus(400);
            response.setSuccess(false);
        } catch (Exception e) {
            response.setMessage(INTERNAL_SERVER_ERROR);
            response.setStatus(500);
            response.setSuccess(false);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/read/{id}")
    public ResponseEntity<BaseResponseDTO> findBankingById(@PathVariable Long id) {
        BaseResponseDTO<BankingReadResponseDTO> response = new BaseResponseDTO<>();

        try {
            BankingReadResponseDTO result = bankingService.findBankingById(id);
            response.setMessage("성공적으로 입출금 내역을 불러왔습니다.");
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


    @PostMapping(value = "/update")
    public ResponseEntity<BaseResponseDTO> updateBankingHistory(@RequestBody BankingUpdateDTO dto) {
        BaseResponseDTO<BankingUpdateDTO> response = new BaseResponseDTO<>();
        try {
            BankingUpdateDTO result = bankingService.updateBanking(dto);
            response.setSuccess(true);
            response.setStatus(200);
            response.setData(result);
            response.setMessage("입출금 내역이 성공적으로 수정되었습니다.");
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

    @DeleteMapping(value = "/delete/{bankingId}")
    public ResponseEntity<BaseResponseDTO> deleteBankingHistory(@PathVariable(value = "bankingId") Long bankingId) {
        BaseResponseDTO<Void> response = new BaseResponseDTO<>();

        try {
            bankingService.deleteBankingHistory(bankingId);
            response.setMessage("입출금 내역이 성공적으로 삭제되었습니다.");
            response.setSuccess(true);
            response.setStatus(200);

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.setMessage(e.getMessage());
            response.setSuccess(false);
            response.setStatus(500);

            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PostMapping("/insert")
    public ResponseEntity bankingInsert(@RequestBody BankingInsertRequestDTO dto) {
        BaseResponseDTO response = new BaseResponseDTO<>();

        try {
            Long savedId = bankingService.insertBankingHistory(dto);
            Map<String, Long> map = new HashMap<>();
            map.put("bankingSeq", savedId);

            response.setStatus(StatusCode.OK);
            response.setMessage(ResponseMessage.BANKING_INSERT_SUCCESS);
            response.setSuccess(true);
            response.setData(map);

            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            response.setStatus(StatusCode.NOT_FOUND);
            response.setMessage(ResponseMessage.NOT_FOUND_USER);
            response.setSuccess(false);

            return ResponseEntity.badRequest().body(response);
        } catch (RuntimeException e) {
            e.printStackTrace();
            response.setStatus(StatusCode.INTERNAL_SERVER_ERROR);
            response.setMessage(e.getMessage());
            response.setSuccess(false);

            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PostMapping(value = "/read/category")
    public ResponseEntity<BaseResponseDTO> readCategoryProportion(@RequestBody ReadCategoryRequestDTO requestDTO) {
//        BaseResponseDTO<List<ReadCategoryResponseDTO>> result = new BaseResponseDTO<>();
        BaseResponseDTO<List<Map<String, Integer>>> result = new BaseResponseDTO<>();
        try {
            List<Map<String, Integer>> resultData = bankingService.readCategoryProportion(requestDTO);
            result.setMessage(ResponseMessage.READ_CATEGORY_SUCCESS);
            result.setStatus(StatusCode.OK);
            result.setSuccess(true);
            result.setData(resultData);
            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException e) {
            result.setMessage(e.getMessage());
            result.setStatus(StatusCode.NOT_FOUND);
            result.setSuccess(false);
            return ResponseEntity.badRequest().body(result);
        } catch (RuntimeException e) {
            result.setMessage(ResponseMessage.INTERNAL_SERVER_ERROR);
            result.setStatus(StatusCode.INTERNAL_SERVER_ERROR);
            result.setSuccess(false);
            return ResponseEntity.internalServerError().body(result);
        }
    }

    @PostMapping(value = "/read/total")
    public ResponseEntity<BaseResponseDTO> readCategoryProportion(@RequestBody ReadTotalRequestDTO requestDTO) {
        BaseResponseDTO<ReadTotalResponseDTO> result = new BaseResponseDTO<>();
        try {
            ReadTotalResponseDTO totalResponseDTO = bankingService.calculateTotalSpend(requestDTO);
            result.setMessage(ResponseMessage.READ_TOTAL_SUCCESS);
            result.setStatus(StatusCode.OK);
            result.setSuccess(true);
            result.setData(totalResponseDTO);
            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException e) {
            result.setMessage(e.getMessage());
            result.setStatus(StatusCode.NOT_FOUND);
            result.setSuccess(false);
            return ResponseEntity.badRequest().body(result);
        } catch (RuntimeException e) {
            result.setMessage(ResponseMessage.INTERNAL_SERVER_ERROR);
            result.setStatus(StatusCode.INTERNAL_SERVER_ERROR);
            result.setSuccess(false);
            return ResponseEntity.internalServerError().body(result);
        }
    }

    @PostMapping(value = "/read/reward")
    public ResponseEntity<BaseResponseDTO> readTotalReward(@RequestBody GetTotalRewardRequestDTO dto) {
        BaseResponseDTO<Integer> result = new BaseResponseDTO<>();

        try {
            int totalReward = bankingService.calculateReward(dto);
            result.setMessage(ResponseMessage.READ_TOTAL_SUCCESS);
            result.setStatus(StatusCode.OK);
            result.setSuccess(true);
            result.setData(totalReward);
            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException e) {
            result.setMessage(e.getMessage());
            result.setStatus(StatusCode.NOT_FOUND);
            result.setSuccess(false);
            return ResponseEntity.badRequest().body(result);
        } catch (RuntimeException e) {
            e.printStackTrace();
            result.setMessage(ResponseMessage.INTERNAL_SERVER_ERROR);
            result.setStatus(StatusCode.INTERNAL_SERVER_ERROR);
            result.setSuccess(false);
            return ResponseEntity.internalServerError().body(result);
        }
    }
}
