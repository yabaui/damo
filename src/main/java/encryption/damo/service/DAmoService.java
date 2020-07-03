package encryption.damo.service;

import com.penta.scpdb.ScpDbAgent;
import com.penta.scpdb.ScpDbAgentException;
import encryption.damo.dto.response.damo.CipherHashResponse;
import encryption.damo.dto.response.damo.CryptoResponse;
import encryption.damo.enums.DAmoHashId;
import encryption.damo.utils.CryptoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DAmoService {
    @Value("${damo.agent.path}")
    private String agentPath;
    @Value("${btob.security.personal.key}")
    private String securityKey;
    @Value("${btob.security.finance.key}")
    private String financeSecurityKey;

    /**
     * string 암호화 함수
     * 난수로 생성된 key 로만 암호화 가능
     *
     * @param value 암호화 대상
     * @return CryptoResponse hex-string
     */
    public CryptoResponse getEncode(String value) {
        return CryptoResponse.builder()
                .input(value)
                .output(CryptoUtil.encryptionHex(value, securityKey, agentPath))
                .build();
    }

    /**
     * string 암호화 함수
     * 난수로 생성된 key 로만 암호화 가능
     *
     * @param value 암호화 대상
     * @return CryptoResponse base64-string
     */
    public CryptoResponse getEncodeB64(String value) {
        return CryptoResponse.builder()
                .input(value)
                .output(CryptoUtil.encryptionBase64(value, securityKey, agentPath))
                .build();
    }

    /**
     * hex string 복호화 함수
     * 난수로 생성된 key 로만 복호화 가능
     *
     * @param value 복호화 대상
     * @return string
     */
    public CryptoResponse getDecode(String value) {
        return CryptoResponse.builder()
                .input(value)
                .output(CryptoUtil.decryptionHex(value, securityKey, agentPath))
                .build();
    }

    /**
     * base 64 string 복호화 함수
     * 난수로 생성된 key 로만 복호화 가능
     *
     * @param value 복호화 대상
     * @return string
     */
    public CryptoResponse getDecodeB64(String value) {
        return CryptoResponse.builder()
                .input(value)
                .output(CryptoUtil.decryptionBase64(value, securityKey, agentPath))
                .build();
    }

    /**
     * key 에 매칭되는 value 정보 추출
     * 임의 생성 key 만 제공
     * 난수로 생성된 key 는 제공 안됨
     *
     * @param key 암호화 키
     * @return ExportKeyResponse.class
     */
    public CryptoResponse getScpExportKey(String key) {
        return CryptoResponse.builder()
                .input(key)
                .output(CryptoUtil.exportCustomCrypto(key, agentPath))
                .build();
    }

    /**
     * 단방향 암호화 함수
     *
     * @param keyword 암호화 대상
     */
    public CipherHashResponse getCipherHash(String keyword) {
        this.loggingJavaSystem();

        return CipherHashResponse.builder()
                .agentCipherHash(this.executeAgentCipherHash(keyword))
                .agentCipherHashB64(this.executeAgentCipherHashB64(keyword))
                .agentCipherHashBase64(this.executeAgentCipherHashBase64(keyword))
                .agentCipherHashString(this.executeAgentCipherHashString(keyword))
                .agentCipherHashStringB64(this.executeAgentCipherHashStringB64(keyword))
                .build();
    }

    /**
     * 단방향 암호화 - byte[]
     *
     * @param keyword 암호화 대상
     */
    private String executeAgentCipherHash(String keyword) {
        try {
            ScpDbAgent agent = new ScpDbAgent();
            agent.AgentInit(agentPath);

            return new String(agent.AgentCipherHash(DAmoHashId.ID_SHA256.getId(), keyword));
        } catch (ScpDbAgentException se) {
            log.error("executeAgentCipherHash error \n");
            log.error(se.toString());
        } catch (Exception e) {
            log.error("executeAgentCipherHash error \n");
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            log.error(e.toString());
        }

        return null;
    }

    /**
     * 단방향 암호화 - byte[] base64
     * AgentCipherHashBase64 wrapper
     *
     * @param keyword 암호화 대상
     */
    private String executeAgentCipherHashB64(String keyword) {
        try {
            ScpDbAgent agent = new ScpDbAgent();
            agent.AgentInit(agentPath);

            return new String(agent.AgentCipherHashB64(DAmoHashId.ID_SHA256.getId(), keyword));
        } catch (ScpDbAgentException se) {
            log.error("executeAgentCipherHashB64 error \n");
            log.error(se.toString());
        } catch (Exception e) {
            log.error("executeAgentCipherHashB64 error \n");
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            log.error(e.toString());
        }

        return null;
    }

    /**
     * 단방향 암호화 - byte[] base64
     *
     * @param keyword 암호화 대상
     */
    private String executeAgentCipherHashBase64(String keyword) {
        try {
            ScpDbAgent agent = new ScpDbAgent();
            agent.AgentInit(agentPath);

            return new String(agent.AgentCipherHashBase64(DAmoHashId.ID_SHA256.getId(), keyword));
        } catch (ScpDbAgentException se) {
            log.error("executeAgentCipherHashBase64 error \n");
            log.error(se.toString());
        } catch (Exception e) {
            log.error("executeAgentCipherHashBase64 error \n");
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            log.error(e.toString());
        }

        return null;
    }

    /**
     * 단방향 암호화 - hex string utf-8
     *
     * @param keyword 암호화 대상
     */
    private String executeAgentCipherHashString(String keyword) {
        try {
            ScpDbAgent agent = new ScpDbAgent();
            agent.AgentInit(agentPath);

            return agent.AgentCipherHashString(DAmoHashId.ID_SHA256.getId(), keyword);
        } catch (ScpDbAgentException se) {
            log.error("executeAgentCipherHashString error \n");
            log.error(se.toString());
        } catch (Exception e) {
            log.error("executeAgentCipherHashString error \n");
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            log.error(e.toString());
        }

        return null;
    }

    /**
     * 단방향 암호화 - hex string utf-8 or euc-kr
     *
     * @param keyword 암호화 대상
     */
    private String executeAgentCipherHashStringUTF8(String keyword) {
        try {
            ScpDbAgent agent = new ScpDbAgent();
            agent.AgentInit(agentPath);

            return agent.AgentCipherHashString(DAmoHashId.ID_SHA256.getId(), keyword, "utf-8");
        } catch (ScpDbAgentException se) {
            log.error("executeAgentCipherHashStringUTF8 error \n");
            log.error(se.toString());
        } catch (Exception e) {
            log.error("executeAgentCipherHashStringUTF8 error \n");
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            log.error(e.toString());
        }

        return null;
    }

    /**
     * 단방향 암호화 - base64 string utf-8
     *
     * @param keyword 암호화 대상
     */
    private String executeAgentCipherHashStringB64(String keyword) {
        try {
            ScpDbAgent agent = new ScpDbAgent();
            agent.AgentInit(agentPath);

            return agent.AgentCipherHashStringB64(DAmoHashId.ID_SHA256.getId(), keyword);
        } catch (ScpDbAgentException se) {
            log.error("executeAgentCipherHashStringB64 error \n");
            log.error(se.toString());
        } catch (Exception e) {
            log.error("executeAgentCipherHashStringB64 error \n");
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            log.error(e.toString());
        }

        return null;
    }

    /**
     * 단방향 암호화 - base64 string utf-8 or euc-kr
     *
     * @param keyword 암호화 대상
     */
    private String executeAgentCipherHashStringB64UTF8(String keyword) {
        try {
            ScpDbAgent agent = new ScpDbAgent();
            agent.AgentInit(agentPath);

            return agent.AgentCipherHashStringB64(DAmoHashId.ID_SHA256.getId(), keyword, "utf-8");
        } catch (ScpDbAgentException se) {
            log.error("executeAgentCipherHashStringB64UTF8 error \n");
            log.error(se.toString());
        } catch (Exception e) {
            log.error("executeAgentCipherHashStringB64UTF8 error \n");
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            log.error(e.toString());
        }

        return null;
    }

    private void loggingJavaSystem() {
        log.info("JAVA CLASS PATH : " + System.getProperty("java.class.path"));
        log.info("JAVA LIBRARY PATH : " + System.getProperty("java.library.path"));
    }
}
