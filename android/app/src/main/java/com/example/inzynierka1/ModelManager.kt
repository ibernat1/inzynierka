package com.example.inzynierka1

import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.channels.FileChannel
import javax.inject.Inject
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import kotlin.random.Random


class ModelManager@Inject constructor(@ApplicationContext private val context: Context) {
    private val TAG = "Model_Manager"
    private var interpreter: Interpreter? = null
    val TFLITE_MODEL_NAME = "model1.tflite"

    fun createInterpreter(): Interpreter? {
        Log.d(TAG, "createInterpreter")
        val tfLiteOptions = Interpreter.Options()//can be configure to use GPUDelegate
        interpreter = Interpreter(FileUtil.loadMappedFile(context, TFLITE_MODEL_NAME), tfLiteOptions)
        return interpreter
    }

    fun simpleInference(data: List<List<Float>>): Float{
        val interpreter = Interpreter(FileUtil.loadMappedFile(context, TFLITE_MODEL_NAME))
        val inputTensor = interpreter.getInputTensor(0)
        val outputTensor = interpreter.getOutputTensor(0)

        val inputShape = inputTensor.shape() // [1, 291,3]
        val inputType = inputTensor.dataType() // Data type: FLOAT32
        val outputShape = outputTensor.shape() // [1, 1]
        val outputType = outputTensor.dataType() // Data type: FLOAT32

        println("Input Shape: ${inputShape.joinToString()}")
        println("Input Type: $inputType")
        println("Output Shape: ${outputShape.joinToString()}")
        println("Output Type: $outputType")

        val inputBuffer = TensorBuffer.createFixedSize(inputShape, inputType)
        val outputBuffer = TensorBuffer.createFixedSize(outputShape, outputType)

        val inputData = data.take(291).flatten().toFloatArray()
//        val inputData = FloatArray(291 * 3) { Random.nextFloat() }
        println("Dane wejściowe: ${inputData.joinToString(", ")}")
        inputBuffer.loadArray(inputData)

        interpreter.run(inputBuffer.buffer, outputBuffer.buffer)

        val outputData = outputBuffer.floatArray

        println("Wynik inferencji: ${outputData.joinToString(", ")}")

        return outputData[0]
    }




//    fun initializeInterpreter(): String {
//        val assetManager = context.assets
//        val model = loadModelFile(assetManager, TFLITE_MODEL_NAME)
//        val interpreter = Interpreter(model)
//
//        // Read input shape from model file
//        val inputShape = interpreter.getInputTensor(0).shape()
//        Log.d(TAG, inputShape.toString())
//
//// Finish interpreter initialization
//        this.interpreter = interpreter
//
//        interpreter.close()
//        return inputShape.toString()
//    }
//    @Throws(IOException::class)
//    private fun loadModelFile(assetManager: AssetManager, filename: String): ByteBuffer {
//        val fileDescriptor = assetManager.openFd(filename)
//        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
//        val fileChannel = inputStream.channel
//        val startOffset = fileDescriptor.startOffset
//        val declaredLength = fileDescriptor.declaredLength
//        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
//    }
}