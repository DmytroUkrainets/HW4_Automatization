package org.example.processor;

import org.example.annotation.CompileTimeAnnotation;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;

@SupportedAnnotationTypes("org.example.annotation.CompileTimeAnnotation")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class CompileTimeAnnotationProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(CompileTimeAnnotation.class)) {
            CompileTimeAnnotation annotation = element.getAnnotation(CompileTimeAnnotation.class);
            String className = annotation.className();
            try {
                JavaFileObject javaFileObject = processingEnv.getFiler().createSourceFile("org.example." + className);
                try (Writer writer = javaFileObject.openWriter()) {
                    writer.write("package org.example;\n");
                    writer.write("public class " + className + " {\n");
                    writer.write("    public void generatedMethod() {\n");
                    writer.write("        System.out.println(\"Generated method called\");\n");
                    writer.write("    }\n");
                    writer.write("}\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
