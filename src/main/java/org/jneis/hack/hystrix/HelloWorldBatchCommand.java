package org.jneis.hack.hystrix;

import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class HelloWorldBatchCommand extends HystrixCommand<List<String>> {
    private final Collection<HystrixCollapser.CollapsedRequest<String, Integer>> collapsedRequests;

    public HelloWorldBatchCommand(Collection<HystrixCollapser.CollapsedRequest<String, Integer>> collapsedRequests) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("GetValueForKey")));
        this.collapsedRequests = collapsedRequests;
    }

    @Override
    protected List<String> run() throws Exception {
        return collapsedRequests
                .stream()
                .map(request -> "ValueForKey: " + request.getArgument())
                .collect(Collectors.toList());
    }
}
