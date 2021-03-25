package com.xantrix.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xantrix.webapp.entities.DettPromo;

/*
 * Execute Stored Procedure by JPQL in SQL Server
 */
@Repository
public interface DettPromoRepository  extends JpaRepository<DettPromo, Long>
{
	// EXEC <sp_name> :<param1>, :<param2>, :..., nativeQuery = ? )
	@Query(value = "EXEC Sp_SelActivePromoFid :codFid,:codArt", nativeQuery = true)
	DettPromo SelDettPromoByCodFid(@Param("codFid") String CodFid, @Param("codArt") String CodArt);
	
	// Example using jdbc query instead Entities 
	@Modifying
	@Query(value = "UPDATE DETTPROMO SET OGGETTO = :oggetto WHERE ID = :id", nativeQuery = true)
	void UpdOggettoPromo(@Param("oggetto") String Oggetto, @Param("id") Long Id);
}
