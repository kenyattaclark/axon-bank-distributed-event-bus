package org.axonframework.samples.bank.api.bankaccount;

import lombok.Value;

@Value
public class UpdateExpenseFromSystemCommand {

    private String expenseId;
    private String type;
    private double amount;
    private String systemId;

}
