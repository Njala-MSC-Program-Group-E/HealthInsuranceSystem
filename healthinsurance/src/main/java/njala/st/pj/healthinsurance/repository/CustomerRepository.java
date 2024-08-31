package njala.st.pj.healthinsurance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import njala.st.pj.healthinsurance.model.Customer;
import java.util.Optional;


public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Optional<Customer> findById(long id);
}
