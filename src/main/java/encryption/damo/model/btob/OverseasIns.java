package encryption.damo.model.btob;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "overseas_ins")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class OverseasIns implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_no")
    private InsuranceContract insuranceContract;

    private String name;

    @Column(name = "eng_name")
    private String engName;

    private String email;

    private String gender;

    @Column(name = "mobile_number")
    private String mobileNumber;

    private String premium;

    @Setter
    private String ssn;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "birth_date")
    private String birthDate;

    @Column(name = "policyholder_yn")
    private String policyholderYn;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "result_status")
    private String resultStatus;

    @Column(name = "result_code")
    private String resultCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "overseas_hanwha_plan_id")
    private OverseasHanwhaPlan overseasHanwhaPlan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "overseas_axa_plan_id")
    private OverseasAXAPlan overseasAXAPlan;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private Timestamp createdDate;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedDate;
}
