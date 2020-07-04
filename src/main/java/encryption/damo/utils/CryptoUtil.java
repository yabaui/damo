package encryption.damo.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import com.penta.scpdb.ScpDbAgent;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Component
@Slf4j
public class CryptoUtil {
    public static String aesEncryption(String encryptedString, String key, String iv) {
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());

            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");

            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

            byte[] encrypted = cipher.doFinal(encryptedString.getBytes());

            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public static String aesDecryption(String decryptedString, String key, String iv) {
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));

            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");

            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

            byte[] original = cipher.doFinal(Base64.getDecoder().decode(decryptedString));

            return new String(original);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public static String aesDecryptionEUCKR(String decryptedString, String key, String iv) {
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes("euc-kr"));

            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("euc-kr"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");

            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

            byte[] original = cipher.doFinal(Base64.getDecoder().decode(decryptedString));

            return new String(original);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public static String getCryptoKey(String cryptoKey) {
        if (StringUtils.isEmpty(cryptoKey)) return null;

        return cryptoKey.replaceAll("-", "").trim();
    }

    public static String getCryptoIv(String cryptoIv) {
        if (StringUtils.isEmpty(cryptoIv)) return null;

        return cryptoIv.substring(0, 16);
    }

    /**
     * provide damo
     * base 64 string 암호화 함수
     * 난수로 생성된 key 로만 암호화 가능
     *
     * @param encryptedString 암호화 대상
     * @param key 암호화 키 enum
     * @return base 64 string
     */
    public static String encryptionBase64(String encryptedString, String key, String agentPath) {
        if (StringUtils.isEmpty(key)
                || StringUtils.isEmpty(agentPath)) {
            return null;
        }

        try {
            ScpDbAgent agent = new ScpDbAgent();

            return agent.ScpEncB64(agentPath, key, encryptedString);
        } catch (UnsatisfiedLinkError | Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * provide damo
     * base 64 string 복호화 함수
     * 난수로 생성된 key 로만 복호화 가능
     *
     * @param encryptedString 복호화 대상
     * @param key 암호화 키 enum
     * @return string
     */
    public static String decryptionBase64(String encryptedString, String key, String agentPath) {
        if (StringUtils.isEmpty(key)
                || StringUtils.isEmpty(agentPath)) {
            return null;
        }

        try {
            ScpDbAgent agent = new ScpDbAgent();

            return agent.ScpDecB64(agentPath, key, encryptedString);
        } catch (UnsatisfiedLinkError | Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * provide damo
     * string 암호화 함수
     * 난수로 생성된 key 로만 암호화 가능
     *
     * @param encryptedString 암호화 대상
     * @param key 암호화 키 enum
     * @return base 64 string
     */
    public static String encryptionHex(String encryptedString, String key, String agentPath) {
        if (StringUtils.isEmpty(key)
                || StringUtils.isEmpty(agentPath)) {
            return null;
        }

        try {
            ScpDbAgent agent = new ScpDbAgent();

            return agent.ScpEncStr(agentPath, key, encryptedString);
        } catch (UnsatisfiedLinkError | Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * provide damo
     * string 복호화 함수
     * 난수로 생성된 key 로만 복호화 가능
     *
     * @param encryptedString 복호화 대상
     * @param key 암호화 키 enum
     * @return string
     */
    public static String decryptionHex(String encryptedString, String key, String agentPath) {
        if (StringUtils.isEmpty(key)
                || StringUtils.isEmpty(agentPath)) {
            return null;
        }

        try {
            ScpDbAgent agent = new ScpDbAgent();

            return agent.ScpDecStr(agentPath, key, encryptedString);
        } catch (UnsatisfiedLinkError | Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * provide damo
     * key 에 매칭되는 value 정보 추출
     * 임의 생성 key 만 제공
     * 난수로 생성된 key 는 제공 안됨
     *
     * @param key 암호화 키 enum
     * @return string
     */
    public static String exportCustomCrypto(String key, String agentPath) {
        if (StringUtils.isEmpty(key)
                || StringUtils.isEmpty(agentPath)) {
            return null;
        }

        try {
            ScpDbAgent agent = new ScpDbAgent();

            return agent.ScpExportKey(agentPath, key, " ");
        } catch (UnsatisfiedLinkError | Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public static String decryptedUTF8(String value) {
        final String devKey = "54204599-a1fa-4c10-96a8-99af0d38f8f6";

        String privateKey = getCryptoKey(devKey);
        String privateIv = getCryptoIv(privateKey);

        String decryptedValue = aesDecryption(value, privateKey, privateIv);

        if (Objects.nonNull(decryptedValue)) {
            return decryptedValue;
        }

        final String stgKey = "ce545558-fcc0-4aa4-adc2-06257c8d26c8";

        privateKey = getCryptoKey(stgKey);
        privateIv = getCryptoIv(privateKey);

        decryptedValue = aesDecryption(value, privateKey, privateIv);

        if (Objects.nonNull(decryptedValue)) {
            return decryptedValue;
        }

        final String prodKey = "754aa6dd-bcd4-4e06-a95f-9bcc3dd407aa";

        privateKey = getCryptoKey(prodKey);
        privateIv = getCryptoIv(privateKey);

        decryptedValue = aesDecryption(value, privateKey, privateIv);

        if (Objects.nonNull(decryptedValue)) {
            return decryptedValue;
        }

        final String memberKey = "64ddc7b5-9aef-4d7a-99b2-65977fbe41cc";

        privateKey = getCryptoKey(memberKey);
        privateIv = getCryptoIv(privateKey);

        return aesDecryption(value, privateKey, privateIv);
    }

    public static String decryptedEUCKR(String value) {
        final String devKey = "54204599-a1fa-4c10-96a8-99af0d38f8f6";

        String privateKey = getCryptoKey(devKey);
        String privateIv = getCryptoIv(privateKey);

        String decryptedValue = aesDecryptionEUCKR(value, privateKey, privateIv);

        if (Objects.nonNull(decryptedValue)) {
            return decryptedValue;
        }

        final String stgKey = "ce545558-fcc0-4aa4-adc2-06257c8d26c8";

        privateKey = getCryptoKey(stgKey);
        privateIv = getCryptoIv(privateKey);

        decryptedValue = aesDecryptionEUCKR(value, privateKey, privateIv);

        if (Objects.nonNull(decryptedValue)) {
            return decryptedValue;
        }

        final String prodKey = "754aa6dd-bcd4-4e06-a95f-9bcc3dd407aa";

        privateKey = getCryptoKey(prodKey);
        privateIv = getCryptoIv(privateKey);

        decryptedValue = aesDecryptionEUCKR(value, privateKey, privateIv);

        if (Objects.nonNull(decryptedValue)) {
            return decryptedValue;
        }

        final String memberKey = "64ddc7b5-9aef-4d7a-99b2-65977fbe41cc";

        privateKey = getCryptoKey(memberKey);
        privateIv = getCryptoIv(privateKey);

        return aesDecryptionEUCKR(value, privateKey, privateIv);
    }
}
