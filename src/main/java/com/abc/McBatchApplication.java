package com.abc;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.sql.SQLException;
import java.util.Set;

//@SpringBootApplication
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})

//@EnableBatchProcessing
@EnableScheduling
public class McBatchApplication {

	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	Job job;

	@Autowired
	private JobExplorer jobExplorer;

	public static void main(String[] args) {
		SpringApplication.run(McBatchApplication.class, args);
		//System.exit(SpringApplication.exit(SpringApplication.run(McBatchApplication.class, args)));
	}

	@Scheduled(cron = "0 */1 * * * ?")
	public void perform() throws Exception
	{
		Set<JobExecution> runningJob = jobExplorer.findRunningJobExecutions("orderJob");

		if (!runningJob.isEmpty()) {
			throw new JobExecutionException(" Order Job  is already in Start State  ");
		}

		JobParameters params = new JobParametersBuilder()
				.addString("JobID", String.valueOf(System.currentTimeMillis()))
				.toJobParameters();
		jobLauncher.run(job, params);
	}

//	@Bean
//	public ServletRegistrationBean h2servletRegistration() {
//		ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
//		registrationBean.addUrlMappings("/console/*");
//		return registrationBean;
//	}

//	@Bean(initMethod = "start", destroyMethod = "stop")
//	public Server h2Server() throws SQLException {
//		//return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
//		return Server.createWebServer();
//	}
}
