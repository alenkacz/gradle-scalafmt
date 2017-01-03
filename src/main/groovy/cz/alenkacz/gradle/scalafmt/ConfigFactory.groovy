package cz.alenkacz.gradle.scalafmt

import org.gradle.api.Project
import org.gradle.api.logging.Logger
import org.scalafmt.config.Config
import org.scalafmt.config.ScalafmtConfig
import org.scalafmt.config.ScalafmtConfig$

class ConfigFactory {
    static ScalafmtConfig load(Logger logger, Project project, String configFilePath) {
        def customConfig = project.file(configFilePath)
        if (!customConfig.exists()) {
            logger.info("Custom config $configFilePath not found, using default scalafmt config")
            return ScalafmtConfig$.MODULE$.default
        }
        def parsedConfig = Config.fromHocon(customConfig.text, Config.fromHocon$default$2())
        if (parsedConfig.right) {
            parsedConfig.right().get()
        } else {
            logger.warn("Unable to parse .scalafmt.conf in the project root. The format of the file is incorrect.")
            ScalafmtConfig$.MODULE$.default
        }
    }
}
