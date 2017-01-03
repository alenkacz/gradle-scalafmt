package cz.alenkacz.gradle.scalafmt

class PluginExtension {
    def String configFilePath

    public PluginExtension() {
        configFilePath = ".scalafmt.conf"
    }
}
