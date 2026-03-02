Name: Raul Rahimli
Student ID: 19808
Class: Object Oriented Analysis & Design
CRN: 20966
Project name: Multiple Readers, Single Writer


## Project Overview
The project implements Ring Buffer with Multiple Readers and Single Writer. The buffer has capacity N and stores integers. Only one writer is allowed to call method write(). Readers can be several, and each of them has their own reading position. When the buffer is full, writer overwrites the oldest data

Files:
- RingBuffer.java - the main data structure
- Reader.java - manages reader registrations and reader positions
- Writer.java - manages a rule of single writer thread
- Main.java - demo/test runner


## Design Explanation:

### RingBuffer
Responsibility - coordinate everything (main structure)
Has a fixed storage int[] buffer and its state:
- capacity (fixed N)
- next_w (next write slot index)
- total_w (total number of writes)

Provides public API:
- regR (readerId), unregR(readerId)
- write (value)
- read(readerId)
- printState()


### Reader
Responsibility - Reader tracking
Stores each reader's next sequence to read. Used ArrayList

Provides:
- register/unregister (regR, unregR)
- read-position getters/setters (getNS, setNS)
- snapshots for debugging (snapshotReaders)

Reading is not removing data from buffer


### Writer
Responsibility - 'Only one writer thread can write' rule
- Very first thread that called Current() is becoming a registered writer (wId)
- Other thread calling Current() throws an exception


### Main
Responsibility - Demonstration and testing of the assignment requirements




## UML Class Diagram, UML Sequence Diagram for write(), and UML Sequence Diagram for read()
They are presented separately as PNG file in rb\README folder



## How to Run / Test the Project

1. Project files
Make sure these files are in the same directory:
- Main.java
- RingBuffer.java
- Reader.java
- Writer.java

2. Compile
Open a terminal in the project folder and run:

"javac Main.java RingBuffer.java Reader.java Writer.java"