package org.jneis.hack.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixRequestLog;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class HelloWorldBatch {

    public static void main(String... args) throws ExecutionException, InterruptedException {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();

        Future<String> f1 = new HelloWorldRequestCollapser(1).queue();
        Future<String> f2 = new HelloWorldRequestCollapser(2).queue();
        Future<String> f3 = new HelloWorldRequestCollapser(3).queue();
        Future<String> f4 = new HelloWorldRequestCollapser(4).queue();

        System.out.println(f1.get());
        System.out.println(f2.get());
        System.out.println(f3.get());
        System.out.println(f4.get());

        Collection<HystrixCommand<?>> executedCommands = HystrixRequestLog.getCurrentRequest().getExecutedCommands();
        executedCommands.forEach(command -> System.out.println(command.getCommandKey().name()));

        context.shutdown();
    }

}
