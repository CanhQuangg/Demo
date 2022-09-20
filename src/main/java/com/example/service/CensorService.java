package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.example.entity.Censor;
import com.example.entity.Censor.Content.Media;
import com.example.entity.Censor2;
import com.example.repository.Censor2Repository;
import com.example.repository.CensorRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CensorService {

	@Autowired
	CensorRepository censorRepository;

	@Autowired
	Censor2Repository censor2Repository;

	MongoTemplate mongoTemplate;

	// lấy tất cả censor
	public List<Censor> getAllCensor() {
		return censorRepository.findAll();
	}

	// lấy censor theo id
	public Map<String, Object> findCensorById(String id) {
//		Censor censor = censorRepository.findBy_id(id);

		Censor2 censor2 = censor2Repository.findBy_id(id);
		System.out.println(censor2);

		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = mapper.convertValue(censor2, new TypeReference<Map<String, Object>>() {
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

	// chưa hoàn thiện
	// convert từ map sang pojo để update
	public Censor updateMediaType(String id) {
		try {
			Censor censor = censorRepository.findBy_id(id);
			ObjectMapper mapper = new ObjectMapper();

			// convert censor to map
			Map<String, Object> censorMap = mapper.convertValue(censor, new TypeReference<Map<String, Object>>() {
			});

			Map<String, Object> contentMap = (Map<String, Object>) censorMap.get("content");

			// list media in medias
			List<Media> medias2 = new ArrayList<>();
			medias2 = censor.getContent().getMedias();
			System.out.println(medias2);
			List<Map<String, Object>> medias = new ArrayList<>();
			medias = (List<Map<String, Object>>) contentMap.get("medias");

			// map of first media in medias
//			Map<String, Object> media = mapper.convertValue(medias.get(0), new TypeReference<Map<String, Object>>() {
//			});
			// chỗ này anh nói cái hướng cho làm nha, chứ làm này là bị sai logic rồi á
			// oke a
			// chỗ này tạo vòng lặp for của cái medias xong rồi em cần push cái nào thì em
			// mới set vào mới đúng

			for (Map<String, Object> item : medias) {
				item.put("type", "new value");
			} // kiểu như này nè mới đúng

			// update type
//			media.put("type", "new value");
//			media.remove("type"); //remove trường type

//			medias.set(0, media);

//			medias.add(media); //thêm media vào medias

//			contentMap.put("medias", medias); // chỗ này đã put ở vòng lặp ròi medias tự set lại dữ liệu rồi nên khoogn cần put vô lại
//			censorMap.put("content", contentMap);

			// after update
			System.out.println("\n" + censorMap);

			// Convert Map to POJO
//			 đang lỗi ở trường medias list trong entity
//			Censor updatedCensor = mapper.convertValue(censorMap, Censor.class);

//			System.out.println(updatedCensor);
			// return censorRepository.save(censor);

//			Gson gson = new Gson();
//			JsonElement jsonElement = gson.toJsonTree(map);
//			MyPojo pojo = gson.fromJson(jsonElement, MyPojo.class);

//			censor.getContent().setMedias(null);
//			censorRepository.save(censorMap);
//			ObjectMapper objectMapper = new ObjectMapper();
//			Censor pojo = objectMapper.convertValue(censorMap, Censor.class);
//			System.err.println(pojo);

		} catch (Exception e) {
			System.out.println("error");
			e.printStackTrace();
		}

		return null;
	}

	public Censor updateMediaType_v2(String id) {
		Censor censor = censorRepository.findBy_id(id);
		for (Media media : censor.getContent().getMedias()) {
			media.setType(5);
		}

		return censorRepository.save(censor);
	}
}
