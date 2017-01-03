package cz.alenkacz.gradle.scalafmt

class ScalafmtFormatException extends Exception {
    public ScalafmtFormatException(List<String> filePaths) {
        super("Files incorrectly formatted: " + filePaths.join(","))
    }
}
