package org.axonframework.samples.bank.config;

import org.axonframework.amqp.eventhandling.spring.SpringAMQPMessageSource;
import org.axonframework.config.Component;
import org.axonframework.config.Configuration;
import org.axonframework.config.ModuleConfiguration;
import org.axonframework.eventhandling.EventProcessor;
import org.axonframework.eventhandling.SubscribingEventProcessor;
import org.axonframework.eventhandling.saga.AnnotatedSagaManager;
import org.axonframework.eventhandling.saga.SagaRepository;
import org.axonframework.eventhandling.saga.repository.AnnotatedSagaRepository;
import org.axonframework.eventhandling.saga.repository.SagaStore;
import org.axonframework.eventhandling.saga.repository.inmemory.InMemorySagaStore;

import java.util.function.Function;


public class SagaConfiguration<S> implements ModuleConfiguration {
    private final Component<EventProcessor> processor;
    private final Component<AnnotatedSagaManager<S>> sagaManager;
    private final Component<SagaRepository<S>> sagaRepository;
    private final Component<SagaStore<? super S>> sagaStore;
    private Configuration config;

    /**
     * Initialize a configuration for a Saga of given {@code sagaType}, using a Subscribing Event Processor to process
     * incoming Events.
     *
     * @param sagaType The type of Saga to handle events with
     * @param <S>      The type of Saga configured in this configuration
     * @return a SagaConfiguration instance, ready for further configuration
     */
    public static <S> SagaConfiguration subscribingSagaManager(Class<S> sagaType, SpringAMQPMessageSource messageSource) {
        return new SagaConfiguration(sagaType, messageSource);
    }

    @SuppressWarnings("unchecked")
    private SagaConfiguration(Class<S> sagaType, SpringAMQPMessageSource messageSource) {
        String managerName = sagaType.getSimpleName() + "Manager";
        String processorName = sagaType.getSimpleName() + "Processor";
        String repositoryName = sagaType.getSimpleName() + "Repository";
        sagaStore = new Component<>(() -> config, "sagaStore", c -> c.getComponent(SagaStore.class, InMemorySagaStore::new));
        sagaRepository = new Component<>(() -> config, repositoryName,
                c -> new AnnotatedSagaRepository<>(sagaType, sagaStore.get(), c.resourceInjector(),
                        c.parameterResolverFactory()));
        sagaManager = new Component<>(() -> config, managerName, c -> new AnnotatedSagaManager<>(sagaType, sagaRepository.get(),
                c.parameterResolverFactory()));
        processor = new Component<>(() -> config, processorName,
                c -> new SubscribingEventProcessor(managerName, sagaManager.get(), messageSource));
    }

    /**
     * Configures the Saga Store to use to store Saga instances of this type. By default, Sagas are stored in the
     * Saga Store configured in the global Configuration. This method can be used to override the store for specific
     * Sagas.
     *
     * @param sagaStoreBuilder The builder that returnes a fully initialized Saga Store instance based on the global
     *                         Configuration
     * @return this SagaConfiguration instance, ready for further configuration
     */
    public SagaConfiguration<S> configureSagaStore(Function<Configuration, SagaStore<? super S>> sagaStoreBuilder) {
        sagaStore.update(sagaStoreBuilder);
        return this;
    }

    @Override
    public void initialize(Configuration config) {
        this.config = config;
    }

    @Override
    public void start() {
        processor.get().start();
    }

    @Override
    public void shutdown() {
        processor.get().shutDown();
    }
}
