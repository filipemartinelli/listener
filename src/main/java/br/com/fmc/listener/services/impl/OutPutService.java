package br.com.fmc.listener.services.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.fmc.listener.builder.BuilderReport;
import br.com.fmc.listener.repository.Clients;
import br.com.fmc.listener.repository.Sales;
import br.com.fmc.listener.repository.SalesMans;
import br.com.fmc.listener.util.FileUtil;

@Component
public class OutPutService {


	private static final Logger LOGGER = LoggerFactory.getLogger(OutPutService.class);
	
	@Value("${app.out}")
	private String out;
	
	@Value("${file.name.out}")
	private String fileName;
	
	@Autowired
	private Sales sales;
	
	@Autowired 
	private Clients clients;
	
	@Autowired 
	private SalesMans salesMans;
	
	
	public void generateReport() {
		LOGGER.info("REPORT ");
		
		String file = reportProcess();
		
		try {
			FileUtil.write(out,fileName, file);
		} catch (IOException e) {
			LOGGER.error("Error OutPutService.generateReport()");
			throw new RuntimeException(e);
		}
	}
	
	public String reportProcess(){
		
		return BuilderReport.getInstance()
				 .withAmountClients(clients.count())
				 .withAmountSalesMan(salesMans.count())
				 .withSalesMoreExpensive(sales.getSalesMoreExpensive())
				 .withWorstSeller(sales.getSalesByNameSalesMan())
				 .build();
	}

}
