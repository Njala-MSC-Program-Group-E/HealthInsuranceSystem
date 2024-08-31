package njala.st.pj.healthinsurance.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String fulladdress;
    private String mobile;
    private String email;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_account_id", unique = true, nullable = false)
    private User userAccount;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    @JsonIgnore
    private List<CustomerPlans> plansEnrolled;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    @JsonIgnore
    private List<CustomerContribution> contributions;

    public void addContribution(CustomerContribution cont){
        contributions.add(cont);
    }

    public void removeContribution(CustomerContribution cont){
        contributions.remove(cont);
    }

    public List<CustomerContribution> getContributions() {
        return contributions;
    }
    public void setContributions(List<CustomerContribution> contributions) {
        this.contributions = contributions;
    }
    public List<Long> getPlanIds(){
        List<Long> nms = new ArrayList<>();
        for (CustomerPlans pl : plansEnrolled) {
            nms.add(pl.getPlan().getId());
        }
        return nms;
    }
    public List<String> getPlanNames(){
        List<String> nms = new ArrayList<>();
        for (CustomerPlans pl : plansEnrolled) {
            nms.add(pl.getPlan().getName());
        }
        return nms;
    }

    public List<CustomerPlans> getPlansEnrolled() {
        return plansEnrolled;
    }

    public void addPlan(CustomerPlans cplan){
        if(!plansEnrolled.contains(cplan)){
            plansEnrolled.add(cplan);
            cplan.setCustomer(this);
        }
    }

    public void removePlan(CustomerPlans cplan){
        plansEnrolled.remove(cplan);
        cplan.setCustomer(null);
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public User getUserAccount() {
        return userAccount;
    }
    public void setUserAccount(User userAccount) {
        this.userAccount = userAccount;
    }
    public String getFulladdress() {
        return fulladdress;
    }
    public void setFulladdress(String fulladdress) {
        this.fulladdress = fulladdress;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
    
}
