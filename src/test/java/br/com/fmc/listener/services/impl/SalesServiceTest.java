package br.com.fmc.listener.services.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fmc.listener.domain.Sale;
import br.com.fmc.listener.repository.Sales;

public class SalesServiceTest {

	private SaleService saleService;
	
	@Mock
	private Sales salesMocked;
	

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		saleService = new SaleService(salesMocked);
		saleService.setItemSeparator(",");
		saleService.setItemInfSeparator("-");
	}
	
	@Test
	public void convert_test(){
		String linha = "003;10;[1-10-100,2-30-2.50,3-40-3.10];Pedro";
		 Sale sale = saleService.convert(linha, ";");
		 assertThat(sale.getTotal(), equalTo(new BigDecimal("105.60")));
	}
	
	@Test
	public void convert_itens_test(){
	     String itens = "[1-10-100,2-30-2.50,3-40-3.10]";
		 Sale sale = saleService.convertItens("10", "Pedro", itens);

		 assertThat(sale.getTotal(), equalTo(new BigDecimal("105.60")));
	}
	
	
	@Test
	public void process_test(){
		String linha = "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro";
		Sale sale= saleService.processLine(linha, "ç");
		Sale persisted = new Sale("10", "Pedro", new BigDecimal("105.60"));
		
		when(salesMocked.save(sale)).thenReturn(persisted);
		assertThat(persisted.getTotal(), equalTo(new BigDecimal("105.60")));
	}
	

}
