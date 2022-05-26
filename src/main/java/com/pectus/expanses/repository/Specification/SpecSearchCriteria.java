package com.pectus.expanses.repository.Specification;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SpecSearchCriteria {

    private String key;
    private SearchOperation operation;
    private Object value;


    public SpecSearchCriteria(String key, String operation, String value) {
        SearchOperation op = SearchOperation.getSimpleOperation(operation);
        this.key = key;
        this.operation = op;
        this.value = value;
    }

}
