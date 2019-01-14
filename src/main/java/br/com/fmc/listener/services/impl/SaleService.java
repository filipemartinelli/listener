package br.com.fmc.listener.services.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.fmc.listener.domain.Sale;
import br.com.fmc.listener.repository.Sales;
import lombok.Data;

@Component
@Data
public class SaleService {

	@Value("${file.item.separator.in}")
	private String itemSeparator;
	
	@Value("${file.item-inf.separator.in}")
	private String itemInfSeparator;


	private Sales sales;

	public SaleService(@Autowired Sales sales) {
		this.sales = sales;
	}

	public Sale processLine(String line, String separator) {
		Sale sale = convert(line, separator);
		return sales.save(sale);
	}

	protected Sale convert(String line, String separator) {
		String[] split = line.split(separator);
		String idSales = split[1];
		String salesManName = split[3];
		return convertItens(idSales, salesManName, split[2]);

	}

	protected Sale convertItens(String idSales, String salesManName, String itens) {
		String[] split = itens.substring(1, itens.length() - 1).split(itemSeparator);
		BigDecimal total = BigDecimal.ZERO;
		for (int i = 0; i < split.length; i++) {
			String[] infs = split[i].split(itemInfSeparator);
			BigDecimal price = new BigDecimal(infs[2]);
			total = total.add(price);
		}
		
		return new Sale(idSales, salesManName, total);
		
	}

}
