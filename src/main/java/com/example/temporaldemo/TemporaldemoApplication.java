package com.example.temporaldemo;

import com.example.temporaldemo.temporal.Activity;
import com.example.temporaldemo.temporal.WorkFlow;
import com.example.temporaldemo.temporal.WorkflowImpl;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TemporaldemoApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext appContext = SpringApplication.run(TemporaldemoApplication.class);
		WorkerFactory factory = appContext.getBean(WorkerFactory.class);
		Activity signUpActivity = appContext.getBean(Activity.class);
		//Activity signUpActivity = appContext.getBean(Activity.class);
		Worker worker = factory.newWorker(WorkFlow.QUEUE_NAME);
		worker.registerWorkflowImplementationTypes(WorkflowImpl.class);
		worker.registerActivitiesImplementations(signUpActivity);
		factory.start();
	}

}
