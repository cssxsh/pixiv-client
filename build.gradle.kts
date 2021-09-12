import java.util.*

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

group = "xzy.cssxsh.pixiv"
version = "1.0.0"

repositories {
    mavenLocal()
    if (Locale.getDefault() == Locale.SIMPLIFIED_CHINESE) {
        maven(url = "https://maven.aliyun.com/repository/public")
        maven(url = "https://maven.aliyun.com/repository/gradle-plugin")
    }
    mavenCentral()
    maven(url = "https://maven.pkg.github.com/Moon70/APNG-builder")
    maven(url = "https://maven.pkg.github.com/Moon70/GPAC")
    maven(url = "https://maven.pkg.github.com/Moon70/LunarTools")
    filterIsInstance<MavenArtifactRepository>().forEach { repo ->
        if (repo.url.host == "maven.pkg.github.com") {
            println(repo.url)
            repo.credentials {
                username = System.getenv("GITHUB_ID")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
    gradlePluginPortal()
}

dependencies {
    implementation(kotlinx("coroutines-core", Versions.coroutines))
    implementation(ktor("client-auth", "1.6.1")) {
        exclude(group = "io.ktor")
    }
    implementation(ktor("client-core", Versions.ktor))
    implementation(ktor("client-serialization", Versions.ktor))
    implementation(ktor("client-encoding", Versions.ktor))
    implementation(ktor("client-okhttp", Versions.ktor))
    implementation(okhttp3("okhttp", Versions.okhttp))
    implementation(okhttp3("okhttp-dnsoverhttps", Versions.okhttp))
    api(square("gifencoder", Versions.gifencoder))
    api("lunartools:apngbuilder:0.9-SNAPSHOT")
    // implementation(jsoup(Versions.jsoup))
    testImplementation(kotlin("test-junit5"))
    testImplementation(junit("api", Versions.junit))
    testRuntimeOnly(junit("engine", Versions.junit))
}

kotlin {
    sourceSets {
        all {
            languageSettings.useExperimentalAnnotation("kotlin.RequiresOptIn")
//            languageSettings.useExperimentalAnnotation("kotlin.ExperimentalUnsignedTypes")
//            languageSettings.useExperimentalAnnotation("kotlin.time.ExperimentalTime")
//            languageSettings.useExperimentalAnnotation("kotlinx.coroutines.ExperimentalCoroutinesApi")
//            languageSettings.useExperimentalAnnotation("io.ktor.utils.io.core.ExperimentalIoApi")
            languageSettings.useExperimentalAnnotation("io.ktor.util.InternalAPI")
//            languageSettings.useExperimentalAnnotation("kotlinx.serialization.InternalSerializationApi")
            languageSettings.useExperimentalAnnotation("kotlinx.serialization.ExperimentalSerializationApi")
        }
    }
}

tasks {

    test {
        useJUnitPlatform()
    }

    compileKotlin {
        kotlinOptions.freeCompilerArgs += "-Xjvm-default=all"
        kotlinOptions.jvmTarget = "11"
    }

    compileTestKotlin {
        kotlinOptions.freeCompilerArgs += "-Xjvm-default=all"
        kotlinOptions.jvmTarget = "11"
    }
}