package encryption.damo.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DAmoStringUtils {
    public static boolean isNumeric(String value) {
        if (org.springframework.util.StringUtils.isEmpty(value)) {
            return false;
        }

        try {
            Double.parseDouble(value);
        } catch (NumberFormatException nfe) {
            return false;
        }

        return true;
    }
}
