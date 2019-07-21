package coordinator.controllers;

import org.springframework.web.bind.annotation.RestController;

import coordinator.helpers.StateMachineHelper;
import coordinator.models.Events;
import coordinator.models.MonitorPayload;
import coordinator.models.States;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class CoordinatorController {
    
    @Autowired
    private StateMachineHelper stateMachineService;

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @PostMapping("/api/debug/state/{transactionId}/sendEvent/{eventType}")
    public String changeState(@PathVariable String transactionId, @PathVariable Events eventType) throws Exception {
        Assert.notNull(transactionId, "transactionId must be set");
        Assert.notNull(eventType, "eventType must be set");

        MonitorPayload monitorPayload = new MonitorPayload();
        monitorPayload.setEventType(eventType.toString());
        monitorPayload.setTransactionId(transactionId);
        monitorPayload.setMessageId(UUID.randomUUID().toString());
        monitorPayload.setMessageBytes(null);

        States stateBefore = stateMachineService.getStateForTransaction(transactionId);
       
        States stateAfter = stateMachineService.sendEventForTransaction(monitorPayload);

        return "Changed from " + stateBefore + " to " + stateAfter;
    }

    @GetMapping("/api/debug/state/{transactionId}")
    public String getState(@PathVariable String transactionId) throws Exception {
        Assert.notNull(transactionId, "transactionId must be set");

        States currentState = stateMachineService.getStateForTransaction(transactionId);

        return currentState.name();
    }
}
