package com.dicoding.asclepius.view

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.dicoding.asclepius.helper.ImageClassifierHelper
import com.dicoding.asclepius.util.Constant.EXTRA_IMAGE_URI
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.text.NumberFormat

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private lateinit var imageClassifierHelper: ImageClassifierHelper
    private lateinit var label: String
    private lateinit var score: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = getString(R.string.result)
        supportActionBar?.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val image = intent.getStringExtra(EXTRA_IMAGE_URI)!!

        // TODO: Menampilkan hasil gambar, prediksi, dan confidence score.
        val imageUri = Uri.parse(image)
        imageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.resultImage.setImageURI(it)
        }

        classify(imageUri)
    }

    private fun classify(imageUri: Uri) {
        imageClassifierHelper = ImageClassifierHelper(
            context = this,
            classifierListener = object : ImageClassifierHelper.ClassifierListener {
                override fun onError(error: String) {
                    runOnUiThread {
                        Toast.makeText(this@ResultActivity, error, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onResults(result: List<Classifications>?, inferenceTime: Long) {
                    runOnUiThread {
                        result?.let { it ->
                            if (it.isNotEmpty() && it[0].categories.isNotEmpty()) {

                                // Menemukan 'category' dengan skor tertinggi menggunakan maxBy
                                val highestScoringCategory =
                                    it[0].categories.maxByOrNull { it.score }

                                if (highestScoringCategory != null) {
                                    val displayResult = "${highestScoringCategory.label} " +
                                            NumberFormat.getPercentInstance()
                                                .format(highestScoringCategory.score).trim()

                                    binding.resultText.text = displayResult

                                    label = highestScoringCategory.label
                                    score = NumberFormat.getPercentInstance()
                                        .format(highestScoringCategory.score).trim()
                                } else {
                                    binding.resultText.text =
                                        "No category with high enough score found."
                                }
                            } else {
                                binding.resultText.text = ""
                            }
                        }
                    }
                }
            }
        )

        imageClassifierHelper.classifyStaticImage(imageUri)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}