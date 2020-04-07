buildscript {
    repositories {
        jcenter()
        google()
    }
    dependencies {
        classpath(Config.Tools.androidGradle)
        classpath(Config.Tools.kotlinGradlePlugin)
        classpath(Config.Android.navigationSafeArgs)
    }
}

allprojects {
    repositories {
        jcenter()
        google()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}