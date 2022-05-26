package com.pectus.expanses.repository.Specification;

import com.pectus.expanses.model.Expanse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class ExpanseSpecification implements Specification<Expanse> {

    private SpecSearchCriteria criteria;

    @Override
    public Predicate toPredicate(final Root<Expanse> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {

        if (criteria.getKey().equalsIgnoreCase("date")) {
            return DateToPredicate(root.get(criteria.getKey()), LocalDate.parse(criteria.getValue().toString()), builder);
        } else {
            return toPredicate(root.get(criteria.getKey()), criteria.getValue().toString(), builder);
        }

    }

    private Predicate toPredicate(Expression<String> key, String value, final CriteriaBuilder builder) {
        switch (criteria.getOperation()) {
            case EQUALITY:
                return builder.equal(key, value);
            case NEGATION:
                return builder.notEqual(key, value);
            case GREATER_THAN:
                return builder.greaterThan(key, value);
            case LESS_THAN:
                return builder.lessThan(key, value);
            case LESS_THAN_OR_EQUAL:
                return builder.lessThanOrEqualTo(key, value);
            case GREATER_THAN_OR_EQUAL:
                return builder.greaterThanOrEqualTo(key, value);
            default:
                return null;
        }

    }

    private Predicate DateToPredicate(Expression<LocalDate> key, LocalDate value, final CriteriaBuilder builder) {
        switch (criteria.getOperation()) {
            case EQUALITY:
                return builder.equal(key, value);
            case NEGATION:
                return builder.notEqual(key, value);
            case GREATER_THAN:
                return builder.greaterThan(key, value);
            case LESS_THAN:
                return builder.lessThan(key, value);
            case LESS_THAN_OR_EQUAL:
                return builder.lessThanOrEqualTo(key, value);
            case GREATER_THAN_OR_EQUAL:
                return builder.greaterThanOrEqualTo(key, value);
            default:
                return null;
        }

    }
}
