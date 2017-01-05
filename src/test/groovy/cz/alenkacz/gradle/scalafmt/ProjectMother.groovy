package cz.alenkacz.gradle.scalafmt

import java.nio.file.Files
import java.nio.file.Paths

class ProjectMother {
    static def basicProjectWithCorrectlyFormattedFile() {
        TestProject testProject = null
        File.createTempDir().with {
            deleteOnExit()
            def srcFolder = new File(absoluteFile, sourceFilePath)
            srcFolder.mkdirs()
            def srcFile = Files.createFile(Paths.get(srcFolder.absolutePath, "Test.scala"))
            srcFile.write """import java.nio.file.{Paths, Files}
                     |object Test {
                     |  foo(a, // comment
                     |      b)
                     |}
                     |""".stripMargin()
            testProject = new TestProject(absoluteFile, srcFile.toFile())
        }
        return testProject
    }

    static def basicProjectWithIncorrectTestFile() {
        TestProject testProject = null
        File.createTempDir().with {
            deleteOnExit()
            def srcFolder = new File(absoluteFile, testSourceFilePath)
            srcFolder.mkdirs()
            def srcFile = Files.createFile(Paths.get(srcFolder.absolutePath, "Test.scala"))
            srcFile.write """
                     |object Test {
                     |  if(true){}
                     |}
                     |""".stripMargin()
            testProject = new TestProject(absoluteFile, srcFile.toFile())
        }
        return testProject
    }

    static def basicProject() {
        TestProject testProject = null
        File.createTempDir().with {
            deleteOnExit()
            def srcFolder = new File(absoluteFile, sourceFilePath)
            srcFolder.mkdirs()
            def srcFile = Files.createFile(Paths.get(srcFolder.absolutePath, "Test.scala"))
            srcFile.write testSourceFile
            testProject = new TestProject(absoluteFile, srcFile.toFile())
        }
        return testProject
    }

    private static def testSourceFile = """import java.nio.file.{Paths, Files}
                                           |object Test { foo(a, // comment
                                           |    b)}
                                           """.stripMargin()
    private static def sourceFilePath = "src/main/scala/cz/alenkacz/gradle/scalafmt/test"
    private static def testSourceFilePath = "src/test/scala/cz/alenkacz/gradle/scalafmt/test"

    static def projectWithConfig() {
        TestProject testProject = null
        File.createTempDir().with {
            deleteOnExit()
            def configFile = Files.createFile(Paths.get(absolutePath, ".scalafmt.conf"))
            configFile.write "rewrite.rules = [SortImports]"
            def srcFolder = new File(absoluteFile, sourceFilePath)
            srcFolder.mkdirs()
            def srcFile = Files.createFile(Paths.get(srcFolder.absolutePath, "Test.scala"))
            srcFile.write testSourceFile
            testProject = new TestProject(absoluteFile, srcFile.toFile())
        }
        return testProject
    }

    static def projectWithBadllyFormattedConfig() {
        TestProject testProject = null
        File.createTempDir().with {
            deleteOnExit()
            def configFile = Files.createFile(Paths.get(absolutePath, ".scalafmt.conf"))
            configFile.write "badconfig 123"
            def srcFolder = new File(absoluteFile, sourceFilePath)
            srcFolder.mkdirs()
            def srcFile = Files.createFile(Paths.get(srcFolder.absolutePath, "Test.scala"))
            srcFile.write testSourceFile
            testProject = new TestProject(absoluteFile, srcFile.toFile())
        }
        return testProject
    }

    static def projectWithCustomPathConfig() {
        TestProject testProject = null
        File.createTempDir().with {
            deleteOnExit()

            def configFolder = new File(absoluteFile, "config")
            configFolder.mkdirs()
            def configFile = Files.createFile(Paths.get(configFolder.absolutePath, ".scalafmt.conf"))
            configFile.write "rewrite.rules = [SortImports]"
            def srcFolder = new File(absoluteFile, sourceFilePath)
            srcFolder.mkdirs()
            def srcFile = Files.createFile(Paths.get(srcFolder.absolutePath, "Test.scala"))
            srcFile.write testSourceFile
            testProject = new TestProject(absoluteFile, srcFile.toFile())
        }
        return testProject
    }
}
