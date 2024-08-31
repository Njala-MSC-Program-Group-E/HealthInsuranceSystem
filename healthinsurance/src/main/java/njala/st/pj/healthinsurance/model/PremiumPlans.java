package njala.st.pj.healthinsurance.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class PremiumPlans {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "plan_name")
    private String name;
    @Column(name = "min_contribution")
    private Double minContribution;
    @Column(name = "min_claim")
    private Double minClaim;
    @Column(name = "claim_rate")
    private Double claimRate;
    @Column(name = "min_duration")
    private int minContributionDuration;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CustomerPlans> customersEnrolled;

    public List<Long> getCustomerIds(){
        List<Long> nms = new ArrayList<>();
        for (CustomerPlans pl : customersEnrolled) {
            nms.add(pl.getCustomer().getId());
        }
        return nms;
    }
    public List<String> getPlanNames(){
        List<String> nms = new ArrayList<>();
        for (CustomerPlans pl : customersEnrolled) {
            nms.add(pl.getCustomer().getUserAccount().getFullname());
        }
        return nms;
    }

    public void addCustomer(CustomerPlans cplan){
        if(!customersEnrolled.contains(cplan)){
            customersEnrolled.add(cplan);
            cplan.setPlan(this);
        }
    }

    public void removeCustomer(CustomerPlans cplan){
        customersEnrolled.remove(cplan);
        cplan.setPlan(null);
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Double getMinContribution() {
        return minContribution;
    }
    public void setMinContribution(Double minContribution) {
        this.minContribution = minContribution;
    }
    public Double getMinClaim() {
        return minClaim;
    }
    public void setMinClaim(Double minClaim) {
        this.minClaim = minClaim;
    }
    public Double getClaimRate() {
        return claimRate;
    }
    public void setClaimRate(Double claimRate) {
        this.claimRate = claimRate;
    }
    public int getMinContributionDuration() {
        return minContributionDuration;
    }
    public void setMinContributionDuration(int minContributionDuration) {
        this.minContributionDuration = minContributionDuration;
    }
}
