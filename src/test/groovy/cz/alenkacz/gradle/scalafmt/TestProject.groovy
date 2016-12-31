package cz.alenkacz.gradle.scalafmt

class TestProject {
    private File projectRoot
    private File testFile

    public TestProject(File projectRoot, File testFile) {
        this.testFile = testFile
        this.projectRoot = projectRoot
    }

    File getProjectRoot() {
        return projectRoot
    }

    File getTestFile() {
        return testFile
    }
}
