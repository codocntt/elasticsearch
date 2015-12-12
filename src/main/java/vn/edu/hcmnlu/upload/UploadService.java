package vn.edu.hcmnlu.upload;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.xml.bind.DatatypeConverter;

import org.elasticsearch.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import vn.edu.hcmnlu.bean.Student;
import vn.edu.hcmnlu.constants.Constants;
import vn.edu.hcmnlu.elastic.ClientConnection;
import vn.edu.hcmnlu.elastic.DocumentOperations;
import vn.edu.hcmnlu.elastic.IndicesOperations;
import vn.edu.hcmnlu.elastic.MappingOperations;

@Service
public class UploadService {
	private static final Logger logger = LoggerFactory
			.getLogger(UploadService.class);

	private String getFile(String fileName) {

		StringBuilder result = new StringBuilder("");

		// Get file from resources folder
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());

		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				result.append(line);
			}
			scanner.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return result.toString();

	}

	public boolean indexDocumentFileToES(Client client, Student docs, MultipartFile file) {
		IndicesOperations indices = new IndicesOperations(client);
		if (!indices.checkIndexExists(Constants.INDICES)) {
			// indices.createIndex(Contants.INDICES);
			try {
				// XContentBuilder mappingBuilder = jsonBuilder()
				// .startObject()
				// .startObject(Contants.TYPE)
				// .startObject("properties")
				// .startObject("document")
				// .field("type", "attachment")
				// .startObject("fields")
				// .startObject("document")
				// .field("type", "string")
				// .field("store", "true")
				// .field("term_vector", "with_positions_offsets")
				// .endObject()
				// .endObject()
				// .endObject()
				// .endObject()
				// .endObject()
				// .endObject();
				
				String settings = getFile("config/settings.json");
				String templates = getFile("config/templates.json");
				MappingOperations mappings = new MappingOperations(client);
				mappings.createMappingTemplate(Constants.INDICES, templates, settings);
			} catch (IOException e) {
				logger.error("ERROR:" + e.getMessage());
				return false;
			}
		}

		/*
		 * process data uploading
		 */

		try {
			String encoded = DatatypeConverter.printBase64Binary(file
					.getBytes());
			docs.document = encoded;
			docs.date = String.valueOf(new Date());
			docs.filename = file.getOriginalFilename();
			docs.typeofdocument = file.getContentType();
			DocumentOperations docop = new DocumentOperations(client);
			docop.insertDocument(Constants.INDICES, Constants.TYPE,
					convertDocsToMap(docs));
		} catch (IOException e) {
			logger.error("ERROR:" + e.getMessage());
			return false;
		}
		return true;
	}

	private Map<String, Object> convertDocsToMap(Student docs) {
		Map<String, Object> maps = new HashMap<String, Object>();
		Class<?> cls = docs.getClass();
		Field fieldlist[] = cls.getDeclaredFields();
		for (Field f : fieldlist) {
			try {
				Field fi = cls.getField(f.getName());
				Object value = fi.get(docs);
				if (value != null) {
					maps.put(f.getName(), value);
				}
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return maps;
	}

	public String saveFile(MultipartFile resource, String rootPath,
			String fileName) {
		String url = rootPath;
		File file = new File(url);

		if (!file.exists()) {
			file.mkdirs();
		}
		fileName = String.valueOf(System.currentTimeMillis()) + "_" + fileName;
		url = url + fileName;
		file = new File(url);
		try {
			int len = 0;
			byte[] bytes = new byte[1000000];
			BufferedInputStream bis = new BufferedInputStream(
					resource.getInputStream());
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(file));
			while ((len = bis.read(bytes)) >= 0) {
				bos.write(bytes, 0, len);
			}
			bis.close();
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileName;
	}

}
