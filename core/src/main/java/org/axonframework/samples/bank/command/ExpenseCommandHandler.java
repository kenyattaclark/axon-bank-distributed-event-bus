package org.axonframework.samples.bank.command;


import org.axonframework.commandhandling.model.Repository;
import org.axonframework.eventhandling.EventBus;

public class ExpenseCommandHandler {

    private Repository<Expense> repository;
    private EventBus eventBus;

    public ExpenseCommandHandler(Repository<Expense> repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }



//    @CommandHandler
//    public void handle(CreateExpenseFromSystemCommand command) throws Exception {
//        repository.newInstance(() -> new Expense(command.getExpenseId(), command.getType(), command.getAmount(), command.getSystemId()));
//    }

//    @CommandHandler
//    public void handle(UpdateExpenseFromSystemCommand command) {
//        Aggregate<Expense> expense = repository.load(command.getExpenseId());
//        expense
//    }
}
