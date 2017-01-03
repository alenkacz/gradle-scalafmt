package cz.alenkacz.gradle.scalafmt

import org.gradle.api.tasks.TaskAction

class ScalafmtTask extends ScalafmtFormatBase {
    @TaskAction
    def format() {
        runScalafmt()
    }
}
