package vn.shp.app.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.envers.Audited;


import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by hant1 on 26/08/2017.
 */
@Entity
@Table(name = "LOCATION")
@DynamicInsert
@DynamicUpdate
@Data
@Audited
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;

    @GeneratedValue(strategy = GenerationType.AUTO, generator = "LOCATION_ID_SEQ")
    @SequenceGenerator(name = "LOCATION_ID_SEQ", sequenceName = "LOCATION_SEQ", allocationSize = 1)
    @Id
    @Column(name = "LOCATION_ID", unique = true, nullable = false)
    private Long id;

    @Column(name = "LOC_CODE", nullable = false, length = 9)
    private String locCode;

    @Column(name = "LOC_NAME", length = 105)
    private String locName;

    @Column(name = "RECORD_STAT", length = 1)
    private String recordStat;

    @Column(name = "AUTH_STAT", length = 1)
    private String authStat;

    @Column(name = "ONCE_AUTH", length = 1)
    private String onceAuth;

    @Column(name = "MOD_NO")
    private BigDecimal modNo;

    @Column(name = "MAKER_ID", length = 12)
    private String makerId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MAKER_DT_STAMP")
    private Date makerDtStamp;

    @Column(name = "CHECKER_ID", length = 12)
    private String checkerId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CHECKER_DT_STAMP")
    private Date checkerDtStamp;

    @Column(name = "SEQ")
    private int seq;

    @Column(name = "PARENT_CODE", nullable = true, length = 9)
    private String parentCode;
}
