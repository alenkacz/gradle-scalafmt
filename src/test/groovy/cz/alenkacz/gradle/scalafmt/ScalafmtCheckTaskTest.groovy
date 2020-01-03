package cz.alenkacz.gradle.scalafmt

import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class ScalafmtCheckTaskTest extends Specification {
    def "fail for badly formatted code"() {
        given:
        def testProject = ProjectMother.basicProject()
        def project = ProjectBuilder.builder().withProjectDir(testProject.projectRoot).build()
        project.plugins.apply 'scala'
        project.plugins.apply 'scalafmt'

        when:
        project.tasks.checkScalafmt.format()

        then:
        thrown ScalafmtFormatException
    }

    def "not fail for correctly formatted code"() {
        given:
        def testProject = ProjectMother.basicProjectWithCorrectlyFormattedFile()
        def project = ProjectBuilder.builder().withProjectDir(testProject.projectRoot).build()
        project.plugins.apply 'scala'
        project.plugins.apply 'scalafmt'

        when:
        project.tasks.checkScalafmt.format()

        then:
        noExceptionThrown()
    }

    def "ignore source files in test"() {
        given:
        def testProject = ProjectMother.basicProjectWithIncorrectTestFile()
        def project = ProjectBuilder.builder().withProjectDir(testProject.projectRoot).build()

        project.plugins.apply 'scalafmt'
        project.plugins.apply 'scala'

        when:
        project.evaluate()
        project.tasks.checkScalafmt.format()

        then:
        noExceptionThrown()
    }

    def "not fail Scala compilation with custom repository"() {
        given:
        def testProject = ProjectMother.basicProjectWithCorrectlyFormattedFile()
        def project = ProjectBuilder.builder().withProjectDir(testProject.projectRoot).build()
        project.plugins.apply 'scala'
        project.plugins.apply 'scalafmt'
        project.repositories {
            mavenCentral()
        }
        project.dependencies {
            compile 'org.scala-lang:scala-library:2.12.10'
        }

        when:
        project.tasks.checkScalafmt.format()
        project.tasks.compileScala.compile()

        then:
        noExceptionThrown()
    }
}
