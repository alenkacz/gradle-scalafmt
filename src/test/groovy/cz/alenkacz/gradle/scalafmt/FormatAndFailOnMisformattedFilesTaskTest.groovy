package cz.alenkacz.gradle.scalafmt

import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class FormatAndFailOnMisformattedFilesTaskTest extends Specification {
    def "fail for badly formatted code"() {
        given:
        def testProject = ProjectMother.basicProject()
        def project = ProjectBuilder.builder().withProjectDir(testProject.projectRoot).build()
        project.plugins.apply 'scala'
        project.plugins.apply 'scalafmt'

        when:
        project.tasks.formatAndFailOnMisformattedFilesScalafmt.format()

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
        project.tasks.formatAndFailOnMisformattedFilesScalafmt.format()

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
        project.tasks.formatAndFailOnMisformattedFilesScalafmt.format()

        then:
        noExceptionThrown()
    }
}
