package org.jneis.hack.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixObservableCommand;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

public class HelloWorld {

    public static void main(String... args) throws ExecutionException, InterruptedException {
        HystrixCommand<String> command = new HelloWorldCommand("Hystrix");
        HystrixCommand<String> command2 = new HelloWorldCommand("Hystrix");

        HystrixObservableCommand<String> observableCommand = new HelloWorldObservableCommand("HYSTRIx");
        HystrixObservableCommand<String> observableCommand2 = new HelloWorldObservableCommand("HYSTRIx");

        String value = command.execute();
        Future<String> future = command2.queue();
        Observable<String> hotObservable = observableCommand.observe();
        Observable<String> coldObservable = observableCommand2.toObservable();

        System.out.println(value);

        // non-blocking, ignore errors and completed signals
        hotObservable.subscribe(v -> System.out.println("hot observable's next " + v));

        // blocking, main thread waiting for command2 to finish
        System.out.println("blocking " + future.get());

        // non-blocking
        coldObservable.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println("completed");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("error " + e.getMessage());
            }

            @Override
            public void onNext(String v) {
                System.out.println("next " + v);
            }
        });

    }

}
