package njala.st.pj.healthinsurance.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import njala.st.pj.healthinsurance.model.PremiumPlans;

public interface PremiumPlansRepository extends JpaRepository<PremiumPlans,Long> {
    Optional<PremiumPlans> findById(long id);
}
