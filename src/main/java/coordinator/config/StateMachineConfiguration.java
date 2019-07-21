package coordinator.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import coordinator.helpers.StateMachineHelper;
import coordinator.models.Events;
import coordinator.models.States;
import coordinator.models.statemachine.StateAction;
import coordinator.models.statemachine.StateMachineFactory;

@Configuration
public class StateMachineConfiguration {

    @Autowired
    private StateAction stateMachineAction;

    @Bean
    public StateMachine<States, Events> stateMachine() throws Exception {
        return new StateMachineFactory(stateMachineAction).buildStateMachine();
    }
    
    @Bean
    public StateMachineHelper buildStateMachineService(){
        return new StateMachineHelper();
    }
}
