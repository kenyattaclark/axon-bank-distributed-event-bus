package org.axonframework.samples.bank.api.expense;

import lombok.Value;

@Value
public class CreateExpenseFromSystemCommand {

    private String expenseId;
    private String type;
    private double amount;
    private String systemId;
}
