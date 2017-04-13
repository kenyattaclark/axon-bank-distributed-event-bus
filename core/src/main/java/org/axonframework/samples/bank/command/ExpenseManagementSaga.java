package org.axonframework.samples.bank.command;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.samples.bank.api.expense.SystemExpenseUpdatedEvent;
import org.axonframework.samples.bank.api.bankaccount.UpdateExpenseFromSystemCommand;

import javax.inject.Inject;

import static org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage;

//@Saga
public class ExpenseManagementSaga {

    private transient CommandBus commandBus;

    @Inject
    public void setCommandBus(CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "expenseId")
    public void on(SystemExpenseUpdatedEvent event) {
//        DepositMoneyCommand command = new DepositMoneyCommand(event.getBankAccountId(), event.getAmount());
        UpdateExpenseFromSystemCommand command = new UpdateExpenseFromSystemCommand(event.getExpenseId(), event.getType(), event.getAmount(), event.getSystemId());
        commandBus.dispatch(asCommandMessage(command));
    }
}
