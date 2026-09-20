import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

df = pd.read_csv('results.csv')

plt.figure(figsize=(10, 6))
for algo in df['algorithm'].unique():
    subset = df[df['algorithm'] == algo]
    avg_time = subset.groupby('n')['time_ms'].mean().reset_index()
    plt.plot(avg_time['n'], avg_time['time_ms'], marker='o', label=algo)

plt.xscale('log')
plt.yscale('log')
plt.xlabel('Array Size (n)')
plt.ylabel('Time (ms)')
plt.title('Execution Time vs Array Size (n)')
plt.legend()
plt.grid(True, which="both", ls="--")
plt.savefig('time_vs_n.png', dpi=300, bbox_inches='tight')
plt.close()

plt.figure(figsize=(10, 6))
for algo in df['algorithm'].unique():
    subset = df[df['algorithm'] == algo]
    avg_depth = subset.groupby('n')['max_depth'].mean().reset_index()
    plt.plot(avg_depth['n'], avg_depth['max_depth'], marker='s', label=algo)

plt.xscale('log')
plt.xlabel('Array Size (n)')
plt.ylabel('Max Recursion Depth')
plt.title('Maximum Recursion Depth vs Array Size (n)')
plt.legend()
plt.grid(True, which="both", ls="--")
plt.savefig('depth_vs_n.png', dpi=300, bbox_inches='tight')
plt.close()

plt.figure(figsize=(10, 6))
for algo in df['algorithm'].unique():
    subset = df[df['algorithm'] == algo].copy()
    if algo == 'QuickSelect':
        subset['ratio'] = subset['comparisons'] / subset['n']
    else:
        subset['ratio'] = subset['comparisons'] / (subset['n'] * np.log2(subset['n']))

    avg_ratio = subset.groupby('n')['ratio'].mean().reset_index()
    plt.plot(avg_ratio['n'], avg_ratio['ratio'], marker='^', label=f'{algo} Ratio')

plt.xscale('log')
plt.xlabel('Array Size (n)')
plt.ylabel('Ratio: Comparisons / Growth Function')
plt.title('Ratio Analysis for Theta Verification')
plt.legend()
plt.grid(True, which="both", ls="--")
plt.savefig('ratio_vs_n.png', dpi=300, bbox_inches='tight')
plt.close()

print("Success!")
#python generate_plots.py