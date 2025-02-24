plugins {
	application
}

repositories {
	mavenCentral()
	maven { url = uri("https://jitpack.io") }
}

dependencies {
	implementation("org.eclipse.collections:eclipse-collections-api:11.1.0")
	implementation("org.eclipse.collections:eclipse-collections:11.1.0")
	implementation("commons-cli:commons-cli:1.9.0")
	implementation("com.googlecode.lanterna:lanterna:3.1.3")
	implementation("com.github.steos.jnafilechooser:jnafilechooser-api:1.1.2")
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
	from(configurations.runtimeClasspath.get().map { zipTree(it) }) {
		exclude("META-INF/*.SF", "META-INF/*.DSA", "META-INF/*.RSA")
	}
	duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
