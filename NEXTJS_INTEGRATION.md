# Integrating Java Email API with Next.js

Since you are using Next.js, use the **Proxy Pattern** to keep your API Key secure.
Do not call the Java API directly from the browser/frontend components.

## Option 1: App Router (`app/api/send/route.ts`)
If you are using the modern App Router (Next.js 13+):

```typescript
import { NextResponse } from 'next/server';

export async function POST(request: Request) {
  const body = await request.json();

  // 1. Forward to Java API
  try {
    const javaResponse = await fetch('http://localhost:8080/api/send-email', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        // 2. Inject the Secret Key here (Server-side only)
        'x-api-key': process.env.JAVA_EMAIL_API_KEY || 'your-key-here', 
      },
      body: JSON.stringify({
        to: 'inventa.us.team@gmail.com', // Admin receives it
        subject: `New Contact: ${body.subject}`,
        body: `From: ${body.email}\n\n${body.message}`,
        html: false,
        replyTo: body.email // Critical: Allows you to hit "Reply" to the user
      }),
    });

    if (!javaResponse.ok) {
        throw new Error('Java API failed');
    }

    return NextResponse.json({ success: true });

  } catch (error) {
    return NextResponse.json({ error: 'Failed to send email' }, { status: 500 });
  }
}
```

## Option 2: Pages Router (`pages/api/send.js`)
If you are using the older `pages` directory:

```javascript
export default async function handler(req, res) {
  if (req.method !== 'POST') {
    return res.status(405).json({ message: 'Method not allowed' });
  }

  const { email, subject, message } = req.body;

  try {
    const javaResponse = await fetch('http://localhost:8080/api/send-email', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'x-api-key': process.env.JAVA_EMAIL_API_KEY, 
      },
      body: JSON.stringify({
        to: 'inventa.us.team@gmail.com',
        subject: `Contact Form: ${subject}`,
        body: `From: ${email}\n\n${message}`,
        html: false,
        replyTo: email
      }),
    });

    if (javaResponse.ok) {
      res.status(200).json({ success: true });
    } else {
      res.status(500).json({ error: 'Failed' });
    }
  } catch (error) {
    res.status(500).json({ error: 'Server error' });
  }
}
```

## Deployment Note
When you deploy `Inventa` and this `EmailAPI` to production:
1. Change `http://localhost:8080` to your real Java API URL (e.g., `https://my-email-api.onrender.com`).
2. Set `JAVA_EMAIL_API_KEY` in your Next.js environment variables (e.g., Vercel Dashboard).
