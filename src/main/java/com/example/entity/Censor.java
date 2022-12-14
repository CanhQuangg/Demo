package com.example.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "censor")
public class Censor {

	@Id
	// kiểu dữ liệu ObjectId
	private String _id;

	private String target;
	private String owner;
	private String category;
	private String lang;

	private Content content;

	@Data
	@NoArgsConstructor
	public class Content {
		// kiểu dữ liệu của id là ObjectId
		private String _id;
		private String scope;
		private String typpost;
		private String actor;
		private String content;

		private List<Media> medias;

		@NoArgsConstructor
		@Data
		public class Media {
			private Integer height;
			private String path;
			private String thumb;
			private Integer type;
			private Integer width;
			private String id;
		}

		private String target;

		// kiểu dữ liệu Date
		private String dl147;
		private String dl146;
		// date
		private LocalDateTime when;
		private String lang;
		private String langDefault;
		private Integer isShare;

		private Profile profile;

		@Data
		@NoArgsConstructor
		public class Profile {
			private String id;
			private String name;

			private Avatar avatar;

			@Data
			public class Avatar {
				private String id;
				private String path;
				private String thumb;
				private String frame;
			}
		}
	}

	private String type;
	private Integer level;

	// date
	private LocalDateTime when;

}
