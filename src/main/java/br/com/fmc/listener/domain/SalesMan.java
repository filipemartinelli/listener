package br.com.fmc.listener.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class SalesMan {

	@Id
	@EqualsAndHashCode.Include
	private String cpf;
	
	public SalesMan(String cpf) {
		this.cpf = cpf;
	}
	public SalesMan() {}
}
