package introspect;

import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.ClassVisitorWrapper;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.jar.asm.ClassVisitor;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;
import net.bytebuddy.jar.asm.Type;
import net.bytebuddy.jar.asm.commons.AdviceAdapter;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.instrument.Instrumentation;

public class IntrospectProfilingAgent {

  private static final Instrumentation instrumentation = ByteBuddyAgent.installOnOpenJDK();

  private static class MethodLogger extends AdviceAdapter {

    private final String name;
    private final String desc;
    private final int access;

    protected MethodLogger(int api, MethodVisitor methodVisitor, int access, String name, String desc) {
      super(api, methodVisitor, access, name, desc);

      this.desc = desc;
      this.name = name;
      this.access = access;
    }

    @Override
    protected void onMethodEnter() {
      // checks if the method is static.
      // The difference is that "this" is stored in ALOAD_0 and the arguments are stored in ALOAD_1, ALOAD_2, ...
      // But there is no "this" for a static method call. Therefor the arguments are stored in ALOAD_0, ALOAD_1 ,...
      // If we want to access the arguments we need to differentiate between static and non static method calls.
      boolean isStatic = (access & ACC_STATIC) > 0;

      int length = Type.getArgumentTypes(desc).length;

      // pushes the method name on the stack
      super.visitLdcInsn(name);
      // pushes the count of arguments on the stack
      // could be optimized if we would use iconst_0, iconst_1, ..., iconst_5 for 0 to 5.
      super.visitIntInsn(BIPUSH, length);
      // creates an object array with the count of arguments
      super.visitTypeInsn(ANEWARRAY, "java/lang/Object");

      // stores the arguments in the array
      for (int i = 0; i < length; i++) {
        // duplicates the reference to the array. Because the AASTORE opcode consumes the stack element with the reference to the array.
        super.visitInsn(DUP);
        // could be optimized
        super.visitIntInsn(BIPUSH, i);
        // puts the value of the current argument on the stack
        super.visitVarInsn(ALOAD, i + (isStatic ? 0 : 1));
        // stores the value of the current argument in the array
        super.visitInsn(AASTORE);
      }

      // calls the MethodLogger#log(String, Object...) method with the corresponding arguments - which we created just before
      super.visitMethodInsn(INVOKESTATIC, "introspect/Interceptor", "log", "(Ljava/lang/String;[Ljava/lang/Object;)V", false);
    }

//    @Override
//    protected void onMethodExit(int opcode) {
//
//    }
  }


  public static void initializeAgent(String name) {
    new AgentBuilder.Default()
      .rebase(ElementMatchers.nameContains(name)
                             .and(ElementMatchers.not(ElementMatchers.nameContains("load")))
                             .and(ElementMatchers.not(ElementMatchers.nameContains("auto")))
                             .and(ElementMatchers.not(ElementMatchers.nameContains("init"))))
      .transform(new AgentBuilder.Transformer() {
        @Override
        public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription) {
          return builder.classVisitor(new ClassVisitorWrapper() {
            @Override
            public ClassVisitor wrap(ClassVisitor classVisitor) {
              return new ClassVisitor(Opcodes.ASM4, classVisitor) {
                @Override
                public MethodVisitor visitMethod(int access, String name, String desc, String signature,
                                                 String[] ex) {
                  MethodVisitor orig = super.visitMethod(access, name, desc, signature, ex);
                  System.out.println("Methodname:" + name);
                  System.out.println("Desc:" +desc);

                  if (!name.equals("invoke")) {
                    return orig;
                  }

                  return new MethodLogger(Opcodes.ASM4, orig, access, name, desc);
                }
              };
            }
          });
        }
      })

      .installOn(instrumentation);

        //      .transform(new AgentBuilder.Transformer() {
        //        @Override
        //        public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder,
        //                                                TypeDescription typeDescription) {
        //          if (!typeDescription.isInterface() && !typeDescription.isAbstract()) {
        //            System.out.printf("Transforming %s\n", typeDescription.getName());
        //            return builder.method(ElementMatchers.any()
        //                                  //                                                 .and(ElementMatchers.not(ElementMatchers.isToString()))
        //                                  //                                                 .and(ElementMatchers.not(ElementMatchers.isHashCode()))
        //                                  //                                                 .and(ElementMatchers.not(ElementMatchers.isEquals()))
        //                                 )
        //                          .intercept(
        //                            MethodDelegation.to(Interceptor.class)
        //                                            .andThen(SuperMethodCall.INSTANCE));
        //          } else {
        //            return null;
        //          }
        //        }
        //      })
    //      {
    //        @Override
    //        public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription) {
    //          // System.out.printf("%s, isinterface: %s \n", typeDescription, typeDescription.isInterface());
    //
    //        }
    //      })
    //      .installOn(instrumentation);
  }

}
