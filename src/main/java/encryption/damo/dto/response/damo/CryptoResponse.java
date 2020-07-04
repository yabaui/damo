package encryption.damo.dto.response.damo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CryptoResponse {
    private final String input;
    private final String output;
}
