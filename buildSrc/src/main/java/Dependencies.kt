import Versions.kotlin_version

object Versions {
    val kotlin_version = "1.2.41"
    val support_lib = "27.0.2"
    val retrofit = "2.3.0"
    val rxjava = "2.1.9"
    val klog = "1.6.0"
}

object Deps {
    val support_annotations = "com.android.support:support-annotations:${Versions.support_lib}"
    val support_appcompat_v7 = "com.android.support:appcompat-v7:${Versions.support_lib}"
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val retrofit_rxjava_adapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    val rxjava = "io.reactivex.rxjava2:rxjava:${Versions.rxjava}"
    val klog = "com.github.zhaokaiqiang.klog:library:${Versions.klog}"
    val kotlin_gradle_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${kotlin_version}"
}