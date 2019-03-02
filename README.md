# Scalafmt Gradle plugin

[![Build Status](https://travis-ci.org/alenkacz/gradle-scalafmt.svg)](https://travis-ci.org/alenkacz/gradle-scalafmt) [ ![Download](https://api.bintray.com/packages/alenkacz/maven/gradle-scalafmt/images/download.svg) ](https://bintray.com/alenkacz/maven/gradle-scalafmt/_latestVersion)

This plugin will allow you to format your scala code as a part of your build process. In the background, it uses [scalafmt](https://github.com/scalameta/scalafmt) library.

You can use scalafmt plugin without any configuration. Then defaults from [scalafmt](http://scalameta.org/scalafmt/) will be used. You can adjust these settings by putting .scalafmt.conf to the root of your project. For the supported format and properties see the [scalafmt page](http://scalameta.org/scalafmt/)

We are using scalafmt-dynamic which allows people to choose their scalafmt version inside scalafmt config. As a default, we're still sticking to the last [stable version](https://github.com/alenkacz/gradle-scalafmt/blob/master/src/main/groovy/cz/alenkacz/gradle/scalafmt/ScalafmtFormatBase.groovy#L16). E.g. to try out this plugin with non-stable 2.0.0-RC4 release, just put `version = "2.0.0-RC4"` into your scalafmt config. 

Usage
====================

	plugins {
		id 'cz.alenkacz.gradle.scalafmt' version '$latestVersion'
	}
	
	scalafmt {
	    // .scalafmt.conf in the project root is default value, provide only if other location is needed
	    // config file has to be relative path from current project or root project in case of multimodule projects
	    // example usage: 
	    // configFilePath = ".scalafmt.conf"
	}


Tasks
====================
Tasks added to your project when applying this plugin:

- *scalafmt* - formats your scala and sbt source code (main sourceset only)
- *checkScalafmt* - checks whether all files are correctly formatted, if not, the task fails  (main sourceset only)
- *testScalafmt* - formats your test scala code based on the provided configuration
- *checkTestScalafmt* - checks whether your test scala code is correctly formatted
- *scalafmtAll* - formats scala code from all source sets
- *checkScalafmtAll* - checks formatting of all source sets
