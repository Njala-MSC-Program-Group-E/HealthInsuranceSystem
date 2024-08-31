package njala.st.pj.healthinsurance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import njala.st.pj.healthinsurance.model.Customer;
import njala.st.pj.healthinsurance.model.CustomerContribution;
import njala.st.pj.healthinsurance.model.PremiumPlans;

public interface CustomerContributionRepository extends JpaRepository<CustomerContribution,Long> {
    @Query("SELECT SUM(c.amount) FROM CustomerContribution c WHERE c.customer =:customerId AND c.plan=:planId")
    Double SumByCustomerAndPlan(@Param("customerId") Customer customerId, @Param("planId") PremiumPlans planId);

    int countByCustomerAndPlan(Customer customerId, PremiumPlans planId);
    Long countByCustomerIdAndPlanId(Long customerId, Long planId);

    
}
