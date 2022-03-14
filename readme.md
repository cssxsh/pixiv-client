# PixivClient

A pixiv API library based on Kotlin JVM.

Use ktor client as HttpClient.

Currently, only the JVM has been improved and tested.

This client is based on the api of the mobile app.

Consider adding WebApi support in the future.

[![pixiv-client](https://img.shields.io/maven-central/v/xyz.cssxsh.pixiv/pixiv-client)](https://search.maven.org/artifact/xyz.cssxsh.pixiv/pixiv-client)

## Gradle

```
repositories {
    mavenCentral()
}

dependencies {
    implementation("xyz.cssxsh.pixiv:pixiv-client:${version}")
}
```