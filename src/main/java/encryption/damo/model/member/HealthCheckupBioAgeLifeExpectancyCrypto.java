package encryption.damo.model.member;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;
import encryption.damo.model.member.key.HealthCheckupBioAgeLifeExpectancyCryptoKey;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(
        name = "health_checkup_bio_age_life_expectancy_crypto",
        indexes = {
                @Index(name = "health_checkup_bio_age_life_expectancy_crypto_index_1", columnList = "mb_uid"),
                @Index(name = "health_checkup_bio_age_life_expectancy_crypto_index_2", columnList = "mb_uid,checkup_date")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(HealthCheckupBioAgeLifeExpectancyCryptoKey.class)
public class HealthCheckupBioAgeLifeExpectancyCrypto implements Serializable {

    @Id
    @Column(name = "mb_uid", nullable = false)
    private Long mbUid;

    @Id
    @Column(name = "checkup_date", nullable = false, length = 512)
    private String checkupDate;

    @Setter
    @Column(name = "aging_index", length = 512)  // 노화 지수
    private String agingIndex;

    @Setter
    @Column(name = "aging_rank", length = 512)   // 노화 등수
    private String agingRank;

    @Setter
    @Column(name = "tle", length = 512)  // 기대 수명
    private String tle;

    @Setter
    @Column(name = "tle_avg", length = 512)  // 평균 기대수명
    private String tleAvg;

    @Setter
    @Column(name = "caa", length = 512)  // 심장나이
    private String caa;

    @Setter
    @Column(name = "hea", length = 512)  // 간나이
    private String hea;

    @Setter
    @Column(name = "paa", length = 512)  // 췌장나이
    private String paa;

    @Setter
    @Column(name = "rea", length = 512)  // 신장나이
    private String rea;

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
    private String agingIndexDecrypt;

    @Setter
    @Transient
    private String agingRankDecrypt;

    @Setter
    @Transient
    private String totalGuideDecrypt;

    @Setter
    @Transient
    private String tleDecrypt;

    @Setter
    @Transient
    private String tleAvgDecrypt;

    @Setter
    @Transient
    private String caaDecrypt;

    @Setter
    @Transient
    private String heaDecrypt;

    @Setter
    @Transient
    private String paaDecrypt;

    @Setter
    @Transient
    private String reaDecrypt;

    @Builder
    public HealthCheckupBioAgeLifeExpectancyCrypto (@NonNull Long mbUid, @NonNull String checkupDate, String agingIndex,
                                                    String agingRank, String tle, String tleAvg, String caa, String hea,
                                                    String paa, String rea, Timestamp deletedAt) {
        this.mbUid = mbUid;
        this.checkupDate = checkupDate;
        this.agingIndex = agingIndex;
        this.agingRank = agingRank;
        this.tle = tle;
        this.tleAvg = tleAvg;
        this.caa = caa;
        this.hea = hea;
        this.paa = paa;
        this.rea = rea;
        this.deletedAt = deletedAt;
    }
}
