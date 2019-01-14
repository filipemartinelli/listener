package br.com.fmc.listener.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.fmc.listener.enums.Type;
import br.com.fmc.listener.util.FileUtil;

@Component
public class InputService {
	
	@Value("${file.separator.in :ç}")
	private String separator;

	@Autowired
	private ClientService clientService;

	@Autowired
	private SalesManService salesManService;

	@Autowired
	private SaleService salesItemService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InputService.class);
	
	
	public void process(String line) {
		if (FileUtil.isValidLine(line, separator)) {
			Type type = Type.getValue(line.substring(0, 3));
			switch (type) {
			case CLIENT:
				clientService.processLine(line, separator);
				break;
			case SALESMAN:
				salesManService.processLine(line, separator);
				break;
			case SALE:
				salesItemService.processLine(line, separator);
				break;
			default:
				break;
			}
		}else{
			LOGGER.error("Invalide Line: "+line);
		}
	}
	
}
