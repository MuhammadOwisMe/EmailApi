# Deployment Guide (Render.com)

Since you want to use this for **Inventa**, deploying to **Render** is the easiest free option for Docker/Java apps.

## Step 1: Push to GitHub
1. Create a Repository on GitHub.
2. Initialize Git in this folder and push your code:
   ```bash
   git init
   git add .
   git commit -m "Initial commit of Email API"
   git branch -M main
   git remote add origin https://github.com/YOUR_USERNAME/email-api.git
   git push -u origin main
   ```

## Step 2: Create Web Service on Render
1. Go to [dashboard.render.com](https://dashboard.render.com).
2. Click **New +** -> **Web Service**.
3. Connect your GitHub repository.
4. Render will detect the `Dockerfile`. Use the default settings:
   - **Runtime**: Docker
   - **Plan**: Free

## Step 3: Add Environment Variables 🚨
**Crucial Step**: Render does not know your passwords. You must add them manually in the Render Dashboard.
1. Scroll down to **Environment Variables**.
2. Add the following (Key = Value):
   - `EMAIL_USERNAME` = `inventa.us.team@gmail.com`
   - `EMAIL_PASSWORD` = `cfxb tcfg dlsq tsvm`
   - `API_KEY` = `clk_82nks0-92jdmn-29zkqm-x9d2`

## Step 4: Deploy
1. Click **Create Web Service**.
2. Render will build your app (this takes ~2-3 minutes).
3. Once done, it will give you a public URL (e.g., `https://email-api-xyz.onrender.com`).

## Step 5: Update Your Website (Inventa)
Update your Next.js code to point to the new URL:

```javascript
// In your Next.js API Route
const javaResponse = await fetch('https://email-api-xyz.onrender.com/api/send-email', ...
```
