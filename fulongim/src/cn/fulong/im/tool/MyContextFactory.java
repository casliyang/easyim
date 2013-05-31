package cn.fulong.im.tool;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyContextFactory {
	private static ApplicationContext context = null;
	public static ApplicationContext getContext(){
		if (context == null) {
			context = new ClassPathXmlApplicationContext("applicationContext.xml");
		}
		return context;
	}
}
