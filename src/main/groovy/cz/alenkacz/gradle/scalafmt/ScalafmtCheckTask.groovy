package cz.alenkacz.gradle.scalafmt

import org.gradle.api.tasks.TaskAction

class ScalafmtCheckTask extends ScalafmtFormatBase {
    @TaskAction
    def format() {
        runScalafmt(true)
    }
}
