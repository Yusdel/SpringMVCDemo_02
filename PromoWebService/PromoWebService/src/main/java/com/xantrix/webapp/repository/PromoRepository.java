package com.xantrix.webapp.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xantrix.webapp.entities.Promo;

/*
 * Spring Data JPA Interface: JpaRepository<T, ID>
 * @Param<T> = Entity to work on
 * @Param<Key> = Primary Key Value
 * 
 * @Query("JPQL") = use this query instead of the JpaRepository
 */
@Repository
public interface PromoRepository extends JpaRepository<Promo, Long>
{
	Promo findByIdPromo(String IdPromo); // custom method ( ID can be any table Column name )
	
	Promo findByAnnoAndCodice(int Anno, String Codice);
	
	// Custom query
	@Query("SELECT a FROM Promo a JOIN a.dettPromo b WHERE ?1 BETWEEN b.inizio AND b.fine")
	List<Promo> SelActivePromo(Date Data);
}
