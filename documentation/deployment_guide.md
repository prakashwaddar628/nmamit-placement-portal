# Backend Deployment Guide

Since our backend is built with Spring Boot and **MySQL**, there is an important detail to know about Render: **Render only provides PostgreSQL databases natively.**

Because of this, I highly recommend using **Railway.app** instead. Railway is just as easy as Render, but it supports MySQL databases out of the box with one click.

Here is the step-by-step guide for the easiest deployment path (Railway), followed by Render if you still prefer it.

---

## 🥇 Option 1: Deploy to Railway (Highly Recommended)

Railway makes it incredibly simple to deploy both our MySQL database and the Spring Boot Docker container.

### Step 1: Push your code to GitHub
If you haven't already, push this entire `nmamit-placement-portal` folder to a public or private GitHub repository.

### Step 2: Create a MySQL Database on Railway
1. Go to [Railway.app](https://railway.app/) and sign in with GitHub.
2. Click **New Project** -> **Provision PostgreSQL** (Wait, no! Choose **Provision MySQL**).
3. Railway will instantly create a MySQL database for you. 
4. Click on your new MySQL database, go to the **Connect** tab, and copy the **MySQL Connection URL** (it looks like `mysql://root:password@host:port/railway`).

### Step 3: Deploy the Spring Boot App
1. In the same Railway project, click **New** -> **GitHub Repo**.
2. Select your `nmamit-placement-portal` repository.
3. Railway will ask you for the "Root Directory". Set it to `/backend/placement-backend` because our backend isn't at the very root of the repo.
4. Railway will automatically detect the `Dockerfile` and start building the app!

### Step 4: Add Environment Variables
While the app is building, click on the Spring Boot service, go to the **Variables** tab, and add these:

* `DB_USERNAME`: (The username from your Railway MySQL)
* `DB_PASSWORD`: (The password from your Railway MySQL)
* `SPRING_DATASOURCE_URL`: `jdbc:mysql://<host>:<port>/<database_name>?useSSL=false` (replace with details from Railway)
* `JWT_SECRET`: (Create a random long string for security)
* `SERVER_PORT`: `8080`

Once added, Railway will restart the app, and you will get a public URL!

---

## 🥈 Option 2: Deploy to Render (Requires External Database)

If you absolutely want to use Render, we need to get a MySQL database from a free third-party provider first, since Render doesn't offer them.

### Step 1: Create a free MySQL Database
1. Go to [Aiven.io](https://aiven.io/) or [TiDB](https://tidbcloud.com/) and create a free MySQL database.
2. Copy your database connection details (Host, Port, User, Password).

### Step 2: Deploy to Render
1. Push your code to GitHub.
2. Go to [Render.com](https://render.com/) and sign in.
3. Click **New** -> **Web Service**.
4. Select your GitHub repository.
5. In the configuration:
   * **Name**: `placement-backend`
   * **Root Directory**: `backend/placement-backend`
   * **Environment**: `Docker`
   * **Instance Type**: Free
6. Scroll down to **Environment Variables** and add:
   * `DB_USERNAME`: (Your external DB user)
   * `DB_PASSWORD`: (Your external DB password)
   * `SPRING_DATASOURCE_URL`: `jdbc:mysql://<external-host>:<port>/<db_name>?useSSL=false`
   * `JWT_SECRET`: (A random string)

7. Click **Create Web Service**. Render will build the Docker container and give you a public `https://...` URL!

---

## Final Step: Connect the Flutter App

Once your backend is live on either platform and you have your public URL (e.g., `https://placement-backend.up.railway.app`), simply update your Flutter app:

Run your Flutter app using this command:
```bash
flutter run --dart-define=API_BASE_URL=https://placement-backend.up.railway.app/api
```

And just like that, your app is fully live and can be used on any phone!
