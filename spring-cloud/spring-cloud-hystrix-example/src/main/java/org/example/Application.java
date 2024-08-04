package org.example;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableCircuitBreaker
public class Application {
  public static void main(String[] args) {
    System.out.println("Hello World!");
    ConfigurableApplicationContext context = SpringApplication.run(Application.class);
    Command command = (Command) context.getBean("command");
    System.out.println(command.execute());
  }

  @Component("command")
  public class Command {

    @HystrixCommand(fallbackMethod = "fallback")
    public Object execute() {
      // do stuff that might fail;
      throw new RuntimeException("runtime exception");
    }

    public Object fallback() {
      return "fallback";
    }
  }
}
