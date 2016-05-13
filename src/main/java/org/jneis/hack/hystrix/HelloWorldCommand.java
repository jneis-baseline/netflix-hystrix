package org.jneis.hack.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixThreadPoolKey;

public class HelloWorldCommand extends HystrixCommand<String> {

    private final String name;

    public HelloWorldCommand(String name) {
        super(Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("HelloWorld"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("HelloWorldPool")));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
//        throw new RuntimeException("failed");
        return "Hello, " + name;
    }

    @Override
    protected String getFallback() {
        return "Hello failure " + name;
    }

    @Override
    protected String getCacheKey() {
        return String.valueOf(name.hashCode());
    }

}
