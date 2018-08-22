package test;

import java.io.*;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;

public class Agent {
  public static void agentmain(String agentArg, Instrumentation inst) throws Exception {
    inst.addTransformer(new ClassTransformer());

    inst.redefineClasses(
        define(Exception.class),
        define(File.class),
        define(FileInputStream.class)
    );
  }

  private static ClassDefinition define(Class<?> cl) throws IOException {
    InputStream inStream = ClassLoader.getSystemResourceAsStream(Utils.getClassName(cl));
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    int read;
    byte[] data = new byte[65536];
    while ((read = inStream.read(data, 0, data.length)) != -1) {
      outStream.write(data, 0, read);
    }

    return new ClassDefinition(cl, outStream.toByteArray());
  }
}
