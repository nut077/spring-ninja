package com.github.nut077.springninja.config;

import com.github.nut077.springninja.config.property.AsyncProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.boot.task.TaskExecutorBuilder;

import java.util.Map;
import java.util.concurrent.Executor;

@Log4j2
@EnableAsync
@RequiredArgsConstructor
@Configuration
public class AsyncConfig implements AsyncConfigurer {

    private final AsyncProperty prop;

    @Override
    public Executor getAsyncExecutor() {
       /* ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(properties.getCorePoolSize());
        executor.setQueueCapacity(properties.getQueueCapacity());
        executor.setMaxPoolSize(properties.getMaxPoolSize());
        executor.setKeepAliveSeconds(properties.getKeepAlive());
        executor.setThreadNamePrefix(properties.getThreadNamePrefix());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.initialize();*/
        ThreadPoolTaskExecutor executor = new TaskExecutorBuilder()
                .corePoolSize(prop.getCorePoolSize())
                .queueCapacity(prop.getQueueCapacity())
                .maxPoolSize(prop.getMaxPoolSize())
                .threadNamePrefix(prop.getThreadNamePrefix())
                .keepAlive(prop.getKeepAlive())
                .allowCoreThreadTimeOut(true)
                .taskDecorator(runnable -> process(runnable, ThreadContext.getContext()))
                .build();
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.initialize();
        return executor;

    }

    /*if(numberOfThreads < corePoolSize)
              create new thread & run new task
          else if(numberOfThreads >= corePoolSize)
              if(queue is not full)
                  put task into queue
              else if(queue is full)
                  if(numberOfThreads < maxPoolSize)
                      create new thread & run new task
                  else if(numberOfThreads >= maxPoolSize)
                      reject task*/

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) -> {
            log.error("AsyncMethod::{}({})", method.getName(), params);
            log.error("Exception::{}", ex.getMessage());
            // save exception information to queue or database for retry
        };
    }

    private Runnable process(Runnable runnable, Map<String, String> context) {
        return () -> {
            ThreadContext.putAll(context);
            runnable.run();
            ThreadContext.clearAll();
        };
    }
}
