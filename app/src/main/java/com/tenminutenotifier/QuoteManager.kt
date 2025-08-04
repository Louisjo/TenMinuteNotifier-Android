package com.tenminutenotifier

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.random.Random

data class Quote(
    val id: Int,
    val text: String,
    val author: String,
    val category: String? = null
)

class QuoteManager(private val context: Context) {
    
    private val prefs = context.getSharedPreferences("quotes_prefs", Context.MODE_PRIVATE)
    
    // Replace this URL with your GitHub Pages URL
    private val quotesUrl = "https://Louisjo.github.io/my-quotes-service/quotes.json"
    
    suspend fun updateQuotes(): Boolean = withContext(Dispatchers.IO) {
        return@withContext try {
            val jsonString = downloadQuotes()
            if (jsonString != null) {
                val quotesData = JSONObject(jsonString)
                val remoteVersion = quotesData.getInt("version")
                val localVersion = prefs.getInt("quotes_version", 0)
                
                if (remoteVersion > localVersion) {
                    // Save new quotes
                    prefs.edit()
                        .putString("quotes_json", jsonString)
                        .putInt("quotes_version", remoteVersion)
                        .putLong("last_updated", System.currentTimeMillis())
                        .apply()
                    true
                } else {
                    false // No update needed
                }
            } else {
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
    
    private fun downloadQuotes(): String? = try {
        val url = URL(quotesUrl)
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.connectTimeout = 10000
        connection.readTimeout = 10000
        
        if (connection.responseCode == HttpURLConnection.HTTP_OK) {
            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            val response = reader.readText()
            reader.close()
            response
        } else {
            null
        }
    } catch (e: Exception) {
        null
    }
    
    suspend fun getRandomQuote(): Quote? = withContext(Dispatchers.IO) {
        val quotes = getAllQuotes()
        return@withContext if (quotes.isNotEmpty()) {
            quotes[Random.nextInt(quotes.size)]
        } else {
            // If no quotes available, try to download some
            if (updateQuotes()) {
                val newQuotes = getAllQuotes()
                if (newQuotes.isNotEmpty()) {
                    newQuotes[Random.nextInt(newQuotes.size)]
                } else {
                    getDefaultQuote()
                }
            } else {
                getDefaultQuote()
            }
        }
    }
    
    private fun getAllQuotes(): List<Quote> {
        val quotesJson = prefs.getString("quotes_json", null) ?: return getDefaultQuotes()
        
        return try {
            val quotesData = JSONObject(quotesJson)
            val quotesArray = quotesData.getJSONArray("quotes")
            val quotes = mutableListOf<Quote>()
            
            for (i in 0 until quotesArray.length()) {
                val quoteObj = quotesArray.getJSONObject(i)
                quotes.add(
                    Quote(
                        id = quoteObj.getInt("id"),
                        text = quoteObj.getString("text"),
                        author = quoteObj.getString("author"),
                        category = quoteObj.optString("category", null)
                    )
                )
            }
            quotes
        } catch (e: Exception) {
            getDefaultQuotes()
        }
    }
    
    private fun getDefaultQuote(): Quote {
        return Quote(
            id = 1,
            text = "The best time to plant a tree was 20 years ago. The second best time is now.",
            author = "Chinese Proverb"
        )
    }
    
    private fun getDefaultQuotes(): List<Quote> {
        return listOf(
            Quote(1, "The best time to plant a tree was 20 years ago. The second best time is now.", "Chinese Proverb"),
            Quote(2, "Every moment is a fresh beginning.", "T.S. Eliot"),
            Quote(3, "The future depends on what you do today.", "Mahatma Gandhi"),
            Quote(4, "Success is not final, failure is not fatal: it is the courage to continue that counts.", "Winston Churchill"),
            Quote(5, "The only way to do great work is to love what you do.", "Steve Jobs"),
            Quote(6, "Life is what happens to you while you're busy making other plans.", "John Lennon"),
            Quote(7, "The purpose of our lives is to be happy.", "Dalai Lama"),
            Quote(8, "In the end, we will remember not the words of our enemies, but the silence of our friends.", "Martin Luther King Jr."),
            Quote(9, "Don't judge each day by the harvest you reap but by the seeds that you plant.", "Robert Louis Stevenson"),
            Quote(10, "The only impossible journey is the one you never begin.", "Tony Robbins")
        )
    }
}