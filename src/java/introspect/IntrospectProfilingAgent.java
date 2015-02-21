package introspect;

import java.lang.instrument.Instrumentation;

public class IntrospectProfilingAgent {

  public static void premain(String agentArgs, Instrumentation inst) {
    inst.addTransformer(new IntrospectClassFileTransformer());
    return;
  }

}
