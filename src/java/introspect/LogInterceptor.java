package introspect;

import net.bytebuddy.instrumentation.method.bytecode.bind.annotation.AllArguments;
import net.bytebuddy.instrumentation.method.bytecode.bind.annotation.Origin;

import java.lang.reflect.Method;

public class LogInterceptor {
  public static void log(@AllArguments Object[] allArguments,
                  @Origin Method method) {
    for (int i = 0; i < allArguments.length; i++) {
      System.out.println(allArguments[i]);
    }
    System.out.println("Called on:" + method.getDeclaringClass().toString());
  }
}