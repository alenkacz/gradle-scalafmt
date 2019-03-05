package cz.alenkacz.gradle.scalafmt

import org.gradle.api.DefaultTask
import org.gradle.api.plugins.JavaBasePlugin
import org.gradle.api.tasks.SourceSet
import org.scalafmt.interfaces.Scalafmt
import org.scalafmt.interfaces.ScalafmtClassLoader
import java.nio.file.Paths

class ScalafmtFormatBase extends DefaultTask {
    SourceSet sourceSet
    ClassLoader cl = this.class.getClassLoader()
    PluginExtension pluginExtension
    def formatter = Scalafmt.create(cl)
            .withRespectVersion(false)
            .withDefaultVersion("1.5.1")


    def runScalafmt(boolean testOnly = false) {
        if (project.plugins.withType(JavaBasePlugin).empty) {
            logger.info("Java or Scala gradle plugin not available in this project, nothing to format")
            return
        }

        formatFiles(sourceSet.allSource.getFiles(),testOnly)

        if (testOnly && !misformattedFiles.empty) {
            throw new ScalafmtFormatException(misformattedFiles)
        }
    }

    def formatFiles(Set<File> files, testOnly = false) {
        def configpath = ConfigFactory.get(logger,project,pluginExtension.configFilePath)
        def misformattedFiles = new ArrayList<String>()
        files.findAll { File f -> canBeFormatted(f) }.each { File f ->
            String contents = f.text
            logger.debug("Formatting '$f'")
            def formattedContents = formatter.format(configpath.toPath(), f.toPath(), contents)
            if (testOnly) {
                if (contents != formattedContents) {
                    misformattedFiles.add(f.absolutePath)
                }
            } else {
                f.write(formattedContents)
            }
        }
        return misformattedFiles
    }

    def boolean canBeFormatted(File file) {
        file.getAbsolutePath().endsWith(".scala") || file.getAbsolutePath().endsWith(".sbt")
    }
}
