import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-android-extensions")
}

val mapKey: String = gradleLocalProperties(rootDir).getProperty("MAPS_API_KEY")

android {
    compileSdkVersion(Versions.compileSdkVersion)
    buildToolsVersion(Versions.buildTools)

    defaultConfig {
        applicationId = Versions.applicationId
        minSdkVersion(Versions.minSdkVersion)
        targetSdkVersion(Versions.targetSdkVersion)
        versionCode = Versions.versionCode
        versionName = Versions.versionName

        testInstrumentationRunner = Tests.testInstrumentationRunner


//        if (rootProject.file("local.properties").exists()) {
//            properties.load(rootProject.file("local.properties").newDataInputStream())
//        }

        //manifestPlaceholders["mapsApiKey"] = mapKey
        //manifestPlaceholders = [ mapsApiKey : properties.getProperty("MAPS_API_KEY", "") ]
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
        unitTests.isReturnDefaultValues = true
    }

    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }
    kotlinOptions {
        jvmTarget = Versions.jvm
    }

    dependencies {
        implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
        implementation(Kotlin.kotlin_std)
        implementation(Kotlin.kotlin_coroutine)
        implementation(Kotlin.kotlin_coroutine_core)

        //androidx
        implementation(AndroidX.core)
        implementation(AndroidX.appcompat)
        implementation(AndroidX.constrainLayout)
        implementation(AndroidX.recyclerView)
        implementation(AndroidX.swipeRefreshLayout)

        implementation(AndroidX.lifecycleExtension)
        implementation(AndroidX.viewModel)
        implementation(AndroidX.activity)
        implementation(AndroidX.fragment)

        implementation(AndroidX.navigationUI)
        implementation(AndroidX.navigation)

        //Google map
        implementation(Libraries.googleMap)
        implementation (Libraries.googleMapKtx)


        //Material
        implementation(Libraries.materialDesign)

        //dagger
        implementation(Libraries.dagger2)
        kapt(Libraries.daggerCompiler)
        implementation(Libraries.daggerAndroid)

        //retrofit
        implementation(Libraries.retrofit)
        implementation(Libraries.retrofitConverter)

        //room
        implementation(AndroidX.room)
        kapt(AndroidX.roomCompiler)
        implementation(AndroidX.roomKotlin)

        // optional - Test helpers
        testImplementation(AndroidX.roomTest)

        //Test
        testImplementation(Tests.junit)
        testImplementation(AndroidX.archTest)
        testImplementation(Tests.mockito)
        testImplementation(Tests.mockk)
        testImplementation(Tests.coroutines)


        //UI test
        androidTestImplementation(AndroidX.androidxTestCore)
        androidTestImplementation(AndroidX.testRunner)
        androidTestImplementation(AndroidX.androidxJunit)
        androidTestImplementation(AndroidX.androidxEspresso)
        androidTestImplementation(AndroidX.androidEspressoContrib)

        debugImplementation(AndroidX.fragmentTest)
        debugImplementation(AndroidX.androidxTestCore)
        debugImplementation(AndroidX.monitor)
    }

}
