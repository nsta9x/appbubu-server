package com.nsta.w2.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsta.w2.Models.Notebook;
import com.nsta.w2.Models.Word;
import com.nsta.w2.Repository.NotebookRepository;
import com.nsta.w2.Repository.WordRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class NotebookController {
	@Autowired
	NotebookRepository nbRepository;
	
	@Autowired
	WordRepository wordRepository;
	
	@PostMapping("/bookstore")
	public ResponseEntity<Map<String, Object>> getListNotebook(@RequestBody String req){
		Map<String, Object> res = new HashMap();
		try {
			long userId = Long.parseLong((new JSONObject(req).get("user_id").toString()));
			List<Notebook> bookstore = (List<Notebook>) nbRepository.fetchBookstoreByUserId(userId);
			res.put("bookstore", bookstore);
			return new ResponseEntity<Map<String, Object>>(res, HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/notebook")
	public ResponseEntity<Map<String, Object>> getListWord(@RequestBody String req){
		Map<String, Object> res = new HashMap();
		try {
			long notebook_id = Long.parseLong((new JSONObject(req).get("notebook_id").toString()));
			List<Word> list_word = (List<Word>) wordRepository.findWordByNb(notebook_id);
			res.put("list_word", list_word);
			
			Notebook notebook = nbRepository.findNotebookById(notebook_id);
			res.put("notebook", notebook);
			
			return new ResponseEntity<Map<String, Object>>(res, HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}