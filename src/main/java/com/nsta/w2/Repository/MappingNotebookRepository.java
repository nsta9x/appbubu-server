package com.nsta.w2.Repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nsta.w2.Models.MappingNotebook;

@Repository
public interface MappingNotebookRepository extends CrudRepository<MappingNotebook, Long>{
	
	@Query(
			value = "SELECT * FROM mappingnotebook m WHERE m.word_id = ?1 AND m.notebook_id = ?2",
			nativeQuery = true)
	MappingNotebook getMappingNotebook(long word_id, long notebook_id);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM mappingnotebook WHERE word_id = ?1 AND notebook_id = ?2",
			nativeQuery = true)
	void removeOldExamples(long word_id, long notebook_id);
}