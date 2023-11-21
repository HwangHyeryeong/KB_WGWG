package kb.wgwg.common.dto;

import lombok.Data;

@Data
public class BaseResponseDTO<T> {
    private int status;

    private boolean success;

    private String message;

    private T data;
}