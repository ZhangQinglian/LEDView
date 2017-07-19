# LEDView

[YouTube Address](https://youtu.be/kLp5kWkhPHE)

[Bilibili Address](http://www.bilibili.com/video/av11323144/)

![GIF](http://7xprgn.com1.z0.glb.clouddn.com/android-led-view.gif)

## Usage

```java
public void setLED(String str ,@ColorInt int ledColor, int ledTextSize,int ledPixel)
```
- str: What you want to show in LEDView.
- ledColor: The color of LEDView.
- ledTextSize: LEDView's text size.
- ledPixel: Pixel count of you LEDView in vertical axis.

### Other methods
```java
// change above args 'str'
public void setLEDContent(String content)
```
```java
// change above args 'ledColor'
public void setLEDTextColor(int color)
```
```java
// change above args 'ledPixel'
public void setLEDSize(int size)
```
```java
// change above args 'ledTextSize'
public void setLEDTextSize(int size)
```

```
Copyright 2017 Qinglian.Zhang

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
