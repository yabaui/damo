package encryption.damo.model.member;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(
        name = "health_checkup_crypto",
        indexes = {
                @Index(name = "health_checkup_crypto_index_1", columnList = "mb_uid"),
                @Index(name = "health_checkup_crypto_index_2", columnList = "mb_uid,checkup_date"),
                @Index(name = "health_checkup_crypto_index_3", columnList = "mb_uid,checkup_date,checkup_type")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HealthCheckupCrypto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid")
    private Long uid;

    @Column(name = "acid", length = 64)
    private String acid;

    @Column(name = "mb_uid", nullable = false)
    private Long mbUid;

    @Column(name = "checkup_date", nullable = false, length = 10)
    private String checkupDate;

    @Column(name = "checkup_institution", length = 128)
    private String checkupInstitution;

    @Column(name = "checkup_type", nullable = false, length = 64)
    private String checkupType;

    @Setter
    @Column(name = "optional", nullable = false, length = 512)
    private String optional;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    @Setter
    @Transient
    private String optionalDecrypt = "";

    @Builder
    public HealthCheckupCrypto (String acid, @NonNull Long mbUid, @NonNull String checkupDate, String checkupInstitution,
                                @NonNull String checkupType, @NonNull String optional, Timestamp deletedAt) {
        this.acid = acid;
        this.mbUid = mbUid;
        this.checkupDate = checkupDate;
        this.checkupInstitution = checkupInstitution;
        this.checkupType = checkupType;
        this.optional = optional;
        this.deletedAt = deletedAt;
    }
}
