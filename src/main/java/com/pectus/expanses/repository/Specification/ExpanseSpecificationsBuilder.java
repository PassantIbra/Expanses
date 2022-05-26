package com.pectus.expanses.repository.Specification;

import com.pectus.expanses.model.Expanse;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ExpanseSpecificationsBuilder {

    private final List<SpecSearchCriteria> params = new ArrayList<>();

    public final ExpanseSpecificationsBuilder with(final String key, final String operation, final Object value, final String prefix, final String suffix) {
        SearchOperation op = SearchOperation.getSimpleOperation(operation);
        if (op != null) {
            params.add(new SpecSearchCriteria(key, op, value));
        }
        return this;
    }

    public Specification<Expanse> build() {
        if (params.size() == 0)
            return null;

        Specification<Expanse> result = new ExpanseSpecification(params.get(0));

        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result).and(new ExpanseSpecification(params.get(i)));
        }

        return result;
    }

}
