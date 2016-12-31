package cz.alenkacz.gradle.scalafmt

import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Paths

class ScalafmtTaskTest extends Specification {
    def "format source code"() {
        given:
        def testProject = prepareProject()
        def project = ProjectBuilder.builder().withProjectDir(testProject.projectRoot).build()
        project.plugins.apply 'scala'
        project.plugins.apply 'scalafmt'

        when:
        project.tasks.scalafmt.format()

        then:
        def actual = testProject.testFile.text
        actual == """object Test {
                     |  foo(a, // comment
                     |      b)
                     |}
                     |""".stripMargin()
    }

    def prepareProject() {
        TestProject testProject = null
        File.createTempDir().with {
            deleteOnExit()
            def srcFolder = new File(absoluteFile, "src/main/scala/cz/alenkacz/gradle/scalafmt/test")
            srcFolder.mkdirs()
            def srcFile = Files.createFile(Paths.get(srcFolder.absolutePath, "Test.scala"))
            srcFile.write """
             |object Test { foo(a, // comment
             |    b)}
             """.stripMargin()
            testProject = new TestProject(absoluteFile, srcFile.toFile())
        }
        return testProject
    }
}
