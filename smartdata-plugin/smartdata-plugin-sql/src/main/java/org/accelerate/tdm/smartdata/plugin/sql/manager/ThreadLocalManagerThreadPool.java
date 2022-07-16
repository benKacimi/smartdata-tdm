package org.accelerate.tdm.smartdata.plugin.sql.manager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.sql.DataSource;

import org.accelerate.tdm.smartdata.core.exception.TdmException;
import org.accelerate.tdm.smartdata.plugin.sql.ThreadContext;
import lombok.AccessLevel;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level= AccessLevel.PRIVATE)
@Getter
@Setter

public class ThreadLocalManagerThreadPool extends ThreadPoolExecutor{
    
    protected static final Logger LOGGER = LoggerFactory.getLogger(ThreadLocalManagerThreadPool.class);

    private DataSource  dataSource;

    public ThreadLocalManagerThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
            BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public static ExecutorService newFixedThreadPool(int nThreads) {
        return new ThreadLocalManagerThreadPool(nThreads, nThreads,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
    }
    @Override    
    protected void beforeExecute(Thread t, Runnable r) { 
        try {
            Connection jdbcCon = dataSource.getConnection();
            ThreadContext.setConnexion(jdbcCon);
        } catch (SQLException e){
            LOGGER.error("ThreadLocalManagerThreadPool - cannot start thread due to jdbcConnection error : {} ", e.getMessage());
            throw new TdmException(e.getLocalizedMessage());
        }
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        ThreadContext.remove();
    }
  
    
}
