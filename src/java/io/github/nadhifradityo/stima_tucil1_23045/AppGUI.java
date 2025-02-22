package io.github.nadhifradityo.stima_tucil1_23045;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Future.State;

import org.lwjgl.glfw.GLFW;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import com.badlogic.gdx.graphics.GL20;

import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.extension.imguifiledialog.ImGuiFileDialog;
import imgui.extension.imguifiledialog.flag.ImGuiFileDialogFlags;
import imgui.flag.ImGuiConfigFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import io.github.nadhifradityo.stima_tucil1_23045.bitfields.BitField;

public class AppGUI extends ApplicationAdapter {

	private static ImGuiImplGlfw imGuiGlfw;
	private static ImGuiImplGl3 imGuiGl3;
	private static InputProcessor tmpProcessor;

	public static void initImGui() {
		imGuiGlfw = new ImGuiImplGlfw();
		imGuiGl3 = new ImGuiImplGl3();
		long windowHandle = ((Lwjgl3Graphics) Gdx.graphics).getWindow().getWindowHandle();
		ImGui.createContext();
		ImGuiIO io = ImGui.getIO();
		io.setConfigFlags(ImGuiConfigFlags.DockingEnable | ImGuiConfigFlags.ViewportsEnable);
		io.setIniFilename("imgui.ini");
		io.getFonts().addFontDefault();
		io.getFonts().build();
		imGuiGlfw.init(windowHandle, true);
		imGuiGl3.init("#version 150");
	}
	public static void shutdownImGui() {
		imGuiGl3.shutdown();
		imGuiGl3 = null;
		imGuiGlfw.shutdown();
		imGuiGlfw = null;
		ImGui.destroyContext();
	}
	public static void startImGui() {
		if(tmpProcessor != null) {
			Gdx.input.setInputProcessor(tmpProcessor);
			tmpProcessor = null;
		}
		imGuiGl3.newFrame();
		imGuiGlfw.newFrame();
		ImGui.newFrame();
	}
	public static void endImGui() {
		ImGui.render();
		imGuiGl3.renderDrawData(ImGui.getDrawData());
		if(ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
			long backupCurrentContext = GLFW.glfwGetCurrentContext();
			ImGui.updatePlatformWindows();
			ImGui.renderPlatformWindowsDefault();
			GLFW.glfwMakeContextCurrent(backupCurrentContext);
		}
		if(ImGui.getIO().getWantCaptureKeyboard() || ImGui.getIO().getWantCaptureMouse()) {
			tmpProcessor = Gdx.input.getInputProcessor();
			Gdx.input.setInputProcessor(null);
		}
	}

	protected static ExecutorService executorService = Executors.newFixedThreadPool(Math.min(4, Runtime.getRuntime().availableProcessors()));
	protected final MainGUI mainGUI = new MainGUI();

	public static void main(String... args) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Application");
		config.setWindowedMode(800, 600);
		new Lwjgl3Application(new AppGUI(), config);
	}

	@Override
	public void create() {
		initImGui();
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		startImGui();
		mainGUI.render();
		endImGui();
	}

	@Override
	public void dispose() {
		shutdownImGui();
	}

	public static class MainGUI {
		public void render() {
			if(ImGui.button("Input File"))
				ImGuiFileDialog.openModal("input-case", "Choose file", ".txt", ".");
			if(ImGuiFileDialog.display("input-case", ImGuiFileDialogFlags.None, 200, 400, 800, 600)) {
				if(ImGuiFileDialog.isOk())
					loadCaseFile(new File(ImGuiFileDialog.getFilePathName()));
				ImGuiFileDialog.close();
			}
			if(loadCaseFileFuture != null) {
				if(!loadCaseFileFuture.isDone())
					ImGui.text("Loading file...");
				if(loadCaseFileFuture.state() == State.FAILED)
					ImGui.text(Utils.throwableAsString(loadCaseFileFuture.exceptionNow()));
			}
		}
		protected Future<Void> loadCaseFileFuture = null;
		protected void loadCaseFile(File file) {
			if(loadCaseFileFuture != null && !loadCaseFileFuture.isDone())
				loadCaseFileFuture.cancel(true);
			loadCaseFileFuture = executorService.submit(() -> {
				String contents = Files.readString(file.toPath(), StandardCharsets.UTF_8);
				loadCaseFileFuture = null;
				return null;
			});
		}
	}
	public static class SolverGUI {
		protected final Solver solver;

		public SolverGUI(Solver solver) {
			this.solver = solver;
		}

		public void render() {

		}
	}
	public static class BitFieldGUI {
		protected final BitField field;
		
		public BitFieldGUI(BitField field) {
			this.field = field;
		}

		public void render() {

		}
	}
}
