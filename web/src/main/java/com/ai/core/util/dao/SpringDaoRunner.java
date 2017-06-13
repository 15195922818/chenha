package com.ai.core.util.dao;

import java.sql.Connection;

import javax.sql.DataSource;

import org.nutz.dao.ConnCallback;
import org.nutz.dao.impl.DaoRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

/**
 * nutz.dao与spring事务控制整合
 * @author peiyu
 *
 */
@Service
public class SpringDaoRunner implements DaoRunner {
	
	private static final Logger log = LoggerFactory.getLogger(SpringDaoRunner.class);

	@Override
	public void run(DataSource dataSource, ConnCallback callback) {
		long start = System.currentTimeMillis();
		log.debug("进入spring事务控制.....");
		Connection conn = DataSourceUtils.getConnection(dataSource);
		try {
			callback.invoke(conn);
		} catch (Exception e) {
			log.error("执行异常:", e);
			if(e instanceof RuntimeException)
				throw (RuntimeException) e;
			else
				throw new RuntimeException(e);
		}
		finally {
			log.debug("spring事务控制结束.....");
			DataSourceUtils.releaseConnection(conn, dataSource);
			long end = System.currentTimeMillis();
			long time = end - start;
			log.debug("------------sql执行耗时:{}ms------------", time);
		}

	}

}
