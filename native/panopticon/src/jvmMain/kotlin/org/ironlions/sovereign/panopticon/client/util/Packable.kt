package org.ironlions.sovereign.panopticon.client.util

import glm_.vec3.Vec3
import glm_.vec4.Vec4
import org.lwjgl.opengl.GL41.GL_FLOAT
import java.nio.ByteBuffer
import java.lang.reflect.Field
import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class PackMe

abstract class Packable<T : Any>(val type: KClass<T>) {
    private enum class PackableType {
        VECTOR_3, VECTOR_4, FLOAT,
    }

    val stride: Int
    val pointers: MutableList<Triple<Int, Int, Int>> = mutableListOf()

    init {
        var stride = 0

        getPackableFields().forEach { field ->
            val triple = when (field.first) {
                PackableType.VECTOR_3 -> Triple(3, GL_FLOAT, Float.SIZE_BYTES)
                PackableType.VECTOR_4 -> Triple(4, GL_FLOAT, Float.SIZE_BYTES)
                PackableType.FLOAT -> Triple(1, GL_FLOAT, Float.SIZE_BYTES)
            }

            pointers.add(triple)
            stride += triple.first * triple.third
        }

        this.stride = stride
    }

    private fun getPackableFields(): List<Pair<PackableType, Field>> {
        val packables: MutableList<Pair<PackableType, Field>> = mutableListOf()

        for (field in type.java.declaredFields) {
            if (!field.isAnnotationPresent(PackMe::class.java)) continue

            packables.add(
                when (field.type.simpleName) {
                    "Vec3" -> Pair(PackableType.VECTOR_3, field)
                    "Vec4" -> Pair(PackableType.VECTOR_4, field)
                    "Float" -> Pair(PackableType.FLOAT, field)
                    else -> throw RuntimeException("Unknown field type in ${type.qualifiedName} '${field.type.simpleName}'.")
                }
            )
        }

        return packables
    }

    open fun pack(packable: T, buffer: ByteBuffer) {
        getPackableFields().forEach { field ->
            when (field.first) {
                PackableType.VECTOR_3 -> {
                    val vec3 = field.second.get(packable) as Vec3
                    buffer.putFloat(vec3.x)
                    buffer.putFloat(vec3.y)
                    buffer.putFloat(vec3.z)
                }

                PackableType.VECTOR_4 -> {
                    val vec4 = field.second.get(packable) as Vec4
                    buffer.putFloat(vec4.x)
                    buffer.putFloat(vec4.y)
                    buffer.putFloat(vec4.z)
                    buffer.putFloat(vec4.w)
                }

                PackableType.FLOAT -> {
                    buffer.putFloat(field.second.get(packable) as Float)
                }
            }
        }
    }
}
