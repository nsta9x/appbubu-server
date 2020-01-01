package com.nsta.w2.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nsta.w2.Models.MappingTranslate;

@Repository
public interface TranslateRepository extends JpaRepository<MappingTranslate, Long>{
	
	@Query(
	value = "SELECT w2 FROM mappingtranslate WHERE w1 = ?",
	nativeQuery = true)
	List<Long> findWordId(long id);
	
	@Transactional
	@Modifying
	@Query(
		value = "DELETE FROM mappingtranslate WHERE w1 = ?1 OR w2 = ?1",
		nativeQuery = true)
	void deleteMappingTranslate(long id);
}