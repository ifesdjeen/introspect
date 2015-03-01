package introspect;

import net.bytebuddy.instrumentation.method.bytecode.bind.annotation.*;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import clojure.lang.IFn;

public class Interceptor {

	public static IFn callback;

  @RuntimeType
  public static Object intercept(@SuperCall Callable method,
                                 @This Object proxy,
                                 @AllArguments Object[] allArguments) throws Exception{

    Object returnValue = null;
    try {
      returnValue = method.call();
    } catch (Throwable throwable) {
			callback.invoke(proxy, allArguments, throwable);
      throwable.printStackTrace();
			throw throwable;
    }

		callback.invoke(proxy, allArguments, returnValue);
    return returnValue;
  }

	public static void setCallback(IFn fn) {
		callback = fn;
	}
}