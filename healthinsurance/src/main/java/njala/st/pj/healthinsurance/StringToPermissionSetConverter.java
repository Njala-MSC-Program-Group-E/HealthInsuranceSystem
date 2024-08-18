package njala.st.pj.healthinsurance;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import njala.st.pj.healthinsurance.model.Permission;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class StringToPermissionSetConverter implements Converter<String[], Set<Permission>> {

    @Override
    public Set<Permission> convert(String[] source) {
        if (source == null) {
            return null;
        }
        return Arrays.stream(source)
                     .map(Permission::valueOf)
                     .collect(Collectors.toSet());
    }
}

