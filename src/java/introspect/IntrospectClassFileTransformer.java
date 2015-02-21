package introspect;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import clojure.asm.commons.AdviceAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class IntrospectClassFileTransformer implements ClassFileTransformer {

  @Override
  public byte[] transform(ClassLoader loader, final String className, Class<?> classBeingRedefined,
                          ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

    // fixme better exclude for system classes
    if ( className.startsWith("introspect_test") && !className.contains("loading") && !className.contains("eval") && !className.contains("_main") ) {
      ClassReader classReader = new ClassReader(classfileBuffer);
      ClassWriter classWriter = new ClassWriter(classReader, 0);
      ClassVisitor classVisitor = new ClassVisitor(Opcodes.ASM4, classWriter) {

          @Override
          public MethodVisitor visitMethod(int access, String methodName, String desc, String signature, String[] exceptions) {
            MethodVisitor methodVisitor = super.visitMethod(access, methodName, desc, signature, exceptions);

            if (!methodName.equals("invoke")) {
              return methodVisitor;
            }
            return new ProfilingMethodVisitor(api, className, methodName, desc, methodVisitor);
          }

        };

      classReader.accept(classVisitor, ClassReader.SKIP_FRAMES);

      return classWriter.toByteArray();
    } else {
      return classfileBuffer;
    }

  }


}
