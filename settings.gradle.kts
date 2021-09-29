@file:Suppress("UnstableApiUsage")

pluginManagement {
    plugins {
        kotlin("jvm") version "1.5.30"
        kotlin("plugin.serialization") version "1.5.30"
    }
    repositories {
        mavenLocal()
        maven(url = "https://maven.aliyun.com/repository/public")
        maven(url = "https://maven.aliyun.com/repository/gradle-plugin")
        mavenCentral()
        gradlePluginPortal()
    }
}
rootProject.name = "pixiv-client"
enableFeaturePreview("GRADLE_METADATA")

