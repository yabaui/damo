package encryption.damo.model.member.key;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class HealthCheckupBioAgeLifeExpectancyCryptoKey implements Serializable {

    @Column(name = "mb_uid", nullable = false, updatable = false)
    private Long mbUid;

    @Column(name = "checkup_date", nullable = false, updatable = false, length = 10)
    private String checkupDate;
}
