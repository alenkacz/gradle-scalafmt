package cz.alenkacz.gradle.scalafmt

class TestProject {
    private File projectRoot
    private File testFile
    private File buildDir

    public TestProject(File projectRoot, File testFile) {
        this.testFile = testFile
        this.projectRoot = projectRoot
        this.buildDir = new File(projectRoot, "build")
        buildDir.mkdir()
    }

    File getProjectRoot() {
        return projectRoot
    }

    File getTestFile() {
        return testFile
    }
}
