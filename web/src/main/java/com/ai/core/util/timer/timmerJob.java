package com.ai.core.util.timer;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ai.core.constant.Constant;
import com.ai.web.service.QuestionSevice;
@Component
public class timmerJob {

	private static final Logger log = LoggerFactory.getLogger(timmerJob.class);
	@Resource
	private QuestionSevice questionSevice;
    /**
     *  Function:清空map
     *  @author chenhongan
     * @throws ClassNotFoundException 
     * @throws InstantiationException 
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     * @throws SecurityException 
     * @throws NoSuchFieldException 
     */
    //@Scheduled(fixedDelay = 5000)   //5秒更新一次
    @Scheduled(cron = "0 0 3 * * ?")  
    public void clearStaticMap() throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException, NoSuchFieldException, SecurityException {
    	//更新为5
    	questionSevice.updatePkTimesToOrigin();
    	
    	clearMap();
    	
    	log.debug("=================>定时任务启动");
        
    }
 
    @SuppressWarnings("rawtypes")
	public static void clearMap() throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException, NoSuchFieldException, SecurityException {
		
    	Class<?> clazz = Class.forName(Constant.QuestionController);
		
        Field[] fields = clazz.getDeclaredFields();
        
        for(Field field : fields)

        {

        	log.debug(field.getName() +"->");
        	
        	log.debug(Modifier.toString(field.getModifiers()));
        	
        	String mdfy = String.valueOf(Modifier.toString(field.getModifiers()));
        	
        	if(mdfy.indexOf("private static")>=0 && field.getType().getName().toString().indexOf("Map")>=0)
        	
        	{
        		
        		field.setAccessible(true);  //该方法表示取消java语言访问检查 
        		
        		Object pro = field.get("prop");
        		
        		((Map)pro).clear();
        		
        	}

        }
        
	}
    
}