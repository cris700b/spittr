package it.spittr.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.spittr.exceptions.DuplicateSpittleException;
import it.spittr.exceptions.SpittleNotFoundException;
import it.spittr.model.Spittle;

public interface SpittleRepository extends JpaRepository<Spittle, Long>{

	List<Spittle> findSpittles(long maxId, int count);

	Spittle findOne(long spittleId) throws SpittleNotFoundException;

	@SuppressWarnings("unchecked")
	Spittle save(Spittle spittle) throws DuplicateSpittleException;
}
