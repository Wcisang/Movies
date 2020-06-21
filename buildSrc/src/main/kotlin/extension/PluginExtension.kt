package extension

import dependencie.AndroidTestDependencie
import dependencie.Dependencie
import dependencie.KaptDependencie
import dependencie.TestDependencie
import org.gradle.kotlin.dsl.DependencyHandlerScope

fun DependencyHandlerScope.implementationDeps() {
    "implementation".run {
        Dependencie.values()
            .forEach {
                this.invoke(it.route)
            }
    }
}

fun DependencyHandlerScope.kaptDeps() {
    "kapt".run {
        KaptDependencie.values()
            .forEach {
                this.invoke(it.route)
            }
    }
}

fun DependencyHandlerScope.implementationTestDeps() {
    "testImplementation".run {
        TestDependencie.values()
            .forEach {
                this.invoke(it.route)
            }
    }
}

fun DependencyHandlerScope.implementationAndroidTestDeps() {
    "androidTestImplementation".run {
        AndroidTestDependencie.values()
            .forEach {
                this.invoke(it.route)
            }
    }
}