package njala.st.pj.healthinsurance;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import njala.st.pj.healthinsurance.model.Customer;
import njala.st.pj.healthinsurance.model.PremiumPlans;
import njala.st.pj.healthinsurance.repository.CustomerContributionRepository;
import njala.st.pj.healthinsurance.repository.CustomerRepository;
import njala.st.pj.healthinsurance.repository.PremiumPlansRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CountCustomerContribution {
    @Autowired
    private CustomerContributionRepository customerContributionRepository;
    @Autowired
    private CustomerRepository cusRep;
    @Autowired
    private PremiumPlansRepository plRep;

    @Test
    //@Sql("/test-data.sql") // Optional: Load test data from an SQL file
    public void testCountByCustomerAndPlan() {

        Customer customer = cusRep.findById(1L).get();
        PremiumPlans plan = plRep.findById(1L).get();

        int count = customerContributionRepository.countByCustomerAndPlan(customer, plan);

        System.out.println(plan.getName());
        System.out.println(customer.getUserAccount().getFullname());
        // Assert the expected count
        //assertEquals(plan.getMinContributionDuration(), count);  // Adjust expected count as per your test data

        System.out.println("Count: " + count);
        assertTrue(count >= plan.getMinContributionDuration(),"Count should be greater than or equal to 0");
    }

    @Test
    public void testSumByCustomerAndPlan() {

        Customer customer = cusRep.findById(1L).get();
        PremiumPlans plan = plRep.findById(1L).get();

        double count = customerContributionRepository.SumByCustomerAndPlan(customer, plan);

        System.out.println(plan.getName());
        System.out.println(customer.getUserAccount().getFullname());
        // Assert the expected count
        //assertEquals(plan.getMinContributionDuration(), count);  // Adjust expected count as per your test data

        System.out.println("sum: " + count);
        assertTrue(count > plan.getMinContribution(),"sum of contributions should be less than plan minimum contribution.");
    }

    @Test
    public void testClaimAmount() {

        Customer customer = cusRep.findById(1L).get();
        PremiumPlans plan = plRep.findById(1L).get();

        double count = Utils.CalculateCustomerClaim(9000d, 300d, plan);

        System.out.println(plan.getName());
        System.out.println(customer.getUserAccount().getFullname());
        // Assert the expected count
        //assertEquals(plan.getMinContributionDuration(), count);  // Adjust expected count as per your test data

        System.out.println("maxclaim: " + count);
        assertTrue(count > plan.getMinClaim(),"Error Claim is less than minimum.");
    }
}



