# 在Gradle中使用EJC编译带Lombok的项目

项目原代码中含有只能通过EJC(JDT)进行编译的内容，且原始代码不允许修改。同时项目中也使用了Lombok。要求使用gradle进行打包。

JDK:17  
IDE:Eclipse/IDEA  
Gradle:7.5.1  
themrmilchmann.ecj:0.2.0  
springframework.boot:3.0.0  
projectlombok:1.18.28


```java
public class ECJTest implements BaseInterface<TClass>{

    public static void main(String[] args) {
        TClass  s = new TClass();
        System.out.println(s.hashCode());

        LombokData lombokTest = new LombokData();
        System.out.println(lombokTest.getTest());

    }
}
```
上述代码，在使用JDK编译会出现编译错误
```
Type parameter 'org.example.TClass' is not within its bound; should extend 'org.example.BaseClass<?>'
```
必须通过JDT Core Programmer Guide/ECJ才能通过编译

在build.gradle中使用ecj插件io.github.themrmilchmann.ecj
```gradle
plugins {
    id 'java'
    id 'war'
    id 'org.springframework.boot' version '3.0.0'
    id 'io.spring.dependency-management' version '1.1.0'
    id 'com.github.node-gradle.node' version '3.5.0'
    id "io.github.themrmilchmann.ecj" version "0.2.0"
}
```
同时由于LombokData使用了Lombok
```java
@Data
public class LombokData {
    private String test;
}
```
需要在build.gradle中增加如下两部分
```gradle
dependencies {
    compileOnly 'org.projectlombok:lombok:1.18.28'
    annotationProcessor 'org.projectlombok:lombok:1.18.28'
}
```
```gradle
compileJava {
    options.forkOptions.jvmArgs += '-javaagent:lib/lombok-1.18.28.jar'
}
```

经过测试可以正常的进行编译以及构建war包

---
参考资料  
https://projectlombok.org/setup/  
https://github.com/TheMrMilchmann/gradle-ecj  
https://stackoverflow.com/questions/36696242/use-maven-compiler-plugin-with-eclipse-compiler-and-lombok
