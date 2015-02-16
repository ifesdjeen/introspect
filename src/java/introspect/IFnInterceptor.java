package introspect;

import net.bytebuddy.instrumentation.method.bytecode.bind.annotation.AllArguments;
import net.bytebuddy.instrumentation.method.bytecode.bind.annotation.Origin;
import net.bytebuddy.instrumentation.method.bytecode.bind.annotation.RuntimeType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class IFnInterceptor {

  @RuntimeType
  public static Object intercept(@AllArguments Object[] allArguments,
																 @Origin Method method) {
    try {
			System.out.println("PRINTLN");

      method.invoke(allArguments);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    }
    return null;
  }

}
