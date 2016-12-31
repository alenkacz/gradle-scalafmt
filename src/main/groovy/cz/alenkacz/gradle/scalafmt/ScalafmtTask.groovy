package cz.alenkacz.gradle.scalafmt

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.scalafmt.Scalafmt
import org.scalafmt.config.ScalafmtConfig

class ScalafmtTask extends DefaultTask {
    @TaskAction
    def format() {
        project.sourceSets.main.allScala.filter { File f -> f.getAbsolutePath().endsWith(".scala") } .each { File f ->
            String contents = f.text
            logger.debug("Formatting '$f'")
            def formattedContents = Scalafmt.format(contents, Scalafmt.format$default$2(), Scalafmt.format$default$3())
            f.write(formattedContents.get())
        }
    }
}
