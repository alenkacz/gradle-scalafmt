package cz.alenkacz.gradle.scalafmt

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class ScalafmtPluginTest extends Specification {
    def "add scalafmt task to the project"() {
        when:
        Project project = ProjectBuilder.builder().build()
        project.plugins.apply "scalafmt"

        then:
        project.tasks.scalafmt instanceof ScalafmtTask
    }
}
