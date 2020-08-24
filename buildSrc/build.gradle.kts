plugins {
    `kotlin-dsl`
}

repositories {
    mavenLocal()
    jcenter()
    maven(url = "https://dl.bintray.com/kotlin/kotlin-eap")
    mavenCentral()
}

kotlin {
    sourceSets{
        all {
            languageSettings.useExperimentalAnnotation("kotlin.Experimental")
            languageSettings.useExperimentalAnnotation("kotlin.RequiresOptIn")
        }
    }
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "11"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "11"
    }
}