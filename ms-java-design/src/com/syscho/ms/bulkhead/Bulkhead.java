package com.syscho.ms.bulkhead;
import java.util.concurrent.*;

public class Bulkhead {
    private final ExecutorService executor;

    /**
     * Initializes the Bulkhead with a fixed thread pool and a queue limit.
     *
     * @param maxThreads  Maximum number of concurrent threads.
     * @param queueLimit  Maximum number of tasks allowed in the queue.
     */
    public Bulkhead(int maxThreads, int queueLimit) {
        this.executor = new ThreadPoolExecutor(
                maxThreads, maxThreads, 0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(queueLimit), // Queue limit for overload handling
                new ThreadPoolExecutor.AbortPolicy()  // Reject tasks when the queue is full
        );
    }

    /**
     * Submits a task while handling overload scenarios.
     * @param task The task to execute.
     */
    public void submitTask(Runnable task , String taskType ) {
        try {
            executor.submit(task);
        } catch (RejectedExecutionException e) {
            System.out.println(taskType + "🚨 Task rejected due to Bulkhead overload!  ");
        }
    }

    /**
     * Gracefully shuts down the Bulkhead.
     */
    public void shutdown() {
        executor.shutdown();
    }
}
