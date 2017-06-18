package cz.alenkacz.gradle.scalafmt

import org.gradle.api.Task
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class ScalafmtTaskTest extends Specification {
    def "format source code"() {
        given:
        def testProject = ProjectMother.basicProject()
        def project = ProjectBuilder.builder().withProjectDir(testProject.projectRoot).build()
        project.plugins.apply 'scala'
        project.plugins.apply 'scalafmt'

        when:
        project.tasks.scalafmt.format()

        then:
        def actual = testProject.testFile.text
        actual == """import java.nio.file.{Paths, Files}
                     |object Test {
                     |  foo(a, // comment
                     |      b)
                     |}
                     |""".stripMargin()
    }

    def "format play source code"() {
        given:
        def testProject = ProjectMother.playProject()
        def project = ProjectBuilder.builder().withProjectDir(testProject.projectRoot).build()
        project.plugins.apply 'scala'
        project.plugins.apply 'play'
        project.plugins.apply 'scalafmt'

        when:
        project.tasks.scalafmt.format()

        then:
        def actual = testProject.testFile.text
        actual == """import java.nio.file.{Paths, Files}
                     |object Test {
                     |  foo(a, // comment
                     |      b)
                     |}
                     |""".stripMargin()
    }

    def "finish successfully even for project without scala and java plugin applied"() {
        when:
        def testProject = ProjectMother.basicProject()
        def project = ProjectBuilder.builder().withProjectDir(testProject.projectRoot).build()
        project.plugins.apply 'scalafmt'

        then:
        project.tasks.checkScalafmtAll instanceof Task
        project.tasks.scalafmtAll instanceof Task
    }

    def "load configuration from project root and apply it to the source"() {
        given:
        def testProject = ProjectMother.projectWithConfig()
        def project = ProjectBuilder.builder().withProjectDir(testProject.projectRoot).build()
        project.plugins.apply 'scalafmt'
        project.plugins.apply 'scala'

        when:
        project.tasks.scalafmt.format()

        then:
        def actual = testProject.testFile.text
        actual == """import java.nio.file.{Files, Paths}
                     |object Test {
                     |  foo(a, // comment
                     |      b)
                     |}
                     |""".stripMargin()
    }

    def "finish successfully even for project with badly formatted config"() {
        given:
        def testProject = ProjectMother.projectWithBadllyFormattedConfig()
        def project = ProjectBuilder.builder().withProjectDir(testProject.projectRoot).build()
        project.plugins.apply 'scalafmt'
        project.plugins.apply 'scala'

        when:
        project.tasks.scalafmt.format()

        then:
        noExceptionThrown()
    }

    def "load configuration from a custom location and apply it to the source"() {
        given:
        def testProject = ProjectMother.projectWithCustomPathConfig()
        def project = ProjectBuilder.builder().withProjectDir(testProject.projectRoot).build()
        project.plugins.apply 'scalafmt'
        project.plugins.apply 'scala'
        def extension = (PluginExtension) project.extensions.findByName('scalafmt')
        extension.setConfigFilePath("./config/.scalafmt.conf")

        when:
        project.tasks.scalafmt.format()

        then:
        def actual = testProject.testFile.text
        actual == """import java.nio.file.{Files, Paths}
                     |object Test {
                     |  foo(a, // comment
                     |      b)
                     |}
                     |""".stripMargin()
    }
}
