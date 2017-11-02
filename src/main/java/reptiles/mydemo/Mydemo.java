package reptiles.mydemo;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import reptiles.demo1.StringUtil.StringUtil;
import reptiles.demo1.view.Rule;

public class Mydemo {
	
	//返回值 list news 
	private static List<News> newsList=new ArrayList<>();
	
	public static String goUrl(String url,String tag) throws Exception{
		
		int requestType=Rule.GET;
		
		//-1 查body
		int type=-1;
		
		String resultTagName=null;
		
		Connection conn = Jsoup.connect(url);
		
		Document doc = null;
		switch (requestType)
		{
		case Rule.GET:
			doc = conn.ignoreContentType(true).userAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.15)").timeout(10000).get();
			break;
		case Rule.POST:
			doc = conn.ignoreContentType(true).userAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.15)").timeout(10000).post();
			break;
		}
		
		Elements results = new Elements();
		switch (type)
		{
		case Rule.CLASS:
			results = doc.getElementsByClass(resultTagName);
			break;
		case Rule.ID:
			Element result = doc.getElementById(resultTagName);
			results.add(result);
			break;
		case Rule.SELECTION:
			results = doc.select(resultTagName);
			break;
		default:
			//当resultTagName为空时默认去body标签
			if (StringUtil.isEmpty(resultTagName))
			{
				results = doc.getElementsByTag("body");
			}
		}
		
		//根据条件搜索
		
		for (Element element : results) {
			if("a".equals(tag)){
				Elements elementsByClass = element.getElementsByClass("news_shijiuda");
				if(elementsByClass!=null){
				for (Element element2 : elementsByClass) {
					Elements links = element2.getElementsByTag(tag);
						for (Element element3 : links) {
							
							String attr = element3.attr("href");
							String linkText = element3.text();
							
							//此处应调用递归
							News news=new News();
							news.setTitle(linkText);
							news.setContent(goUrl(attr,"p"));
							
							newsList.add(news);
						}
					}
				}
			
			}else if("p".equals(tag)){
				Elements elementsByClass = element.getElementsByClass("text_con_left");
				if(elementsByClass!=null){
					for (Element element2 : elementsByClass) {
						Elements links = element2.getElementsByTag(tag);
						String con="";
						for (Element element3 : links) {
							String linkText = element3.text();
							con+=linkText;
						}
						return con;
					}
				}
				
			}
		}
		return null;
	}
	
	//题目+标题  
	public static void main(String[] args) throws Exception {
		goUrl("http://www.people.com.cn/","a");
		for (News news : newsList) {
			System.out.println(news.getTitle());
			System.out.println(news.getContent());
		}
	}
}
