package njala.st.pj.healthinsurance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import njala.st.pj.healthinsurance.model.CustomerPlans;

public interface CustomerPlansRepository extends JpaRepository<CustomerPlans,Long> {
    
}
