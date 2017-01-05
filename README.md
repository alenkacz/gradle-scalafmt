# Scalafmt Gradle plugin

[![Build Status](https://travis-ci.org/alenkacz/gradle-scalafmt.svg)](https://travis-ci.org/alenkacz/gradle-scalafmt) [ ![Download](https://api.bintray.com/packages/alenkacz/maven/gradle-scalafmt/images/download.svg) ](https://bintray.com/alenkacz/maven/gradle-scalafmt/_latestVersion)

This plugin will allow you to format your scala code as a part of your build process. In the background, it uses [scalafmt](https://github.com/olafurpg/scalafmt) library.

You can use scalafmt plugin without any configuration. Then defaults from [scalafmt](https://olafurpg.github.io/scalafmt/) will be used. You can adjust these settings by putting .scalafmt.conf to the root of your project. For the supported format and properties see the [scalafmt page](https://olafurpg.github.io/scalafmt/)

Usage
====================

	buildscript {
		repositories {
			jcenter()
		}
		dependencies {
			classpath 'cz.alenkacz.gradle:gradle-scalafmt:$putCurrentVersionHere'
		}
	}

	apply plugin: 'scalafmt'
	
	scalafmt {
        // configFilePath = ".scalafmt.conf" // .scalafmt.conf in the project root is default value, provide only if other location is needed
        // sourceSets = [project.sourceSets.main] // limit to main source set only
    }
	
Tasks
====================
Tasks added to your project when applying this plugin:

- *scalafmt* - formats your scala and sbt source code based on the provided configuration
- *scalafmtTest* - tests whether all files are correctly formatted, if not, the task fails
