package njala.st.pj.healthinsurance.model;

import java.math.BigDecimal;
import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

@Entity
public class CustomerClaim {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_id")
    private CustomerPlans customerPlans;
    private BigDecimal claimAmount;
    private BigDecimal approvedAmount;
    private BigDecimal paidAmount;
    private Date claimDate;
    private Date approvedDate;
    private Date payDate;
    @Transient
    private String formatedClaimAmount;
    @Transient
    private String formatedApprovedAmount;
    @Transient
    private String formatedPaidAmount;

    public String getFormatedClaimAmount() {
        return formatedClaimAmount;
    }
    public void setFormatedClaimAmount(String formatedClaimAmount) {
        this.formatedClaimAmount = formatedClaimAmount;
    }
    public String getFormatedApprovedAmount() {
        return formatedApprovedAmount;
    }
    public void setFormatedApprovedAmount(String formatedApprovedAmount) {
        this.formatedApprovedAmount = formatedApprovedAmount;
    }
    public String getFormatedPaidAmount() {
        return formatedPaidAmount;
    }
    public void setFormatedPaidAmount(String formatedPaidAmount) {
        this.formatedPaidAmount = formatedPaidAmount;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public CustomerPlans getCustomerPlans() {
        return customerPlans;
    }
    public void setCustomerPlans(CustomerPlans customerPlans) {
        this.customerPlans = customerPlans;
    }
    public BigDecimal getClaimAmount() {
        return claimAmount;
    }
    public void setClaimAmount(BigDecimal claimAmount) {
        this.claimAmount = claimAmount;
    }
    public BigDecimal getApprovedAmount() {
        return approvedAmount;
    }
    public void setApprovedAmount(BigDecimal approvedAmount) {
        this.approvedAmount = approvedAmount;
    }
    public BigDecimal getPaidAmount() {
        return paidAmount;
    }
    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }
    public Date getClaimDate() {
        return claimDate;
    }
    public void setClaimDate(Date claimDate) {
        this.claimDate = claimDate;
    }
    public Date getApprovedDate() {
        return approvedDate;
    }
    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }
    public Date getPayDate() {
        return payDate;
    }
    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }
    
}
