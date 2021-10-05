# HuntChain Project

HuntChain is a blockchain-based malware detection project, that has been been developed for the Degree Project (2dv50e) course at LNU. 

It is an on-demand scanning tool that uses signature-based detection to scan files for potential known malware, and blockchain as a database containing malware information.

It has at the moment 2 signature-based detection techniques: 
* Integrity Check 
* Top-and-Tail

In this repository, you can also find the fake malware that can be used to test the code, and reproduce the Evaluation Chapter in the thesis. They are in the folder namd "malware". 

The file hash.txt contains the "malwareCode.jar" and "notMal.jar" hashes, that can be added into the blockchain. 


## Configuration 
To run the code, you need to have installed Java, and an IDE that supports JAVA (e.g. IntelliJ IDEA). 


## Contribution 

If you want to do a major change, please open an issue so we can discuss it. But any future work to contribute on improving the code is welcomed. Here is the future work section that I have put in my thesis: 

