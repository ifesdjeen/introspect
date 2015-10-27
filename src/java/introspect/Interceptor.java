package introspect;

import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedDeque;

import clojure.lang.IFn;

public class Interceptor {

  public static IFn                               callback;
  public static ConcurrentLinkedDeque<MethodCall> argumentStack = new ConcurrentLinkedDeque<>();

  public static void in(Object[] arguments, String className, String methodName) {
    argumentStack.push(new MethodCall(className, methodName, arguments));
  }

  public static void out(Object returnArg, String className, String methodName) {
		MethodCall call = argumentStack.pop();
		assert(call.getClassName().equals(className));
		assert(call.getMethodName().equals(methodName));

		callback.invoke(call.getClassName(), call.getMethodName(), call.getArguments(), returnArg);
  }

  public static void setCallback(IFn fn) {
    callback = fn;
  }

  public static class MethodCall {

    private final String   className;
    private final String   methodName;
    private final Object[] arguments;

    public MethodCall(String className, String methodName, Object[] arguments) {
      this.className = className;
      this.methodName = methodName;
      this.arguments = arguments;
    }

    public String getClassName() {
      return className;
    }

    public String getMethodName() {
      return methodName;
    }

    public Object[] getArguments() {
      return arguments;
    }

  }
}
