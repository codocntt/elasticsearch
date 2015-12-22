package vn.edu.hcmnlu.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import vn.edu.hcmnlu.bean.ResponseData;
import vn.edu.hcmnlu.bean.Student;
import vn.edu.hcmnlu.elastic.ClientConnection;
import vn.edu.hcmnlu.elastic.DocumentOperations;
import vn.edu.hcmnlu.elastic.IndicesOperations;
import vn.edu.hcmnlu.elastic.ManageMappingTemplate;
import vn.edu.hcmnlu.elastic.MappingOperations;
import vn.edu.hcmnlu.elastic.QueryCreation;
import vn.edu.hcmnlu.util.Utils;

import com.google.gson.Gson;

/**
 * Handles requests for the application home page.
 */
@Controller
public class IndexController {
	
	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	//@Autowired
	//private UploadService uploadService;
	
	@Autowired
	DocumentOperations documentOperations;
	
	@Autowired
	IndicesOperations indicesOperations;
	
	@Autowired
    ServletContext context; 
	
	@Autowired
	ManageMappingTemplate manageMappingTemplate;
	
	@Autowired
	MappingOperations mappingOperations;
	
	@Autowired
	private ClientConnection clientConnection;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> index() 
	{
		return new ResponseEntity<String>("OK", HttpStatus.OK);	
	}
	
	@RequestMapping(value = "/indexing", method = RequestMethod.POST)
	public void indexingDocument(@RequestParam(value="indexName") String indexName,
								@RequestParam(value="typeName") String typeName,
								@RequestParam(value="content") String content) throws UnsupportedEncodingException
	{
		System.out.println("done");
		if(!indicesOperations.checkIndexExists(clientConnection.getTransportClient(), indexName)) 
			manageMappingTemplate.createMappingTemplate(clientConnection.getTransportClient(), mappingOperations, indexName);
		documentOperations.insertDocument(clientConnection.getTransportClient(), indexName, typeName, Utils.convertDocsToMap(Utils.convertStringToObject(typeName, content)));
	}
	
	@RequestMapping(value = "/searching", method = RequestMethod.POST, produces={"application/json; charset=UTF-8"})
	@ResponseBody
	public String search(@RequestParam(value="indexName") String indexName,
							@RequestParam(value="typeName") String typeName,
							@RequestParam(value="keyword") String keyword) 
	{
		ResponseData response = new ResponseData();
		QueryCreation query = new QueryCreation();
		List<Student> data = query.responseData(clientConnection.getTransportClient(), indexName, typeName, keyword);
		response.size = data.size();
		response.data = data;
		return new Gson().toJson(response);
	}
	
	/*@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(@RequestParam("file") MultipartFile file, @RequestParam("title") String title,
			@RequestParam("description") String description, @RequestParam("author") String author,
			HttpServletRequest request) {
		Student docs = new Student();
		String rootPath = context.getRealPath("") + File.separator + Constants.RESOURCE_PATH + File.separator;
		docs.url = uploadService.saveFile(file, rootPath, file.getOriginalFilename());
		docs.author = author;
		docs.title = title;
		docs.description = description;
		uploadService.indexDocumentFileToES(clientConnection.getTransportClient(), docs,file);
		return "index";
	}*/
	
}
