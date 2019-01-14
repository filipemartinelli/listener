package br.com.fmc.listener.builder;

import java.util.List;
import java.util.Optional;

import br.com.fmc.listener.domain.Sale;

public class BuilderReport {
	
	StringBuilder buffer = new StringBuilder();

	String lineBreak = System.getProperty("line.separator");
	
	public static BuilderReport getInstance(){
		return new BuilderReport();
	}
	
	public BuilderReport withAmountClients(Long amount){
		if(amount > 0){
			buffer.append("Quantidade de clientes no arquivo de entrada: ").append(amount).append(lineBreak);
		}
		return this;
	}
	
	public BuilderReport withAmountSalesMan(Long amount){
		if(amount > 0){
			buffer.append("Quantidade de vendedor no arquivo de entrada: ").append(amount).append(lineBreak);
		}
		return this;
	}
	
	public BuilderReport withSalesMoreExpensive(Optional<Sale> sale){
		if(sale.isPresent()){
			
			buffer.append("ID da venda mais cara: ").append(sale.get().getIdSale()).append(lineBreak);
		}
		return this;
	}
	
	public BuilderReport withWorstSeller(Optional<List<Sale>> sales){
		if(sales.isPresent()){
			buffer.append("O pior vendedor: ").append(sales.get().iterator().next().getSalesManName());
		}
		return this;
	}
	
	public String build(){
		return buffer.toString();
	}

}
