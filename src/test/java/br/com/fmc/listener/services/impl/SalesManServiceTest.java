package br.com.fmc.listener.services.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fmc.listener.domain.SalesMan;
import br.com.fmc.listener.repository.SalesMans;

public class SalesManServiceTest {

	private SalesManService salesManService;
	
	@Mock
	private SalesMans salesMansMocked;
	

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		salesManService = new SalesManService(salesMansMocked);
	}
	
	@Test
	public void convert_test(){
		String linha = "001;1234567891234;Pedro;50000";
		SalesMan salesMan = salesManService.convert(linha, ";");
		assertThat(salesMan.getCpf(), equalTo("1234567891234"));
	}
	
	@Test
	public void process_test(){
		String linha = "001ç1234567891234çPedroç50000";
		SalesMan salesMan = salesManService.processLine(linha, "ç");
		SalesMan persisted = new SalesMan("1234567891234");
		
		when(salesMansMocked.save(salesMan)).thenReturn(persisted);
		assertThat(persisted.getCpf(), equalTo("1234567891234"));
	}
}
