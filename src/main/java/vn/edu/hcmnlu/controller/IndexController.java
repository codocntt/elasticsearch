package vn.edu.hcmnlu.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import vn.edu.hcmnlu.bean.DocsMappping;
import vn.edu.hcmnlu.contants.Contants;
import vn.edu.hcmnlu.elastic.QueryCreation;
import vn.edu.hcmnlu.upload.UploadService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class IndexController {
	
	@Autowired
	private UploadService uploadService;
	
	@Autowired
    ServletContext context; 
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		return "index";
	}
	
	@RequestMapping(value = "/getuploadpage", method = RequestMethod.GET)
	public String getUploadPage() {
		return "uploadPage";
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search(@RequestParam(value="keyword") String keyword) {
		try {
			keyword = URLDecoder.decode(keyword,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		QueryCreation query = new QueryCreation();
		List<DocsMappping> data = query.responseData(Contants.INDICES, Contants.TYPE, keyword);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("list", data);
		map.put("keyword", keyword);
		return new ModelAndView("response", map);
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(@RequestParam("file") MultipartFile file, @RequestParam("title") String title,
			@RequestParam("description") String description, @RequestParam("author") String author,
			HttpServletRequest request) {
		DocsMappping docs = new DocsMappping();
		String rootPath = context.getRealPath("") + File.separator + Contants.RESOURCE_PATH + File.separator;
		docs.url = uploadService.saveFile(file, rootPath, file.getOriginalFilename());
		docs.author = author;
		docs.title = title;
		docs.description = description;
		uploadService.indexDocumentFileToES(docs,file);
		return "index";
	}

}
