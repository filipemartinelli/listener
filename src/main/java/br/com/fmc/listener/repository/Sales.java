package br.com.fmc.listener.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.fmc.listener.domain.Sale;

public interface Sales extends JpaRepository<Sale, Long> {

	@Query(value = "select new br.com.fmc.listener.domain.Sale(s.idSale,s.salesManName, s.total) from Sale s where rownum < 2 order by s.total desc")
	Optional<Sale> getSalesMoreExpensive();
	
	@Query(value = "select new br.com.fmc.listener.domain.Sale('', s.salesManName, sum(s.total)) from Sale s group by s.salesManName order by s.total asc ")
	Optional<List<Sale>> getSalesByNameSalesMan();
	
}
