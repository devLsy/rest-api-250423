package com.test.lsy.restapi250423.common.bean;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j
public class RefreshableSqlSessionFactoryBean extends SqlSessionFactoryBean implements DisposableBean  {

    private SqlSessionFactory proxy;
    private int interval = 3000;

    private Timer timer;
    private TimerTask task;

    private Resource[] mapperLocations;

    private boolean running = false;

    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private final Lock r = rwl.readLock();
    private final Lock w = rwl.writeLock();

    public void setMapperLocations(Resource[] mapperLocations) {
        super.setMapperLocations(mapperLocations);
        this.mapperLocations = mapperLocations;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public void refresh() throws Exception {
        w.lock();
        try {
            super.afterPropertiesSet();
        } finally {
            w.unlock();
        }
        log.info("sqlMapClient refreshed.");
    }

    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
    }

    public SqlSessionFactory getObject() {
        if(this.proxy == null) {
            setRefreshable();
        }
        return this.proxy;
    }

    public Class<? extends SqlSessionFactory> getObjectType() {
        return (this.proxy != null ? this.proxy.getClass() : SqlSessionFactory.class);
    }

    public boolean isSingleton() {
        return true;
    }

    public void setCheckInterval(int ms) {
        interval = ms;

        if (timer != null) {
            resetInterval();
        }
    }

    public void destroy() throws Exception {
        timer.cancel();
    }

    private void setRefreshable() {

        proxy = (SqlSessionFactory) Proxy.newProxyInstance(SqlSessionFactory.class.getClassLoader()
                , new Class[] {SqlSessionFactory.class}, new InvocationHandler() {
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        return method.invoke(getParentObject(), args);
                    }
                });

        task = new TimerTask() {

            private Map<Resource, Long> map = new HashMap<Resource, Long>();

            public void run() {
                if (isModified()) {
                    try {
                        refresh();
                    } catch (Exception e) {
                        log.error("caught exception", e);
                    }
                }
            }

            private boolean isModified() {
                boolean retVal = false;

                if (mapperLocations != null) {
                    for (int i = 0; i < mapperLocations.length; i++) {
                        Resource mappingLocation = mapperLocations[i];
                        retVal |= findModifiedResource(mappingLocation);
                    }
                }

                return retVal;
            }

            private boolean findModifiedResource(Resource resource) {
                boolean retVal = false;
                List<String> modifiedResources = new ArrayList<String>();

                try {
                    long modified = resource.lastModified();

                    if (map.containsKey(resource)) {
                        long lastModified = ((Long) map.get(resource)).longValue();

                        if (lastModified != modified) {
                            map.put(resource, Long.valueOf(modified));
                            modifiedResources.add(resource.getDescription());
                            retVal = true;
                        }
                    } else {
                        map.put(resource, Long.valueOf(modified));
                    }
                } catch (IOException e) {
                    log.error("caught exception", e);
                }
                if (retVal) {
                    log.info("modified files : " + modifiedResources);
                }
                return retVal;
            }

        };

        timer = new Timer(true);
        resetInterval();

    }

    private Object getParentObject() throws Exception {
        r.lock();
        try {
            return super.getObject();
        } finally {
            r.unlock();
        }
    }

    private void resetInterval() {
        if (running) {
            timer.cancel();
            running = false;
        }
        if (interval > 0) {
            timer.schedule(task, 0, interval);
            running = true;
        }
    }

}