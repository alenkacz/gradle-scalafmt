package cz.alenkacz.gradle.scalafmt

import org.gradle.api.Plugin
import org.gradle.api.Project

class ScalafmtPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        ScalafmtTask scalafmtTask = project.task('scalafmt', type: ScalafmtTask)
        PluginExtension extension = project.extensions.create('scalafmt', PluginExtension)
    }
}
