import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig
import java.util.Properties
import java.io.FileInputStream


plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlin.serialization)
    id("com.github.gmazzo.buildconfig") version "5.5.2"
}
repositories {
    google()
    mavenCentral()
    // Jetbrains Compose repository
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    // Sonatype snapshots
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    // Ktor Early Access Preview
    maven("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
    // Jitpack repository
    maven("https://jitpack.io")
}

// Charger les propriétés du fichier secret.properties
val secretPropertiesFile = rootProject.file("secret.properties")
val secretProperties = Properties()
if (secretPropertiesFile.exists()) {
    secretProperties.load(FileInputStream(secretPropertiesFile))
} else {
    secretProperties["baseUrl"] = "https://api.openweathermap.org/data/2.5"
    secretProperties["apiKey"] = "PLEASE_ADD_YOUR_API_KEY"
}

// Tâche pour générer le fichier de configuration
tasks.register("generateConfig") {
    val configFile = file("${projectDir}/src/commonMain/kotlin/org/perso/weatherapp/network/Config.kt")

    doLast {
        configFile.parentFile.mkdirs()
        configFile.writeText("""
            package org.perso.weatherapp.network

            // Ce fichier est généré automatiquement. Ne pas modifier directement.
            object Config {
                const val BASE_URL = "${secretProperties["baseUrl"]}"
                const val API_KEY = "${secretProperties["apiKey"]}"
            }
        """.trimIndent())
    }
}

// Configuration des dépendances des tâches de compilation
tasks.named("preBuild").configure {
    dependsOn("generateConfig")
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
        compilations.all {
            compileTaskProvider.get().dependsOn("generateConfig")
        }

    }
    
    jvm("desktop") {
        compilations.all {
            compileTaskProvider.get().dependsOn("generateConfig")
        }

    }
    
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "composeApp"
        browser {
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "composeApp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(rootDirPath)
                        add(projectDirPath)
                    }
                }
            }
        }
        compilations.all {
            compileTaskProvider.get().dependsOn("generateConfig")
        }

        binaries.executable()
    }
    
    sourceSets {
        val desktopMain by getting
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.okhttp)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)

        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.java)
        }
    }
}

android {
    namespace = "org.perso.weatherapp"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "org.perso.weatherapp"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "BASE_URL", "\"${secretProperties["baseUrl"]}\"")
        buildConfigField("String", "API_KEY", "\"${secretProperties["apiKey"]}\"")

    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "org.perso.weatherapp.MainKt"

        args += listOf(
            "-DbaseUrl=${secretProperties["baseUrl"]}",
            "-DapiKey=${secretProperties["apiKey"]}"
        )


        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.perso.weatherapp"
            packageVersion = "1.0.0"
        }
    }
}
