package br.com.fmc.listener.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fmc.listener.domain.SalesMan;

public interface SalesMans extends JpaRepository<SalesMan, String> {

}
