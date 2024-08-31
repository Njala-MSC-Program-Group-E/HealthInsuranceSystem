package njala.st.pj.healthinsurance.model;

import java.sql.Date;
import java.text.DecimalFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import njala.st.pj.healthinsurance.Utils;

@Entity
public class CustomerContribution {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private PremiumPlans plan;
    private Double amount;
    private Date paymentdate;
    private String period;
    @Transient
    private String formatedAmount;

    
    public String getFormatedAmount() {
        if (formatedAmount != null && !formatedAmount.isEmpty()) {
            return formatedAmount;
        }

        return Utils.Format(amount);
    }
    public void setFormatedAmount(String formatedAmount) {
        this.formatedAmount = formatedAmount;
    }
    public String getPeriod() {
        return period;
    }
    public void setPeriod(String period) {
        this.period = period;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public PremiumPlans getPlan() {
        return plan;
    }
    public void setPlan(PremiumPlans plan) {
        this.plan = plan;
    }
    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
        DecimalFormat dFormat = new DecimalFormat();
        dFormat.setMaximumFractionDigits(2);
        dFormat.setMinimumFractionDigits(0);
        String strAmount = dFormat.format(amount);
        setFormatedAmount(strAmount);
    }
    public Date getPaymentdate() {
        return paymentdate;
    }
    public void setPaymentdate(Date paymentdate) {
        this.paymentdate = paymentdate;
    }
    
}
