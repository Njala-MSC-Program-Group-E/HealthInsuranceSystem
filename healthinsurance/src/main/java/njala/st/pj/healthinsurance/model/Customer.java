package njala.st.pj.healthinsurance.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
