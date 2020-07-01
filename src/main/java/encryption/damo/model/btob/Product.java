package encryption.damo.model.btob;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "product")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product implements Serializable {
    @Column(columnDefinition = "bigint auto_increment")
    private Long id;

    @Id
    @Column(name = "code")
    private String code;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "ins_comp_name")
    private String insCompName;

    @ManyToOne
    @JoinColumn(name = "insurance_company_code")
    private InsuranceCompany insuranceCompany;

    @Column(name = "type")
    private String type;

    @Column(name = "fixed_term_yn", length = 2)
    private String fixedTermYn;

    @Column
    private String version;

    @Column(name = "ins_coverage", columnDefinition = "text")
    private String insCoverage;

    @Column(name = "use_yn", length = 2)
    private String useYn;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private Timestamp createdDate;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedDate;
}
