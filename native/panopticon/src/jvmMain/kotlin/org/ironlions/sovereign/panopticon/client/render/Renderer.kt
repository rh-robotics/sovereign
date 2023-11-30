package org.ironlions.sovereign.panopticon.client.render

import imgui.ImGui
import imgui.flag.ImGuiConfigFlags
import imgui.glfw.ImGuiImplGlfw
import imgui.gl3.ImGuiImplGl3
import org.ironlions.sovereign.panopticon.client.ClientApplication
import org.ironlions.sovereign.panopticon.client.Logging
import org.ironlions.sovereign.panopticon.client.event.Event
import org.ironlions.sovereign.panopticon.client.event.EventDispatcher
import org.ironlions.sovereign.panopticon.client.ui.GraphicsScene
import org.ironlions.sovereign.panopticon.client.ui.Inspector
import org.ironlions.sovereign.panopticon.client.ui.button
import org.ironlions.sovereign.panopticon.client.ui.installImGuiTheme
import org.ironlions.ui.marsh.Marsh
import org.ironlions.ui.marsh.Toast
import org.lwjgl.glfw.Callbacks
import org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR
import org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR
import org.lwjgl.glfw.GLFW.GLFW_FALSE
import org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE
import org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT
import org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE
import org.lwjgl.glfw.GLFW.GLFW_RESIZABLE
import org.lwjgl.glfw.GLFW.GLFW_SAMPLES
import org.lwjgl.glfw.GLFW.GLFW_TRUE
import org.lwjgl.glfw.GLFW.GLFW_VISIBLE
import org.lwjgl.glfw.GLFW.glfwCreateWindow
import org.lwjgl.glfw.GLFW.glfwDefaultWindowHints
import org.lwjgl.glfw.GLFW.glfwDestroyWindow
import org.lwjgl.glfw.GLFW.glfwGetFramebufferSize
import org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor
import org.lwjgl.glfw.GLFW.glfwGetTime
import org.lwjgl.glfw.GLFW.glfwGetVideoMode
import org.lwjgl.glfw.GLFW.glfwGetWindowSize
import org.lwjgl.glfw.GLFW.glfwInit
import org.lwjgl.glfw.GLFW.glfwMakeContextCurrent
import org.lwjgl.glfw.GLFW.glfwPollEvents
import org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback
import org.lwjgl.glfw.GLFW.glfwSetErrorCallback
import org.lwjgl.glfw.GLFW.glfwSetFramebufferSizeCallback
import org.lwjgl.glfw.GLFW.glfwSetWindowPos
import org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose
import org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback
import org.lwjgl.glfw.GLFW.glfwShowWindow
import org.lwjgl.glfw.GLFW.glfwSwapBuffers
import org.lwjgl.glfw.GLFW.glfwSwapInterval
import org.lwjgl.glfw.GLFW.glfwTerminate
import org.lwjgl.glfw.GLFW.glfwWindowHint
import org.lwjgl.glfw.GLFW.glfwWindowShouldClose
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.glfw.GLFWVidMode
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL41.GL_CULL_FACE
import org.lwjgl.opengl.GL41.GL_DEPTH_TEST
import org.lwjgl.opengl.GL41.GL_RENDERER
import org.lwjgl.opengl.GL41.GL_VENDOR
import org.lwjgl.opengl.GL41.GL_VERSION
import org.lwjgl.opengl.GL41.GL_MULTISAMPLE
import org.lwjgl.opengl.GL41.glClearColor
import org.lwjgl.opengl.GL41.glEnable
import org.lwjgl.opengl.GL41.glGetString
import org.lwjgl.opengl.GL41.glViewport
import org.lwjgl.opengl.GLCapabilities
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil

/** The renderer to render a scene with. */
class Renderer {
    private val openGLMajor = 4
    private val openGLMinor = 1
    private val requiredCapabilities = arrayOf("OpenGL41")
    private val shaderPrelude = "#version 410 core"
    private val eventDispatcher = EventDispatcher()
    private var imGuiImplGlfw: ImGuiImplGlfw = ImGuiImplGlfw()
    private var imGuiImplGl3: ImGuiImplGl3 = ImGuiImplGl3()
    private var physicalWidth: Int? = null
    private var physicalHeight: Int? = null
    private var framebufferWidth: Int? = null
    private var framebufferHeight: Int? = null
    private var lastFrame: Float = 0f
    private var lastMouseX: Float = 0f
    private var lastMouseY: Float = 0f
    private var firstMouse: Boolean = true
    private var displayAbout: Boolean = false
    private var windowWidth: Int = 1200
    private var windowHeight: Int = 700
    var window: Long = 0
    var deltaTime: Float = 0f

