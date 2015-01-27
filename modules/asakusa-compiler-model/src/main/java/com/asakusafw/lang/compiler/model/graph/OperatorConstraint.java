package com.asakusafw.lang.compiler.model.graph;

/**
 * An attribute of operator.
 */
public enum OperatorConstraint {

    /**
     * the operator may produce non-empty datasets even if the operator inputs are empty.
     */
    GENERATOR,

    /**
     * the operator must affect at most once per data record.
     */
    AT_MOST_ONCE,

    /**
     * the operator must affect at lease once per data record.
     * That is, this prevents 'dead code elimination'.
     */
    AT_LEAST_ONCE,
}
