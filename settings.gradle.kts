dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
//        maven { url "https://jitpack.io" }
    }
}

rootProject.name = "ShakeNetworkLog"
include(":app")
include(":shakenetworklog")
