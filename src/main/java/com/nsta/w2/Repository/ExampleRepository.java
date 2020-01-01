package com.nsta.w2.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nsta.w2.Models.Example;

@Repository
public interface ExampleRepository extends CrudRepository<Example, Long>{
	
	@Query(value = "select e.ex from example e where e.id in :listIds", nativeQuery = true)
	List<String> findExByListIds(@Param("listIds") List<Integer> listIds);
}