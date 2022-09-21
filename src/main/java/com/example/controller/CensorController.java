package com.example.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Censor;
import com.example.service.CensorService;

@RestController
@RequestMapping("/censor")
public class CensorController {
	private final CensorService censorService;

	@Autowired
	public CensorController(CensorService censorService) {
		this.censorService = censorService;
	}

	// lấy tất cả
	@GetMapping("/all")
	public List<Censor> getAllCensor() {
		return censorService.getAllCensor_v2();
	}

	// lấy theo id
//	@GetMapping("/{id}")
//	public Map<String, Object> getById(@PathVariable(name = "id") String id) {
//		return censorService.findCensorById(id);
//	}

	// lấy theo id bằng mongoTemplate
	@GetMapping("/{id}")
	public Censor getById(@PathVariable(name = "id") String id) {
		return censorService.findCensorById_v2(id);
	}

	// lấy media đầu tiên trong medias
	@GetMapping("/medias/{id}")
	public Map<String, Object> getContentById(@PathVariable String id) {
		return censorService.getMediasById(id);
	}

	// cập nhật ngôn ngữ cho censor
	@PutMapping("/lang/{id}")
	public Censor updateCensor(@PathVariable(name = "id") String id) {
		return censorService.updateCensorLang(id);
	}

	// Cập nhật type trong medias
	@PutMapping("/update/mediatype/{id}")
	public Censor updateMediaType(@PathVariable(name = "id") String id) {
		return censorService.updateMediaType(id);
	}

	// Thêm trường bất kì vào medias
	@PutMapping("/update/medias/{id}")
	public Censor updateAddNewFieldMedias(@PathVariable(name = "id") String id) {
		return censorService.addNewFieldInMedias(id);
	}

	// remove trường newField
	@PutMapping("/update/medias/removefield/{id}")
	public void removeFieldMedias(@PathVariable(name = "id") String id) {
		censorService.removeFieldInMedias(id);
	}

	// Thêm phần tử vào trong medias
	@PutMapping("/update/medias/add/{id}")
	public void addMedia(@PathVariable(name = "id") String id) {
		censorService.addElementInMedias(id);
	}

	// Xoá phần tử mới thêm trong medias
	@PutMapping("/update/medias/remove/{id}")
	public void removeMedia(@PathVariable(name = "id") String id) {
		censorService.removeElementInMedias(id);
	}

}
