package org.axonframework.samples.bank.command;

import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.samples.bank.api.expense.SystemExpenseUpdatedEvent;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
@NoArgsConstructor
public class Expense {

    @AggregateIdentifier
    private String expenseId;

    private String type;
    private double amount;
    private String systemId;

//    public Expense(String expenseId, String type, double amount, String systemId) {
//        apply(new SystemExpenseCreated)
//    }

    public void updateFromSystem(String expenseId, String type, double amount, String systemId) {
        apply(new SystemExpenseUpdatedEvent(expenseId, type, amount, systemId));
    }
}
