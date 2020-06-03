package encryption.damo.dto.response.damo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExportKeyResponse {
    private String key;
    private String value;
}
