package encryption.damo.service;

import java.util.Objects;

import com.penta.scpdb.ScpDbAgent;
import com.penta.scpdb.ScpDbAgentException;
import encryption.damo.dto.response.damo.CipherHashResponse;
import encryption.damo.dto.response.damo.ExportKeyResponse;
import encryption.damo.enums.DAmoHashId;
import encryption.damo.enums.DAmoSecurityKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DAmoService {
    @Value("${penta.agent.path}")
    private String agentPath;

    /**
     * string 암호화 함수
     * 난수로 생성된 key 로만 암호화 가능
     *
     * @param keyword 암호화 대상
     * @param key 암호화 키
     * @return hex string
     */
    public String getEncode(String keyword, String key) {
        try {
            this.loggingJavaSystem();

            ScpDbAgent agent = new ScpDbAgent();

            return agent.ScpEncStr(agentPath, key, keyword);
        } catch (ScpDbAgentException se) {
            log.error(se.toString());
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            log.error(e.toString());
        }

        return null;
    }

    /**
     * string 암호화 함수
     * 난수로 생성된 key 로만 암호화 가능
     *
     * @param keyword 암호화 대상
     * @param key 암호화 키
     * @return base 64 string
     */
    public String getEncodeB64(String keyword, String key) {
        try {
            this.loggingJavaSystem();

            ScpDbAgent agent = new ScpDbAgent();

            return agent.ScpEncB64(agentPath, key, keyword);
        } catch (ScpDbAgentException se) {
            log.error(se.toString());
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            log.error(e.toString());
        }

        return null;
    }

    /**
     * 주민번호 string 암호화 함수
     * 난수로 생성된 key 로만 암호화 가능
     * 주민번호 format 검중, 전체 주민번호만 검증 가능
     *
     * @param keyword 주민번호
     * @param key 암호화 키
     * @return base 64 string
     */
    public String getEncodeB64RRN(String keyword, String key) {
        try {
            this.loggingJavaSystem();

            ScpDbAgent agent = new ScpDbAgent();

            return agent.ScpEncRRNB64(agentPath, key, keyword);
        } catch (ScpDbAgentException se) {
            log.error(se.toString());
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            log.error(e.toString());
        }

        return null;
    }

    /**
     * hex string 복호화 함수
     * 난수로 생성된 key 로만 복호화 가능
     *
     * @param keyword 복호화 대상
     * @param key 복호화 키
     * @return string
     */
    public String getDecode(String keyword, String key) {
        try {
            this.loggingJavaSystem();

            ScpDbAgent agent = new ScpDbAgent();

            return agent.ScpDecStr(agentPath, key, keyword);
        } catch (ScpDbAgentException se) {
            log.error(se.toString());
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            log.error(e.toString());
        }

        return null;
    }

    /**
     * base 64 string 복호화 함수
     * 난수로 생성된 key 로만 복호화 가능
     *
     * @param keyword 복호화 대상
     * @param key 복호화 키
     * @return string
     */
    public String getDecodeB64(String keyword, String key) {
        try {
            this.loggingJavaSystem();

            ScpDbAgent agent = new ScpDbAgent();

            return agent.ScpDecB64(agentPath, key, keyword);
        } catch (ScpDbAgentException se) {
            log.error(se.toString());
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            log.error(e.toString());
        }

        return null;
    }

    /**
     * key 에 매칭되는 value 정보 추출
     * 임의 생성 key 만 제공
     * 난수로 생성된 key 는 제공 안됨
     *
     * @param keyName 암호화 키
     * @return ExportKeyResponse.class
     */
    public ExportKeyResponse getScpExportKey(String keyName) {
        DAmoSecurityKey securityKey = DAmoSecurityKey.getSecurityKey(keyName);

        ExportKeyResponse response = ExportKeyResponse.builder()
                .key(keyName)
                .build();

        if (Objects.isNull(securityKey)) {
            return response;
        }

        this.loggingJavaSystem();

        try {
            ScpDbAgent agent = new ScpDbAgent();

            response.setValue(agent.ScpExportKey(agentPath, securityKey.getKey(), " "));

            return response;
        } catch (ScpDbAgentException se) {
            log.error(se.toString());
            response.setValue(se.getMessage());
            return response;
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            log.error(e.toString());
            response.setValue(e.getMessage());
            return response;
        }
    }

    public ExportKeyResponse getScpExportKeyServiceID(String keyName) {
        DAmoSecurityKey securityKey = DAmoSecurityKey.getSecurityKey(keyName);

        ExportKeyResponse response = ExportKeyResponse.builder()
                .key(keyName)
                .build();

        if (Objects.isNull(securityKey)) {
            return response;
        }

        this.loggingJavaSystem();

        try {
            ScpDbAgent agent = new ScpDbAgent();

            response.setValue(agent.ScpExportKeyServiceID(agentPath, securityKey.getServiceId(), " "));

            return response;
        } catch (ScpDbAgentException se) {
            log.error(se.toString());
            response.setValue(se.getMessage());
            return response;
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            log.error(e.toString());
            response.setValue(e.getMessage());
            return response;
        }
    }

    /**
     * key 에 매칭되는 context 정보 추출
     *
     * @param keyName 암호화 키
     * @return ExportKeyResponse.class
     */
    public ExportKeyResponse getScpExportContext(String keyName) {
        DAmoSecurityKey securityKey = DAmoSecurityKey.getSecurityKey(keyName);

        ExportKeyResponse response = ExportKeyResponse.builder()
                .key(keyName)
                .build();

        if (Objects.isNull(securityKey)) {
            return response;
        }

        this.loggingJavaSystem();

        try {
            ScpDbAgent agent = new ScpDbAgent();

            response.setValue(agent.ScpExportContext(agentPath, securityKey.getKey()));

            return response;
        } catch (ScpDbAgentException se) {
            log.error(se.toString());
            response.setValue(se.getMessage());
            return response;
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            log.error(e.toString());
            response.setValue(e.getMessage());
            return response;
        }
    }

    public ExportKeyResponse getScpExportContextServiceID(String keyName) {
        DAmoSecurityKey securityKey = DAmoSecurityKey.getSecurityKey(keyName);

        ExportKeyResponse response = ExportKeyResponse.builder()
                .key(keyName)
                .build();

        if (Objects.isNull(securityKey)) {
            return response;
        }

        this.loggingJavaSystem();

        try {
            ScpDbAgent agent = new ScpDbAgent();

            response.setValue(agent.ScpExportContextServiceID(agentPath, securityKey.getServiceId()));

            return response;
        } catch (ScpDbAgentException se) {
            log.error(se.toString());
            response.setValue(se.getMessage());
            return response;
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            log.error(e.toString());
            response.setValue(e.getMessage());
            return response;
        }
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
