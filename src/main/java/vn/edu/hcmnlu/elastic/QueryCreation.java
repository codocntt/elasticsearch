package vn.edu.hcmnlu.elastic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHitField;
import org.elasticsearch.search.highlight.HighlightField;

import vn.edu.hcmnlu.bean.DocsMappping;


public class QueryCreation {
	
	public List<DocsMappping> responseData(Client client, String index, String type, String keyword) {
		
		List<DocsMappping> arr = new ArrayList<DocsMappping>();
		
		// check exist of indices
		IndicesOperations indices = new IndicesOperations(client);
		if(!indices.checkIndexExists(index)){
			return arr;
		}
		
		//prepare Query
		QueryBuilder queryBuilder = QueryBuilders.boolQuery()
							.should(QueryBuilders.matchQuery("title", keyword))
							.should(QueryBuilders.matchQuery("author", keyword))
							.should(QueryBuilders.matchQuery("date", keyword))
							.should(QueryBuilders.matchQuery("description", keyword))
							.should(QueryBuilders.matchQuery("document", keyword))
							.should(QueryBuilders.matchQuery("filename", keyword));
		
		SearchResponse response = client.prepareSearch(index).setTypes(type).setQuery(queryBuilder)
										.addField("title")
										.addField("date")
										.addField("author")
										.addHighlightedField("title")
										.addHighlightedField("author")
										.addHighlightedField("date")
										.addHighlightedField("description")
										.addHighlightedField("document")
										.addHighlightedField("filename")
										.setHighlighterPreTags("<b>")
										.setHighlighterPostTags("</b>")
										.execute().actionGet();
		
		for(SearchHit hit : response.getHits().getHits()){
			Map<String, SearchHitField> maps = hit.getFields();
			String title = maps.get("title").value();
			String date = maps.get("date").value();
			String author = maps.get("author").value();
			DocsMappping p = new DocsMappping();
			p.id = hit.getId();
			p.title = title;
			p.date = date;
			p.author = author;
			p.highlight = getHighlight(hit.getHighlightFields());
			arr.add(p);
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
