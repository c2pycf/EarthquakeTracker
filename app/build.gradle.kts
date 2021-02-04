/*
 * MIT License
 *
 * Copyright (c) 2021 Fang Chen
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-android-extensions")
}

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
        implementation(AndroidX.liveDataExtension)
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
        testImplementation(Tests.robolectric)


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
