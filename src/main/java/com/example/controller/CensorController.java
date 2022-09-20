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
		return censorService.getAllCensor();
	}

	// lấy theo id
	@GetMapping("/{id}")
	public Map<String, Object> getById(@PathVariable(name = "id") String id) {
		return censorService.findCensorById(id);
	}

	// lấy media đầu tiên trong medias
	@GetMapping("/medias/{id}")
	public Map<String, Object> getContentById(@PathVariable String id) {
		return censorService.getMediasById(id);
	}

	// lấy toàn bộ media trong medias
//	@GetMapping("/medias/{id}")
//	public List<Map<String, Object>> getContentById(@PathVariable String id) {
//		return censorService.getMediasById(id);
//	}

	// lấy doc có level lớn hơn 2
	@GetMapping("/level")
	public List<Censor> getLevelGreaterThan2() {
		return censorService.getLevelGt2();
	}

	// cập nhật ngôn ngữ cho censor
	@PutMapping("/{id}")
	public Censor updateCensor(@PathVariable(name = "id") String id) {
//		return censorService.updateCensor(id);
		return censorService.updateCensor(id);
	}

	// Thêm trường bất kì vào medias
	// chưa hoàn thành
	@PutMapping("/update/medias/{id}")
	public Censor updateAddNewFieldMedias(@PathVariable(name = "id") String id) {
		return censorService.addNewFieldInMedias(id);
	}

	@PutMapping("/update/mediatype/{id}")
	public Censor updateMediaType(@PathVariable(name = "id") String id) {
		return censorService.updateMediaType_v2(id);
	}

}
