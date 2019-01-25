package br.com.allanlarangeiras.contentbroker.config;

import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

import br.com.allanlarangeiras.contentbroker.jobs.PDFProcessorJob;

/**
 * Created by allan.larangeiras on 25/01/2019.
 */
@Configuration
public class QuartzConfig {


	@Bean
    public JobDetailFactoryBean pdfProcessorJobDetail() {
		JobDetailFactoryBean factory = new JobDetailFactoryBean();
		factory.setJobClass(PDFProcessorJob.class);
		factory.setDurability(true);
		return factory;
    }

    @Bean
    public Trigger getPdfProcessorTrigger(JobDetail jobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), "pdf-processor-trigger")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.repeatMinutelyForever())
                .build();


    }

}
