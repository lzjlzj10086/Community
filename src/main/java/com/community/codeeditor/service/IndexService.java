package com.community.codeeditor.service;

import com.community.codeeditor.classloader.FileClassLoader;
import com.community.codeeditor.dto.ExecuteResults;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.tools.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author: leisurexi
 * @date: 2020-02-01 9:44 下午
 * @description:
 * @since JDK 1.8
 */
@Service
@Slf4j
public class IndexService {

    /**
     * 执行代码，返回执行结果
     *
     * @param code 代码字符串
     * @return 执行结果
     */
    public ExecuteResults executeCode(String code) throws Exception {
        String stdout = null;
        code = "import java.io.*;\n" +
                "import java.lang.*;\n" +
                "import java.math.*;\n" +
                "import java.net.*;\n" +
                "import java.nio.*;\n" +
                "import java.text.*;\n" +
                "import java.time.*;\n" +
                "import java.util.*;\n" + code;
        String filePath = "./Test.java";
        List<String> errorMessage = compilerJavaCode(filePath, code);
        
        //代表编译成功
        if (errorMessage.isEmpty()) {
            Class<?> clazz = new FileClassLoader().loadClass(filePath.replace(".java", ".class"));
            if (clazz != null) {
                //给输出流加锁，防止多线程情况下的并发问题
                synchronized (System.out) {
                    //原先的输出流
                    PrintStream oldStream = System.out;
                    //创建获取控制台信息的输出流
                    ByteArrayOutputStream byteStream = new ByteArrayOutputStream(1024);
                    PrintStream printStream = new PrintStream(byteStream);
                    //截取控制台输出信息
                    System.setOut(printStream);
                    Method method = clazz.getMethod("main", new Class[]{String[].class});
                    method.invoke(null, new String[]{null});
                    //将截取的信息转换成字符串
                    stdout = byteStream.toString();
                    //截取完毕，将输出信息返回给控制台
                    System.setOut(oldStream);
                    
                }
            }
        }

        return ExecuteResults.builder()
                .compiled(errorMessage.size() == 0)
                .errorMessage(errorMessage)
                .stdout(stdout == null ? "" : stdout)
                .build();
    }

    /**
     * 保存代码字符串为.java文件，然后通过JavaCompiler编译为.class文件
     *
     * @param filePath .java文件地址
     * @param code     代码
     * @return 编译错误的信息，编译成功长度为0
     * @throws Exception
     */
    private List<String> compilerJavaCode(String filePath, String code) throws Exception {
        File file = new File(filePath);
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            byte[] bytes = code.getBytes();
            outputStream.write(bytes);
            outputStream.flush();
            // 使用JavaCompiler编译java文件
            JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
            // 建立DiagnosticCollector对象
            DiagnosticCollector<JavaFileObject> diagnosticCollectors = new DiagnosticCollector<>();
            StandardJavaFileManager fileManager = javaCompiler.getStandardFileManager(diagnosticCollectors, null, null);
            //建立用于保存被编译文件名的对象
            //每个文件被保存在一个继承JavaFileObject的类中
            Iterable<? extends JavaFileObject> fileObjects = fileManager.getJavaFileObjects(filePath);
            JavaCompiler.CompilationTask task = javaCompiler.getTask(null, fileManager, diagnosticCollectors, null, null, fileObjects);
            Boolean call = task.call();
            fileManager.close();

            if (call) {
                return Collections.emptyList();
            }
            List<String> errorMessage = new ArrayList<>();
            for (Diagnostic diagnostic : diagnosticCollectors.getDiagnostics()) {
                String message = String.format("Line %s: error: %s", diagnostic.getLineNumber(), diagnostic.getMessage(null));
                errorMessage.add(message);
            }
            errorMessage.add(errorMessage.size() + " errors");
            return errorMessage;
        } catch (Exception e) {
            throw e;
        }
    }

}
