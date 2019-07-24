package coordinator.utils;

import java.util.HashMap;
import org.springframework.statemachine.StateMachine;
import coordinator.models.transitions.Event;
import coordinator.models.transitions.State;

public class StateMachineSender {

    private final StateMachine<State, Event> stateMachine;
    private final HashMap<Event, Boolean> eventsResult;

    public StateMachineSender(StateMachine<State, Event> stateMachine) {
        this.stateMachine = stateMachine;
        this.eventsResult = new HashMap<>();

        stateMachine.start();
    }

    public void sendEvent(Event event) {
        boolean accepted = stateMachine.sendEvent(event);
        eventsResult.put(event, accepted);
    }

    public void sendEvents(Event... events){
        for (Event event : events) {
            sendEvent(event);
        }
    }

    public HashMap<Event, Boolean> getEventResults(){
        return eventsResult;
    }
}
