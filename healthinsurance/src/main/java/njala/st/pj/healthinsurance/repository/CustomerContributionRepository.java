package njala.st.pj.healthinsurance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import njala.st.pj.healthinsurance.model.CustomerContribution;

public interface CustomerContributionRepository extends JpaRepository<CustomerContribution,Long> {
    
}
