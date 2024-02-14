package com.example.temporaldemo.temporal;

import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;

import java.time.Duration;

public class WorkflowImpl implements WorkFlow{
    private final RetryOptions retryOptions = RetryOptions.newBuilder()
            .setInitialInterval(Duration.ofSeconds(1))
            .setMaximumInterval(Duration.ofSeconds(100))
            .setBackoffCoefficient(2)
            .setMaximumAttempts(50000)
            .build();
    private final ActivityOptions options = ActivityOptions.newBuilder().setStartToCloseTimeout(Duration.ofSeconds(30))
            .setRetryOptions(retryOptions).build();
    private final Activity activity = Workflow.newActivityStub(Activity.class, options);

    public boolean isOrderAccepted = false;

    public boolean isOrderPickedUp = false;

    public boolean isOrderDelivered = false;
    @Override
    public void startApprovalWorkflow() {
        activity.placeOrder();

        System.out.println("*****Waiting for Restaurant to confirm your order");
        Workflow.await(() -> isOrderAccepted);
        System.out.println("*****please wait till we assign a delivery executive");
        Workflow.await(()-> isOrderPickedUp);

        Workflow.await(() -> isOrderDelivered);

    }

    @Override
    public void signalOrderAccepted() {
        activity.setOrderAccepted();
        this.isOrderAccepted = true;

    }

    @Override
    public void signalOrderPickup() {
        activity.setOrderPickedUp();
        this.isOrderPickedUp = true;

    }

    @Override
    public void signalOrderDelivered() {
        activity.setOrderDelivered();
        this.isOrderDelivered = true;

    }
}
