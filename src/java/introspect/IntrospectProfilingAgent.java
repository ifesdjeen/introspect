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

  //private static final Instrumentation instrumentation = ByteBuddyAgent.installOnOpenJDK();

  private static class MethodLogger extends AdviceAdapter {

    private final String className;
    private final String name;
    private final String desc;
    private final int access;

    protected MethodLogger(int api, String className, MethodVisitor methodVisitor, int access, String name, String desc) {
      super(api, methodVisitor, access, name, desc);

      this.className = className;
      this.desc = desc;
      this.name = name;
      this.access = access;
    }

    @Override
    protected void onMethodEnter() {
      boolean isStatic = (access & ACC_STATIC) > 0;

      int length = Type.getArgumentTypes(desc).length;

      super.visitIntInsn(BIPUSH, length);
      super.visitTypeInsn(ANEWARRAY, "java/lang/Object");

      for (int i = 0; i < length; i++) {
        dup();
        super.visitIntInsn(BIPUSH, i);
        super.visitVarInsn(ALOAD, i + (isStatic ? 0 : 1));
        super.visitInsn(AASTORE);
      }


      super.visitLdcInsn(className);
      super.visitLdcInsn(name);
      super.visitMethodInsn(INVOKESTATIC, "introspect/Interceptor", "in", "([Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;)V", false);
    }

    @Override
    protected void onMethodExit(int opcode) {
      // pushes the method name on the stack

      if(opcode==RETURN) {
        visitInsn(ACONST_NULL);
      } else if(opcode==ARETURN || opcode==ATHROW) {
        dup();
      } else {
        if(opcode==LRETURN || opcode==DRETURN) {
          dup2();
        } else {
          dup();
        }
        box(Type.getReturnType(this.methodDesc));

      }

      super.visitLdcInsn(className);
      super.visitLdcInsn(name);
      super.visitMethodInsn(INVOKESTATIC, "introspect/Interceptor", "out", "(Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;)V", false);
    }

  }


  public static void initializeAgent(String name, Instrumentation instrumentation) {

    new AgentBuilder.Default()
      .rebase(ElementMatchers.nameStartsWith(name)
//                             .and(ElementMatchers.not(ElementMatchers.nameContains("load")))
//                             .and(ElementMatchers.not(ElementMatchers.nameContains("auto")))
//                             .and(ElementMatchers.not(ElementMatchers.nameContains("init")))
             )
      .transform(new AgentBuilder.Transformer() {
        @Override
        public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription) {
          return builder.classVisitor(new ClassVisitorWrapper() {
            @Override
            public ClassVisitor wrap(ClassVisitor classVisitor) {
              return new ClassVisitor(Opcodes.ASM4, classVisitor) {

                private String className;

                @Override
                public void visit(int version, int access, String className, String signature, String superName,
                                  String[] interfaces) {
                  super.visit(version, access, className, signature, superName, interfaces);
                  this.className = className.replace("/", ".");
                }

                @Override
                public MethodVisitor visitMethod(int access, String name, String desc, String signature,
                                                 String[] ex) {
                  MethodVisitor orig = super.visitMethod(access, name, desc, signature, ex);

                  System.out.println("Injecting: " + className + " " + name);
                  if (!name.equals("invoke")) {
                    return orig;
                  } else {
                    return new MethodLogger(Opcodes.ASM4, className, orig, access, name, desc);
                  }
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
