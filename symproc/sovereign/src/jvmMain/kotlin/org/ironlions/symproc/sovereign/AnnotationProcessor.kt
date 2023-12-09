package org.ironlions.symproc.sovereign

import com.google.devtools.ksp.containingFile
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import java.io.File
import java.io.OutputStreamWriter

class AnnotationProcessor(private val codeGenerator: CodeGenerator, private val logger: KSPLogger) :
    SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        val visitor = ClassVisitor(logger)
        val symbols =
            resolver.getSymbolsWithAnnotation("org.ironlions.sovereign.opmode.MakeAvailable")
        val invalid: List<KSAnnotated> = symbols.filter {
            !generateProviderForOpMode(it, visitor)
        }.toList()

        return invalid
    }

    private fun generateProviderForOpMode(symbol: KSAnnotated, visitor: ClassVisitor): Boolean {
        if (symbol.containingFile == null) {
            logger.warn("Symbol '$symbol' has no containing file.")
            return false
        }

        val containingFile = symbol.containingFile!!
        val packageName = containingFile.packageName.asString()
        val className = File(containingFile.fileName).nameWithoutExtension
        val providerClassName = "${className}Provider"

        codeGenerator.createNewFile(Dependencies.ALL_FILES, packageName, providerClassName, "java")
            .use { output ->
                OutputStreamWriter(output).use { writer ->
                    symbol.accept(visitor, writer)
                }
            }

        return true
    }
}