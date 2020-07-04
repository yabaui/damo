package encryption.damo.model.member;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(
        name = "family_health_checkup_crypto",
        indexes = {
                @Index(name = "family_health_checkup_crypto_index_1", columnList = "mb_uid"),
                @Index(name = "family_health_checkup_crypto_index_2", columnList = "mb_uid,risk_score")
        }
)
@Getter
// jpa 에서 프록시 생성을 위해 반드시 하나의 생성자는 필요하나, 접근 권한은 protected 면 충분하다.
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// builder 사용을 위해 제공, 내부 Builder 구현시 제거 가능
//@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FamilyHealthCheckupCrypto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid", nullable = false, updatable = false)
    private Long uid;

    @Column(name = "mb_uid", nullable = false, updatable = false)
    private Long mbUid;

    @Column(name = "question", nullable = false, length = 20)
    private String question;

    @Column(name = "question_key", nullable = false, length = 40)
    private String questionKey;

    @Column(name = "item", nullable = false, length = 60)
    private String item;

    @Column(name = "item_key", nullable = false, length = 40)
    private String itemKey;

    @Setter
    @Column(name = "risk_score", nullable = false, length = 128)
    private String riskScore;

    @Column(name = "target_diseases_key", nullable = false, length = 40)
    private String targetDiseasesKey;

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
    private int riskScoreDecrypt = 0;

    @Builder
    public FamilyHealthCheckupCrypto(@NonNull Long mbUid, @NonNull String question, @NonNull String questionKey,
                                     @NonNull String item, @NonNull String itemKey, @NonNull String riskScore,
                                     @NonNull String targetDiseasesKey, Timestamp deletedAt) {
        this.mbUid = mbUid;
        this.question = question;
        this.questionKey = questionKey;
        this.item = item;
        this.itemKey = itemKey;
        this.riskScore = riskScore;
        this.targetDiseasesKey = targetDiseasesKey;
        this.deletedAt = deletedAt;
    }
}
