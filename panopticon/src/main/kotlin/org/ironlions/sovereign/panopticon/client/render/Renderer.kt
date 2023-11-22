package org.ironlions.sovereign.panopticon.client.render

import glm_.mat4x4.Mat4
import glm_.vec3.Vec3
import org.ironlions.sovereign.panopticon.client.Logging
import org.ironlions.sovereign.panopticon.client.ecs.Entity
import org.ironlions.sovereign.panopticon.client.ecs.Scene
import org.ironlions.sovereign.panopticon.client.ecs.components.Mesh
import org.ironlions.sovereign.panopticon.client.render.event.Event
import org.ironlions.sovereign.panopticon.client.render.event.EventDispatcher
import org.ironlions.sovereign.panopticon.client.render.geometry.Vertex
import org.ironlions.sovereign.panopticon.client.render.shader.Program
import org.ironlions.sovereign.panopticon.client.util.IOUtil
import org.lwjgl.glfw.Callbacks
import org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR
import org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR
import org.lwjgl.glfw.GLFW.GLFW_CURSOR
import org.lwjgl.glfw.GLFW.GLFW_CURSOR_DISABLED
import org.lwjgl.glfw.GLFW.GLFW_FALSE
import org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE
import org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE
import org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT
import org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE
import org.lwjgl.glfw.GLFW.GLFW_RELEASE
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
import org.lwjgl.glfw.GLFW.glfwSetInputMode
import org.lwjgl.glfw.GLFW.glfwSetKeyCallback
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
import org.lwjgl.opengl.GL41.GL_FRAMEBUFFER_SRGB
import org.lwjgl.opengl.GL41.GL_COLOR_BUFFER_BIT
import org.lwjgl.opengl.GL41.GL_CULL_FACE
import org.lwjgl.opengl.GL41.GL_DEPTH_BUFFER_BIT
import org.lwjgl.opengl.GL41.GL_DEPTH_TEST
import org.lwjgl.opengl.GL41.GL_RENDERER
import org.lwjgl.opengl.GL41.GL_VENDOR
import org.lwjgl.opengl.GL41.GL_VERSION
import org.lwjgl.opengl.GL41.GL_MULTISAMPLE
import org.lwjgl.opengl.GL41.glClear
import org.lwjgl.opengl.GL41.glClearColor
import org.lwjgl.opengl.GL41.glEnable
import org.lwjgl.opengl.GL41.glGetString
import org.lwjgl.opengl.GL41.glViewport
import org.lwjgl.opengl.GLCapabilities
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil

/** The renderer to render a scene with. */
class Renderer {
    private var window: Long = 0
    private val openGLMajor = 4
    private val openGLMinor = 1
    private val requiredCapabilities = arrayOf("OpenGL41")
    private val eventDispatcher = EventDispatcher()
    private val vertices = listOf(
        // Face 1 (closest to camera)
        Vertex(
            Vec3(0.5, 0.5, 0.5), Vec3(1, 0, 0), Vec3(0.0, 0.0, 1.0)
        ), Vertex(
            Vec3(0.5, -0.5, 0.5), Vec3(1, 0, 0), Vec3(0.0, 0.0, 1.0)
        ), Vertex(
            Vec3(-0.5, -0.5, 0.5), Vec3(1, 0, 0), Vec3(0.0, 0.0, 1.0)
        ), Vertex(
            Vec3(-0.5, 0.5, 0.5), Vec3(1, 0, 0), Vec3(0.0, 0.0, 1.0)
        ),

        // Face 2 (top)
        Vertex(
            Vec3(0.5, 0.5, 0.5), Vec3(1, 0, 0), Vec3(0.0, 1.0, 0.0)
        ), Vertex(
            Vec3(-0.5, 0.5, 0.5), Vec3(1, 0, 0), Vec3(0.0, 1.0, 0.0)
        ), Vertex(
            Vec3(-0.5, 0.5, -0.5), Vec3(1, 0, 0), Vec3(0.0, 1.0, 0.0)
        ), Vertex(
            Vec3(0.5, 0.5, -0.5), Vec3(1, 0, 0), Vec3(0.0, 1.0, 0.0)
        ),

        // Face 3 (bottom)
        Vertex(
            Vec3(0.5, -0.5, 0.5), Vec3(1, 0, 0), Vec3(0.0, -1.0, 0.0)
        ), Vertex(
            Vec3(-0.5, -0.5, 0.5), Vec3(1, 0, 0), Vec3(0.0, -1.0, 0.0)
        ), Vertex(
            Vec3(-0.5, -0.5, -0.5), Vec3(1, 0, 0), Vec3(0.0, -1.0, 0.0)
        ), Vertex(
            Vec3(0.5, -0.5, -0.5), Vec3(1, 0, 0), Vec3(0.0, -1.0, 0.0)
        ),

        // Face 4 (left)
        Vertex(
            Vec3(-0.5, 0.5, -0.5), Vec3(1, 0, 0), Vec3(-1.0, 0.0, 0.0)
        ), Vertex(
            Vec3(-0.5, -0.5, -0.5), Vec3(1, 0, 0), Vec3(-1.0, 0.0, 0.0)
        ), Vertex(
            Vec3(-0.5, -0.5, 0.5), Vec3(1, 0, 0), Vec3(-1.0, 0.0, 0.0)
        ), Vertex(
            Vec3(-0.5, 0.5, 0.5), Vec3(1, 0, 0), Vec3(-1.0, 0.0, 0.0)
        ),

        // Face 5 (right)
        Vertex(
            Vec3(0.5, 0.5, -0.5), Vec3(1, 0, 0), Vec3(1.0, 0.0, 0.0)
        ), Vertex(
            Vec3(0.5, -0.5, -0.5), Vec3(1, 0, 0), Vec3(1.0, 0.0, 0.0)
        ), Vertex(
            Vec3(0.5, -0.5, 0.5), Vec3(1, 0, 0), Vec3(1.0, 0.0, 0.0)
        ), Vertex(
            Vec3(0.5, 0.5, 0.5), Vec3(1, 0, 0), Vec3(1.0, 0.0, 0.0)
        ),

        // Face 6 (farthest from camera)
        Vertex(
            Vec3(-0.5, 0.5, -0.5), Vec3(1, 0, 0), Vec3(0.0, 0.0, -1.0)
        ), Vertex(
            Vec3(-0.5, -0.5, -0.5), Vec3(1, 0, 0), Vec3(0.0, 0.0, -1.0)
        ), Vertex(
            Vec3(0.5, -0.5, -0.5), Vec3(1, 0, 0), Vec3(0.0, 0.0, -1.0)
        ), Vertex(
            Vec3(0.5, 0.5, -0.5), Vec3(1, 0, 0), Vec3(0.0, 0.0, -1.0)
        )
    )
    private val indices = listOf(
        // Face 1
        3, 1, 0,
        3, 2, 1,

        // Face 2
        3+4, 1+4, 0+4,
        3+4, 2+4, 1+4,

        // Face 3
        0+8, 1+8, 3+8,
        1+8, 2+8, 3+8,

        // Face 4
        0+12, 1+12, 3+12,
        1+12, 2+12, 3+12,

        // Face 5
        3+16, 1+16, 0+16,
        3+16, 2+16, 1+16,

        // Face 6
        3+20, 1+20, 0+20,
        3+20, 2+20, 1+20,
    )
    private val scene: Scene = Scene(
        name = "main",
    )

