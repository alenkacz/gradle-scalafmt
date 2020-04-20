package cz.alenkacz.gradle.scalafmt

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.plugins.JavaBasePlugin
import org.gradle.api.plugins.JavaPluginConvention

class ScalafmtPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        Task scalafmtAll = project.tasks.create('scalafmtAll')
        scalafmtAll.description = "Formats all source sets using scalafmt."
        Task diffScalaFmtAll = project.tasks.create('diffScalafmtAll')
        diffScalaFmtAll.description = "Formats files returned from 'git diff' from all source sets using scalafmt."
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

                def diffTask = project.tasks.create(sourceSet.getTaskName("diff", "scalafmt"), ScalafmtTaskGit).configure { Task t ->
                    t.sourceSet = sourceSet
                    t.pluginExtension = extension
                    t.description = "Formats `git diff` files in ${sourceSet.name} source set using scalafmt."
                }

                def checkTask = project.tasks.create(sourceSet.getTaskName("check", "scalafmt"), ScalafmtCheckTask).configure { Task t ->
                    t.sourceSet = sourceSet
                    t.pluginExtension = extension
                    t.description = "Checks formatting of ${sourceSet.name} source set using scalafmt."
                }

                scalafmtAll.dependsOn task
                diffScalaFmtAll.dependsOn diffTask
                checkScalafmtAll.dependsOn checkTask
            }
        }
    }
}

