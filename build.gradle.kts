plugins {
	application
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.eclipse.collections:eclipse-collections-api:11.1.0")
	implementation("org.eclipse.collections:eclipse-collections:11.1.0")
	implementation("com.badlogicgames.gdx:gdx:1.13.1")
	implementation("com.badlogicgames.gdx:gdx-backend-lwjgl3:1.13.1")
	implementation("com.badlogicgames.gdx:gdx-platform:1.13.1:natives-desktop")
	implementation("io.github.spair:imgui-java-binding:1.88.0")
	implementation("io.github.spair:imgui-java-lwjgl3:1.88.0")
	implementation("io.github.spair:imgui-java-natives-linux:1.88.0")
	implementation("io.github.spair:imgui-java-natives-macos:1.88.0")
	implementation("io.github.spair:imgui-java-natives-windows:1.88.0")
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
