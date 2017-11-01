package reptiles.mydemo;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import reptiles.demo1.StringUtil.StringUtil;
import reptiles.demo1.view.Rule;

public class Mydemo {
	
	public static void goUrl(String url) throws Exception{
		
		int requestType=Rule.GET;
		
		//-1 查body
		int type=-1;
		
		String resultTagName=null;
		
		Connection conn = Jsoup.connect(url);
		
		Document doc = null;
		switch (requestType)
		{
		case Rule.GET:
			doc = conn.ignoreContentType(true).userAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.15)").timeout(5000).get();
			break;
		case Rule.POST:
			doc = conn.ignoreContentType(true).userAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.15)").timeout(5000).post();
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
			Elements elementsByClass = element.getElementsByClass("news_shijiuda");
			for (Element element2 : elementsByClass) {
				Elements links = element2.getElementsByTag("a");
				for (Element element3 : links) {
					
					String attr = element3.attr("href");
					String linkText = element3.text();
					
					//此处应调用递归
//					News news=new News();
//					news.setTitle(linkText);
//					Connection conn2 = Jsoup.connect(attr);
					
					System.out.println(attr);
					System.out.println(linkText);
				}
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		goUrl("http://www.people.com.cn/");
	}
}
