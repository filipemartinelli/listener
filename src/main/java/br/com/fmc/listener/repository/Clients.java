package br.com.fmc.listener.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fmc.listener.domain.Client;

public interface Clients extends JpaRepository<Client, String> {
}
