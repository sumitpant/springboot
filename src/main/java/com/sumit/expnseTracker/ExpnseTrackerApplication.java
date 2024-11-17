package com.sumit.expnseTracker;

import com.sumit.expnseTracker.stub.ExpenseStub;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ExpnseTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpnseTrackerApplication.class, args);
		ExpenseStub.getStub();
	}

}
