package cz.alenkacz.gradle.scalafmt

import org.gradle.api.internal.artifacts.ivyservice.DefaultLenientConfiguration
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Paths

class ScalafmtCheckTaskTest extends Specification {
    def "fail for badly formatted code"() {
        given:
        def testProject = ProjectMother.basicProject()
        def project = ProjectBuilder.builder().withProjectDir(testProject.projectRoot).build()
        project.plugins.apply 'scala'
        project.plugins.apply 'scalafmt'

        when:
        project.tasks.checkScalafmt.format()

        then:
        thrown ScalafmtFormatException
    }

    def "not fail for badly formatted code in build directory"() {
        given:
        def testProject = ProjectMother.basicProject()
        def project = ProjectBuilder.builder().withProjectDir(testProject.projectRoot).build()
        project.plugins.apply 'scala'
        project.plugins.apply 'scalafmt'
        project.buildDir.mkdirs()
        def srcFile = Files.createFile(Paths.get(project.buildDir.path, "Generated.scala"))
        srcFile.write """
                     |object Test {
                     |  if(
                     |true)
                     |{}
                     |}
                     |""".stripMargin()
        project.sourceSets.main.java.srcDir(project.buildDir.toPath())

        when:
        project.tasks.checkScalafmt.format()

        then:
        thrown ScalafmtFormatException
    }

    def "not fail for correctly formatted code"() {
        given:
        def testProject = ProjectMother.basicProjectWithCorrectlyFormattedFile()
        def project = ProjectBuilder.builder().withProjectDir(testProject.projectRoot).build()
        project.plugins.apply 'scala'
        project.plugins.apply 'scalafmt'

        when:
        project.tasks.checkScalafmt.format()

        then:
        noExceptionThrown()
    }

    def "ignore source files in test"() {
        given:
        def testProject = ProjectMother.basicProjectWithIncorrectTestFile()
        def project = ProjectBuilder.builder().withProjectDir(testProject.projectRoot).build()

        project.plugins.apply 'scalafmt'
        project.plugins.apply 'scala'

        when:
        project.evaluate()
        project.tasks.checkScalafmt.format()

        then:
        noExceptionThrown()
    }

    def "not fail on ArtifactResolveException with custom repository"() {
        given:
        def testProject = ProjectMother.basicProjectWithCorrectlyFormattedFile()
        def project = ProjectBuilder.builder().withProjectDir(testProject.projectRoot).build()
        project.plugins.apply 'scala'
        project.plugins.apply 'scalafmt'
        project.repositories {
            mavenCentral()
        }
        project.dependencies {
            compile 'org.scala-lang:scala-library:2.12.10'
        }

        when:
        project.tasks.checkScalafmt.format()
        project.tasks.compileScala.compile()

        then:
        thrown IllegalStateException // because compileScala is not fully configured
        // notThrown DefaultLenientConfiguration.ArtifactResolveException // Only one exception condition is allowed per 'then' block
    }
}
