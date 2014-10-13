package aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class LogAspect {
	@Before("execution(public * *.*(..))")
	//@AfterReturning("execution(* *.*(..))")
	public void log(JoinPoint pjp) throws Throwable{
		System.out.println("log " + pjp.getSignature().getName() );//+pjp.getThis().toString());
	}

}