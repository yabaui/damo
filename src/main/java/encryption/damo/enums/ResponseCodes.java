package encryption.damo.enums;

import lombok.Getter;

public enum ResponseCodes {
    SUCCESS("0000", "안내", "성공"),
    FAIL("9999", "안내", "실패");

    @Getter
    private String code;
    @Getter
    private String title;
    @Getter
    private String message;

    ResponseCodes(String code, String title, String message) {
        this.code = code;
        this.title = title;
        this.message = message;
    }
}
