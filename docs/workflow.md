# Understanding the workflow

The Saga Coordinator has a sample workflow that simulates money transfer. It simulates a scenario where you have to:

- Validate if bank accounts are valid
- Perform credit and debit operations
- Return a transaction receipt to the user if the transaction is finished successfully

As can be seen in the workflow diagram below, it brings a succesfull scenario (blue nodes) and error scenarios where compensating transactions need to be done (red nodes).

![](../images/workflow.jpg)

<!-- ## Sucessfull scenario

The succesfull scenario covers:

1. The validation service 

## Error scnarios -->
