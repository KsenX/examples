import jetbrains.buildServer.configs.kotlin.v2018_1.*
import jetbrains.buildServer.configs.kotlin.v2018_1.buildSteps.BazelStep
import jetbrains.buildServer.configs.kotlin.v2018_1.buildSteps.bazel
import jetbrains.buildServer.configs.kotlin.v2018_1.triggers.vcs

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2018.1"

project {

    buildType(Build)

    template(id11_1)

    subProject(id11)
}

object Build : BuildType({
    name = "Build"

    artifactRules = """
        java-maven\bazel-out\x64_windows-fastbuild\bin\java-maven.exe
        java-maven\bazel-out\x64_windows-fastbuild\bin\tests.exe
    """.trimIndent()

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        bazel {
            workingDir = "java-maven"
            command = "clean"
            arguments = "--announce_rc"
        }
        bazel {
            workingDir = "java-maven"
            command = "build"
            targets = ":java-maven"
            param("verbosity", "Verbose")
        }
        bazel {
            workingDir = "java-maven"
            command = "build"
            targets = ":java-maven-lib"
            param("verbosity", "Verbose")
        }
        bazel {
            workingDir = "java-maven"
            command = "test"
            targets = ":tests2"
            param("verbosity", "Verbose")
        }
        bazel {
            workingDir = "java-maven"
            command = "run"
            targets = ":java-maven"
            logging = BazelStep.Verbosity.Diagnostic
        }
    }

    triggers {
        vcs {
        }
    }
})

object id11_1 : Template({
    id("11")
    name = "11"

    steps {
        bazel {
            name = "11"
            id = "RUNNER_18"
            command = "build"
            targets = ":target"
            arguments = "--lalalalalala"
            startupOptions = "-lalalala"
        }
    }
})


object id11 : Project({
    id("11")
    name = "11"

    buildType(id11_11)
})

object id11_11 : BuildType({
    templates(id11_1)
    id("11_11")
    name = "11"

    steps {
        bazel {
            name = "11"
            id = "RUNNER_18"
            command = "build"
            targets = ":target"
            arguments = "--lalalalalala"
            startupOptions = "-lalalala"
            logging = BazelStep.Verbosity.Diagnostic
        }
    }
})
