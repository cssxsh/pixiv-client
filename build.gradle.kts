import java.util.*

plugins {
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.serialization") version "1.8.22"
    id("me.him188.maven-central-publish") version "1.0.0-dev-3"
    id("com.github.gmazzo.buildconfig") version "3.1.0"
}

group = "xyz.cssxsh.pixiv"
version = "1.3.1"

mavenCentralPublish {
    useCentralS01()
    singleDevGithubProject("cssxsh", "pixiv-client")
    licenseFromGitHubProject("AGPL-3.0")
    workingDir = System.getenv("PUBLICATION_TEMP")?.let { file(it).resolve(projectName) }
        ?: buildDir.resolve("publishing-tmp")
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    api(platform("io.ktor:ktor-bom:2.3.3"))
    api("io.ktor:ktor-client-auth")
    api("io.ktor:ktor-client-encoding")
    api("io.ktor:ktor-client-okhttp")
    api("io.ktor:ktor-client-content-negotiation")
    api("io.ktor:ktor-serialization-kotlinx-json")
    api(platform("com.squareup.okhttp3:okhttp-bom:4.11.0"))
    api("com.squareup.okhttp3:okhttp-dnsoverhttps")
    implementation(platform("org.slf4j:slf4j-parent:2.0.7"))
    testImplementation("org.slf4j:slf4j-simple")
}

kotlin {
    explicitApi()
    target.compilations {
        all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

//tasks {
//    test {
//        useJUnitPlatform()
//    }
//}

val properties = Properties().apply {
    try {
        load(rootProject.file("local.properties").reader())
    } catch (e: Exception) {
        println("local.properties file not found, you won't be able to run tests")
    }
}

buildConfig {
    buildConfigField("String", "REFRESH_TOKEN", provider { "\"${properties["refresh_token"]}\"" })
}