package encryption.damo.model.btob;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "overseas_hanwha_plan")
public class OverseasHanwhaPlan implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_plan_id")
    private ProductPlan productPlan;

    private String gender;

    private int age;

    private String premium;

    @Column(name = "plan_cd")
    private String planCd;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private Timestamp createdDate;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedDate;
}
