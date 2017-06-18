package cz.alenkacz.gradle.scalafmt

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.plugins.JavaBasePlugin
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.play.PlayApplicationSpec
import org.gradle.play.plugins.PlayApplicationPlugin
import org.gradle.play.plugins.PlayPlugin

class ScalafmtPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        Task scalafmtAll = project.tasks.create('scalafmtAll')
        scalafmtAll.description = "Formats all source sets using scalafmt."
        Task checkScalafmtAll = project.tasks.create('checkScalafmtAll')
        checkScalafmtAll.description = "Checks formatting of all source sets using scalafmt."
        PluginExtension extension = project.extensions.create('scalafmt', PluginExtension)

        project.plugins.withType(JavaBasePlugin) {
            def jpc = project.convention.getPlugin(JavaPluginConvention)
            jpc.sourceSets.all { sourceSet ->
                def task = project.tasks.create(sourceSet.getTaskName("", "scalafmt"), ScalafmtTask).configure { Task t ->
                    t.sourceSet = sourceSet
                    t.pluginExtension = extension
                    t.description = "Formats ${sourceSet.name} source set using scalafmt."
                }

                def checkTask = project.tasks.create(sourceSet.getTaskName("check", "scalafmt"), ScalafmtCheckTask).configure { Task t ->
                    t.sourceSet = sourceSet
                    t.pluginExtension = extension
                    t.description = "Checks formatting of ${sourceSet.name} source set using scalafmt."
                }
                scalafmtAll.dependsOn task
                checkScalafmtAll.dependsOn checkTask
            }
        }
    }
}
