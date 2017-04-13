package org.axonframework.samples.bank.api.expense;

import lombok.Value;
import lombok.experimental.NonFinal;

@Value
@NonFinal
public class SystemExpenseUpdatedEvent {

    private String expenseId;
    private String type;
    private double amount;
    private String systemId;
}
