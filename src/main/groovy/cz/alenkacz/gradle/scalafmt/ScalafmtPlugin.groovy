package cz.alenkacz.gradle.scalafmt

import org.gradle.api.Plugin
import org.gradle.api.Project

class ScalafmtPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.task('scalafmt', type: ScalafmtTask)
        project.task('scalafmtTest', type: ScalafmtTestTask)
        project.extensions.create('scalafmt', PluginExtension)
    }
}
