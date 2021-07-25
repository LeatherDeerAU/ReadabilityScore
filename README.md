# ReadabilityScore
This is a readability calculator for Java.

You're able to calculate the readability of a text using several algorithms:
 - Flesch-Kincaid  https://en.wikipedia.org/wiki/Flesch%E2%80%93Kincaid_readability_tests
 - Coleman-Liau
 - SMOG
 - Automated Readability Index
 
 # Example:
 
 ```String textAsString = "This is the front page of the Simple English Wikipedia. Wikipedias are places where people work together to write encyclopedias in different languages. 
 We use Simple English words and grammar here. The Simple English Wikipedia is for everyone! That includes children and adults who are learning English. 
 There are 142,262 articles on the Simple English Wikipedia. All of the pages are free to use. They have all been published under both the Creative Commons License and the GNU 
 Free Documentation License. You can help here! You may change these pages and make new pages. Read the help pages and other good pages to learn how to write pages here. 
 If you need help, you may ask questions at Simple talk. Use Basic English vocabulary and shorter sentences. This allows people to understand normally complex terms or phrases."
 
 TextAnalyzer text = new TextAnalyzer(textAsString);
 text.printStatistic();
 text.printARI();
 text.printFK();
 text.printSMOG();
 text.printCL();
 ```
 
Result:
 Words: 137
 Sentences: 14
 Characters: 687
 Syllables: 210
 Polysyllables: 17

 Automated Readability Index: 7,08 (about 14-year-olds)
 Flesch–Kincaid readability tests: 6,31 (about 13-year-olds)
 Simple Measure of Gobbledygook: 9,42 (about 16-year-olds)
 Coleman–Liau index: 10,66 (about 17-year-olds)

