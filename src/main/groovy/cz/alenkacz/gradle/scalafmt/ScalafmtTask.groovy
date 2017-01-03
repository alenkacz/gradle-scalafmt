package cz.alenkacz.gradle.scalafmt

import org.gradle.api.DefaultTask
import org.gradle.api.plugins.JavaBasePlugin
import org.gradle.api.tasks.TaskAction
import org.scalafmt.Scalafmt
import org.scalafmt.config.ScalafmtConfig

class ScalafmtTask extends DefaultTask {
    @TaskAction
    def format() {
        if (project.plugins.withType(JavaBasePlugin).empty) {
            logger.info("Java or Scala gradle plugin not available in this project, nothing to format")
            return
        }

        project.sourceSets.all { sourceSet ->
            sourceSet.allSource.filter { File f -> canBeFormatted(f) }.each { File f ->
                String contents = f.text
                logger.debug("Formatting '$f'")
                def formattedContents = Scalafmt.format(contents, ConfigFactory.load(logger, project, project.scalafmt.configFilePath), Scalafmt.format$default$3())
                f.write(formattedContents.get())
            }
        }
    }

    def boolean canBeFormatted(File file) {
        file.getAbsolutePath().endsWith(".scala") || file.getAbsolutePath().endsWith(".sbt")
    }
}
