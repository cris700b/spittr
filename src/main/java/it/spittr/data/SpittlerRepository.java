package it.spittr.data;

import org.springframework.data.jpa.repository.JpaRepository;

import it.spittr.model.Spittler;

public interface SpittlerRepository extends JpaRepository<Spittler, Long> {

	@SuppressWarnings("unchecked")
	Spittler save(Spittler spittler);

	Spittler findByUsername(String username);

//	void addSpittler(Spittler spittler);
	
}
