package com.nsta.w2.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsta.w2.Models.Example;
import com.nsta.w2.Models.MappingNotebook;
import com.nsta.w2.Models.MappingTranslate;
import com.nsta.w2.Models.Word;
import com.nsta.w2.Repository.ExampleRepository;
import com.nsta.w2.Repository.MappingNotebookRepository;
import com.nsta.w2.Repository.TranslateRepository;
import com.nsta.w2.Repository.WordRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class WordController {
	public final String RES_CODE = "RES_CODE";
	public final String ERR_WORD_EXISTED = "ERR_WORD_EXISTED";
	
	@Autowired
	WordRepository wordRepository;
	
	@Autowired
	TranslateRepository translateRepository;
	
	@Autowired
	MappingNotebookRepository mnRepository;
	
	@Autowired
	ExampleRepository exRepository;
	
	@PostMapping("/word")
	public ResponseEntity<Map<String, Object>> getWordDetail(@RequestBody String req)
	{
		Map<String, Object> res = new HashMap();
		try {
			long W_id = Long.parseLong((new JSONObject(req).get("word_id").toString()));
			long N_id = Long.parseLong((new JSONObject(req).get("notebook_id").toString()));
			Optional<Word> w_result = wordRepository.findById(W_id);
			Word w = null;
			if(w_result.isPresent()) {
				w = (Word) w_result.get();
				w.setTranslate(fetchTranslation(w));
				w.setRelated(fetchRelation(w));
				w.setExamples(fetchExamples(W_id, N_id));
				res.put("word", w);
				return new ResponseEntity<Map<String, Object>>(res, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
		}
		catch(Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private List<String> fetchExamples(long W_id, long N_id) {
		try {
			List<Integer> e_ids_list = getListIdsExample(W_id, N_id);
			if(e_ids_list == null) return null;
			List<String> listExample = exRepository.findExByListIds(e_ids_list);
			return listExample;
		} catch (Exception e) {
			return null;
		}
	}
	
	private List<Integer> getListIdsExample(long W_id, long N_id){
		MappingNotebook mn = mnRepository.getMappingNotebook(W_id, N_id);
		String ids = mn.getExample_ids();
		if(ids == null) return null;
		List<String> e_ids_str = Arrays.asList(ids.split(","));
		return e_ids_str.stream().map(Integer::valueOf).collect(Collectors.toList());
	}

	public List<Word> fetchTranslation(Word w) {
		List<Long> t_ids = translateRepository.findWordId(w.getId());
		if(t_ids == null) return null;
        List<Word> translate = (List<Word>) wordRepository.findAllById(t_ids);
        return translate;
	}
	
	public List<Word> fetchRelation(Word w) {
//		List<Long> res = mappingRepository.findWordId(w.getId());
//		if(res == null) return null;
//        List<Word> translate = (List<Word>) wordRepository.findAllById(res);
//        return translate;
		return null;
	}
	
	@PostMapping(path = "/newword", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Map<String, Object>> newWord(@RequestBody String req) {
		ObjectMapper mapper = new ObjectMapper();
		StringBuilder ex_Ids = new StringBuilder();
		Map<String, Object> res = new HashMap();
		try {
			//Save word:
			String S_word = (new JSONObject(req).get("word")).toString();
			Word newW = mapper.readValue(S_word, Word.class);
			if(checkIfWordExist(newW)) {
				res.put(RES_CODE, ERR_WORD_EXISTED);
				return new ResponseEntity<Map<String, Object>>(res, HttpStatus.CREATED);
			}
			long word_id = wordRepository.save(newW).getId();
			
			//Save Examples
			String S_ex = (new JSONObject(req).get("examples")).toString();
			String[] L_ex = mapper.readValue(S_ex, String[].class);
			List<String> examples = Arrays.asList(L_ex);
			examples.forEach(ex -> {
				if(ex == null || ex.contentEquals("")) {
					// Do nothing.
				} else {
					Example e = new Example(ex);
					long id_ex = exRepository.save(e).getId();
					ex_Ids.append(id_ex);
					ex_Ids.append(","); 
				}		
			});

			//Assign new Notebook and example mapping:
			long notebook_id = Long.parseLong((new JSONObject(req).get("notebook_id")).toString());
			
			MappingNotebook mn = new MappingNotebook(word_id, notebook_id, ex_Ids.toString());
			mnRepository.save(mn);
			
			//Save translate words:
			List<Word> translate = newW.getTranslate();
			translate.forEach(w -> {
				long id2 = wordRepository.save(w).getId();
				MappingTranslate m1 = new MappingTranslate(word_id, id2);
				MappingTranslate m2 = new MappingTranslate(id2, word_id);
				translateRepository.save(m1);
				translateRepository.save(m2);
			});
			res.put(RES_CODE, HttpStatus.OK);
			return new ResponseEntity<Map<String, Object>>(res, HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	boolean checkIfWordExist(Word w) {
		String content = w.getContent();
		int type = w.getType();
		int lang_id = w.getLang_id();
		Word result = wordRepository.findWordExist(content, type, lang_id);
		boolean exist = result == null ? false : true;
		return exist;
	}
	
	@PostMapping(path = "/modword", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Word> modWord(@RequestBody String req) {
		ObjectMapper mapper = new ObjectMapper();
		StringBuilder ex_Ids = new StringBuilder();
		try {
			//Save word:
			String S_word = (new JSONObject(req).get("word")).toString();
			Word newW = mapper.readValue(S_word, Word.class);
			long word_id = wordRepository.save(newW).getId();
			
			//Get current Notebook and example mapping:
			long notebook_id = Long.parseLong((new JSONObject(req).get("notebook_id")).toString());
			mnRepository.removeOldExamples(word_id, notebook_id);

			String S_ex = (new JSONObject(req).get("examples")).toString();
			String[] L_ex = mapper.readValue(S_ex, String[].class);
			List<String> examples = Arrays.asList(L_ex);
			examples.forEach(ex -> {
				if(ex == null || ex.contentEquals("")) {
					// Do nothing.
				} else {
					Example e = new Example(ex);
					long id_ex = exRepository.save(e).getId();
					ex_Ids.append(id_ex);
					ex_Ids.append(","); 
				}
			});
			String saveEx = ex_Ids.toString();
			if(ex_Ids.length() == 0) {
				saveEx = null;
			}
			
			MappingNotebook mn = new MappingNotebook(word_id, notebook_id, saveEx);
			mnRepository.save(mn);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	void modWord(@RequestBody Word word) {
		wordRepository.save(word);
	}
	
	@PostMapping("/delword")
	void delWord(@RequestBody Long wId) {
		translateRepository.deleteMappingTranslate(wId);
		wordRepository.deleteById(wId);
	}
}