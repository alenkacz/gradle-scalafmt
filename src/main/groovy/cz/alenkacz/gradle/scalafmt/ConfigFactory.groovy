package cz.alenkacz.gradle.scalafmt

import org.gradle.api.Project
import org.gradle.api.logging.Logger

class ConfigFactory {
    static File get(Logger logger, Project project, String configFilePath) {
        def customConfig = project.file(configFilePath)
        if (!customConfig.exists()) {
            // when config does not exist in the project folder, look also to a root of multimodule project
            customConfig = project.rootProject.file(configFilePath)
            if (!customConfig.exists()) {
                logger.info("Custom config $configFilePath not found, using default scalafmt config")
            }
        }
        return customConfig
    }
}
