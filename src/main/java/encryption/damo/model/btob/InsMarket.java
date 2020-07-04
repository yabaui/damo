package encryption.damo.model.btob;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "ins_market")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class InsMarket implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid")
    private Long uid;

    @Column(name = "status")
    private String status;

    @Column(name = "min_version")
    private String minVersion;

    @Column(name = "connect_type", length = 1)
    private String connectType;

    @Column(name = "type", length = 3)
    private String type;

    @Column(name = "insurance_name", length = 128)
    private String insuranceName;

    @Column(name = "insurance_code", length = 3)
    private String insuranceCode;

    @Column(name = "title", length = 128)
    private String title;

    @Column(name = "content", length = 512)
    private String content;

    @Column(name = "image_url", length = 512)
    private String imageUrl;

    @Column(name = "banner_image_url", length = 512)
    private String bannerImageUrl;

    @Column(name = "product_url", length = 512)
    private String productUrl;

    @Column(name = "join_count")
    private Long joinCount;

    @Column(name = "is_show")
    private String isShow = "T";

    @Column(name = "banner_yn")
    private String bannerYn = "N";

    @Column(name = "main_yn")
    private String mainYn = "N";

    @Column(name = "sort_order")
    private Long sortOrder;

    @Column(name = "banner_order")
    private String bannerOrder;

    @Column(name = "main_order")
    private String mainOrder;

    @Column(name = "opened_at")
    private Date openedAt;

    @Column(name = "image_type", length = 1, nullable = false)
    private String imageType;

    @Column(name = "ios_image_url", length = 512)
    private String iosImageUrl;

    @Column(name = "aos_image_url", length = 512)
    private String aosImageUrl;

    @Column(name = "product_url_type", length = 1)
    private String productUrlType;

    @Column(name = "claim_yn", length = 1)
    private String claimYn;

    @Column(name = "writer")
    private Long writer;

    @Column(name = "modifier")
    private Long modifier;

    @Column(name = "min_version_ios")
    private String minVersionIos;

    @Column(name = "min_version_android")
    private String minVersionAndroid;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private Timestamp createdDate;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedDate;
}
