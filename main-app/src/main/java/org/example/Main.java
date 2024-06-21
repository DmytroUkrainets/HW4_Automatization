package org.example;

import org.example.annotation.CompileTimeAnnotation;
import org.example.annotation.RuntimeAnnotation;

import java.lang.reflect.Method;

@CompileTimeAnnotation(className = "GeneratedClass")
public class Main {

    @RuntimeAnnotation("Hello, World!")
    public void annotatedMethod() {
        System.out.println("This is an annotated method.");
    }

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.processRuntimeAnnotations();

        GeneratedClass generatedClass = new GeneratedClass();
        generatedClass.generatedMethod();
    }

    private void processRuntimeAnnotations() throws Exception {
        for (Method method : this.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(RuntimeAnnotation.class)) {
                RuntimeAnnotation annotation = method.getAnnotation(RuntimeAnnotation.class);
                System.out.println("Annotation value: " + annotation.value());
                method.invoke(this);
            }
        }
    }
}
