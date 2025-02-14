# Simple Calculator App

# Description
The calculator app helps users to perform the simple calculations. It performs arthimetic operations like Addition, Subtraction, Multiplication, Division along with percentage, negation and decimal

## Functionality 

The following **required** functionality is completed:


The Users sees the calculator app with the Text View with the default value as 0 and also buttons to perform operations.
1. Users can click the number button which is displayed in the TextView instead of 0
2. When we click on the "+" button, the addition operation is invoked and when we click on another number and press "=" it will calculate the addition operation like 2+3=5
3. When we click on the "-" button, the subtraction operation is invoked and when we click on another number and press "=" it will calculate the subraction operation like 5-2=3
4. When we click on the "x" button, the multiplication operation is invoked and when we click on another number and press "=" it will calculate the multiplication operation like 2*5=10
5. When we click on the "/" button, the division operation is invoked and when we click on another number and press "=" it will calculate the division operation like 2*5=10
6. When we click on the "%" button, the percentage operation is invoked which divides the number by a hundred and displays it on the textview like 2% = 0.02
7. When we click on the "C" button, the reset operation is invoked which displays the textView to be 0
8. When we click on the "+/-" button, it makes the number positive or negative
9. When we click on the "." button, it will keep a . for the number like 2. which is useful when we are handling with the decimal numbers
10. When we click on the "=" button, it takes the two numbers with operator and calculates the value


The following **extensions** are implemented:

I have use enableEdgeToEdge() function to use full-screen immersive layouts.

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='Videowalkthrough.gif' title='Video Walkthrough' width='50%' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Describe any challenges encountered while building the app.
The first challenge i faced was to give the text view double the space of rows and 0 double the width, which was overcomed by ysing android:layout_weight
The second challenge was to convert the rounded buttons to squared ones using app:cornerRadius="0dp"
The third challenge is calculation after we have done a calculation which made us think about the functionality thoroughly 

## License

    Copyright [2025] [Rajesh Kumar Reddy Avula]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
