package reptiles.mydemo2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import reptiles.demo1.StringUtil.StringUtil;
import reptiles.demo1.view.Rule;

public class Mydemo2Test {
	
	private static int count=0;
	
	private final static Executor executor = Executors.newCachedThreadPool();//启用多线程
	
	public static void downZK(String url,String resultTagName,int type,final String tagName) throws Exception{
		
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
		
		//根据条件搜索
		
		for (final Element element : results) {
			if("a".equals(tagName)){
				executor.execute(new Runnable() {
				@Override
                 public void run() {
					Elements elementsByClass = element.getElementsByTag(tagName);
					for (Element element3 : elementsByClass) {
						
						String attr = element3.attr("href");
						System.out.println(element3.text());
						
						try {
							downZK(attr,"reveal-work-wrap",Rule.CLASS,"img");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				});
			}else if("img".equals(tagName)){
				Elements elementsByClass = element.getElementsByTag(tagName);
				for (Element element3 : elementsByClass) {
					String attr = element3.attr("src");
					Down(attr,"D:\\baiduDownload\\"+count+attr.substring(attr.lastIndexOf(".")));
					count++;
				}
			}
			
			
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		downZK("http://www.zcool.com.cn/search/content?&word=%E6%8F%92%E7%94%BB","card-img",Rule.CLASS,"a");
	}
	
	public static void Down(String imgurl,String filePath) throws Exception {
		
		URL url=new URL(imgurl);
		
		URLConnection openConnection = url.openConnection();
		InputStream inputStream2 = openConnection.getInputStream();
		
		File file=new File(filePath);
		
		FileOutputStream fileOutputStream=new FileOutputStream(file);
		
        int i = 0;  
        while((i = inputStream2.read()) != -1){  
        	fileOutputStream.write(i);  
        }  
        fileOutputStream.close();  
        inputStream2.close();  
		
	}
}
