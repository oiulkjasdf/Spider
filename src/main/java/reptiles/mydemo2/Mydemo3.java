package reptiles.mydemo2;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Mydemo3 {
	//test multithreading
	
	private final static Executor executor = Executors.newCachedThreadPool();//启用多线程
	
	public static void main(String[] args) {
		for(int i=0;i<=100;i++){
		final int j=i;                                                                                                                                  //关键是这一句代码，将 i 转化为  j，这样j 还是final类型的参与线程
		executor.execute(new Runnable() {
		                 @Override
		                 public void run() {
		                  try{
		                  System.out.println(j); 
		                  }catch(Exception e){
		                  }
		}
		}); 
		}}
}
