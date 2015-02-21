package introspect;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.instrumentation.MethodDelegation;
import net.bytebuddy.instrumentation.SuperMethodCall;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.instrument.Instrumentation;

// TODO isSubtypeIf(AFN)
public class MainTest {
  public static void initialize(String name, Instrumentation inst) {

    new AgentBuilder.Default(new ByteBuddy())


            .rebase(ElementMatchers.nameContains(name)
                            .and(ElementMatchers.not(ElementMatchers.nameContains("LogInterceptor")))
                            .and(ElementMatchers.not(ElementMatchers.nameContains("load")))
                            .and(ElementMatchers.not(ElementMatchers.nameContains("init")))
                   )
            .transform(new AgentBuilder.Transformer() {
              @Override
              public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder) {
                String s = builder.toString();
                System.out.println(s.replaceAll("binaryRepresentation(.*)classFileLocator", ""));
                // System.out.println(builder.);
                return builder.method(ElementMatchers.any())
                               .intercept(MethodDelegation.to(LogInterceptor.class)
                                                  .andThen(SuperMethodCall.INSTANCE)
                                         )
//                               .constructor(ElementMatchers.any())
//                               .intercept(MethodDelegation.to(LogInterceptor.class)
//                                                  .andThen(SuperMethodCall.INSTANCE))

                        ;
              }
            }) //.allowRetransformation()
            .installOn(inst);
  }
}
