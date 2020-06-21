package plugin

import com.android.build.gradle.BaseExtension
import config.AppConfigVersion
import extension.implementationAndroidTestDeps
import extension.implementationDeps
import extension.implementationTestDeps
import extension.kaptDeps
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidCommonPlugin : Plugin<Project> {

    private val Project.android: BaseExtension
        get() = extensions.findByName("android") as? BaseExtension
            ?: error("$name isn`t a android module")

    override fun apply(target: Project) {
        with(target) {
            applyPlugins()
            setupAndroid()
            setupDeps()
        }
    }

    private fun Project.applyPlugins() {
        plugins.run {
            apply("kotlin-android")
            apply("kotlin-android-extensions")
            apply("kotlin-kapt")
            apply("dagger.hilt.android.plugin")
        }
    }

    private fun Project.setupAndroid() {
        android.run {
            compileSdkVersion(AppConfigVersion.compileSdk)
            buildToolsVersion(AppConfigVersion.buildTools)
            defaultConfig {
                minSdkVersion(AppConfigVersion.minSdk)
                targetSdkVersion(AppConfigVersion.targetSdk)
                versionCode = AppConfigVersion.versionCode
                versionName = AppConfigVersion.versionName
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }

            buildTypes {
                getByName("release") {
                    isMinifyEnabled = false
                    proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
                }

            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_1_8
                targetCompatibility = JavaVersion.VERSION_1_8
            }
        }
    }

    private fun Project.setupDeps() = dependencies {
        implementationDeps()
        kaptDeps()
        implementationTestDeps()
        implementationAndroidTestDeps()
    }
}