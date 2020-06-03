package encryption.damo.dto.response.global;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseObject<T> {
    private String code;

    private String title;

    private String message;

    private T data;
}
