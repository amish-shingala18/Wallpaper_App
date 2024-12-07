# 🌟 HD Wallpaper Application 🌟

## 📖 Overview

The **HD Wallpaper Application** is an Android app built using Kotlin, designed to provide users with **high-quality wallpapers**. It integrates the **Pixabay API** to fetch images, supports **searching for wallpapers**, allows **saving images** to the **home and lock screen**, includes **sharing functionality**, and offers an option to **change the app theme** using **SharedPreferences**. Additionally, it uses a **BroadcastReceiver** to monitor the **internet connection** status.

## 🔥 Features

- 🔍 **Wallpaper Search**: Search for wallpapers based on keywords using the Pixabay API.
- 💾 **Save Image**: Save selected images to the device's gallery.
- 🏠 **Set Image to Home/Lock Screen**: Set images directly to the home or lock screen.
- 📲 **Share Image**: Share images directly via supported sharing apps.
- 🎨 **Theme Customization**: Change the app's theme (light/dark mode) using SharedPreferences.
- 🌐 **Internet Connectivity Monitoring**: Uses a BroadcastReceiver to monitor the internet connection status.

## ⚙️ Setup and Configuration

### 1. 🖼️ **API Integration (Pixabay)**

- The app uses the **Pixabay API** to fetch high-definition images. An API key is required to access the API.
- The images are fetched based on a search query provided by the user.
- **Retrofit** is used to handle network calls and parse the JSON data.

### 2. 🔍 **Searching Functionality**

- Users can search for wallpapers by typing keywords in the search bar. 
- The app queries the Pixabay API and displays the matching wallpapers in a **RecyclerView**.

### 3. 💾 **Saving Images to Home/Lock Screen**

- Users can save images to the device's **gallery**.
- Images can be set as the **home** or **lock screen** wallpaper using Android's `WallpaperManager`.

### 4. 📲 **Sharing Images**

- Users can share images using Android's **sharing intents**, enabling them to share wallpapers with apps like **social media** or **messaging apps**.

### 5. 🎨 **Theme Change (Light/Dark Mode)**

- The app allows users to toggle between **light** and **dark modes**. 🌞🌙
- The theme preference is saved in **SharedPreferences**, retaining the user's choice even after the app is closed.

### 6. 🌐 **BroadcastReceiver (Internet Connectivity Monitoring)**

- A **BroadcastReceiver** is used to monitor the device's **internet connectivity** status.
- The app can notify users if there is **no internet connection** while trying to access the API. ❌🌐

## 🔧 Technologies Used

- **Kotlin**: Main programming language used for development.
- **Pixabay API**: For fetching wallpapers.
- **SharedPreferences**: For saving user preferences (theme selection).
- **BroadcastReceiver**: For monitoring internet connection.
- **Retrofit**: For making API calls.
- **WallpaperManager**: For setting home/lock screen wallpapers.
