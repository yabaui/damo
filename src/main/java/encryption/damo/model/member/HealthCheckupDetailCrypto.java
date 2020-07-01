package encryption.damo.model.member;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(
        name = "health_checkup_detail_crypto",
        indexes = {
                @Index(name = "health_checkup_detail_crypto_index_1", columnList = "mb_uid"),
                @Index(name = "health_checkup_detail_crypto_index_2", columnList = "mb_uid,checkup_date"),
                @Index(name = "health_checkup_detail_crypto_index_3", columnList = "mb_uid,checkup_date,target_diseases_key"),
                @Index(name = "health_checkup_detail_crypto_index_4", columnList = "mb_uid,checkup_date,item_key")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HealthCheckupDetailCrypto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid")
    private Long uid;

    @Column(name = "mb_uid", nullable = false)
    private Long mbUid;

    @Column(name = "checkup_type", length = 64)
    private String checkupType;

    @Column(name = "checkup_date", nullable = false, length = 10)
    private String checkupDate;

    @Setter
    @Column(name = "target_diseases", nullable = false, length = 256)
    private String targetDiseases;

    @Column(name = "inspection_item", nullable = false, length = 128)
    private String inspectionItem;

    @Column(name = "reference_unit", length = 16)
    private String referenceUnit;

    @Setter
    @Column(name = "checkup_value", nullable = false, length = 512)
    private String checkupValue;

    @Setter
    @Column(name = "target_diseases_key", nullable = false, length = 256)
    private String targetDiseasesKey;

    @Column(name = "item_key", nullable = false, length = 40)
    private String itemKey;

    @Column(name = "reference_normal", nullable = false, length = 64)
    private String referenceNormal;

    @Column(name = "reference_caution", nullable = false, length = 64)
    private String referenceCaution;

    @Column(name = "reference_danger", nullable = false, length = 64)
    private String referenceDanger;

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
    private String targetDiseasesDecrypt;

    @Setter
    @Transient
    private String targetDiseasesKeyDecrypt;

    @Setter
    @Transient
    private String checkupValueDecrypt;

    @Builder
    public HealthCheckupDetailCrypto(@NonNull Long mbUid, String checkupType, @NonNull String checkupDate,
                                     @NonNull String targetDiseases, @NonNull String inspectionItem, String referenceUnit,
                                     @NonNull String checkupValue, @NonNull String targetDiseasesKey, @NonNull String itemKey,
                                     @NonNull String referenceNormal, @NonNull String referenceCaution,
                                     @NonNull String referenceDanger, Timestamp deletedAt, String targetDiseasesDecrypt,
                                     String targetDiseasesKeyDecrypt, String checkupValueDecrypt) {
        this.mbUid = mbUid;
        this.checkupType = checkupType;
        this.checkupDate = checkupDate;
        this.targetDiseases = targetDiseases;
        this.inspectionItem = inspectionItem;
        this.referenceUnit = referenceUnit;
        this.checkupValue = checkupValue;
        this.targetDiseasesKey = targetDiseasesKey;
        this.itemKey = itemKey;
        this.referenceNormal = referenceNormal;
        this.referenceCaution = referenceCaution;
        this.referenceDanger = referenceDanger;
        this.deletedAt = deletedAt;

        this.targetDiseasesDecrypt = targetDiseasesDecrypt;
        this.targetDiseasesKeyDecrypt = targetDiseasesKeyDecrypt;
        this.checkupValueDecrypt = checkupValueDecrypt;
    }
}
