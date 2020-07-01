package encryption.damo.model.btob;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "ins_market_subscription_history")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class InsMarketSubscriptionHistory implements Serializable {
    @Id
    @Column(name = "transaction_no", length = 256)
    private String transactionNo;

    @Column(name = "mb_uid")
    private Long mbUid;

    @ManyToOne(targetEntity = InsMarket.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "market_uid")
    private InsMarket insMarket;

    @Column(name = "subscription_type", length = 3, columnDefinition = "varchar(3) default 'G'", nullable = false)
    private String subscriptionType;

    @Column(name = "inflow_channel", length = 2, columnDefinition = "varchar(2) default 'M'", nullable = false)
    private String inflowChannel;

    @Column(name = "insurance_code", length = 3)
    private String insuranceCode;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    @Column(name = "policy_start_date", length = 8)
    private String policyStartDate;

    @Column(name = "policy_end_date", length = 8)
    private String policyEndDate;

    @Column(name = "policy_start_hour", length = 2, columnDefinition = "varchar(2) default '00'", nullable = false)
    private String policyStartHour = "00";

    @Column(name = "policy_end_hour", length = 2, columnDefinition = "varchar(2) default '00'", nullable = false)
    private String policyEndHour = "00";

    @Column(name = "policy_start_minute", length = 2, columnDefinition = "varchar(2) default '00'", nullable = false)
    private String policyStartMinute = "00";

    @Column(name = "policy_end_minute", length = 2, columnDefinition = "varchar(2) default '00'", nullable = false)
    private String policyEndMinute = "00";

    @Column(name = "total_insurance_amount")
    private Long totalInsuranceAmount;

    @Column(name = "status", length = 1)
    private String status;

    @Column(name = "insurance_no", length = 64)
    private String insuranceNo;

    @Column(name = "email", length = 256)
    private String email;

    @Column(name = "name", length = 256)
    private String name;

    @Column(name = "eng_name", length = 256)
    private String engName;

    @Column(name = "birth", length = 8)
    private String birth;

    @Column(name = "sex", length = 1)
    private String sex;

    @Setter
    @Column(name = "ssn")
    private String ssn;

    @Column(name = "member_count")
    private Long memberCount;

    @Column(name = "insurance_json_data", columnDefinition = "text")
    private String insuranceJsonData;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "insured_at")
    private Date insuredAt;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private Timestamp createdDate;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedDate;

    @PrePersist
    public void prePersist() {
        this.policyStartHour = this.policyStartHour == null ? "00" : this.policyStartHour;
        this.policyEndHour = this.policyEndHour == null ? "00" : this.policyEndHour;
        this.policyStartMinute = this.policyStartMinute == null ? "00" : this.policyStartMinute;
        this.policyEndMinute = this.policyEndMinute == null ? "00" : this.policyEndMinute;
    }
}
