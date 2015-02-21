package introspect;

import net.bytebuddy.instrumentation.method.bytecode.bind.annotation.AllArguments;
import net.bytebuddy.instrumentation.method.bytecode.bind.annotation.Origin;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;

public class LogInterceptor {

  public static AtomicInteger counter = new AtomicInteger();

  public static void log(@AllArguments Object[] allArguments,
                         @Origin Method method) {
    counter.addAndGet(1);
    for (int i = 0; i < allArguments.length; i++) {
      System.out.println(allArguments[i]);
    }
    System.out.println("Called on:" + method.getDeclaringClass().toString());
  }

//  public static Object invoke(Object a, Object b) {
//    counter.addAndGet(1);
//    System.out.println(111);
//    return 555;
//  }
}