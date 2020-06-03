package encryption.damo.enums;

import java.util.Arrays;

import lombok.Getter;

public enum DAmoSecurityKey {
    KEY_DB_SECURITY("KEY1", "BOMAPP-DEV"),
    KEY_TEST_SECURITY("KEY2", "TEST01");

    @Getter
    private String key;

    @Getter
    private String serviceId;

    DAmoSecurityKey(String key, String serviceId) {
        this.key = key;
        this.serviceId = serviceId;
    }

    public static DAmoSecurityKey getSecurityKey(String key) {
        return Arrays.stream(DAmoSecurityKey.values())
                .filter(securityKey -> securityKey.key.equals(key))
                .findFirst()
                .orElse(null);
    }
}
