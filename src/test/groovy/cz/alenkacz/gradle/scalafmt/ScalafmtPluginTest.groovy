package cz.alenkacz.gradle.scalafmt

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class ScalafmtPluginTest extends Specification {
    def "add scalafmt task to the project"() {
        when:
        Project project = ProjectBuilder.builder().build()
        project.plugins.apply 'scala'
        project.plugins.apply "scalafmt"

        then:
        project.tasks.scalafmt instanceof ScalafmtTask
        project.tasks.testScalafmt instanceof ScalafmtTask
        project.tasks.checkScalafmt instanceof ScalafmtCheckTask
        project.tasks.checkTestScalafmt instanceof ScalafmtCheckTask
        project.tasks.scalafmtAll instanceof Task
        project.tasks.checkScalafmtAll instanceof Task
        project.tasks.formatAndFailOnMisformattedFilesTestScalafmt instanceof ScalafmtFormatAndFailOnMisformattedFilesTask
        project.tasks.scalafmtAllAndFailOnMisformattedFiles instanceof Task
    }
}
