@file:Suppress("unused")

import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.kotlinx(module: String, version: String = Versions.kotlin) =
    "org.jetbrains.kotlinx:kotlinx-$module:$version"

fun DependencyHandler.ktor(module: String, version: String= Versions.ktor) =
    "io.ktor:ktor-$module:$version"

fun DependencyHandler.okhttp3(module: String, version: String = Versions.okhttp) =
    "com.squareup.okhttp3:$module:$version"