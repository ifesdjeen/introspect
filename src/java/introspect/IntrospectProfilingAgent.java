package introspect;

import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.instrumentation.MethodDelegation;
import net.bytebuddy.instrumentation.SuperMethodCall;
import net.bytebuddy.instrumentation.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatchers;

import javax.lang.model.element.Element;
import java.lang.instrument.Instrumentation;

public class IntrospectProfilingAgent {

  private static final Instrumentation instrumentation = ByteBuddyAgent.installOnOpenJDK();

  public static void initializeAgent(String name) {
    new AgentBuilder.Default()
      .rebase(ElementMatchers.nameContains(name)
                // .and(ElementMatchers.nameContains("$"))
                //.and(ElementMatchers.not(ElementMatchers.nameContains("SomeProtocol")))
                //.and(ElementMatchers.not(ElementMatchers.nameMatches("(.*)\\$fn__(\\d+)\\$__GT(.*)")))
                //.and(ElementMatchers.not(ElementMatchers.nameMatches("(.*)\\$fn__(\\d+)\\$G__(\\d+)__(.*)")))
                //.and(ElementMatchers.not(ElementMatchers.nameContains("G__")))
                //.and(ElementMatchers.not(ElementMatchers.nameContains("original")))
                //.and(ElementMatchers.not(ElementMatchers.nameContains("accessor")))
                .and(ElementMatchers.not(ElementMatchers.nameContains("load")))
                .and(ElementMatchers.not(ElementMatchers.nameContains("auto")))
                .and(ElementMatchers.not(ElementMatchers.nameContains("init"))))

      .transform(new AgentBuilder.Transformer() {
        @Override
        public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription) {
          System.out.printf("Transforming %s\n", typeDescription.getName());
          System.out.printf("%s, isinterface: %s \n", typeDescription, typeDescription.isInterface());
          if (!typeDescription.isInterface() && !typeDescription.isAbstract() && !typeDescription.isWrapper()) {
            return builder.method(ElementMatchers.any()
                                          .and(ElementMatchers.not(ElementMatchers.nameContains("methodImplCache")))
                                          .and(ElementMatchers.not(ElementMatchers.isGetter()))
                                          .and(ElementMatchers.not(ElementMatchers.isSetter()))
                                          .and(ElementMatchers.not(ElementMatchers.isToString()))
                                          .and(ElementMatchers.not(ElementMatchers.isHashCode()))
                                          .and(ElementMatchers.not(ElementMatchers.isEquals())))
                          .intercept(MethodDelegation.to(Interceptor.class));
          } else {
            return null;
          }
        }
      })
      .installOn(instrumentation);
  }

}
