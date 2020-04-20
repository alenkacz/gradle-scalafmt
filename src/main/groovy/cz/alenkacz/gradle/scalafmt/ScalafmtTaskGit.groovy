package cz.alenkacz.gradle.scalafmt

import org.gradle.api.tasks.TaskAction

import java.util.stream.Collectors

class ScalafmtTaskGit extends ScalafmtFormatBase {
    @TaskAction
    def format() {
        String commandResult = 'git diff --name-only'.execute().text
        logger.info("formatting changed files: ${commandResult}")
        Set<File> files = commandResult.split("\n")
                .toList()
                .stream()
                .map { x -> new File(x) }
                .collect(Collectors.toSet())
        formatFiles(files)
    }
}