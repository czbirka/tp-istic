import org.springframework.context.support.ClassPathXmlApplicationContext;

import fr.istic.tp.spring.engine.IEngine;

public class Main {
	public static void main(String[] args) throws Exception {
		
		/* Init Spring Default Config */
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"client-beans.xml"});
		IEngine engine = (IEngine) context.getBean("engine");
		
		engine.run();

	}

}
