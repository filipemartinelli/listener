package br.com.fmc.listener.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.fmc.listener.domain.Client;
import br.com.fmc.listener.repository.Clients;

@Component
public class ClientService {
	
	private Clients clients;
	
	public ClientService(@Autowired Clients clients) {
		this.clients = clients;
	}

	public Client processLine(String line, String separator) {
		Client client = convert(line, separator);
		return clients.save(client);
	}

	protected Client convert(String line, String separator) {
		String[] split = line.split(separator);
		return new Client(split[1]);
	}
}
