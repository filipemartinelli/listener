package br.com.fmc.listener.services.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fmc.listener.domain.Client;
import br.com.fmc.listener.repository.Clients;

public class ClientServiceTest {

	private ClientService clientService;
	
	@Mock
	private Clients clientsMocked;
	

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		clientService = new ClientService(clientsMocked);
	}
	
	@Test
	public void convert_test(){
		String linha = "002;1234569;aaa;BBBB";
		Client client = clientService.convert(linha, ";");
		assertThat(client.getCnpj(), equalTo("1234569"));
	}
	
	@Test
	public void process_test(){
		String linha = "002ç1234569çaaaçBBBB";
		Client client = clientService.processLine(linha, "ç");
		Client persisted = new Client("1234569");
		
		when(clientsMocked.save(client)).thenReturn(persisted);
		assertThat(persisted.getCnpj(), equalTo("1234569"));
	}
}
