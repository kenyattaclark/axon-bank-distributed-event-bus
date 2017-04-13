package org.axonframework.samples.bank.api.bankaccount;

import lombok.Value;
import lombok.experimental.NonFinal;

@Value
@NonFinal
public class MoneyDepositedFromSystemEvent {

    private String bankAccountId;
    private long amount;
}
