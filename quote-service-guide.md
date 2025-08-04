# Quote Service Setup Guide

## 🚀 5-Minute GitHub Pages Setup

### Step 1: Create Repository
1. Go to [GitHub](https://github.com) → Create new repository
2. Name: `my-quotes-service` (or any name)
3. Make it **public** (required for GitHub Pages)
4. Don't add README/license

### Step 2: Create quotes.json
1. In your repository, click "Create new file"
2. Name: `quotes.json`
3. Paste this starter template:

```json
{
  "version": 1,
  "lastUpdated": "2025-08-04",
  "quotes": [
    {
      "id": 1,
      "text": "The best time to plant a tree was 20 years ago. The second best time is now.",
      "author": "Chinese Proverb"
    },
    {
      "id": 2,
      "text": "Every moment is a fresh beginning.",
      "author": "T.S. Eliot"
    },
    {
      "id": 3,
      "text": "The future depends on what you do today.",
      "author": "Mahatma Gandhi"
    },
    {
      "id": 4,
      "text": "Success is not final, failure is not fatal: it is the courage to continue that counts.",
      "author": "Winston Churchill"
    },
    {
      "id": 5,
      "text": "The only way to do great work is to love what you do.",
      "author": "Steve Jobs"
    }
  ]
}
```

4. Commit the file

### Step 3: Enable GitHub Pages
1. Repository Settings → Pages (left sidebar)
2. Source: "Deploy from a branch"
3. Branch: "main", Folder: "/ (root)"
4. Save and wait 2-3 minutes

### Step 4: Get Your URL
Your quotes URL will be:
```
https://[YOUR-USERNAME].github.io/my-quotes-service/quotes.json
```

### Step 5: Update Android App
In `QuoteManager.kt`, line 27, replace:
```kotlin
private val quotesUrl = "https://[YOUR-USERNAME].github.io/my-quotes-service/quotes.json"
```

## 📝 JSON Format Rules

### Required Fields
- `version` (number): Increment when updating
- `quotes` (array): List of quote objects

### Quote Object
- `id` (number): Unique identifier  
- `text` (string): Quote content
- `author` (string): Author name

### Optional Fields
- `category` (string): Theme/category
- `lastUpdated` (string): Date stamp

## 🔄 Adding New Quotes

1. Edit `quotes.json` in GitHub
2. Add new quote objects to array
3. **Increment version number**
4. Commit changes
5. App auto-downloads updates

## 💡 Tips

- Keep quotes under 280 characters
- Always increment version for updates
- Test URL in browser - should return JSON
- App works offline after first download

## Example 20-Quote Collection

```json
{
  "version": 2,
  "lastUpdated": "2025-08-04",
  "quotes": [
    {"id": 1, "text": "The best time to plant a tree was 20 years ago. The second best time is now.", "author": "Chinese Proverb"},
    {"id": 2, "text": "Every moment is a fresh beginning.", "author": "T.S. Eliot"},
    {"id": 3, "text": "The future depends on what you do today.", "author": "Mahatma Gandhi"},
    {"id": 4, "text": "Success is not final, failure is not fatal: it is the courage to continue that counts.", "author": "Winston Churchill"},
    {"id": 5, "text": "The only way to do great work is to love what you do.", "author": "Steve Jobs"},
    {"id": 6, "text": "Life is what happens to you while you're busy making other plans.", "author": "John Lennon"},
    {"id": 7, "text": "The purpose of our lives is to be happy.", "author": "Dalai Lama"},
    {"id": 8, "text": "In the end, we will remember not the words of our enemies, but the silence of our friends.", "author": "Martin Luther King Jr."},
    {"id": 9, "text": "Don't judge each day by the harvest you reap but by the seeds that you plant.", "author": "Robert Louis Stevenson"},
    {"id": 10, "text": "The only impossible journey is the one you never begin.", "author": "Tony Robbins"},
    {"id": 11, "text": "It is during our darkest moments that we must focus to see the light.", "author": "Aristotle"},
    {"id": 12, "text": "Yesterday is history, tomorrow is a mystery, today is a gift.", "author": "Eleanor Roosevelt"},
    {"id": 13, "text": "The way to get started is to quit talking and begin doing.", "author": "Walt Disney"},
    {"id": 14, "text": "Innovation distinguishes between a leader and a follower.", "author": "Steve Jobs"},
    {"id": 15, "text": "Your time is limited, don't waste it living someone else's life.", "author": "Steve Jobs"},
    {"id": 16, "text": "If you look at what you have in life, you'll always have more.", "author": "Oprah Winfrey"},
    {"id": 17, "text": "If you set your goals ridiculously high and it's a failure, you will fail above everyone else's success.", "author": "James Cameron"},
    {"id": 18, "text": "Whether you think you can or you think you can't, you're right.", "author": "Henry Ford"},
    {"id": 19, "text": "The only person you are destined to become is the person you decide to be.", "author": "Ralph Waldo Emerson"},
    {"id": 20, "text": "Believe you can and you're halfway there.", "author": "Theodore Roosevelt"}
  ]
}
```

---

**🎯 Your quote service is ready! The app will automatically download and rotate through your quotes every 10 minutes.**