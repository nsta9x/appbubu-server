package com.nsta.w2.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nsta.w2.Models.Notebook;

@Repository
public interface NotebookRepository extends CrudRepository<Notebook, Long>{
	@Query(
	value = "SELECT * FROM notebook n WHERE n.user_id = ?",
	nativeQuery = true)
	List<Notebook> fetchBookstoreByUserId(long user_id);
	
	@Query(
	value = "SELECT * FROM notebook n WHERE n.id = ?",
	nativeQuery = true)
	Notebook findNotebookById(long notebook_id);
	
	@Query(
	value = "SELECT * FROM notebook n WHERE n.user_id = ?1 and n.lang_id = ?2 and n.name = 'Default'",
	nativeQuery = true)
	Notebook findNotebookDefault(long user_id, long lang_id);
	
}