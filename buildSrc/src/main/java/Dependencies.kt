object Versions {
    const val versionCode = 1
    const val versionName = "1.0"
    const val targetSdkVersion = 29
    const val minSdkVersion = 23
    const val applicationId = "com.fred.earthquaketracker"
    const val compileSdkVersion = 29
    const val buildTools = "30.0.0"
    const val jvm = "1.8"
}

object Kotlin {
    object Versions {
        const val kotlin_version = "1.4.0"
        const val kotlin_std_version = "1.4.21"
    }

    const val kotlin_gradle_plugin =
        "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin_std_version}"
    const val kotlin_std = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin_std_version}"
    const val kotlin_coroutine_core =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlin_version}"
    const val kotlin_coroutine =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlin_version}"
}

object AndroidX {
    object Versions {
        const val constraintLayout = "2.0.4"
        const val core = "1.3.2"
        const val appcompat = "1.2.0"
        const val junit = "1.1.2"
        const val testCore = "1.3.0"
        const val testRunner = "1.3.0"
        const val espresso = "3.3.0"
        const val lifecycle = "2.1.0"
        const val room = "2.2.6"
        const val activity = "1.2.0-alpha04"
        const val fragment = "1.2.5"
        const val arch = "2.1.0"
        const val recyclerView = "1.1.0"
        const val navigation = "2.3.3"
    }


    const val core = "androidx.core:core-ktx:${Versions.core}"
    const val lifecycleExtension = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
    const val activity = "androidx.activity:activity-ktx:${Versions.activity}"

    const val fragment = "androidx.fragment:fragment-ktx:${Versions.fragment}"
    const val fragmentTest = "androidx.fragment:fragment-testing:${Versions.fragment}"

    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val swipeRefreshLayout =
        "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.recyclerView}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"

    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val constrainLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"

    const val navigation = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigationUI = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"

    const val androidxTestCore = "androidx.test:core:${Versions.testCore}"
    const val testRunner = "androidx.test:runner:${Versions.testRunner}"
    const val androidxJunit = "androidx.test.ext:junit:${Versions.junit}"
    const val androidxEspresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val androidEspressoContrib =
        "androidx.test.espresso:espresso-contrib:${Versions.espresso}"
    const val monitor = "androidx.test:monitor:${Versions.testCore}"

    const val room = "androidx.room:room-runtime:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomKotlin = "androidx.room:room-ktx:${Versions.room}"
    const val roomTest = "androidx.room:room-testing:${Versions.room}"

    const val archTest = "androidx.arch.core:core-testing:${Versions.arch}"


}

object Libraries {
    object Versions {
        const val dagger = "2.30.1"
        const val gson = "2.8.5"
        const val retrofit2 = "2.4.0"
        const val material = "1.2.1"
        const val googleMap = "17.0.0"
        const val googleMapKtx = "2.0.0"
    }

    const val dagger2 = "com.google.dagger:dagger:${Versions.dagger}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    const val daggerAndroid = "com.google.dagger:dagger-android-support:${Versions.dagger}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit2}"
    const val retrofitConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit2}"
    const val materialDesign = "com.google.android.material:material:${Versions.material}"
    const val googleMap = "com.google.android.gms:play-services-maps:${Versions.googleMap}"
    const val googleMapKtx = "com.google.maps.android:maps-utils-ktx:${Versions.googleMapKtx}"
}

object Tests {
    object Versions {
        const val junit4 = "4.12"
        const val mockito = "1.10.19"
        const val mokk = "1.10.5"
        const val coroutine = "1.4.2"
        const val fragment = "1.2.5"
    }

    const val mockito = "org.mockito:mockito-core:${Versions.mockito}"
    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    const val junit = "junit:junit:${Versions.junit4}"
    const val mockk = "io.mockk:mockk:${Versions.mokk}"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutine}"

}

object Google {
    object Versions {

    }

    const val map = ""
}