@file:Suppress("UnstableApiUsage")

pluginManagement {
    plugins {
        kotlin("jvm") version "1.6.0"
        kotlin("plugin.serialization") version "1.6.0"
    }
    repositories {
        mavenLocal()
        maven(url = "https://maven.aliyun.com/repository/central")
        maven(url = "https://maven.aliyun.com/repository/gradle-plugin")
        mavenCentral()
        gradlePluginPortal()
    }
}
rootProject.name = "pixiv-client"
enableFeaturePreview("GRADLE_METADATA")

