package encryption.damo.model.btob;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "partner_company")
public class PartnerCompany implements Serializable {
    @Column(columnDefinition = "bigint auto_increment")
    private Long id;

    @Id
    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "contractor")
    private String contractor;

    @Column(name = "contractor_business_no")
    private String contractorBusinessNo;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private Timestamp createdDate;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedDate;
}
