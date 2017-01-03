package cz.alenkacz.gradle.scalafmt

import org.gradle.api.DefaultTask
import org.gradle.api.plugins.JavaBasePlugin
import org.scalafmt.Scalafmt

class ScalafmtFormatBase extends DefaultTask {
    def runScalafmt(boolean testOnly = false) {
        if (project.plugins.withType(JavaBasePlugin).empty) {
            logger.info("Java or Scala gradle plugin not available in this project, nothing to format")
            return
        }
        def misformattedFiles = new ArrayList<String>()
        project.sourceSets.all { sourceSet ->
            sourceSet.allSource.filter { File f -> canBeFormatted(f) }.each { File f ->
                String contents = f.text
                logger.debug("Formatting '$f'")
                def formattedContents = Scalafmt.format(contents, ConfigFactory.load(logger, project, project.scalafmt.configFilePath), Scalafmt.format$default$3())
                if (testOnly) {
                    if (contents != formattedContents.get()) {
                        misformattedFiles.add(f.absolutePath)
                    }
                } else {
                    f.write(formattedContents.get())
                }
            }
        }

        if (testOnly && !misformattedFiles.empty) {
            throw new ScalafmtFormatException(misformattedFiles)
        }
    }

    def boolean canBeFormatted(File file) {
        file.getAbsolutePath().endsWith(".scala") || file.getAbsolutePath().endsWith(".sbt")
    }
}
