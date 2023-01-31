plugins {
    kotlin("jvm") version "1.7.22"
    kotlin("plugin.serialization") version "1.7.22"
    id("me.him188.maven-central-publish") version "1.0.0-dev-3"
}

group = "xyz.cssxsh.pixiv"
version = "1.3.0"

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
    api("com.squareup:gifencoder:0.10.1")
    compileOnly("org.openpnp:opencv:4.6.0-0")
    compileOnly("org.seleniumhq.selenium:selenium-java:4.8.0")
    testImplementation(kotlin("test"))
    testImplementation("org.openpnp:opencv:4.6.0-0")
    testImplementation("org.seleniumhq.selenium:selenium-java:4.8.0")
    //
    implementation(platform("io.ktor:ktor-bom:2.1.3"))
    api("io.ktor:ktor-client-auth")
    api("io.ktor:ktor-client-encoding")
    api("io.ktor:ktor-client-okhttp")
    api("io.ktor:ktor-client-content-negotiation")
    api("io.ktor:ktor-serialization-kotlinx-json")
    //
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.10.0"))
    api("com.squareup.okhttp3:okhttp-dnsoverhttps")
    //
    implementation(platform("org.slf4j:slf4j-parent:2.0.6"))
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

tasks {
    test {
        useJUnitPlatform()
    }
}