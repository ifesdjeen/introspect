package introspect;

import clojure.lang.IFn;

import java.util.Arrays;

public class Interceptor {

  public static IFn callback;

  public static void in(Object[] arguments, String className, String methodName) {
    System.out.println("Enter method: '" + className + " " + methodName + "' in:" + Arrays.toString(arguments));
    // callback.invoke(methodName, arguments);
  }

  public static void out(Object argument, String className, String methodName) {
    System.out.println("Leave method: '" + className + " " + methodName + "' in:" + argument);
        // callback.invoke(methodName, arguments);
  }

  public static void setCallback(IFn fn) {
    callback = fn;
  }

}
