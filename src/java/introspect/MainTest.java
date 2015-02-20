package introspect;

import clojure.lang.AFn;
import clojure.lang.IFn;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.dynamic.ClassLoadingStrategy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.instrumentation.FieldAccessor;
import net.bytebuddy.instrumentation.MethodDelegation;
import net.bytebuddy.instrumentation.SuperMethodCall;
import net.bytebuddy.instrumentation.method.bytecode.bind.annotation.AllArguments;
import net.bytebuddy.instrumentation.method.bytecode.bind.annotation.Origin;
import net.bytebuddy.instrumentation.method.bytecode.bind.annotation.RuntimeType;
import net.bytebuddy.instrumentation.method.bytecode.bind.annotation.This;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.modifier.Visibility;
import sun.reflect.ReflectionFactory;

import java.lang.reflect.Method;

public class MainTest {
  public static void initialize(String name) {
    new AgentBuilder.Default()
            .rebase(ElementMatchers.nameContains(name)
                            .and(ElementMatchers.not(ElementMatchers.nameContains("load")))
                            .and(ElementMatchers.not(ElementMatchers.nameContains("init")))
                   )
            .transform(new AgentBuilder.Transformer() {
              @Override
              public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder) {
                System.out.println(builder);

                return builder.method(ElementMatchers.any())
                               .intercept(MethodDelegation.to(LogInterceptor.class)
                                                  .andThen(SuperMethodCall.INSTANCE));
              }
            }).allowRetransformation()
            .installOn(ByteBuddyAgent.installOnOpenJDK());
  }

  public static void main(String[] name) {
    initialize("FnImpl");
    System.out.println(new FnImpl().invoke(1, 2));
  }
}
