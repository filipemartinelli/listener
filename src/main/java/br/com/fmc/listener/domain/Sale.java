package br.com.fmc.listener.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Sale {
	
	@Id
	@EqualsAndHashCode.Include
	private String idSale;
	private BigDecimal total;
	private String salesManName;

	
	public Sale(String idSale, String salesManName, BigDecimal total){
		this.idSale = idSale;
		this.total = total;
		this.salesManName = salesManName;
	}
	public Sale() {}
}
