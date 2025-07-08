# **Algorithms Assignment 1** 

## **Project Structure**
- `src/main/java/za/ac/sun/cs/algos/` → Core algorithm implementations.
- `src/test/java/za/ac/sun/cs/algos/` → Path for testing the algorithm implementations.
-- Note, no testing files were added. This path may not show up on git...
- `report/experiments/` → Java programs for benchmarking sorting algorithms.
- `report/experiments/DataAnalysis` → Data analysis scripts & generated results.
- `README.md` → This file.

## Compiling and Running the Main Algorithms (using maven)

#### **Install maven packages**
```mvn clean install```

#### **Compile all java files and clean**
```mvn clean compile```

#### **Compile all java files**
```mvn compile```


#### **Test**
```mvn test```

## **Compiling and Running Experiments**
In the reports/experiments folder you will find:

Helper.java: Utility class for data generation, progress display, and file operations
HybridTests.java: Main benchmark program for hybrid sorting algorithm analysis
InsertionBenchmark.java: Benchmark program for basic insertion sort
MergeBenchmark.java: Benchmark program for basic merge sort
Utilities.java: Contains the core sorting algorithm implementations

DataAnalysis/
Contains Python scripts for analyzing and visualizing the benchmark results:

- base_case_analysis.py: Analyzes performance of the base sorting algorithms
- base_vs_hybrid.py: Compares performance between base and hybrid sorting implementations
- hybrid_analysis.py: Analyzes the hybrid sorting algorithm performance with different thresholds
- requirements.txt: Python dependencies for the analysis scripts
- venv: Python virtual environment directory

Results/ -> Contains all benchmark results and generated visualizations:

- BaseInsertionSort.csv: Raw performance data for insertion sort
- BaseMergeSort.csv: Raw performance data for merge sort
- HybridMergeSort.csv: Raw performance data for hybrid merge sort

- BaseResults/ -> Contains visualizations for the base sorting algorithms:

- ComparisonResults/ -> Contains comparison data between hybrid and base implementations:

- HybridResults/ -> Contains detailed analysis of hybrid sorting performance:

### **🔹 Prerequisites**
- **Java 17** → Ensure you have Java 17 installed.
- **Terminal** → Run all commands from the project root (where `README.md` is located).

### **🔹 Step 1: Compiling the Experiments**
Navigate to `reports/experiments/` and run:
```javac *.java```

### **🔹 Step 2: Running the Experiments**
Each experiment must be run in order. Also, the Utilities.java file was copied and
altered to allow for the MERGESORT_THRESHOLD and SORTTHRESHOLD values to be changed.

**Warning:**  
- `InsertionBenchmark.java` **takes several hours** to run.  
- `HybridTests.java` **can take 30mins-2 hours**.  
- Large amounts of data will be generated. **Run at your own risk!**  

#### **Run the Insertion Sort Benchmark**
```java InsertionBenchmark```

#### **Run the Merge Sort Benchmark**
```java MergeBenchmark```

#### **Run the Hybrid Sorting Threshold Tests**
```java HybridTests```

🔹 **All results will be saved in** `reports/experiments/results/`.  

## **🔹 Data Analysis**
Navigate to `report/experiments/DataAnalysis/` and follow these steps:  

### **Step 1: Set Up a Virtual Environment**
```
pip install virtualenv
virtualenv venv
source venv/bin/activate
```

### **Step 2: Install Dependencies**
```pip install -r requirements.txt```

### **Step 3: Run Analysis Scripts**
- **Base Case Analysis:**  
  ```python3 base_case_analysis.py```
- **Hybrid Sort Analysis:**  
  ```python3 hybrid_analysis.py```
- **Hybrid vs. Base Sorting Comparison:**  
  ```python3 base_vs_hybrid.py```

 **Results will appear in** `report/experiments/results/`.

Please note that these scripts must run in order. There is no error detection for missing files and such.
If you do wish to run these, note that the InsertionBenchmark.java takes a few hours to run. Furthermore the HybridTests.java 
also takes an hour or two.
For this reason I will include all data generated in the repo.
Due to the nature of my experimental environment, a lot of data gets generated.
Run at your own risk!
