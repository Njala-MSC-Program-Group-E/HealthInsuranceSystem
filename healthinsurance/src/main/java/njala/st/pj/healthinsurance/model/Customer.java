package njala.st.pj.healthinsurance.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Customer extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String fulladdress;
    private String mobile;
    private String email;

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
