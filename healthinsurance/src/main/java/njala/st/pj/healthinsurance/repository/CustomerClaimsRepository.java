package njala.st.pj.healthinsurance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import njala.st.pj.healthinsurance.model.CustomerClaim;
import njala.st.pj.healthinsurance.model.CustomerPlans;

public interface CustomerClaimsRepository extends JpaRepository<CustomerClaim,Long> {
    @Query("SELECT SUM(c.claimAmount) FROM CustomerClaim c WHERE c.customerPlans =:customerId")
    Double SumByCustomerPlan(@Param("customerId") CustomerPlans customerPlan);
}
