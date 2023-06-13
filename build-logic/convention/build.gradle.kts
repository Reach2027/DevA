import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.reach.deva"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.ksp.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "reach.android.application"
            implementationClass = "AndroidApplicationPlugin"
        }

        register("androidApplicationCompose") {
            id = "reach.android.application.compose"
            implementationClass = "AndroidApplicationComposePlugin"
        }

        register("androidLibrary") {
            id = "reach.android.library"
            implementationClass = "AndroidLibraryPlugin"
        }

        register("androidLibraryCompose") {
            id = "reach.android.library.compose"
            implementationClass = "AndroidLibraryComposePlugin"
        }

        register("jvmLibrary") {
            id = "reach.jvm.library"
            implementationClass = "JvmLibraryPlugin"
        }

        register("androidRoom") {
            id = "reach.android.room"
            implementationClass = "AndroidRoomPlugin"
        }
    }
}