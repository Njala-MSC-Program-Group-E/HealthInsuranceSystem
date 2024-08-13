package njala.st.pj.healthinsurance.component;

import org.springframework.data.jpa.repository.JpaRepository;

import njala.st.pj.healthinsurance.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    
}
