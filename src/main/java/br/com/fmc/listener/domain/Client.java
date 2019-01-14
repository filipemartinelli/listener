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
public class Client {

	@Id
	@EqualsAndHashCode.Include
	private String cnpj;
	
	public Client(String cnpj) {
		this.cnpj = cnpj;
	}
	
	public Client() {}
}
