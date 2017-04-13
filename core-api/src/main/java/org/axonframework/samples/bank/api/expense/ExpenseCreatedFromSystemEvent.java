package org.axonframework.samples.bank.api.expense;


import org.axonframework.commandhandling.model.AggregateIdentifier;

public class ExpenseCreatedFromSystemEvent {

    @AggregateIdentifier
    private String expenseId;

    private String type;
    private double amount;
    private String systemId;

}
