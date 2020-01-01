package com.nsta.w2.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nsta.w2.Models.Word;

@Repository
public interface WordRepository extends JpaRepository<Word, Long>{
	
	@Transactional
	@Modifying
	@Query("update Word w set w.type = ?1, w.note = ?2 where w.id = ?3")
	void modifyWord(int type, String note, long id);
	
	@Query("select w from Word w where w.id in (select m.word_id from MappingNotebook m where m.notebook_id = ?1)")
	List<Word> findWordByNb(long nbId);
	
	@Query("select w from Word w where w.content = ?1 and w.type = ?2 and w.lang_id = ?3")
	Word findWordExist(String content, int type, int lang_id);
}