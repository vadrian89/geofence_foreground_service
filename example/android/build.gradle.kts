buildscript {
    val kotlin_version by extra("2.2.21")

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:8.1.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

// Set the root project build directory.
val newBuildDir = file("../build")
rootProject.layout.buildDirectory.set(newBuildDir)

// Set each subproject's build directory inside the shared build folder.
subprojects {
    val subBuildDir = File(newBuildDir, project.name)
    project.layout.buildDirectory.set(subBuildDir)
}

// Ensure app project is evaluated first
subprojects {
    project.evaluationDependsOn(":app")
}

// Clean task
tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}
