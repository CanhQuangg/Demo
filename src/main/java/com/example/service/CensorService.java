package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.example.entity.Censor;
import com.example.repository.CensorRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;

@Service
public class CensorService {

	@Autowired
	CensorRepository censorRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	private MongoCollection<Document> getCollectionCensorHis() {
		return mongoTemplate.getCollection("censor");
	}

	// lấy tất cả censor
	public List<Censor> getAllCensor() {
		return censorRepository.findAll();
	}

	// findAll Censor with mongoTemplate: using
	public List<Censor> getAllCensor_v2() {
		return mongoTemplate.findAll(Censor.class);
	}

	// lấy censor theo id
	public Map<String, Object> findCensorById(String id) {
		Censor censor = censorRepository.findBy_id(id);

		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = mapper.convertValue(censor, new TypeReference<Map<String, Object>>() {
		});
		return map;
	}

	// findById with mongoTemplate: using
	public Censor findCensorById_v2(String id) {
		Censor censor = mongoTemplate.findById(id, Censor.class);
		return censor;
	}

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

	// update lang with mongoTemplate: using
	public Censor updateCensorLang(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));

		Update update = new Update();
		update.set("lang", "updated lang");

		return mongoTemplate.findAndModify(query, update, Censor.class);
	}

	// cập nhật type trong medias
	public Censor updateMediaType(String id) {
		Document filter = new Document("_id", new ObjectId(id));
		Document update = new Document("$set", new Document("content.medias.0.type", 1));

		UpdateResult result = getCollectionCensorHis().updateOne(filter, update);

		return null;
	}

	// thêm trường cho media trong medias
	public Censor addNewFieldInMedias(String id) {
		Document filter = new Document("_id", new ObjectId(id));
		Document update = new Document("$set", new Document("content.medias.0.newField", "new field value"));

		UpdateResult result = getCollectionCensorHis().updateOne(filter, update);

		return null;
	}

	// remove trường newField mới tạo trong medias
	public void removeFieldInMedias(String id) {
		Document filter = new Document("_id", new ObjectId(id));
		Document update = new Document("$unset", new Document("content.medias.0.newField", ""));

		UpdateResult result = getCollectionCensorHis().updateOne(filter, update);
	}

	// Thêm 1 phần tử vào trong medias
	public void addElementInMedias(String id) {
		Document element = new Document("name", new String("Nguyen Canh Quang"));
		Document filter = new Document("_id", new ObjectId(id));
		Document update = new Document("$push", new Document("content.medias", element));

		UpdateResult result = getCollectionCensorHis().updateOne(filter, update);
	}

	// xoá phần tử mới thêm
	public void removeElementInMedias(String id) {
		Document element = new Document("name", new String("Nguyen Canh Quang"));
		Document filter = new Document("_id", new ObjectId(id));
		Document update = new Document("$pull", new Document("content.medias", element));

		UpdateResult result = getCollectionCensorHis().updateOne(filter, update);
	}

	// cập nhật phần tử media trong medias theo id
	// _id:631ffb8b57c0d51e4cb366fd
	// medias.id: 63285edd5045286952fba629
	public void updateMediaById(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));

		Update update = new Update();
		update.set("content.medias.$[cont].type", 2);
		update.filterArray(Criteria.where("cont.id").is(new ObjectId("63285edd5045286952fba629")));

		mongoTemplate.updateFirst(query, update, Censor.class);

	}

}