    private var physicalWidth: Int? = null
    private var physicalHeight: Int? = null
    private var framebufferWidth: Int? = null
    private var framebufferHeight: Int? = null
    private var lastFrame: Float = 0f
    private var deltaTime: Float = 0f
    private var lastMouseX: Float = 0f
    private var lastMouseY: Float = 0f
    private var firstMouse: Boolean = true

    /** The active camera. */
    var activeCamera: Camera = Camera()
        private set
    /** The width of the window. */
    var windowWidth = 1200
        private set
    /** The height of the window. */
    var windowHeight = 700
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

        glfwSetKeyCallback(window, ::onKeyPress)
        glfwSetFramebufferSizeCallback(window, ::onFramebufferResize)
        glfwSetWindowSizeCallback(window, ::onWindowResize)
        glfwSetCursorPosCallback(window, ::onMouseMove)
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
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED)
        GL.createCapabilities()
        Logging.logger.debug { "$framebufferWidth, $framebufferHeight" }
        glViewport(0, 0, framebufferWidth!!, framebufferHeight!!)
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f)

        describeOpenGL()
        checkCapabilities()
        enableFeatures()

        eventDispatcher.subscribe(activeCamera, listOf(Event.Mouse::class, Event.Frame::class))

        val entity = Entity(scene)
        entity.addComponent(
            Mesh::class, Mat4(1), Program(
                name = "main",
                vertexSource = IOUtil.ioResourceToByteBuffer("shader.vert", 4096),
                fragmentSource = IOUtil.ioResourceToByteBuffer("shader.frag", 4096)
            ), vertices, indices
        )
        scene.add(entity)
    }

    /** Run per frame. */
    fun loop() {
        val currentFrame = glfwGetTime().toFloat()
        deltaTime = currentFrame - lastFrame
        lastFrame = currentFrame

        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

            eventDispatcher.broadcastToSubscribers(Event.Frame(window, deltaTime))
            scene.draw(this)

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
        glfwWindowHint(GLFW_SAMPLES, 4)
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
        glEnable(GL_FRAMEBUFFER_SRGB)
    }

    /**
     * Handle a key press event.
     *
     * @param window The window that experienced the event.
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
     * Handle a mouse movement event.
     *
     * @param window The window that experienced the event.
     * @param xIn The new x-value.
     * @param yIn The new y-value.
     */
    @Suppress("UNUSED_PARAMETER")
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

        eventDispatcher.broadcastToSubscribers(Event.Mouse(xOffset, yOffset))
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
        eventDispatcher.broadcastToSubscribers(Event.FramebufferResize(width, height))
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
        scene.destroy()

        Callbacks.glfwFreeCallbacks(window)
        glfwDestroyWindow(window)
        glfwTerminate()
        glfwSetErrorCallback(null)!!.free()
    }
}