package com.juice.core.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * To change this template use File | Settings | File Templates.
 *
 * @author wangqing
 * @since 1.0.0
 */
public class ThreadPool {
    private static volatile ThreadPool instance = null;
    private ExecutorService poll;

    private ThreadPool() {
        poll = Executors.newFixedThreadPool(36);
    }

    private ThreadPool(int threadNum) {
        if (threadNum <= 0) threadNum = 1;
        poll = Executors.newFixedThreadPool(threadNum);
    }

    /**
     * 获取ThreadPool对象
     *
     * @return instance
     */
    public static ThreadPool getInstance() {
        if (instance == null) {
            synchronized (ThreadPool.class) {
                if (instance == null)
                    instance = new ThreadPool();
            }
        }
        return instance;
    }

    /**
     * 获取ThreadPool对象
     *
     * @return instance
     */
    public static ThreadPool getInstance(int threadNum) {
        if (instance == null) {
            synchronized (ThreadPool.class) {
                if (instance == null)
                    instance = new ThreadPool(threadNum);
            }
        }
        return instance;
    }

    /**
     * 获取ExecutorService
     *
     * @return
     */
    public ExecutorService get() {
        return poll;
    }

    /**
     * 销毁ExecutorService
     */
    public void destroy() {
        poll.shutdown();
    }

    public void finalize() throws Throwable {
        this.destroy();
        super.finalize();
    }
}
