package test;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import static org.objectweb.asm.Opcodes.*;

public class TransformMethodVisitor extends MethodVisitor {
  TransformMethodVisitor(int i, MethodVisitor methodVisitor) {
    super(i, methodVisitor);
  }

  @Override
  public void visitInsn(int var1) {
    switch (var1) {
      case ARETURN:
      case DRETURN:
      case FRETURN:
      case IRETURN:
      case LRETURN:
      case RETURN:
      case ATHROW:
        visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        visitLdcInsn("Exception detected");
        visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
      default:
        break;
    }
    super.visitInsn(var1);
  }

  @Override
  public void visitEnd() {
    super.visitEnd();
  }
}
