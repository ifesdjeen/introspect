package introspect;

import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.*;

public class DynamicInstrumentationService {

  // protected abstract void record(String anEvent, long aNanos);

  @SuppressWarnings("unchecked")
  public static <T> T instrument(final T aT)
  {
    return (T) Proxy.newProxyInstance(aT.getClass().getClassLoader(),
                                      aT.getClass().getInterfaces(),
                                      new InvocationHandler() {
                                        @Override
                                        public Object invoke(Object aProxy, Method aMethod, Object[] aArgs)
                                                throws Throwable
                                        {
                                          System.out.println(123);
                                          long _start = System.nanoTime();
                                          try {
                                            return aMethod.invoke(aT, aArgs);
                                          } catch (InvocationTargetException anExc) {
                                            throw anExc.getCause();
                                          } finally {
                                            System.out.println(aArgs);
                                            //record(_t.event(), System.nanoTime()-_start);
                                          }
                                        }
                                      });
  }

  public static void main(String[] args) {
    Foo foo = new Foo();

    System.out.println(instrument(foo).foo(1, 2 ));
    System.out.println(foo.foo(1, 5));
  }

  private static class Foo {
    public int foo(int a, int b) {
      return a + b;
    }
  }
}
