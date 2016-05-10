package org.jneis.hack.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class HelloWorldCommand extends HystrixCommand<String> {

    private final String name;

    public HelloWorldCommand(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        throw new RuntimeException("failed");
//        return "Hello, " + name;
    }

    @Override
    protected String getFallback() {
        return "Hello failure " + name;
    }
}
