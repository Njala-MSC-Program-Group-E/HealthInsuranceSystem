package njala.st.pj.healthinsurance.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import njala.st.pj.healthinsurance.model.Customer;
import njala.st.pj.healthinsurance.model.CustomerPlans;
import njala.st.pj.healthinsurance.model.PremiumPlans;

public interface CustomerPlansRepository extends JpaRepository<CustomerPlans,Long> {
    Optional<CustomerPlans> findByCustomerAndPlan(Customer c,PremiumPlans p);
}
