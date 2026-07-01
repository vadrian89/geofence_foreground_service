import com.android.build.gradle.LibraryExtension
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "com.f2fk.geofence_foreground_service"
version = "1.0-SNAPSHOT"

buildscript {
    val kotlinVersion = "1.7.20"

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:8.1.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

apply(plugin = "com.android.library")
apply(plugin = "kotlin-android")

configure<LibraryExtension> {
    namespace = "com.f2fk.geofence_foreground_service"
    compileSdk = 33

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    sourceSets {
        getByName("main").java.srcDir("src/main/kotlin")
        getByName("test").java.srcDir("src/test/kotlin")
    }

    defaultConfig {
        minSdk = 29
    }

    testOptions {
        unitTests.all {
            it.useJUnitPlatform()
            it.outputs.upToDateWhen { false }
            it.testLogging {
                events = setOf(
                    TestLogEvent.PASSED,
                    TestLogEvent.SKIPPED,
                    TestLogEvent.FAILED,
                    TestLogEvent.STANDARD_OUT,
                    TestLogEvent.STANDARD_ERROR,
                )
                showStandardStreams = true
            }
        }
    }
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    add("testImplementation", "org.jetbrains.kotlin:kotlin-test")
    add("testImplementation", "org.mockito:mockito-core:5.1.1")

    add("implementation", "com.google.android.gms:play-services-location:21.0.1")
    add("implementation", "com.google.android.gms:play-services-maps:18.1.0")
    add("implementation", "androidx.work:work-runtime-ktx:2.8.1")
    add("implementation", "androidx.concurrent:concurrent-futures:1.1.0")
}
