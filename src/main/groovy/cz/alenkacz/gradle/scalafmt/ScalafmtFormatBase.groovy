package cz.alenkacz.gradle.scalafmt

import org.gradle.api.DefaultTask
import org.gradle.api.plugins.JavaBasePlugin
import org.gradle.api.tasks.SourceSet
import org.scalafmt.Scalafmt

class ScalafmtFormatBase extends DefaultTask {
    SourceSet sourceSet

    PluginExtension pluginExtension

    def runScalafmt(boolean testOnly = false, boolean failOnMisformattedFiles = false) {
        if (project.plugins.withType(JavaBasePlugin).empty) {
            logger.info("Java or Scala gradle plugin not available in this project, nothing to format")
            return
        }
        def misformattedFiles = new ArrayList<String>()
        sourceSet.allSource.filter { File f -> canBeFormatted(f) }.each { File f ->
            String contents = f.text
            logger.debug("Formatting '$f'")
            def formattedContents = Scalafmt.format(contents, ConfigFactory.load(logger, project, pluginExtension.configFilePath), Scalafmt.format$default$3())
            if (testOnly) {
                if (contents != formattedContents.get()) {
                    misformattedFiles.add(f.absolutePath)
                }
            } else if (failOnMisformattedFiles) {
                if (contents != formattedContents.get()) {
                    misformattedFiles.add(f.absolutePath)
                }
                f.write(formattedContents.get())
            }else {
                f.write(formattedContents.get())
            }
        }

        if (!misformattedFiles.empty && (testOnly || failOnMisformattedFiles)) {
            throw new ScalafmtFormatException(misformattedFiles)
        }
    }

    def boolean canBeFormatted(File file) {
        file.getAbsolutePath().endsWith(".scala") || file.getAbsolutePath().endsWith(".sbt")
    }
}