    /** The active camera. */
    var activeCamera: Camera
        private set

    init {
        Logging.logger.debug { "Initializing renderer." }

        GLFWErrorCallback.createPrint(System.err).set()
        check(glfwInit()) { "Unable to initialize GLFW" }

        setGLFWHints()

        window = glfwCreateWindow(
            windowWidth, windowHeight, "Panopticon Client", MemoryUtil.NULL, MemoryUtil.NULL
        )
        if (window == MemoryUtil.NULL) throw RuntimeException("Failed to create the GLFW window")

        glfwSetFramebufferSizeCallback(window, ::onFramebufferResize)
        glfwSetWindowSizeCallback(window, ::onWindowResize)
        glfwSetCursorPosCallback(window, ::onMouseMove)
        setWindowAttribs(window)
        getSizing()

        val vidmode: GLFWVidMode = glfwGetVideoMode(glfwGetPrimaryMonitor())!!
        glfwSetWindowPos(
            window,
            (vidmode.width() - physicalWidth!!) / 2,
            (vidmode.height() - physicalHeight!!) / 2
        )

        glfwMakeContextCurrent(window)
        glfwSwapInterval(1)
        glfwShowWindow(window)
        GL.createCapabilities()
        activeCamera = Camera(framebufferWidth!!, framebufferHeight!!)
        glViewport(0, 0, framebufferWidth!!, framebufferHeight!!)
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f)

        describeOpenGL()
        checkCapabilities()
        enableFeatures()

        setupImGui(window)

