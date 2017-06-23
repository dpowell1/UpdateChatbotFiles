package utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class HtmlParser {
	
	public Elements getFaqs(String url) {
		Document faq;
		try {
			faq = Jsoup.connect(url).get();
			Elements elems = faq.select(".panel-group");
			return elems;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<JsonObject> formatElements(Elements elems) {
		List<JsonObject> docs = new ArrayList<>();
		for (Element e : elems) {
			Element titleElem = e.getElementsByClass("panel-title").get(0);
			Elements i = titleElem.getElementsByTag("i");
			if (i.size() > 0) {
				i.remove();
			}
			String title = titleElem.text();
			
			Element answerElem = e.getElementsByClass("panel-body").get(0);
			String answer = answerElem.toString(); //keep html formatting & tags
			
			JsonObject doc = new JsonObject();
			doc.add("title", new JsonPrimitive(title));
			doc.add("answer", new JsonPrimitive(answer));
			docs.add(doc);
		}
		return docs;
	}
	
}
