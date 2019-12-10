package cz.alenkacz.gradle.scalafmt

import org.gradle.api.DefaultTask
import org.gradle.api.plugins.JavaBasePlugin
import org.gradle.api.tasks.SourceSet
import org.scalafmt.interfaces.Scalafmt

import java.util.stream.Collectors

class ScalafmtFormatBase extends DefaultTask {
    SourceSet sourceSet
    ClassLoader cl = this.class.getClassLoader()
    PluginExtension pluginExtension

    def globalFormatter = Scalafmt.create(cl)
            .withRespectVersion(false)
            .withDefaultVersion("1.5.1")

    def runScalafmt(boolean testOnly = false) {
        if (project.plugins.withType(JavaBasePlugin).empty) {
            logger.info("Java or Scala gradle plugin not available in this project, nothing to format")
            return
        }
        def configpath = ConfigFactory.get(logger,project,pluginExtension.configFilePath)
        def misformattedFiles = new ArrayList<String>()

        def formatter = globalFormatter
                .withMavenRepositories(repositories())

        sourceSet.allSource.filter { File f -> canBeFormatted(f) }.each { File f ->
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

        if (testOnly && !misformattedFiles.empty) {
            throw new ScalafmtFormatException(misformattedFiles)
        }
    }

    private String[] repositories() {
        project.getRepositories().stream().map { repository ->
            repository.properties.get("url").toString()
        }.collect(Collectors.toList()).toArray(new String[0])
    }

    def boolean canBeFormatted(File file) {
        file.getAbsolutePath().endsWith(".scala") || file.getAbsolutePath().endsWith(".sbt")
    }
}
