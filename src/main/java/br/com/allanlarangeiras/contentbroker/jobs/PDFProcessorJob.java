package br.com.allanlarangeiras.contentbroker.jobs;

import java.io.File;
import java.util.Date;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import br.com.allanlarangeiras.contentbroker.config.JmsConfig;

/**
 * Created by allan.larangeiras on 25/01/2019.
 */
@Component
public class PDFProcessorJob implements Job {
	
	private Logger logger = LoggerFactory.getLogger(PDFProcessorJob.class);
	
	@Value("${fileserver.path}")
	private String fileServerPath;
	
	@Autowired
	private JmsTemplate jmsTemplate;

	@Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info(String.format("starting pdf processor job %s", new Date().toString()));
        
        File fileServerDirectory = new File(this.fileServerPath);
        if (fileServerDirectory.isDirectory()) {
        	for (File file : fileServerDirectory.listFiles()) {
				jmsTemplate.send(JmsConfig.PDF_PROCESSOR, new MessageCreator() {
					
					@Override
					public Message createMessage(Session session) throws JMSException {
						return session.createTextMessage(file.getAbsolutePath());
					}
					
				});
				
			}
        }
    }
}
