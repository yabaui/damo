package encryption.damo.dto.response.damo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CipherHashResponse {
    private String agentCipherHash;
    private String agentCipherHashB64;
    private String agentCipherHashBase64;
    private String agentCipherHashString;
    private String agentCipherHashStringB64;
}
