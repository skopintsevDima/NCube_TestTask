object Config {
    private object Versions {
        // Tools
        const val kotlinVersion = "1.3.71"
        const val kotlinCoroutinesVersion = "1.3.6"
        const val androidGradleVersion = "3.5.3"

        // Android
        const val ktxCoreVersion = "1.1.0"
        const val appcompatVersion = "1.1.0"
        const val materialVersion = "1.2.0-alpha04"
        const val constraintLayoutVersion = "1.1.3"
        const val swipeRefreshLayoutVersion = "1.0.0"
        const val viewModelVersion = "2.2.0"
        const val liveDataVersion = "2.2.0"
        const val roomVersion = "2.2.5"
        const val pagingVersion = "2.1.2"

        // Third-party libs
        const val retrofitVersion = "2.8.1"
        const val gsonVersion = "2.8.6"
        const val picassoVersion = "2.71828"
        const val httpLoggingInterceptorVersion = "4.6.0"
        const val daggerVersion = "2.27"
    }

    object Tools {
        const val androidGradle = "com.android.tools.build:gradle:${Versions.androidGradleVersion}"
        const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}"
        const val kotlinStd = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlinVersion}"
        const val kotlinCoroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutinesVersion}"
        const val kotlinCoroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinCoroutinesVersion}"
    }

    object Android {
        const val buildToolsVersion = "29.0.2"
        const val minSdkVersion = 21
        const val targetSdkVersion = 29
        const val compileSdkVersion = 29
        const val applicationId = "com.test.ncubetest"
        const val versionCode = 1
        const val versionName = "1.0"

        const val ktxCore = "androidx.core:core-ktx:${Versions.ktxCoreVersion}"
        const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompatVersion}"
        const val material = "com.google.android.material:material:${Versions.materialVersion}"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayoutVersion}"
        const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefreshLayoutVersion}"
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.viewModelVersion}"
        const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.liveDataVersion}"
        const val room = "androidx.room:room-runtime:${Versions.roomVersion}"
        const val roomKtx = "androidx.room:room-ktx:${Versions.roomVersion}"
        const val roomCompiler = "androidx.room:room-compiler:${Versions.roomVersion}"
        const val paging = "androidx.paging:paging-runtime-ktx:${Versions.pagingVersion}"
    }

    object ThirdPartyLibs {
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"
        const val retrofitGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofitVersion}"
        const val httpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.httpLoggingInterceptorVersion}"
        const val gson = "com.google.code.gson:gson:${Versions.gsonVersion}"
        const val picasso = "com.squareup.picasso:picasso:${Versions.picassoVersion}"
        const val dagger = "com.google.dagger:dagger:${Versions.daggerVersion}"
        const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.daggerVersion}"
    }
}