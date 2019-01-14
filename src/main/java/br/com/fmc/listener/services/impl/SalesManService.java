package br.com.fmc.listener.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.fmc.listener.domain.SalesMan;
import br.com.fmc.listener.repository.SalesMans;

@Component
public class SalesManService {

	private SalesMans salesMans;

	public SalesManService(@Autowired SalesMans salesMans) {
		this.salesMans = salesMans;
	}

	public SalesMan processLine(String line, String separator) {
		SalesMan salesMan = convert(line, separator);
		return salesMans.save(salesMan);
	}

	protected SalesMan convert(String line, String separator) {
		String[] split = line.split(separator);
		return new SalesMan(split[1]);
	}
}
