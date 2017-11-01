package reptiles.demo1.test;

import java.util.List;

import org.junit.Test;

import reptiles.demo1.view.ExtractService;
import reptiles.demo1.view.LinkTypeData;
import reptiles.demo1.view.Rule;


public class TestReptiles
{
	@Test
	public void getDatasByClass()
	{
		Rule rule = new Rule(
				"http://www.faisco.cn/",null,null,
				"cont_right", Rule.CLASS, Rule.GET);
		List<LinkTypeData> extracts = ExtractService.extract(rule);
		printf(extracts);
	}

	@Test
	public void getDatasByCssQuery()
	{
		Rule rule = new Rule("http://www.11315.com/search",
				new String[] { "name" }, new String[] { "兴网" },
				"div.g-mn div.con-model", Rule.SELECTION, Rule.GET);
		List<LinkTypeData> extracts = ExtractService.extract(rule);
		printf(extracts);
	}

	public void printf(List<LinkTypeData> datas)
	{
		for (LinkTypeData data : datas)
		{
			System.out.println(data.getLinkText());
			System.out.println(data.getLinkHref());
			System.out.println("***********************************");
		}

	}
	
	@Test  
	public void getDatasByCssQueryUserBaidu()  
	{  
	    Rule rule = new Rule("http://172.16.11.46:8080/cloudcard/general/index",  
	            null, null,  
	            null, -1, Rule.GET);  
	    List<LinkTypeData> extracts = ExtractService.extract(rule);  
	    printf(extracts);  
	}  
}
