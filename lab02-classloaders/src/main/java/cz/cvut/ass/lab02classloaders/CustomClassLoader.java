package cz.cvut.ass.lab02classloaders;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CustomClassLoader extends ClassLoader {

    private static final int BUFFER_SIZE = 8192;

    @Override
    protected synchronized Class loadClass(String className, boolean resolve)
            throws ClassNotFoundException {
        try {
            Class cls = super.findSystemClass(className);
            return cls;
        } catch (ClassNotFoundException e) {
            return loadClassFromFile(className);
        }
    }

    private Class loadClassFromFile(String className) throws ClassNotFoundException {
        File f = new File(className + ".class");
        if (!f.exists()) {
            throw new ClassNotFoundException("Cannot load class: " + className);
        }

        byte[] classBytes = null;
        try {
            InputStream in = new FileInputStream(f);
            byte[] buffer = new byte[BUFFER_SIZE];
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int n = -1;
            while ((n = in.read(buffer, 0, BUFFER_SIZE)) != -1) {
                out.write(buffer, 0, n);
            }
            classBytes = out.toByteArray();
        } catch (IOException e) {
            System.out.println("ERROR loading class file: " + e);
        }

        if (classBytes == null) {
            throw new ClassNotFoundException("Cannot load class: " + className);
        }

        Class cls;
        try {
            cls = defineClass(className, classBytes, 0, classBytes.length);
        } catch (SecurityException e) {
            // loading core java classes such as java.lang.String
            // is prohibited, throws java.lang.SecurityException.
            // delegate to parent if not allowed to load class
            cls = super.loadClass(className, false);
        }

        return cls;
    }
}