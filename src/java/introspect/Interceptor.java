package introspect;

import clojure.lang.IFn;
import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class Interceptor {

  public static IFn callback;

  public static void log(String a, Object[] obj) {
    System.out.println("HEREEEE");
    System.out.println(obj);

  }
  // @RuntimeType
  public static void intercept(// @SuperCall Callable method,
                                 @Origin Method originalMethod
//                                 @This Object proxy,
//                                 @AllArguments Object[] allArguments
                                ) // throws Throwable
  {
//    Object returnValue = null;
//    try {
//      System.out.println("CALLING");
//      returnValue = method.call();
//    } catch (Throwable throwable) {
//      callback.invoke(proxy, allArguments, throwable);
//      throwable.printStackTrace();
//      throw throwable;
//    }
//
//    // System.out.printf("___%s, returnValue: %s___\n",proxy, returnValue);
//    callback.invoke(proxy, originalMethod, allArguments, returnValue);
//    return returnValue;
    System.out.println("HERE WE GO");
    System.out.println(originalMethod);
  }

  public static void setCallback(IFn fn) {
    callback = fn;
  }

}
