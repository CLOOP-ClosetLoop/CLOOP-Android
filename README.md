# [2025 APAC Google Solution Challenge] CLOOP-Android

![Frame](https://github.com/user-attachments/assets/c031c510-ccb5-47b4-8b0a-4676cfdc6f9f)
</br></br>

> Wear it, log it, and cycle it  🌱
</br>


CLOOP is a sustainable wardrobe management app that helps you record, organize, and recycle your clothes efficiently.
</br></br>


## Features
<table>
  <tr>
    <td><img width="200" src="https://github.com/user-attachments/assets/24c22fcb-6d70-4fec-9fc8-d0a82dfba932"></td>
    <td><img width="200" src="https://github.com/user-attachments/assets/bdd8bfd9-a0f3-476b-8722-a145def355f4"></td>
    <td><img width="200" src="https://github.com/user-attachments/assets/1b7e8ef4-8b89-4413-8f42-240a34ebb253"></td>
    <td><img width="200" src="https://github.com/user-attachments/assets/61dd86c1-889b-402d-be83-7dff099334f7"></td>
    <td><img width="200" src="https://github.com/user-attachments/assets/cf4cb1ea-9acf-480e-946d-13f41633376e"></td>
  </tr>
  <tr>
    <td align="center"><b>Splash</b></td>
    <td align="center"><b>Login</b></td>
    <td align="center"><b>Home</b></td>
    <td align="center"><b>Closet</b></td>
    <td align="center"><b>Clothing Wear Stats</b></td>
  </tr>
</table>

</br>

- `Onboarding` : Easy login via Google account integration </br>
- `Home` : Home screen that lets you track your daily outfits via a calendar view </br>
- `Closet` : feature to organize and view registered clothes by category at a glance </br>
- `Clothing Wear Stats` : View comprehensive clothing statistics based on last worn date and total number of wears </br>

</br></br>


<table>
  <tr>
    <td><img width="200" src="https://github.com/user-attachments/assets/19f7ac84-e945-452e-9216-a31f666c49fb"></td>
    <td><img width="200" src="https://github.com/user-attachments/assets/e51869d4-a9c3-413f-89aa-9508885228e0"></td>
    <td><img width="200" src="https://github.com/user-attachments/assets/8c6a791f-64ea-4fc7-aaf1-df32df78625d"></td>
    <td><img width="200" src="https://github.com/user-attachments/assets/a2b7e1f9-c103-4aea-86f7-2cd15cc4476d"></td>
    <td><img width="200" src="https://github.com/user-attachments/assets/4a141998-9570-4e1d-a5e6-a1b0c929e3e9"></td>
  </tr>
  <tr>
    <td align="center"><b>Add Cloth Dialog</b></td>
    <td align="center"><b>Add Cloth - Manual</b></td>
    <td align="center"><b>Manual 2</b></td>
    <td align="center"><b>Add Cloth - AI</b></td>
    <td align="center"><b>AI 2</b></td>
  </tr>
</table>

</br>

- `Manual Registration` : Allows users to upload a photo and manually enter details such as category, name, brand, color, purchase date, and season </br>
- `AI Registration` : Automatically classifies category, name, color, and season from a single clothing photo using AI. Users simply review the AI-generated results and make minor edits if necessary  </br>

</br></br>

<table>
  <tr>
    <td><img width="200" src="https://github.com/user-attachments/assets/8b428cbd-85bb-42e4-8c14-8176491b9016"></td>
    <td><img width="200" src="https://github.com/user-attachments/assets/acae644f-d4e9-4672-b6f0-da4c9b8dd2e7"></td>
    <td><img width="200" src="https://github.com/user-attachments/assets/143e094b-6b99-4e2b-a83c-f9b0e5f2d1c8"></td>
    <td><img width="200" src="https://github.com/user-attachments/assets/943eaf72-c2ac-4d10-870f-6bcce269768d"></td>
  </tr>
  <tr>
    <td align="center"><b>Register Outfit</b></td>
    <td align="center"><b>Select outfit items</b></td>
    <td align="center"><b>Outfit</b></td>
    <td align="center"><b>Registered Outfit</b></td>
  </tr>
</table>

</br>

- `Photo Upload` : Capture and upload a photo to record today’s outfit </br>
- `Item Selection` : Select the clothes worn today from your registered wardrobe </br>
- `Registration Complete` : View the outfit and selected clothing items at a glance </br>

</br></br>

<table>
  <tr>
    <td><img width="200" src="https://github.com/user-attachments/assets/9c23137e-5305-4f7e-9fd8-67ce9c44bd07"></td>
    <td><img width="200" src="https://github.com/user-attachments/assets/8a792c20-bf8d-4c92-8c4b-a8de25e1f47f"></td>
    <td><img width="200" src="https://github.com/user-attachments/assets/fdc47dc1-ff2e-461f-ba51-d3a774e118ec"></td>
    <td><img width="200" src="https://github.com/user-attachments/assets/512d7822-c63e-45ab-94bc-da47c1c42c8d"></td>
  </tr>
  <tr>
    <td align="center"><b>Donate</b></td>
    <td align="center"><b>Donation Dialog</b></td>
    <td align="center"><b>Donation Completed</b></td>
    <td align="center"><b>Logout</b></td>
  </tr>
</table>

 </br>
 
- `Donation` : Automatically highlights clothing items that have not been worn for over six months </br> 
- `Logout` : Log out via the My Page section </br>

</br></br>


## Tech

### Project Architecture

<img width="741" alt="스크린샷 2025-05-16 오전 1 20 07" src="https://github.com/user-attachments/assets/1fc5382a-136d-4222-ac60-cfb98b10eb8f" />

</br>


### Android Stack
<table class="tg">
<tbody>
  <tr>
    <td><b>Architecture</b></td>
    <td>MVVM</td>
  </tr>
<tr>
    <td><b>Jetpack Components</b></td>
<td>AppCompat, LifeCycles, ViewModel, LiveData, viewPager2, ... </td>
</tr>
 <tr>
    <td><b>library</b></td>
<td>Standard Library, Material Design, Glide</td>
</tr>
<tr>
    <td><b>Network</b></td>
<td>Retrofit2, OkHttp, GSON, Coroutine, TokenManager</td>
</tr>
</tbody>
</table>

</br>

### Environment
- Android studio Koala 2024.1.2
- compileSdk 35 or higher
- minSdk 26 or higher

</br>
