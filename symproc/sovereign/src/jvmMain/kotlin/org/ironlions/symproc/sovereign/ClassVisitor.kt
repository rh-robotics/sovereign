package org.ironlions.symproc.sovereign

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.visitor.KSTopDownVisitor
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeSpec
import java.io.OutputStreamWriter
import javax.lang.model.element.Modifier

class ClassVisitor(private val logger: KSPLogger) : KSTopDownVisitor<OutputStreamWriter, Unit>() {
    private val componentClass = ClassName.get("org.ironlions.sovereign.components", "Component")
    private val opModeProvider = ClassName.get("org.ironlions.sovereign.opmode", "OpModeProvider")

    override fun defaultHandler(node: KSNode, data: OutputStreamWriter) {}

    override fun visitClassDeclaration(
        classDeclaration: KSClassDeclaration, data: OutputStreamWriter
    ) {
        val isSubclassOfSovereignOpMode = classDeclaration.superTypes.any {
            it.resolve().declaration.qualifiedName?.asString() == componentClass.canonicalName()
        }

        require(isSubclassOfSovereignOpMode) {
            "Class '${classDeclaration.qualifiedName?.asString()}' must inherit from ${componentClass.canonicalName()}."
        }

        val makeAvailableAnnotation =
            classDeclaration.annotations.first { it.toString() == "@MakeAvailable" }
        val opModeType = when (val opModeTypeParameter =
            makeAvailableAnnotation.arguments.first { argument -> argument.name!!.asString() == "type" }.value.toString()) {
            "org.ironlions.sovereign.opmode.OpModeType.TELEOP" -> ClassName.get(
                "com.qualcomm.robotcore.eventloop.opmode", "TeleOp"
            )

            "org.ironlions.sovereign.opmode.OpModeType.AUTON" -> ClassName.get(
                "com.qualcomm.robotcore.eventloop.opmode", "Autonomous"
            )

            else -> {
                logger.error("Unknown OpMode type '${opModeTypeParameter}' on @MakeAvailable annotation (${makeAvailableAnnotation.location}).")
                return
            }
        }

        val packageName = classDeclaration.packageName.asString()
        val className = classDeclaration.simpleName.asString()
        val originalQualifiedClassName = ClassName.get(packageName, className)
        val providerClassName = "${className}Provider"

        val annotationSpecBuilder = AnnotationSpec.builder(opModeType)
        annotationSpecBuilder.addMember("group", "\$S", classDeclaration.packageName.getQualifier())
        annotationSpecBuilder.addMember("name", "\$S", className)
        annotationSpecBuilder.build()
        val annotationSpec = annotationSpecBuilder.build()

        val childField =
            FieldSpec.builder(originalQualifiedClassName, "child", Modifier.PRIVATE).build()

        val providerClassBuilder = TypeSpec.classBuilder(providerClassName)
        providerClassBuilder.addModifiers(Modifier.PUBLIC)
        providerClassBuilder.superclass(opModeProvider)
        providerClassBuilder.addAnnotation(annotationSpec)
        providerClassBuilder.addField(childField)

        providerClassBuilder.addMethod(
            MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC).addStatement(
                "this.\$L = new \$T(this)", childField.name, originalQualifiedClassName
            ).build()
        )

        arrayOf("init_loop", "start", "stop", "loop").forEach {
            providerClassBuilder.addMethod(generatePassthroughMethod(it))
        }

        providerClassBuilder.addMethod(
            MethodSpec.methodBuilder("init").addAnnotation(Override::class.java)
                .addModifiers(Modifier.PUBLIC).returns(Void.TYPE).addCode(
                    "\$L.init();\ntelemetry.addLine(\$S);\n",
                    childField.name,
                    "This OpMode is Sovereign enabled."
                ).build()
        )

        val providerClass = providerClassBuilder.build()
        val javaFile = JavaFile.builder(packageName, providerClass).build()

        data.write("// This file was automatically generated, DO NOT EDIT.\n\n")
        data.write(javaFile.toString())
    }

    private fun generatePassthroughMethod(methodName: String): MethodSpec =
        MethodSpec.methodBuilder(methodName).addAnnotation(Override::class.java)
            .addModifiers(Modifier.PUBLIC).returns(Void.TYPE).addCode("child.\$L();\n", methodName)
            .build()
}