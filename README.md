## The starter project
## The sample scenario
- https://www.kitabevim.az website is opened.
- It is checked whether the Home page is opened.
- Type “roman” in the product search field and press the “Enter” key on the keyboard.
(The word "roman" to be written in the product field must be fetched from a csv file.)
- A random book is chosen from the search results.
- "Səbətə at" button is clicked.
- The value of the basket icon at the top right of the webpage is checked.
- The basket icon is hovered on.- SVG element problem on website
- "Səbətə bax" button is clicked.
- The amount of the product is increased by “1”.
- "Səbəti yenilə" button is clicked.
- It is checked whether the message "Səbət yeniləndi" is displayed.
- Cross button is clicked.
- It is checked whether the basket is empty.

The best place to start with Selenium and Cucumber is to clone or download the starter project on Github.
Project GitHub link   -   https://github.com/3lmdarlyv/interview-task.git

////////////////////////////////
### The project directory structure
Project should be written as a Maven project.
+src
  +test
    +java
       +com.example
         +model
           +ElementInfo.java
         +Hooks.java
         +Steps.java
         +TestRunner.java
    +resources
      +elementValues
        +KitabEvim-HomePage.json
      +features
        +KitabEvim-HomePage.features
      +log4j.properties
   

/////////////////////////////////
Please ensure that the latest version of the chromedriver.exe is added to the project before commencing the test.

Used that project Selenium, JUnit,Cucumber and Log4J libraries.

Support version:Dependencies and plugins
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.example</groupId>
    <artifactId>interview-kitabevim</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
        <maven.complier.version>3.6.0</maven.complier.version>
        <commons.lang3.version>3.5</commons.lang3.version>
        <java.version>1.8</java.version>
        <selenium.java.version>3.141.59</selenium.java.version>
        <cucumber.junit.version>6.8.1</cucumber.junit.version>
        <log4j.version>1.2.17</log4j.version>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>
    <dependencies>

    <dependency>
        <groupId>org.seleniumhq.selenium</groupId>
        <artifactId>selenium-java</artifactId>
        <version>${selenium.java.version}</version>
    </dependency>
        <dependency>
            <groupId>com.opencsv</groupId>
            <artifactId>opencsv</artifactId>
            <version>4.1</version>
        </dependency>

        <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>${log4j.version}</version>
        </dependency>
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
            <version>${commons.lang3.version}</version>
        </dependency>
        <dependency>
            <groupId>io.cucumber</groupId>
            <artifactId>cucumber-junit</artifactId>
            <version>${cucumber.junit.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>io.cucumber</groupId>
            <artifactId>cucumber-java</artifactId>
            <version>6.8.1</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <encoding>UTF-8</encoding>
                    <source>1.8</source>
                    <target>1.8</target>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-javadoc-plugin</artifactId>
                <version>3.0.0</version>
                <configuration>
                    <source>1.8</source>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>


/////////////

Author: Elmdar Aliyev
E-mail:eliyev.elmdar@gmail.com

