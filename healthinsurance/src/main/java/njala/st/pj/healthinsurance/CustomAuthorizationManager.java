package njala.st.pj.healthinsurance;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class CustomAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {


    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        boolean hasRole = authentication.get().getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("Admin"));
        if(context.getRequest().getRequestURI().contains("/customers")){
            // Check for either role or permission
            boolean hasPermission = authentication.get().getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("EDIT_CUSTOMER"));

            return new AuthorizationDecision(hasRole || hasPermission);
        }
        return new AuthorizationDecision(hasRole);
    }
}

