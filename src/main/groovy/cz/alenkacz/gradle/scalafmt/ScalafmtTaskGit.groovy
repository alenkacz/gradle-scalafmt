package cz.alenkacz.gradle.scalafmt

import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.TaskAction

class ScalafmtTaskGit extends ScalafmtFormatBase {
    @TaskAction
    def format() {
        String commandResult = 'git diff --name-only'.execute().text
        logger.info("formatting changed files: ${commandResult}")
        Set<File> files = commandResult.split("\n").each { x -> new File(x)}.toList().toSet()
        formatFiles(files)
    }
}
