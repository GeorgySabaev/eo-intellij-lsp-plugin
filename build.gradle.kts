import org.gradle.kotlin.dsl.from
import org.gradle.kotlin.dsl.testRuntimeOnly
import org.jetbrains.intellij.platform.gradle.TestFrameworkType

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "2.1.20"
    id("org.jetbrains.intellij.platform") version "2.5.0"
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    intellijPlatform {
        bundledPlugin("org.jetbrains.plugins.textmate")
        bundledPlugin("com.intellij.java")
        plugin("com.redhat.devtools.lsp4ij:0.12.0")
        create(providers.gradleProperty("platformType"), providers.gradleProperty("platformVersion"))
        testFramework(TestFrameworkType.Starter)
//        testFramework(TestFrameworkType.Plugin.Java)
    }
//    testImplementation("org.junit.jupiter:junit-jupiter:5.7.1")
     // implementation("com.github.ballerina-platform:lsp4intellij:0.96.1")
}

intellijPlatform {
    pluginConfiguration {
        name = providers.gradleProperty("pluginName")
        version = providers.gradleProperty("pluginVersion")
    }
}

fun buildServer() {

    var install_command = arrayListOf<String>("npm", "install", "--prefix", "eo-lsp-server")
    var install_res = providers.exec {
        commandLine(install_command)
        isIgnoreExitValue=true
    }
    if (install_res.result.get().exitValue != 0) {
        error("Error while running $install_command\n" + install_res.standardError.asText.get())
    }
    var build_command = arrayListOf<String>("npm", "run", "build", "--prefix", "eo-lsp-server")
    var build_res = providers.exec {
        commandLine(build_command)
        isIgnoreExitValue=true
    }
    if (build_res.result.get().exitValue != 0) {
        error("Error while running $build_command\n" + build_res.standardError.asText.get())
    }
    var package_command = arrayListOf<String>("npm", "run", "package", "--prefix", "eo-lsp-server")
    var package_res = providers.exec {
        commandLine(package_command)
        isIgnoreExitValue=true
    }
    if (package_res.result.get().exitValue != 0) {
        error("Error while running $package_command\n" + package_res.standardError.asText.get())
    }
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    patchPluginXml {
        sinceBuild.set("241")
        untilBuild.set("251.*")
    }

    prepareSandbox {
        buildServer()
        from("${rootProject.projectDir}/eo-lsp-server/bin") {
            into("${intellijPlatform.pluginConfiguration.name.get()}/")
        }
    }

    prepareTestSandbox {
        buildServer()
        from("${rootProject.projectDir}/eo-lsp-server/bin") {
            into("${intellijPlatform.pluginConfiguration.name.get()}/")
        }
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}

sourceSets {
    create("integrationTest") {
        compileClasspath += sourceSets.main.get().output
        runtimeClasspath += sourceSets.main.get().output
    }
}

val integrationTestImplementation by configurations.getting {
    extendsFrom(configurations.testImplementation.get())
}

dependencies {
    integrationTestImplementation("org.junit.jupiter:junit-jupiter:5.7.1")
    integrationTestImplementation("org.kodein.di:kodein-di-jvm:7.20.2")
    integrationTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.10.1")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

val integrationTest = task<Test>("integrationTest") {
    val integrationTestSourceSet = sourceSets.getByName("integrationTest")
    testClassesDirs = integrationTestSourceSet.output.classesDirs
    classpath = integrationTestSourceSet.runtimeClasspath
    systemProperty("path.to.build.plugin", tasks.prepareSandbox.get().pluginDirectory.get().asFile)
    useJUnitPlatform()
    dependsOn(tasks.prepareSandbox)
}


idea {
    module {
        testSources.from(sourceSets["integrationTest"].kotlin.srcDirs)
    }
}