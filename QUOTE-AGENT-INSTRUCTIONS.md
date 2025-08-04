# Quote Extraction & Organization Guide

## 📋 Agent Instructions for Quote Processing

This document provides exact specifications for extracting and organizing quotes for the 10-Minute Notifier Android app.

## 🎯 Target Output Format

### GitHub Pages JSON Structure
Create a file called `quotes.json` with this exact structure:

```json
{
  "version": 1,
  "lastUpdated": "2025-08-04",
  "categories": ["motivation", "productivity", "wisdom", "success", "creativity", "mindfulness"],
  "quotes": [
    {
      "id": 1,
      "text": "The best time to plant a tree was 20 years ago. The second best time is now.",
      "author": "Chinese Proverb",
      "category": "motivation"
    },
    {
      "id": 2,
      "text": "Every moment is a fresh beginning.",
      "author": "T.S. Eliot",
      "category": "mindfulness"
    }
  ]
}
```

## 📝 Field Specifications

### Required Fields (MUST BE PRESENT)
- **`version`** (number): Start at 1, increment for each update
- **`quotes`** (array): Contains all quote objects
- **`id`** (number): Unique sequential ID for each quote (1, 2, 3...)
- **`text`** (string): The actual quote content
- **`author`** (string): Quote attribution

### Optional Fields (RECOMMENDED)
- **`lastUpdated`** (string): Date in YYYY-MM-DD format
- **`categories`** (array): List of all available categories
- **`category`** (string): Quote theme/category

## 🎨 Content Guidelines

### Quote Text Rules
- **Length**: Keep under 280 characters for optimal mobile display
- **Format**: Remove extra quotation marks (app adds them automatically)
- **Punctuation**: Include proper punctuation
- **Encoding**: Use standard UTF-8 characters
- **Line breaks**: Single line of text only

### Author Attribution
- **Format**: "First Last" or "Organization Name"
- **Unknown authors**: Use "Unknown" or "Anonymous"
- **Consistency**: Use same format throughout
- **Verification**: Ensure attribution accuracy

### Categories (Use These Exact Names)
- **motivation** - Inspirational and uplifting quotes
- **productivity** - Focus, efficiency, time management
- **wisdom** - Life lessons and philosophical insights
- **success** - Achievement, goals, leadership
- **creativity** - Innovation, artistic expression
- **mindfulness** - Present moment awareness, meditation
- **perseverance** - Overcoming challenges, resilience
- **growth** - Personal development, learning
- **happiness** - Joy, positivity, well-being
- **courage** - Bravery, taking risks, facing fears

## 🔧 Processing Instructions

### Step 1: Quote Extraction
1. Extract quotes from source material
2. Clean up formatting and remove extra characters
3. Verify quote accuracy and attribution
4. Ensure quotes are appropriate and inspiring

### Step 2: JSON Creation
1. Create sequential ID numbers starting from 1
2. Assign appropriate category from the list above
3. Format as valid JSON structure
4. Validate JSON syntax using jsonlint.com

### Step 3: Quality Control
- Remove duplicate quotes
- Verify all quotes have required fields
- Check character limits (under 280 chars)
- Ensure proper JSON formatting

## 📊 Target Collection Size

### Recommended Quantities
- **Minimum**: 20 quotes (for basic functionality)
- **Optimal**: 100-500 quotes (good variety without being overwhelming)
- **Maximum**: 1000+ quotes (handles unlimited quantities)

### Category Distribution
Aim for roughly even distribution across categories:
- Motivation: 20-25%
- Productivity: 15-20%
- Wisdom: 15-20%
- Success: 10-15%
- Other categories: 5-10% each

## 🚀 Upload Instructions

### GitHub Pages Setup (Do This Once)
1. Create GitHub repository named `my-quotes-service`
2. Make repository **public** (required for GitHub Pages)
3. Upload `quotes.json` file to repository root
4. Go to Settings → Pages → Enable from main branch
5. Your quotes will be available at:
   ```
   https://[YOUR-USERNAME].github.io/my-quotes-service/quotes.json
   ```

### Update Process (For Quote Additions)
1. Edit `quotes.json` in GitHub web interface
2. Add new quotes to the `quotes` array
3. **CRITICAL**: Increment the `version` number
4. Update `lastUpdated` date
5. Commit changes

## ⚠️ Critical Requirements

### JSON Validation
- Use [jsonlint.com](https://jsonlint.com) to validate before uploading
- Check for missing commas, brackets, quotes
- Ensure proper UTF-8 encoding

### Version Control
- **ALWAYS** increment version number when adding/removing quotes
- App only downloads if version is higher than cached version
- Start at version 1, then 2, 3, etc.

### Character Escaping
If quotes contain special characters, escape them:
- Double quotes: `\"` 
- Backslashes: `\\`
- Newlines: `\\n` (but avoid in quote text)

## 📱 App Integration

### How the App Uses Quotes
1. Downloads `quotes.json` on first launch
2. Caches quotes locally for offline use
3. Checks for updates when app opens (only if version increased)
4. Randomly selects quotes for notifications
5. Displays quotes every 10 minutes at exact intervals

### Testing Your Quotes
1. Upload `quotes.json` to GitHub Pages
2. Test URL in browser - should display JSON
3. Update Android app with your GitHub username
4. Use "Refresh Quotes" button in app to download
5. Check quote preview displays properly

## 📋 Quality Checklist

Before finalizing quote collection:

- [ ] All quotes under 280 characters
- [ ] Sequential ID numbers (1, 2, 3...)
- [ ] Proper author attribution
- [ ] Appropriate categories assigned
- [ ] Valid JSON syntax verified
- [ ] Version number set correctly
- [ ] No duplicate quotes
- [ ] Inspirational and appropriate content
- [ ] Tested in JSON validator

## 🎯 Example Processing Workflow

1. **Extract**: Pull 100 quotes from source material
2. **Clean**: Remove formatting, verify attribution
3. **Categorize**: Assign categories from approved list
4. **Format**: Create JSON with sequential IDs
5. **Validate**: Check syntax and requirements
6. **Upload**: Create GitHub repository and enable Pages
7. **Test**: Verify URL works and app can download

---

## 📞 Support Notes

### Common Issues
- **JSON syntax errors**: Use jsonlint.com validator
- **App not updating**: Ensure version number was incremented
- **Quotes too long**: Mobile notifications truncate after ~280 chars
- **GitHub Pages not working**: Repository must be public

### File Location
Upload final `quotes.json` to repository root (not in subfolder).

---

**🎯 Goal: Create a high-quality, inspiring quote collection that delivers meaningful 10-minute reminders to users throughout their day.**