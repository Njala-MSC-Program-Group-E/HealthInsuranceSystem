package njala.st.pj.healthinsurance;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import njala.st.pj.healthinsurance.model.PremiumPlans;

public class Utils {
    public static String Format(Double amount){
        if(amount == null || amount.equals(0d)) return "-";

        DecimalFormat dFormat = new DecimalFormat();
        dFormat.setMaximumFractionDigits(2);
        dFormat.setMinimumFractionDigits(0);
        return dFormat.format(amount);
    }
    public static String FormatBidecimal(BigDecimal amount){
        if(amount == null || amount.equals(BigDecimal.ZERO)) return "-";

        DecimalFormat dFormat = new DecimalFormat();
        dFormat.setMaximumFractionDigits(2);
        dFormat.setMinimumFractionDigits(0);
        return dFormat.format(amount);
    }

    public static Double CalculateCustomerClaim(Double totalContribution,Double totalClaims,PremiumPlans plan){
        Double totalClaim = 0d;
        Double balanceContribution = totalContribution - totalClaims;
        if(balanceContribution < plan.getMinContribution()) return 0d;
        totalClaim = plan.getMinClaim();
        balanceContribution = balanceContribution - plan.getMinContribution();
        totalClaim += balanceContribution * plan.getClaimRate()/100;
        return totalClaim;
    }
}
