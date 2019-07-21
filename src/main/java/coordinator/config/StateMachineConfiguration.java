package coordinator.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import coordinator.factories.StateMachineFactory;
import coordinator.helpers.StateMachineHelper;
import coordinator.interfaces.StateMachineActions;
import coordinator.models.Events;
import coordinator.models.States;

@Configuration
public class StateMachineConfiguration {

    @Autowired
    private StateMachineActions stateMachineActions;

    @Bean
    public StateMachine<States, Events> stateMachine() throws Exception {
        return new StateMachineFactory(stateMachineActions).buildStateMachine();
    }
    
    @Bean
    public StateMachineHelper buildStateMachineService(){
        return new StateMachineHelper();
    }
}
