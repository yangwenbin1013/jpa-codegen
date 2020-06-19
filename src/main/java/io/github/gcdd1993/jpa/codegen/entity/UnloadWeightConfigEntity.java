package io.github.gcdd1993.jpa.codegen.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 委托方亏吨计算配置表
 * @author: yangwenbin
 * @date: 2020-06-18 09:53:16
 */
@Entity
@Table(name = "uo_unload_weight_config")
public class UnloadWeightConfigEntity implements Serializable {

    public static final String CACHE_NAME = "{uo_unload_weight_config}::";

    private static final long serialVersionUID = 1L;

    /**
     * 委托方id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "owner_id")
    private Long ownerId;

    /**
     * 0代表不按结算重量计算合理亏吨，1代表按收货重量结算合理亏吨
     */
    @Column(name = "weight_type")
    private Integer weightType;

    /**
     * 状态:0无效(日志表)，1生效，2新增
     */
    @Column(name = "val_status")
    private Integer valStatus;

    /**
     * 合理亏吨小数位控制：1代表1位，2代表2位
     */
    @Column(name = "decimal_control")
    private Integer decimalControl;

    /**
     * 合理亏吨小数位截取方式：1代表四舍五入，2代表四舍不入
     */
    @Column(name = "decimal_control_cal")
    private Integer decimalControlCal;

    /**
     * 合理亏吨是否可修改：0代表不可修改，1代表可修改
     */
    @Column(name = "decimal_control_modi")
    private Integer decimalControlModi;

    /**
     * 超过合理亏损的重量，按照货物价值的比例系数计算亏吨金额
     */
    @Column(name = "deduct_ratio")
    private BigDecimal deductRatio;

    /**
     * 乐观锁
     */
    @Version
    private Integer revision;

    /**
     * 创建人
     */
    @Column(name = "created_by")
    private Long createdBy;

    /**
     * 创建人名
     */
    @Column(name = "created_name")
    private String createdName;

    /**
     * 创建时间
     */
    @Column(name = "created_time")
    private Date createdTime;

    /**
     * 更新人
     */
    @Column(name = "updated_by")
    private Long updatedBy;

    /**
     * 更新人名
     */
    @Column(name = "updated_name")
    private String updatedName;

    /**
     * 更新时间
     */
    @Column(name = "updated_time")
    private Date updatedTime;

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
    public Integer getWeightType() {
        return weightType;
    }

    public void setWeightType(Integer weightType) {
        this.weightType = weightType;
    }
    public Integer getValStatus() {
        return valStatus;
    }

    public void setValStatus(Integer valStatus) {
        this.valStatus = valStatus;
    }
    public Integer getDecimalControl() {
        return decimalControl;
    }

    public void setDecimalControl(Integer decimalControl) {
        this.decimalControl = decimalControl;
    }
    public Integer getDecimalControlCal() {
        return decimalControlCal;
    }

    public void setDecimalControlCal(Integer decimalControlCal) {
        this.decimalControlCal = decimalControlCal;
    }
    public Integer getDecimalControlModi() {
        return decimalControlModi;
    }

    public void setDecimalControlModi(Integer decimalControlModi) {
        this.decimalControlModi = decimalControlModi;
    }
    public BigDecimal getDeductRatio() {
        return deductRatio;
    }

    public void setDeductRatio(BigDecimal deductRatio) {
        this.deductRatio = deductRatio;
    }
    public Integer getRevision() {
        return revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }
    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }
    public String getCreatedName() {
        return createdName;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }
    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }
    public String getUpdatedName() {
        return updatedName;
    }

    public void setUpdatedName(String updatedName) {
        this.updatedName = updatedName;
    }
    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
            sb.append(", ownerId='").append(ownerId).append('\'');
            sb.append(", weightType='").append(weightType).append('\'');
            sb.append(", valStatus='").append(valStatus).append('\'');
            sb.append(", decimalControl='").append(decimalControl).append('\'');
            sb.append(", decimalControlCal='").append(decimalControlCal).append('\'');
            sb.append(", decimalControlModi='").append(decimalControlModi).append('\'');
            sb.append(", deductRatio='").append(deductRatio).append('\'');
            sb.append(", revision='").append(revision).append('\'');
            sb.append(", createdBy='").append(createdBy).append('\'');
            sb.append(", createdName='").append(createdName).append('\'');
            sb.append(", createdTime='").append(createdTime).append('\'');
            sb.append(", updatedBy='").append(updatedBy).append('\'');
            sb.append(", updatedName='").append(updatedName).append('\'');
            sb.append(", updatedTime='").append(updatedTime).append('\'');
        sb.append("]");
        return sb.toString();
    }

}
