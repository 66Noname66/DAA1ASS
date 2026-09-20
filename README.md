Design and Analysis of Algorithms - Assignment 1Course: Design and Analysis of AlgorithmsInstructor: Taubakabyl NurlybekTask: Divide and Conquer & Asymptotic Notations Project OverviewThis project implements a high-performance Divide-and-Conquer Fast Sorting & Selection Engine in Java without relying on standard library sorting functions. It features stack-safe execution, zero memory churn, and specialized partitioning logic tailored for large-scale data processing.Key Architectural HighlightsMergeSort: Optimized with a single top-level reusable buffer (allocates int[] once to prevent GC overhead) and a cutoff to Insertion Sort for sub-arrays of size $n \le 15$.QuickSort: Stack-overflow safe implementation using Random Pivot selection, 3-Way Partitioning (Dijkstra’s Dutch National Flag algorithm for duplicate keys), and tail-recursion elimination (recursing into the smaller partition first to keep depth at $O(\log n)$).QuickSelect: Efficient $k$-th order statistic selection using 3-way partitioning that recurses only into the required partition branch.Metrics & Benchmarks: Thread-safe state collection (Metrics) tracking total comparisons, timing (System.nanoTime()), and peak recursion stack depth across sizes $n \in \{1000, 10000, 100000, 1000000\}$ across 3 input distribution types (random, sorted, duplicates). Repository Structure├── pom.xml                   # Maven project configuration with JUnit 5 dependencies
├── README.md                 # Project build, execution, and architectural guide
├── REPORT.md                 # Theoretical bounds, Master Theorem, plots analysis & discussion
├── results.csv               # Exported benchmark statistical data
├── generate_plots.py         # Python visualization script for generate plot assets
├── time_vs_n.png             # Plot: Execution Time vs Input Size (n)
├── depth_vs_n.png            # Plot: Max Recursion Stack Depth vs Input Size (n)
├── ratio_vs_n.png            # Plot: Growth Ratio (Theta-bound validation)
└── src
    ├── main
    │   └── java
    │       └── com
    │           └── daa
    │               ├── Benchmark.java    # Main benchmark harness & CSV exporter
    │               ├── MergeSort.java    # Reusable buffer + Cutoff MergeSort
    │               ├── Metrics.java      # Isolated metrics counter (Comparisons, Depth)
    │               ├── QuickSelect.java  # O(n) average selection algorithm
    │               └── QuickSort.java    # Bounded depth + 3-way partition QuickSort
    └── test
        └── java
            └── com
                └── daa
                    └── AlgorithmsTest.java # JUnit 5 correctness & depth verification tests
 PrerequisitesEnsure you have the following installed on your environment:JDK 17 or higherApache Maven 3.6+Python 3.x with pandas, matplotlib, and numpy (optional, for generating plot images) Building & Running the Project1. Compile the ProjectTo compile the source code without running tests:mvn clean compile
2. Run JUnit 5 Unit TestsExecutes correctness tests against Arrays.sort() across 100 random arrays, edge cases (empty, single-element, duplicates), and validates that QuickSort depth remains $\le 2 \log_2(n)$ on sorted input of 100,000 elements:mvn test
3. Run Benchmark HarnessTo run the full benchmark suite across all sizes ($n = 1k, 10k, 100k, 1M$), data types (random, sorted, duplicates), compute medians of 5 runs, and output results to results.csv:Option A: Via Maven Terminalmvn clean package
java -cp target/DAA1ASS-1.0-SNAPSHOT.jar com.daa.Benchmark
Option B: Via IntelliJ IDEAOpen src/main/java/com/daa/Benchmark.java.Right-click on public static void main(String[] args) and select Run 'Benchmark.main()'. Generating Visual PlotsOnce results.csv is created in the project root, you can render the performance graphs (time_vs_n.png, depth_vs_n.png, ratio_vs_n.png) using the provided Python script:Install visualization dependencies:pip install pandas matplotlib numpy
Execute script:python generate_plots.py
 Git WorkflowThis project adheres to a clean feature-branch Git workflow:Branches Used:main (tagged release v1.0)feature/mergesortfeature/quicksortfeature/selectfeature/metricsRelease Tag: v1.0
