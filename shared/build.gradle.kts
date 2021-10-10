import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    kotlin("multiplatform")
    //kotlin("native.cocoapods")
    id("com.android.library")
    id("kotlinx-serialization")
    id("com.squareup.sqldelight")
    id("com.codingfeline.buildkonfig")
}

val appPropertiesFile = rootProject.file("app-settings.properties")
val appProperties = Properties()
appProperties.load(appPropertiesFile.inputStream())

kotlin {
    val sqlDelightVersion: String by project
    val rootProp = rootProject.extra.properties
    val coroutine_version = rootProp["coroutine_version"]
    val moko_mvvm_version = rootProp["moko_mvvm_version"]
    val kodeinVersion = rootProp["kodeinVersion"]
    val ktor_version = rootProp["ktor_version"]
    val lifecycle_version = rootProp["lifecycle_version"]
    val serializer_version = rootProp["serializer_version"]

    android()
    ios {
        binaries {
            framework {
                baseName = "shared"
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                // COROUTINE
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${coroutine_version}")
                // SERIALIZATION
//                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:${serializer_version}")
                // KTOR
                implementation("io.ktor:ktor-client-core:$ktor_version")
                implementation("io.ktor:ktor-client-json:$ktor_version")
                implementation("io.ktor:ktor-client-logging:$ktor_version")
                implementation("io.ktor:ktor-client-serialization:$ktor_version")
                // SQL Delight
                implementation("com.squareup.sqldelight:runtime:$sqlDelightVersion")
                // MOKO - MVVM
                implementation("dev.icerock.moko:mvvm:$moko_mvvm_version")
                // KODE IN
                implementation("org.kodein.di:kodein-di:$kodeinVersion")
                //NAPIER LOGGER
                implementation("io.github.aakira:napier:2.0.0-alpha2")
                //LOGBACK
                implementation("ch.qos.logback:logback-classic:1.2.5")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("com.google.android.material:material:1.2.1")
                // MOKO - MVVM
                implementation("androidx.lifecycle:lifecycle-extensions:$lifecycle_version")

                // KTOR
                implementation("io.ktor:ktor-client-android:$ktor_version")

                // SQL Delight
                implementation("com.squareup.sqldelight:android-driver:$sqlDelightVersion")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13")
            }
        }
        val iosMain by getting {
            dependencies {

                // KTOR
                implementation("io.ktor:ktor-client-ios:$ktor_version")
                // SQL Delight
                implementation("com.squareup.sqldelight:native-driver:$sqlDelightVersion")
            }
        }
        val iosTest by getting
    }
}

android {
    compileSdkVersion(29)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(24)
        targetSdkVersion(29)
    }
}

val packForXcode by tasks.creating(Sync::class) {
    group = "build"
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val sdkName = System.getenv("SDK_NAME") ?: "iphonesimulator"
    val targetName = "ios" + if (sdkName.startsWith("iphoneos")) "Arm64" else "X64"
    val framework =
        kotlin.targets.getByName<KotlinNativeTarget>(targetName).binaries.getFramework(mode)
    inputs.property("mode", mode)
    dependsOn(framework.linkTask)
    val targetDir = File(buildDir, "xcode-frameworks")
    from({ framework.outputDirectory })
    into(targetDir)
}

tasks.getByName("build").dependsOn(packForXcode)

sqldelight {
    database("AppDatabase") {
        packageName = "com.adesire.kmmdemo.shared.db"
    }
}

buildkonfig {
    packageName = "com.adesire.kmmdemo.shared"
    // objectName = "YourAwesomeConfig"
    // exposeObjectWithName = "YourAwesomePublicConfig"

    val buildFlavour: String by project
    val envProperties = getBuildVariantConfig(buildFlavour)


    defaultConfigs {
        buildConfigField(STRING, "name", "value")
        buildConfigField(STRING, "BASE_URL_API", "https://random-data-api.com/api/restaurant/random_restaurant")

        /*for (key in envProperties.stringPropertyNames()) {
            buildConfigField(
                STRING, key.replace("\\.", "_").toUpperCase(),
                envProperties.getProperty(key)
            )
//            println("buildVariantStuff = $key : ${envProperties.getProperty(key)}")
        }*/
    }

}

fun getBuildVariantConfig(buildFlavour: String): Properties {
    val rootProp = rootProject.extra.properties
    val appPropertiesFile = rootProject.file("app-settings.properties")
    var environmentPath = ""
    if ((buildFlavour == "release")) {
        environmentPath = appProperties["env.path.production"] as String
    } else if ((buildFlavour == "debug")) {
        environmentPath = appProperties["env.path.debug"] as String
    } else {
        environmentPath = appProperties["env.path.production"] as String
    }
    val envPropertiesFile = rootProject.file(environmentPath)
    val envProperties = Properties()
    envProperties.load(envPropertiesFile.inputStream())
    println("buildVariant = $buildFlavour")
    return envProperties
}
