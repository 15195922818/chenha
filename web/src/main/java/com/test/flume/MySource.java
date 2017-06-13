package com.test.flume;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Random;
 
import org.apache.flume.Context;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.EventDrivenSource;
import org.apache.flume.PollableSource;
import org.apache.flume.conf.Configurable;
import org.apache.flume.event.EventBuilder;
import org.apache.flume.source.AbstractSource;
 
public class MySource extends AbstractSource implements Configurable, PollableSource,EventDrivenSource {
 
    @Override
    public long getBackOffSleepIncrement() {
        // TODO Auto-generated method stub
        return 0;
    }
 
    @Override
    public long getMaxBackOffSleepInterval() {
        // TODO Auto-generated method stub
        return 0;
    }
 
    @Override
    public Status process() throws EventDeliveryException {
        try {
            while (true) {
                int max = 20;
                int min = 10;
                Random random = new Random();
 
                int s = random.nextInt(max) % (max - min + 1) + min;
                HashMap<String, String> header = new HashMap<String, String>();
                header.put("id", Integer.toString(s));
                this.getChannelProcessor()
                        .processEvent(EventBuilder.withBody(Integer.toString(s), Charset.forName("UTF-8"), header));
 
                Thread.sleep(1000);
            }
        }  catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
 
    @Override
    public void configure(Context arg0) {
         
    }
}