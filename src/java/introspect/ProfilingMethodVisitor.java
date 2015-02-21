package introspect;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ProfilingMethodVisitor extends MethodVisitor {

  private final String className;
  private final String methodName;
  private final int argCount;
  private final String argLine;

  public ProfilingMethodVisitor(int api, String className, String methodName, String desc, MethodVisitor mv) {
    super(api, mv);
    this.className = className;
    this.methodName = methodName;
    this.argLine = desc.substring(1, desc.indexOf(')'));
    this.argCount = (argLine.length() == 0) ? 0 : argLine.split(";").length;
  }

  @Override
  public void visitCode() {
    try {
      super.visitCode();
      super.visitLdcInsn(className);

      if (argCount > 0) {
        for (int i = 1; i <= argCount; i++) {
          super.visitVarInsn(Opcodes.ALOAD, i);
        }

        super.visitMethodInsn(Opcodes.INVOKESTATIC, "introspect/Visitor", "methodEntered",
                              "(Ljava/lang/String;" + argLine  + ")V");
      } else  {
        super.visitMethodInsn(Opcodes.INVOKESTATIC, "introspect/Visitor", "methodEntered",
                              "(Ljava/lang/String;)V");
      }

    } catch (Exception e) {

    }
  }

  @Override
  public void visitInsn(int opcode) {
    try {
      if (opcode == Opcodes.IRETURN || opcode == Opcodes.FRETURN
          || opcode == Opcodes.ARETURN
          || opcode == Opcodes.LRETURN || opcode == Opcodes.DRETURN) {


        //        super.visitCode();
        super.visitLdcInsn(className);
        super.visitInsn(Opcodes.DUP2);
        super.visitInsn(Opcodes.POP);

        super.visitMethodInsn(Opcodes.INVOKESTATIC, "introspect/Visitor", "methodLeft",
                              "(Ljava/lang/String;Ljava/lang/Object;)V");

      }

      super.visitInsn(opcode);
    } catch (Exception e) {

    }




  }

}
