package br.com.fmc.listener;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.fmc.listener.services.impl.InputService;
import br.com.fmc.listener.services.impl.OutPutService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ListenerApplicationTests {
	
	@Autowired
	private InputService inputService;
	
	@Autowired
	private OutPutService outPutService;
	
	private List<String> lines;
	
	@Before
	public void setup(){
		lines = new ArrayList<>();
		
		lines.add("001ç1234567891234çPedroç50000");
		lines.add("001ç3245678865434çPauloç40000.99");
		lines.add("002ç2345675434544345çJose da SilvaçRural");
		lines.add("002ç2345675433444345çEduardo PereiraçRural");
		lines.add("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro");
		lines.add("003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo");
		
	}

	@Test
	public void contextLoads() {
		for (String line : lines) {
			inputService.process(line);
		}
		
		String report = outPutService.reportProcess();
		
		assertThat(report, report.contains("Quantidade de clientes no arquivo de entrada: 2"));
		assertThat(report, report.contains("Quantidade de vendedor no arquivo de entrada: 2"));
		assertThat(report, report.contains("ID da venda mais cara: 10"));
	    assertThat(report, report.contains("O pior vendedor: Paulo"));
	}

}

