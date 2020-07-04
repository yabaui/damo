package encryption.damo.model.btob;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "insurance_contract")
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InsuranceContract implements Serializable {
    @Column(columnDefinition = "bigint auto_increment")
    private Long id;

    @Id
    @Column(name = "transaction_no")
    private String transactionNo;

    @ManyToOne
    @JoinColumn(name = "partner_company_code")
    private PartnerCompany partnerCompany;

    @ManyToOne
    @JoinColumn(name = "product_plan_id")
    private ProductPlan productPlan;

    @Column
    private String status;

    @Column(name = "policy_no")
    private String policyNo;

    @Column(name = "policy_start_date", length = 8)
    private String policyStartDate;

    @Column(name = "policy_start_hour", columnDefinition = "varchar(2) default '00'", nullable = false)
    private String policyStartHour;

    @Column(name = "policy_start_minute", columnDefinition = "varchar(2) default '00'", nullable = false)
    private String policyStartMinute;

    @Column(name = "policy_end_date", length = 8)
    private String policyEndDate;

    @Column(name = "policy_end_hour", columnDefinition = "varchar(2) default '00'", nullable = false)
    private String policyEndHour;

    @Column(name = "policy_end_minute", columnDefinition = "varchar(2) default '00'", nullable = false)
    private String policyEndMinute;

    @Column(name = "total_premium")
    private String totalPremium;

    @Column(name = "insured_date", length = 8)
    private String insuredDate;

    @Column(name = "cancelled_date", length = 8)
    private String cancelledDate;

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
