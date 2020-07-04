package encryption.damo.model.member;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;
import encryption.damo.model.member.key.HealthCheckupBioAgeAnalysisGuideCryptoKey;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(
        name = "health_checkup_bio_age_analysis_guide_crypto",
        indexes = {
                @Index(name = "health_checkup_bio_age_analysis_guide_crypto_index_1", columnList = "mb_uid"),
                @Index(name = "health_checkup_bio_age_analysis_guide_crypto_index_2", columnList = "mb_uid,checkup_date")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(HealthCheckupBioAgeAnalysisGuideCryptoKey.class)
public class HealthCheckupBioAgeAnalysisGuideCrypto implements Serializable {

    @Id
    @Column(name = "mb_uid", nullable = false)
    private Long mbUid;

    @Id
    @Column(name = "checkup_date", nullable = false, length = 10)
    private String checkupDate;

    @Column(name = "age", length = 8)  // 나이
    private String age;

    @Setter
    @Column(name = "bio_age", length = 512)   // 생체 나이
    private String bioAge;

    @Setter
    @Column(name = "total_guide", columnDefinition = "text")  // 종합 가이드
    private String totalGuide;

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
    private String bioAgeDecrypt = "";

    @Setter
    @Transient
    private String totalGuideDecrypt = "";

    @Builder
    public HealthCheckupBioAgeAnalysisGuideCrypto(@NonNull Long mbUid, @NonNull String checkupDate, String age,
                                                  String bioAge, String totalGuide, Timestamp deletedAt) {
        this.mbUid = mbUid;
        this.checkupDate = checkupDate;
        this.age = age;
        this.bioAge = bioAge;
        this.totalGuide = totalGuide;
        this.deletedAt = deletedAt;
    }
}
