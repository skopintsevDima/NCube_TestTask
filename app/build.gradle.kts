plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(Config.Android.compileSdkVersion)
    buildToolsVersion(Config.Android.buildToolsVersion)

    defaultConfig {
        applicationId = Config.Android.applicationId
        minSdkVersion(Config.Android.minSdkVersion)
        targetSdkVersion(Config.Android.targetSdkVersion)
        versionCode = Config.Android.versionCode
        versionName = Config.Android.versionName
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles("proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
    }
}

dependencies {
    implementation(Config.Tools.kotlinStd)
    implementation(Config.Tools.kotlinCoroutinesCore)
    implementation(Config.Tools.kotlinCoroutinesAndroid)

    implementation(Config.Android.ktxCore)
    implementation(Config.Android.appcompat)
    implementation(Config.Android.constraintLayout)
    implementation(Config.Android.swipeRefreshLayout)
    implementation(Config.Android.material)
    implementation(Config.Android.viewModel)
    implementation(Config.Android.liveData)
    implementation(Config.Android.paging)
    implementation(Config.Android.room)
    implementation(Config.Android.roomKtx)
    kapt(Config.Android.roomCompiler)

    implementation(Config.ThirdPartyLibs.retrofit)
    implementation(Config.ThirdPartyLibs.retrofitGson)
    implementation(Config.ThirdPartyLibs.httpLoggingInterceptor)
    implementation(Config.ThirdPartyLibs.gson)
    implementation(Config.ThirdPartyLibs.picasso)
    implementation(Config.ThirdPartyLibs.dagger)
    kapt(Config.ThirdPartyLibs.daggerCompiler)
}
