package com.jwlz.sjjc.zstp.service.impl;

import com.alibaba.fastjson2.JSON;
import com.jwlz.sjjc.zstp.entity.GstoreResult;
import com.jwlz.sjjc.zstp.jgsc.GstoreConnector;
import com.jwlz.sjjc.zstp.service.GstoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.concurrent.*;

@Service
@Slf4j
public class GstoreServiceImpl implements GstoreService {

    @Autowired
    private GstoreConnector gc;

    @Override
    public GstoreResult query(String database, String sparQL) {
        String load = gc.load(database, "0");
        log.info("数据库加载结果: {}", load);
        if (load == null || "".equals(load)) {
            return null;
        }
        char c = load.charAt(load.indexOf("StatusCode") + 12);
        if (c != '0') {
            return null;
        }

        /*
         * 设置定时器, 若 30s 内查询不到结果就返回空
         */
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                String result = gc.query(database, "json", sparQL);
                return result;
            }
        });

        String result = null;
        try {
            // 等待操作完成，最多等待30秒
            result = future.get(30 * 1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            // 如果等待超时，则取消任务的执行
            future.cancel(true);
            return null;
        }

        executor.shutdown();

        GstoreResult gstore = JSON.parseObject(result, GstoreResult.class);
        return gstore;
    }
}
