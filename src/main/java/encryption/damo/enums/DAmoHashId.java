package encryption.damo.enums;

import lombok.Getter;

public enum DAmoHashId {
    ID_SHA1(70),
    ID_SHA256(71),
    ID_SHA384(72),
    ID_SHA512(73),
    ID_HAS160(74),
    ID_MD5(75)
    ;

    @Getter
    private int id;

    DAmoHashId(int id) {
        this.id = id;
    }
}
