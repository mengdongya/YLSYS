package store.chinaotec.com.medicalcare.shopmarket.common.utils.httputil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 此类描述的是:线程池
 *
 * @author: fengdi
 * @version:1.5
 * @date:2016年3月11日下午3:24:39
 */
public class ThreadPoolManager {
    private static final ThreadPoolManager manager = new ThreadPoolManager();
    private ExecutorService service;

    private ThreadPoolManager() {
        int num = Runtime.getRuntime().availableProcessors();
        service = Executors.newFixedThreadPool(num * 2);
    }

    public static ThreadPoolManager getInstance() {
        return manager;
    }

    public void addTask(Runnable runnable) {
        service.execute(runnable);
    }
}
