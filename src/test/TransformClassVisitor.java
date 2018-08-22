package test;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

public class TransformClassVisitor extends ClassVisitor {
  TransformClassVisitor(int i, ClassVisitor classVisitor) {
    super(i, classVisitor);
  }

  @Override
  public MethodVisitor visitMethod(int i, String s, String s1, String s2, String[] strings) {
    MethodVisitor visitor = super.visitMethod(i, s, s1, s2, strings);
    if (s.equals("<init>")) {
      visitor = new TransformMethodVisitor(api, visitor);
    }
    return visitor;
  }
}
