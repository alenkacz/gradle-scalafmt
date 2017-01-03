package cz.alenkacz.gradle.scalafmt

import org.gradle.api.tasks.TaskAction

class ScalafmtTestTask extends ScalafmtFormatBase {
    @TaskAction
    def format() {
        runScalafmt(true)
    }
}
