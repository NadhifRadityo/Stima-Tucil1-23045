plugins {
	application
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.eclipse.collections:eclipse-collections-api:11.1.0")
	implementation("org.eclipse.collections:eclipse-collections:11.1.0")
	implementation("io.github.spair:imgui-java-app:1.89.0")
}

sourceSets {
	main {
		java {
			srcDir("src/java")
		}
		resources {
			srcDir("src/resources")
		}
	}
	test {
		java {
			srcDir("test/java")
		}
		resources {
			srcDir("test/resources")
		}
	}
}

testing {
	suites {
		val test by getting(JvmTestSuite::class) {
			useJUnitJupiter("5.11.4")
		}
	}
}

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(21))
	}
}

application {
	mainClass.set("io.github.nadhifradityo.stima_tucil1_23045.Main")
}
tasks.jar {
	manifest {
		attributes["Main-Class"] = "io.github.nadhifradityo.stima_tucil1_23045.Main"
	}
	from(configurations.runtimeClasspath.get().map { zipTree(it) })
	duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
