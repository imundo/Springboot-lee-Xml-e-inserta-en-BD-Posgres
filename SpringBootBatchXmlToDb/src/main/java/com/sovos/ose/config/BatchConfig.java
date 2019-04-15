package com.sovos.ose.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.oxm.xstream.XStreamMarshaller;

import com.sovos.ose.model.Padrones;
import com.sovos.ose.model.Contribuyente;
import com.sovos.ose.processor.PadronesItenProcessor;
import com.sovos.ose.processor.ContribuyenteItenProcessor;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory2;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory2;
	
	@Autowired
	public DataSource dataSource;
	
	@Bean
	public ContribuyenteItenProcessor processor(){
		return new ContribuyenteItenProcessor();
	}
	
	@Bean
	public PadronesItenProcessor processor2(){
		return new PadronesItenProcessor();
	}
//	@Bean
//	public Long getSequence() {
//		  org.springframework.jdbc.core.JdbcTemplate jdbcTemplateObject = new JdbcTemplate(dataSource);
//		  Long seq; 
//		  String sql = "SELECT nextval('hibernate_sequence')";
//		  seq = jdbcTemplateObject.queryForObject(sql, new Object[] {}, Long.class);
//		  return seq;
//		}
	@Bean
	public StaxEventItemReader<Contribuyente> reader(){
		StaxEventItemReader<Contribuyente> reader = new StaxEventItemReader<Contribuyente>();
		reader.setResource(new ClassPathResource("contribuyentes.xml"));
		reader.setFragmentRootElementName("contribuyente");
		
		Map<String,String> aliasesMap =new HashMap<String,String>();
		aliasesMap.put("contribuyente", "com.sovos.ose.model.Contribuyente");
		XStreamMarshaller marshaller = new XStreamMarshaller();
		marshaller.setAliases(aliasesMap);
		
		reader.setUnmarshaller(marshaller);
		return reader;
	}
	@Bean
	public StaxEventItemReader<Padrones> reader2(){
		StaxEventItemReader<Padrones> reader2 = new StaxEventItemReader<Padrones>();
		reader2.setResource(new ClassPathResource("padrones_prueba.xml"));
		reader2.setFragmentRootElementName("padrones");
		
		Map<String,String> aliasesMap =new HashMap<String,String>();
		aliasesMap.put("padrones", "com.sovos.ose.model.Padrones");
		XStreamMarshaller marshaller = new XStreamMarshaller();
		marshaller.setAliases(aliasesMap);
		
		reader2.setUnmarshaller(marshaller);
		return reader2;
	}
	
	@Bean
	public JdbcBatchItemWriter<Contribuyente> writer(){
		JdbcBatchItemWriter<Contribuyente> writer = new JdbcBatchItemWriter<Contribuyente>();
		
		writer.setDataSource(dataSource);
		writer.setSql("insert into SOVOS_TAXPAYERS (taxpayerid,status,condition) values(?,CAST (? AS INTEGER),CAST(? AS INTEGER))");
		writer.setItemPreparedStatementSetter(new ContribuyentePreparedStatementSetter());
		return writer;
	}
	
	@Bean
	public JdbcBatchItemWriter<Padrones> writer2(){
		JdbcBatchItemWriter<Padrones> writer2 = new JdbcBatchItemWriter<Padrones>();
//		  org.springframework.jdbc.core.JdbcTemplate jdbcTemplateObject = new JdbcTemplate(dataSource);
//		  Long seq; 
//		  String sql = "SELECT nextval('hibernate_sequence')";
//		  seq = jdbcTemplateObject.queryForObject(sql, new Object[] {}, Long.class);
		writer2.setDataSource(dataSource);
		writer2.setSql("insert into TAXPAYER_TYPE (id,taxpayerid,type) values(nextval('hibernate_sequence'),?,CAST (? AS INTEGER))");
		writer2.setItemPreparedStatementSetter(new PadronesPreparedStatementSetter());
//		writer2.setItemPreparedStatementSetter(new PadronesPreparedStatementSetter().setValues(padrones, ps));
		return writer2;
	}
	

	
	@Bean
	public Step step1(){
		return stepBuilderFactory.get("Paso 1 insertando contribuyentes a DB").<Contribuyente,Contribuyente>chunk(100).reader(reader()).processor(processor()).writer(writer()).build();
	}
	@Bean
	public Step step2(){
		return stepBuilderFactory2.get("Paso 1 insertando Padrones a DB").<Padrones,Padrones>chunk(100).reader(reader2()).processor(processor2()).writer(writer2()).build();
	}

	@Bean
	public Job exportPerosnJob(){
		return jobBuilderFactory.get("importar Contribuyentes").incrementer(new RunIdIncrementer()).flow(step1()).end().build();
	}
	@Bean
	public Job exportPerosnJob2(){
		return jobBuilderFactory.get("importar Padrones").incrementer(new RunIdIncrementer()).flow(step2()).end().build();
	}
}
