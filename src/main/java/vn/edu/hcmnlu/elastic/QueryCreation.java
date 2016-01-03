package vn.edu.hcmnlu.elastic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHitField;
import org.elasticsearch.search.highlight.HighlightField;

import vn.edu.hcmnlu.bean.User;


public class QueryCreation {
	
	public List<User> responseData(Client client, String index, String type, String keyword) {
		
		List<User> arr = new ArrayList<User>();
		
		//prepare Query
		/*QueryBuilder queryBuilder = QueryBuilders.boolQuery()
							.should(QueryBuilders.matchQuery("title", keyword))
							.should(QueryBuilders.matchQuery("author", keyword))
							.should(QueryBuilders.matchQuery("description", keyword))
							.should(QueryBuilders.matchQuery("fileContent", keyword))
							.should(QueryBuilders.matchQuery("fileName", keyword));*/
		
		MultiMatchQueryBuilder matchQueryBuilder = 
				new MultiMatchQueryBuilder(keyword, "title","title.folded");
		
		SearchResponse response = client.prepareSearch(index).setTypes(type).setQuery(matchQueryBuilder)
										.addField("title")
										.addField("saving_date")
										.addField("author")
//										.addHighlightedField("title")
//										.addHighlightedField("author")
//										.addHighlightedField("description")
//										.addHighlightedField("fileContent")
//										.addHighlightedField("fileName")
//										.setHighlighterPreTags("<b>")
//										.setHighlighterPostTags("</b>")
										.execute().actionGet();
		
		for(SearchHit hit : response.getHits().getHits()){
			Map<String, SearchHitField> maps = hit.getFields();
			String title = maps.get("title").value();
			String date = maps.get("saving_date").value();
			String author = maps.get("author").value();
		}
		return arr;
	}
	
	private String getHighlight(Map<String, HighlightField> hightlights){
		StringBuffer result= new StringBuffer();
		Set<String> keys = hightlights.keySet();
		while(keys.iterator().hasNext()){
			String key = keys.iterator().next();
			HighlightField hlf = hightlights.get(key);
			Text text = hlf.fragments()[0];
			result.append(text.string());
			break;
		}
		
		return result.toString();
	}
	
}
