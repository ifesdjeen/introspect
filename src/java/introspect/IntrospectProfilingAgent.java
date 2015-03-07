package introspect;

import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.instrumentation.MethodDelegation;
import net.bytebuddy.instrumentation.SuperMethodCall;
import net.bytebuddy.instrumentation.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.instrument.Instrumentation;

public class IntrospectProfilingAgent {

  private static final Instrumentation instrumentation = ByteBuddyAgent.installOnOpenJDK();

  public static void initializeAgent(String name) {
    new AgentBuilder.Default()
      .rebase(ElementMatchers.nameContains(name)
              .and(ElementMatchers.nameContains("$"))
              .and(ElementMatchers.not(ElementMatchers.nameContains("load")))
              .and(ElementMatchers.not(ElementMatchers.nameContains("auto")))
              .and(ElementMatchers.not(ElementMatchers.nameContains("init"))))

      .transform(new AgentBuilder.Transformer() {
          @Override
            public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder,
                                                    TypeDescription typeDescription) {
            System.out.printf("Transforming %s\n", typeDescription.getName());
            return builder
              .method(ElementMatchers.named("invoke"))
              .intercept(MethodDelegation.to(Interceptor.class));
          }
        })
      .installOn(instrumentation);
  }

}
