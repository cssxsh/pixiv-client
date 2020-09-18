plugins {
    kotlin("multiplatform") version Versions.kotlin
    kotlin("plugin.serialization") version Versions.kotlin
    `maven-publish`
}
group = "xzy.cssxsh.pixiv"
version = "0.7.0-dev-5"

repositories {
    maven(url = "https://maven.aliyun.com/repository/releases")
    maven(url = "https://mirrors.huaweicloud.com/repository/maven")
    // bintray dl.bintray.com -> bintray.proxy.ustclug.org
    maven(url = "https://bintray.proxy.ustclug.org/him188moe/mirai/")
    maven(url = "https://bintray.proxy.ustclug.org/kotlin/kotlin-dev")
    maven(url = "https://bintray.proxy.ustclug.org/kotlin/kotlinx/")
    maven(url = "https://bintray.proxy.ustclug.org/korlibs/korlibs/")
    // central
    maven(url = "https://maven.aliyun.com/repository/central")
    mavenCentral()
    // jcenter
    maven(url = "https://maven.aliyun.com/repository/jcenter")
    jcenter()
}

kotlin {
    jvm {
        compilations {
            all {
                kotlinOptions {
                    jvmTarget = "11"
                }
            }
        }
        tasks.getByName("jvmTest", Test::class) {
            useJUnitPlatform()
        }
    }
    js {
        browser {
            testTask {
                useKarma {
                    useChromeHeadless()
                    webpackConfig.cssSupport.enabled = true
                }
            }
        }
    }
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    sourceSets {
        all {
            languageSettings.useExperimentalAnnotation("com.soywiz.klock.annotations.KlockExperimental")
            languageSettings.useExperimentalAnnotation("kotlin.ExperimentalUnsignedTypes")
            languageSettings.useExperimentalAnnotation("kotlinx.coroutines.ExperimentalCoroutinesApi")
            languageSettings.useExperimentalAnnotation("io.ktor.utils.io.core.ExperimentalIoApi")
            languageSettings.useExperimentalAnnotation("io.ktor.util.KtorExperimentalAPI")
        }
        getByName("commonMain") {
            dependencies {
                implementation(kotlin("stdlib", Versions.kotlin))
                implementation(kotlin("serialization", Versions.kotlin))
                implementation(kotlinx("coroutines-core", Versions.coroutines))
                implementation(kotlinx("serialization-runtime", Versions.serialization))
                implementation(ktor("client-core", Versions.ktor))
                implementation(ktor("client-serialization", Versions.ktor))
                implementation(ktor("client-encoding", Versions.ktor))
                implementation(korlibs("klock", Versions.klock))
                implementation(korlibs("krypto", Versions.krypto))
            }
        }
        getByName("commonTest") {
            dependencies {
                implementation(kotlin("test-common", Versions.kotlin))
                implementation(kotlin("test-annotations-common", Versions.kotlin))
            }
        }
        getByName("jsMain") {
            dependencies {
                implementation(ktor("client-js", Versions.ktor))
            }
        }
        getByName("jsTest") {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
        getByName("jvmMain") {
            dependencies {
                implementation(ktor("client-okhttp", Versions.ktor)) {
                    exclude(group = "com.squareup.okhttp3")
                }
                implementation(okhttp3("okhttp", Versions.okhttp))
                implementation(okhttp3("okhttp-dnsoverhttps", Versions.okhttp))
            }
        }
        getByName("jvmTest") {
            dependencies {
                implementation("org.junit.jupiter:junit-jupiter:${Versions.junit}")
            }
        }
        getByName("nativeMain") {
            dependencies {
                implementation(ktor("client-curl", Versions.ktor))
            }
        }
        getByName("nativeTest") {

        }
    }
}