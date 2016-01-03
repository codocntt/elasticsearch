package vn.edu.hcmnlu.controller;

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

import vn.edu.hcmnlu.elastic.ClientConnection;

/**
 * Handles requests for the application home page.
 */
@Controller
public class IndexController {
	
	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

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
	
	/**
	 * 
	 * @param namePost
	 * @param typeName
	 * @param content
	 * @param tags
	 */
	@RequestMapping(value = "/posts", method = RequestMethod.POST)
	public void createPost(@RequestParam(value="namepost") String namePost,
								@RequestParam(value="category") String typeName,
								@RequestParam(value="content") String content,
								@RequestParam(value="tags") String tags){
		// do something
	}
	
	
	/**
	 * 
	 * @param idPost
	 * @param idUser
	 * @param content
	 */
	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	public void commentPost(@RequestParam(value="idPost") String idPost,
							@RequestParam(value="idUser") String idUser,
							@RequestParam(value="content") String content){
		// do something
	}
	
	/**
	 * 
	 * @param indexName
	 * @param typeName
	 * @param keyword
	 */
	
	@RequestMapping(value = "/search", method = RequestMethod.POST, produces={"application/json; charset=UTF-8"})
	@ResponseBody
	public void search(@RequestParam(value="keysearch") String keysearch,
						@RequestParam(value="checkTopic") String checkTopic,
						@RequestParam(value="userName") String userName,
						@RequestParam(value="dateCreate") String dateCreate,
						@RequestParam(value="category") String category,
						@RequestParam(value="checkCategory") String checkCategory) {
		// do something 
		// paging from to
	}
	
}
