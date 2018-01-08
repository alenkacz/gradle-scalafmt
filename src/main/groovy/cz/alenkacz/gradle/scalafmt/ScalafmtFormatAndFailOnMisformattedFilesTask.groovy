package cz.alenkacz.gradle.scalafmt

import org.gradle.api.tasks.TaskAction

class ScalafmtFormatAndFailOnMisformattedFilesTask extends ScalafmtFormatBase {
    @TaskAction
    def format() {
        runScalafmt(false, true)
    }
}
