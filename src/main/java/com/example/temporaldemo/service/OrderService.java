package com.example.temporaldemo.service;

import com.example.temporaldemo.temporal.WorkFlow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    WorkflowServiceStubs workflowServiceStubs;

    @Autowired
    WorkflowClient workflowClient;

    public void placeOrder(String workflowId){
        WorkFlow workflow = createWorkFlowConnection(workflowId);
        WorkflowClient.start(workflow::startApprovalWorkflow);
    }
    public void makeOrderAccepted(String workflowId){
        WorkFlow workflow = workflowClient
                .newWorkflowStub(WorkFlow.class, "Order_" +workflowId);
        workflow.signalOrderAccepted();
    }
    public void makeOrderPickedUp(String workflowId){
        WorkFlow workflow = workflowClient.newWorkflowStub(WorkFlow.class, "Order_" +workflowId);
        workflow.signalOrderPickup();
    }
    public void makeOrderDelivered(String workflowId){
        WorkFlow workflow = workflowClient.newWorkflowStub(WorkFlow.class, "Order_" +workflowId);
        workflow.signalOrderDelivered();
    }
    public WorkFlow createWorkFlowConnection(String id){
        WorkflowOptions options = WorkflowOptions.newBuilder().setTaskQueue(WorkFlow.QUEUE_NAME).setWorkflowId("Order_" + id).build();
        return workflowClient.newWorkflowStub(WorkFlow.class, options);
    }
}
