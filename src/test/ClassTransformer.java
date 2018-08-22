package test;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

import static org.objectweb.asm.Opcodes.ASM5;

public class ClassTransformer implements ClassFileTransformer {
  public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
    byte[] ret = classfileBuffer;

    if (className.startsWith("java/io/")) {
      System.out.println(className);
    }

    if (className.equals("java/lang/Exception")) {
      try {
        ClassReader reader = new ClassReader("java.lang.Exception");
        ClassWriter writer = new ClassWriter(0);
        ClassVisitor visitor = new TransformClassVisitor(ASM5, writer);

        reader.accept(visitor, 0);
        ret = writer.toByteArray();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }

    return ret;
  }
}
