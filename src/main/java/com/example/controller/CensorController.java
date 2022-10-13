package com.example.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.CensorDtoTest;
import com.example.entity.Censor;
import com.example.entity.ResponseObject;
import com.example.service.CensorService;
import com.mongodb.MongoException;

@RestController
@RequestMapping("/censor")
public class CensorController {
	private final CensorService censorService;

	@Autowired
	public CensorController(CensorService censorService) {
		this.censorService = censorService;
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(CensorController.class);

	// lấy tất cả censor
	@GetMapping("/all")
	ResponseEntity<ResponseObject> getAllCensor() {
		try {
			List<Censor> data = censorService.getAllCensor_v2();

			if (data.size() > 0) {
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200", "Get all censor", data));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ResponseObject("404", "Censor does not exist", ""));
			}
		} catch (MongoException e) {
			LOGGER.info("==> Exception get All Censor: {}", e);
			return ResponseEntity.status(500).body(new ResponseObject("500", "Error: server error", ""));
		}
	}

	// lấy theo id bằng mongoTemplate
	@GetMapping("/{id}")
	public ResponseEntity<ResponseObject> getById(@PathVariable(name = "id") String id) {
		try {
			Censor data = censorService.findCensorById_v2(id);
			if (data != null) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseObject("200", "Get censor by id " + id, data));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("404", "Not Found", ""));
			}
		} catch (MongoException e) {
			LOGGER.info("Error: {}", e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("400", "Error", ""));
		}
	}

	// lấy media đầu tiên trong medias
//	@GetMapping("/medias/{id}")
//	public Map<String, Object> getContentById(@PathVariable String id) {
//		return censorService.getMediasById(id);
//	}

	// cập nhật ngôn ngữ cho censor
	@PutMapping("/lang/{id}/{newLang}")
	public ResponseEntity<ResponseObject> updateCensor(@PathVariable(name = "id") String id,
			@PathVariable(name = "newLang") String newLang) {
		try {
			if (censorService.findCensorById_v2(id) == null) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseObject("404", "Cannot found Censor " + id, ""));
			} else {
				censorService.updateCensorLang(id, newLang);
				return ResponseEntity.status(HttpStatus.OK).body(
						new ResponseObject("200", "Updated Censor with id " + id, censorService.getCensorWithLang(id)));
			}
		} catch (MongoException e) {
			LOGGER.info("Error: {}", e);
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
					.body(new ResponseObject("503", "Error: " + e, ""));
		}
	}

	// Thêm trường bất kì vào medias
	@PutMapping("/update/medias/{id}")
	public ResponseEntity<ResponseObject> updateAddNewFieldMedias(@PathVariable(name = "id") String id,
			@RequestParam(name = "field") String newField, @RequestParam String value) {
		try {
			if (censorService.findCensorById_v2(id) == null) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseObject("404", "Cannot found Censor " + id, ""));
			}
			censorService.addNewFieldInMedias(id, newField, value);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200", "Updated Censor " + id, ""));
		} catch (MongoException e) {
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
					.body(new ResponseObject("503", "Cannot update Censor " + id, ""));
		}
	}

	// Cập nhật type trong medias
	@PutMapping("/update/mediatype/{id}")
	public Censor updateMediaType(@PathVariable(name = "id") String id) {
		return censorService.updateMediaType(id);
	}

	// remove trường được chỉ định trong medias
	@PutMapping("/update/medias/removefield/{id}")
	public ResponseEntity<ResponseObject> removeFieldMedias(@PathVariable(name = "id") String id,
			@RequestParam String field) {
		try {
			if (censorService.findCensorById_v2(id) == null) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseObject("404", "Cannot found Censor " + id, ""));
			}
			censorService.removeFieldInMedias(id, field);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200", "Updated Censor " + id, ""));
		} catch (MongoException e) {
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
					.body(new ResponseObject("503", "Cannot update Censor " + id, ""));
		}
	}

	// Thêm phần tử vào trong medias
	@PutMapping("/update/medias/add/{id}")
	public ResponseEntity<ResponseObject> addMedia(@PathVariable(name = "id") String id) {
		try {
			if (censorService.findCensorById_v2(id) == null) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseObject("404", "Cannot found Censor " + id, ""));
			}
			censorService.addElementInMedias(id);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200", "Updated Censor " + id, ""));
		} catch (Exception e) {
			LOGGER.info("Error: " + e);
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
					.body(new ResponseObject("503", "Cannot update Censor " + id, ""));
		}
	}

	// Xoá phần tử trong medias theo media.id
	@PutMapping("/update/medias/remove/{id}")
	public ResponseEntity<ResponseObject> removeMedia(@PathVariable(name = "id") String id,
			@RequestParam String mediaId) {
		try {
			if (censorService.findCensorById_v2(id) == null) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseObject("404", "Cannot found Censor " + id, ""));
			}
			if (censorService.removeElementInMedias(id, mediaId)) {
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200", "Updated Censor " + id, ""));
			} else {
				return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
						.body(new ResponseObject("503", "Cannot update Censor " + id, ""));
			}

		} catch (Exception e) {
			LOGGER.info("Error: " + e);
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
					.body(new ResponseObject("503", "Cannot update Censor " + id, ""));
		}
	}

	// cập nhật type media trong medias với điều kiện
	// media.id: 631ffb8b57c0d51e4cb366fd
	// _id:631ffb8b57c0d51e4cb366fd
	@PutMapping("/update/medias/type/{id}")
	public ResponseEntity<ResponseObject> updateMediaTypeWithId(@PathVariable(name = "id") String id,
			@RequestParam String mediaId, @RequestParam int newType) {
		try {
			if (censorService.findCensorById_v2(id) == null) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseObject("404", "Cannot found Censor " + id, ""));
			}
			if (censorService.updateMediaById(id, mediaId, newType)) {
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200", "Updated Censor " + id, ""));
			} else {
				return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
						.body(new ResponseObject("503", "Cannot update Censor " + id, ""));
			}
		} catch (Exception e) {
			LOGGER.info("Error: " + e);
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
					.body(new ResponseObject("503", "Cannot update Censor " + id, ""));
		}

	}

	// tìm theo scope là pub và ngày lớn hơn..., sử dung aggregate
	@GetMapping("/find")
	public List<CensorDtoTest> getByAggregate() {
		return censorService.findByDateAndScope();
	}
}
