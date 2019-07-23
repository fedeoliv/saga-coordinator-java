package coordinator.controllers;

import org.springframework.web.bind.annotation.RestController;
import coordinator.helpers.StateMachineHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import coordinator.models.transitions.Event;
import coordinator.models.transitions.State;

@RestController
public class CoordinatorController {
    
    @Autowired
    private StateMachineHelper stateMachineService;

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @PostMapping("/api/debug/state/{transactionId}/sendEvent/{eventType}")
    public String changeState(@PathVariable String transactionId, @PathVariable Event eventType) throws Exception {
        Assert.notNull(transactionId, "transactionId must be set");
        Assert.notNull(eventType, "eventType must be set");

        State previousState = stateMachineService.getStateForTransaction(transactionId);
        State currentState = stateMachineService.sendEventForTransaction(transactionId, eventType);

        return "Changed from " + previousState + " to " + currentState;
    }

    @GetMapping("/api/debug/state/{transactionId}")
    public String getState(@PathVariable String transactionId) throws Exception {
        Assert.notNull(transactionId, "transactionId must be set");

        State currentState = stateMachineService.getStateForTransaction(transactionId);

        return currentState.name();
    }
}