        eventDispatcher.subscribe(
            activeCamera, listOf(Event.Mouse::class, Event.FramebufferResize::class)
        )
    }

    /** Run per frame. */
    fun loop() {
        while (!glfwWindowShouldClose(window)) {
            val currentFrame = glfwGetTime().toFloat()
            deltaTime = currentFrame - lastFrame
            lastFrame = currentFrame

            frame()
        }
    }

    /** Do a single frame. */
    private fun frame() {
        imGuiImplGlfw.newFrame()
        ImGui.newFrame()

        ImGui.dockSpaceOverViewport(ImGui.getMainViewport())
        ImGui.showDemoWindow()
        menuBar()
        ClientApplication.windows.forEach { it.value.frame(this) }

        if (displayAbout) aboutWindow()

        Marsh.draw(deltaTime)
        ImGui.render()

        imGuiImplGl3.renderDrawData(ImGui.getDrawData())

        glfwSwapBuffers(window)
        glfwPollEvents()
    }

    /** The menu bar. */
    private fun menuBar() {
        if (ImGui.beginMainMenuBar()) {
            if (ImGui.beginMenu("Panopticon")) {
                if (ImGui.menuItem("About")) displayAbout = true
                if (ImGui.menuItem("Settings")) Marsh.show(Toast.Error("This is not implemented."))
                if (ImGui.beginMenu("Data")) {
                    val inspector = ClientApplication.windows[Inspector::class]!! as Inspector
                    if (ImGui.menuItem(if (inspector.dataSource == null) "Connect" else "Reconnect")) {
                        inspector.dataSource = null
                        inspector.wantConnect = true
                    }

                    ImGui.endMenu()
                }

                ImGui.separator()

                if (ImGui.menuItem("Quit")) glfwSetWindowShouldClose(window, true)
                ImGui.endMenu()
            }

            ImGui.endMainMenuBar()
        }
    }

    /** Display information about Panopticon. */
    private fun aboutWindow() {
        ImGui.begin("About Panopticon")
        ImGui.text("Panopticon is brought to you by The Iron Lions (FTC 19922).")
        ImGui.separator()
        ImGui.text("Copyright 2023 Milo Banks (The Principle Architect).")
        button("Close") { displayAbout = false }
        ImGui.end()
    }

    /** Set GLFW and OpenGL loader hints. */
    private fun setGLFWHints() {
        glfwDefaultWindowHints()
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, openGLMajor)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, openGLMinor)
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE)
        glfwWindowHint(GLFW_SAMPLES, 4)
    }

    /** Set GLFW window attributes. */
    @Suppress("UNUSED_PARAMETER")
    private fun setWindowAttribs(window: Long) {
    }

    /** Get window size and framebuffer size from GLFW> */
    private fun getSizing() {
        MemoryStack.stackPush().use { stack ->
            val sPhysicalWidth = stack.mallocInt(1)
            val sPhysicalHeight = stack.mallocInt(1)
            val sFramebufferWidth = stack.mallocInt(1)
            val sFramebufferHeight = stack.mallocInt(1)

            glfwGetWindowSize(window, sPhysicalWidth, sPhysicalHeight)
            glfwGetFramebufferSize(window, sFramebufferWidth, sFramebufferHeight)

            physicalWidth = sPhysicalWidth.get()
            physicalHeight = sPhysicalHeight.get()
            framebufferWidth = sFramebufferWidth.get()
            framebufferHeight = sFramebufferHeight.get()
        }
    }

    /** Describe the OpenGL context to the user. */
    private fun describeOpenGL() {
        Logging.logger.debug { "GL_VENDOR: " + glGetString(GL_VENDOR) }
        Logging.logger.debug { "GL_RENDERER: " + glGetString(GL_RENDERER) }
        Logging.logger.debug { "GL_VERSION: " + glGetString(GL_VERSION) }
    }

    /** Check and ensure we have the correct capabilities. */
    private fun checkCapabilities() {
        val caps = GL.getCapabilities()

        for (capability in requiredCapabilities) {
            val field = GLCapabilities::class.java.getDeclaredField(capability)
            check(field.get(caps) as Boolean) { "This application requires OpenGL $openGLMajor.$openGLMinor or higher." }
        }
    }

    /** Enable the features we need. */
    private fun enableFeatures() {
        glEnable(GL_CULL_FACE)
        glEnable(GL_DEPTH_TEST)
        glEnable(GL_MULTISAMPLE)
    }

    /** Initialize ImGui. */
    private fun setupImGui(window: Long) {
        ImGui.createContext()
        ImGui.loadIniSettingsFromDisk(this::class.java.classLoader.getResource("imgui.ini")!!.path)

        val io = ImGui.getIO()
        io.fonts.addFontFromFileTTF(
            this::class.java.classLoader.getResource("fonts/Roboto-Medium.ttf")!!.path, 16.0f
        )
        io.configFlags = io.configFlags or ImGuiConfigFlags.DockingEnable
        io.configWindowsMoveFromTitleBarOnly = true
        io.iniFilename = null
        io.logFilename = null

        installImGuiTheme()
        imGuiImplGlfw.init(window, true)
        imGuiImplGl3.init(shaderPrelude)
    }

    /**
     * Handle a mouse movement event.
     *
     * @param window The window that experienced the event.
     * @param xIn The new x-value.
     * @param yIn The new y-value.
     */
    private fun onMouseMove(window: Long, xIn: Double, yIn: Double) {
        val x = xIn.toFloat()
        val y = yIn.toFloat()

        if (firstMouse) {
            lastMouseX = x
            lastMouseY = y
            firstMouse = false
        }

        val xOffset = x - lastMouseX
        val yOffset = lastMouseY - y

        lastMouseX = x
        lastMouseY = y

        if (ClientApplication.windows[GraphicsScene::class]!!.hovering) {
            eventDispatcher.broadcastToSubscribers(Event.Mouse(window, xOffset, yOffset))
        }
    }

    /**
     * Handle a framebuffer resize event.
     *
     * @param window The window that experienced the resizal.
     * @param width The new width.
     * @param height The new height.
     */
    @Suppress("UNUSED_PARAMETER")
    private fun onFramebufferResize(window: Long, width: Int, height: Int) {
        getSizing()
        glViewport(0, 0, width, height)
    }

    /**
     * Handle a window resize event.
     *
     * @param window The window that experienced the resizal.
     * @param width The new width.
     * @param height The new height.
     */
    @Suppress("UNUSED_PARAMETER")
    private fun onWindowResize(window: Long, width: Int, height: Int) {
        windowHeight = height
        windowWidth = width
    }

    /** Clean up the application, including GLFW and OpenGL stuff. */
    fun destroy() {
        ClientApplication.windows.forEach { it.value.destroy() }
        Callbacks.glfwFreeCallbacks(window)
        glfwDestroyWindow(window)
        glfwTerminate()
        glfwSetErrorCallback(null)!!.free()
    }
}