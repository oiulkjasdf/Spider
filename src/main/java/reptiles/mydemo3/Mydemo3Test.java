package reptiles.mydemo3;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import reptiles.demo1.StringUtil.StringUtil;
import reptiles.demo1.view.Rule;

public class Mydemo3Test {
	
	public static void getHtml(String url,String resultTagName,int type,String a,String match) throws Exception{
			
			int requestType=Rule.GET;
			
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
			
			//无法获取相邻元素  经过观察发现比较简单的网站大多使用tr td 来显示数据
			System.out.println(results.html());
			if("a".equals(a)){
				for (Element element : results) {
					Elements elementsByTag = element.getElementsByTag(a);
					for (Element element2 : elementsByTag) {
						String attr = element2.attr("href");
						if(attr.length()>5){
							getHtml(attr,resultTagName,type,"tr",match);
						}
					}
				}
			}else if("tr".equals(a)){
				for (Element element : results) {
					Elements elementsByTag = element.getElementsByTag("tr");
					for (Element element2 : elementsByTag) {
						Elements elementsByTag2 = element2.getElementsByTag("td");
						for (Element element3 : elementsByTag2) {
							match+=element3.text()+"   ";
						}
						match+="\n";
					}
				}
			}
			

			
//			//从整个页面中  按照正则表达式抽取数据
//			String html = results.html();
//			
//			//String 正则表达式
//			String regex ="(\\d{1,3}\\.){3}\\d{1,3}";
//			Pattern pattern = Pattern.compile(regex);   
//			Matcher m = pattern.matcher(html);  
//			String match="";
//			while(m.find()){  
//				match+=m.group();
//				match+="\n";
//			}
			
			File file=new File("D:\\baiduDownload\\str.txt");
			
			OutputStream out=new FileOutputStream(file);
			
			InputStream   in_withcode   =   new   ByteArrayInputStream(match.getBytes("UTF-8")); 
		
			IOUtils.copy(in_withcode, out);
			
	}
	
	public static void main(String[] args) throws Exception {
		getHtml("http://www.66ip.cn/areaindex_35/1.html",null,-1,"a","");
	}
}
