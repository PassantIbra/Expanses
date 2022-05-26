package com.pectus.expanses.repository.Specification;

public enum SearchOperation {
    EQUALITY, NEGATION, GREATER_THAN, LESS_THAN, GREATER_THAN_OR_EQUAL, LESS_THAN_OR_EQUAL;

    public static final String[] SIMPLE_OPERATION_SET = {"<=", ">=", "=", "!", ">", "<"};

    public static SearchOperation getSimpleOperation(final String input) {
        switch (input) {
            case "=":
                return EQUALITY;
            case "!":
                return NEGATION;
            case ">":
                return GREATER_THAN;
            case "<":
                return LESS_THAN;
            case ">=":
                return GREATER_THAN_OR_EQUAL;
            case "<=":
                return LESS_THAN_OR_EQUAL;
            default:
                return null;
        }

    }
}

