package org.ironlions.sovereign.panopticon.client

import org.apache.log4j.BasicConfigurator
import org.ironlions.sovereign.panopticon.client.ecs.Entity
import org.ironlions.sovereign.panopticon.client.ecs.Scene
import org.ironlions.sovereign.panopticon.client.ecs.components.Mesh
import org.ironlions.sovereign.panopticon.client.shader.Program
import org.ironlions.sovereign.panopticon.client.util.IOUtil.ioResourceToByteBuffer
import org.lwjgl.glfw.Callbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.glfw.GLFWVidMode
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL20.*
import org.lwjgl.opengl.GLCapabilities
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil

class Client {
    private var window: Long = 0
    private val windowWidth = 1200
    private val windowHeight = 700
    private val openGLMajor = 3
    private val openGLMinor = 2
    private val requiredCapabilities = arrayOf("OpenGL32")
    private val vertices: FloatArray = floatArrayOf(
        -0.5f, -0.5f, 0.0f, 0.5f, -0.5f, 0.0f, 0.0f, 0.5f, 0.0f
    )
    private val scene: Scene = Scene("main")

    init {
        val entity = Entity(scene)

        entity.addComponent(Mesh::class) {}
    }

    /** Application entrypoint. */
    fun run() {
        Logging.logger.debug { "Initializing systems." }
        init()
        Logging.logger.debug { "Starting main loop." }
        loop()
        Logging.logger.debug { "Cleaning up." }
        cleanup()
    }

    /** Application initialization logic. */
    private fun init() {
        GLFWErrorCallback.createPrint(System.err).set()
        check(glfwInit()) { "Unable to initialize GLFW" }

        setGLFWHints()

        window = glfwCreateWindow(
            windowWidth, windowHeight, "Panopticon Client", MemoryUtil.NULL, MemoryUtil.NULL
        )
        if (window == MemoryUtil.NULL) throw RuntimeException("Failed to create the GLFW window")

        glfwSetKeyCallback(window, ::onKeyPress)
        glfwSetFramebufferSizeCallback(window, ::onFramebufferResize)

        MemoryStack.stackPush().use { stack ->
            val pWidth = stack.mallocInt(1)
            val pHeight = stack.mallocInt(1)

            glfwGetWindowSize(window, pWidth, pHeight)

            val vidmode: GLFWVidMode? = glfwGetVideoMode(glfwGetPrimaryMonitor())
            glfwSetWindowPos(
                window, (vidmode!!.width() - pWidth[0]) / 2, (vidmode.height() - pHeight[0]) / 2
            )
        }

        glfwMakeContextCurrent(window)
        glfwSwapInterval(1)
        glfwShowWindow(window)
        GL.createCapabilities()
        glViewport(0, 0, windowWidth, windowHeight);

        describeOpenGL()
        checkCapabilities()
        enableFeatures()

        Program(
            name = "main",
            vertexSource = ioResourceToByteBuffer("shader.vert", 4096),
            fragmentSource = ioResourceToByteBuffer("shader.frag", 4096)
        ).use()
    }

    /** Run per frame. */
    private fun loop() {
        glClearColor(0.95686275f, 0.59607846f, 0.6117647f, 0.0f)

        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
            glfwSwapBuffers(window)
            glfwPollEvents()
        }
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
            check(field.get(caps) as Boolean) { "This application requires OpenGL 3.2 or higher." }
        }
    }

    /** Enable the features we need. */
    private fun enableFeatures() {
        glEnable(GL_CULL_FACE)
        glEnable(GL_DEPTH_TEST)
    }

    /**
     * Handle a key press event.
     *
     * @param window The window that experiecned the event.
     * @param key The key that was pressed.
     * @param scancode The scancode of the key that was pressed.
     * @param action Who knows what this is?
     * @param mods Any modifiers to the pressed key.
     */
    @Suppress("UNUSED_PARAMETER")
    private fun onKeyPress(window: Long, key: Int, scancode: Int, action: Int, mods: Int) {
        if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
            glfwSetWindowShouldClose(window, true)
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
        glViewport(0, 0, width, height);
    }

    /** Clean up the application, including GLFW and OpenGL stuff. */
    private fun cleanup() {
        Callbacks.glfwFreeCallbacks(window)

        glfwDestroyWindow(window)
        glfwTerminate()
        glfwSetErrorCallback(null)!!.free()
    }
}

fun main() {
    BasicConfigurator.configure()
    Client().run()
}