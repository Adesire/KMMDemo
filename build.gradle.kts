buildscript {
    repositories {
        gradlePluginPortal()
        jcenter()
        google()
        mavenCentral()
        maven(url = "https://kotlin.bintray.com/kotlinx")
        maven(url = "https://www.jetbrains.com/intellij-repository/releases")
        maven(url = "https://jetbrains.bintray.com/intellij-third-party-dependencies")
    }
    val sqlDelightVersion: String by project

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.21")
        classpath("com.android.tools.build:gradle:4.2.2")

        classpath(kotlin("serialization", version = "1.5.21"))
        classpath("com.squareup.sqldelight:gradle-plugin:$sqlDelightVersion")
        classpath ("com.codingfeline.buildkonfig:buildkonfig-gradle-plugin:0.9.0")
//        classpath("com.squareup.sqldelight:runtime:1.5.0")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
        maven(url = "http://dl.bintray.com/kotlin/kotlin-eap")
        maven(url = "https://kotlin.bintray.com/kotlinx")
        maven(url = "https://plugins.gradle.org/m2/")
        maven(url = "https://dl.bintray.com/soywiz/soywiz")
        maven(url = "http://dl.bintray.com/kotlin/kotlinx.html")
        maven(url = "https://kotlin.bintray.com/kotlin-js-wrappers")
    }
}

extra.apply {
    set("coroutine_version", "1.5.2-native-mt")
    set("moko_mvvm_version", "0.11.0")
    set("kodeinVersion", "7.5.1")
    set("ktor_version", "1.6.2")
    set("lifecycle_version", "2.1.0")
    set("kotlin_version", "1.5.21")
    set("serializer_version", "1.5.21")
    /*set("env.path.debug", "debug-env.properties")
    set("env.path.production", "production-env.properties")*/
}
