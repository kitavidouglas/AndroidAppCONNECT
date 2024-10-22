


buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.0.0") // or the appropriate version
        classpath(kotlin("gradle-plugin", version = "1.8.0")) // or the appropriate version
        classpath(libs.google.services)

    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    // Add the dependency for the Google services Gradle plugin
    id("com.google.gms.google-services") version "4.4.1" apply false


    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.googleAndroidLibrariesMapsplatformSecretsGradlePlugin) apply false

}