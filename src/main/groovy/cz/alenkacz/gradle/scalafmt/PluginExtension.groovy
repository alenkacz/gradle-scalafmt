package cz.alenkacz.gradle.scalafmt

import org.gradle.api.tasks.SourceSet

class PluginExtension {
    def String configFilePath

    public PluginExtension() {
        configFilePath = ".scalafmt.conf"
    }
}
