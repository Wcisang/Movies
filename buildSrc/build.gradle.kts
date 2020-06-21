plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
    jcenter()
}

gradlePlugin {
    plugins {
        register("AndroidCommonPlugin") {
            id = "AndroidCommonPlugin"
            implementationClass = "plugin.AndroidCommonPlugin"
        }
    }
}

dependencies {
    implementation("com.android.tools.build:gradle:4.0.0")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.72")
    implementation(gradleApi())
    implementation(localGroovy())
}