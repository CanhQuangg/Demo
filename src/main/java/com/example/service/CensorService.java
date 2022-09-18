package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.example.entity.Censor;
import com.example.repository.CensorRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CensorService {

	@Autowired
	CensorRepository censorRepository;

	MongoTemplate mongoTemplate;

	// lấy tất cả censor
	public List<Censor> getAllCensor() {
		return censorRepository.findAll();
	}

	// lấy censor theo id
	public Map<String, Object> findCensorById(String id) {
		Censor censor = censorRepository.findBy_id(id);

		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = mapper.convertValue(censor, new TypeReference<Map<String, Object>>() {
		});
		return map;
	}

	// lấy medias theo id
//	public List<Map<String, Object>> getMediasById(String id) {
//		Censor censor = censorRepository.findBy_id(id);
//
//		ObjectMapper mapper = new ObjectMapper();
//		Map<String, Object> map = mapper.convertValue(censor.getContent(), new TypeReference<Map<String, Object>>() {
//		});
//
//		List<Map<String, Object>> medias = new ArrayList<>();
//		medias = (List<Map<String, Object>>) map.get("medias");
//
//		Map<String, Object> media = mapper.convertValue(medias.get(1), new TypeReference<Map<String, Object>>() {
//		});
//
//		return medias;
//	}

	// lấy media đầu tiên trong medias
	public Map<String, Object> getMediasById(String id) {
		Censor censor = censorRepository.findBy_id(id);

		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = mapper.convertValue(censor.getContent(), new TypeReference<Map<String, Object>>() {
		});

		List<Map<String, Object>> medias = new ArrayList<>();
		medias = (List<Map<String, Object>>) map.get("medias");

		Map<String, Object> media = mapper.convertValue(medias.get(1), new TypeReference<Map<String, Object>>() {
		});

		return media;
	}

	// cập nhật ngôn ngữ cho censor
	public Censor updateCensor(String id) {
		Censor censor = censorRepository.findBy_id(id);
		censor.setLang("en");
		return censorRepository.save(censor);
	}

	// thêm trường cho media trong medias
	public Censor addNewFieldInMedias(String id) {
		Censor censor = censorRepository.findBy_id(id);

		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = mapper.convertValue(censor.getContent(), new TypeReference<Map<String, Object>>() {
		});

		List<Map<String, Object>> medias = new ArrayList<>();
		medias = (List<Map<String, Object>>) map.get("medias");

		Map<String, Object> media = mapper.convertValue(medias.get(1), new TypeReference<Map<String, Object>>() {
		});

		media.put("newField", "new value");
		System.out.println(media);

//		// Convert Map to POJO
		// tạo một censor entity mới có định nghĩa trường mới trong media
//		Censor updatedCensor = mapper.convertValue(map, Censor.class);

		// phải định nghĩa trường mới cho medias
//		return censorRepository.save(censor);

		return null;
	}

	public List<Censor> getLevelGt2() {
		return censorRepository.findCensorLevelGreaterThan2();
	}

	public Censor updateMediaType(String id) {
		Censor censor = censorRepository.findBy_id(id);

		ObjectMapper mapper = new ObjectMapper();

		// convert censor to map
		Map<String, Object> censorMap = mapper.convertValue(censor, new TypeReference<Map<String, Object>>() {
		});

		// get map Content
		Map<String, Object> contentMap = mapper.convertValue(censor.getContent(),
				new TypeReference<Map<String, Object>>() {
				});

		// list media in medias
		List<Map<String, Object>> medias = new ArrayList<>();
		medias = (List<Map<String, Object>>) contentMap.get("medias");

		// map of first media in medias
		Map<String, Object> media = mapper.convertValue(medias.get(0), new TypeReference<Map<String, Object>>() {
		});

		// before
		System.out.println("\n" + censorMap);

		// update type
		media.put("type", "new value");
		medias.set(0, media);
		contentMap.put("medias", medias);
		censorMap.put("content", contentMap);

		// after update
		System.out.println("\n" + censorMap);

//		// Convert Map to POJO
		// đang lỗi ở trường medias list trong entity
		Censor updatedCensor = mapper.convertValue(censorMap, Censor.class);

		System.out.println(updatedCensor);
		// return censorRepository.save(censor);
		return null;
	}

}
