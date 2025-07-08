import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
import os

output_dir = '../results/ComparisonResults'
if not os.path.exists(output_dir):
    os.makedirs(output_dir)

hybrid_df = pd.read_csv('../results/HybridMergeSort.csv')
hybrid_df.columns = ['Size', 'SortThreshold', 'MergeThreshold', 'Type', 'Time(s)']
base_df = pd.read_csv('../results/BaseMergeSort.csv')
base_df.columns = ['Size', 'Type', 'Time(s)']

# print("Hybrid DF shape:", hybrid_df.shape)
# print("Hybrid DF head:")
# print(hybrid_df.head().to_string())
# print("Base DF shape:", base_df.shape)
# print("Base DF head:")
# print(base_df.head().to_string())

hybrid_df['Type'] = hybrid_df['Type'].str.strip()
base_df['Type'] = base_df['Type'].str.strip()

hybrid_df['Size'] = hybrid_df['Size'].astype(int)
base_df['Size'] = base_df['Size'].astype(int)

# print("Unique Sizes in Hybrid Data:", sorted(hybrid_df['Size'].unique()))
# print("Unique Sizes in Base Data:", sorted(base_df['Size'].unique()))
# print("Unique Types in Hybrid Data:", hybrid_df['Type'].unique())
# print("Unique Types in Base Data:", base_df['Type'].unique())

hybrid_df = hybrid_df.groupby(['Size', 'SortThreshold', 'MergeThreshold', 'Type']).mean().reset_index()

best_hybrid = hybrid_df.loc[hybrid_df.groupby(['Size', 'Type'])['Time(s)'].idxmin()]
best_hybrid = best_hybrid[['Size', 'Type', 'SortThreshold', 'MergeThreshold', 'Time(s)']]
best_hybrid.rename(columns={'Time(s)': 'Best_Hybrid_Time(s)'}, inplace=True)

best_comparison_df = pd.merge(best_hybrid, base_df, on=['Size', 'Type'])
best_comparison_df['Time_Difference(s)'] = best_comparison_df['Time(s)'] - best_comparison_df['Best_Hybrid_Time(s)']
best_comparison_df['Improvement(%)'] = (best_comparison_df['Time(s)'] - best_comparison_df['Best_Hybrid_Time(s)']) / best_comparison_df['Time(s)'] * 100

best_comparison_path = f'{output_dir}/best_hybrid_vs_base.csv'
best_comparison_df.to_csv(best_comparison_path, index=False)
print(f"Comparison of best hybrid configurations vs. base merge sort saved to '{best_comparison_path}'")
print("\n### Best Hybrid vs. Base Merge Sort Comparison ###")
print(best_comparison_df.to_string(index=False))

overall_best_pair = hybrid_df.groupby(['SortThreshold', 'MergeThreshold'])['Time(s)'].mean().idxmin()
penultimate_sort, penultimate_merge = overall_best_pair
print(f"\nSelected Penultimate Pair: SortThreshold={penultimate_sort}, MergeThreshold={penultimate_merge}")

penultimate_df = hybrid_df[(hybrid_df['SortThreshold'] == penultimate_sort) & 
                           (hybrid_df['MergeThreshold'] == penultimate_merge)]
penultimate_df = penultimate_df[['Size', 'Type', 'Time(s)']]
penultimate_df.rename(columns={'Time(s)': 'Penultimate_Time(s)'}, inplace=True)

penultimate_comparison_df = pd.merge(penultimate_df, base_df, on=['Size', 'Type'])
penultimate_comparison_df['Time_Difference(s)'] = penultimate_comparison_df['Time(s)'] - penultimate_comparison_df['Penultimate_Time(s)']
penultimate_comparison_df['Improvement(%)'] = (penultimate_comparison_df['Time(s)'] - penultimate_comparison_df['Penultimate_Time(s)']) / penultimate_comparison_df['Time(s)'] * 100

penultimate_comparison_path = f'{output_dir}/penultimate_vs_base.csv'
penultimate_comparison_df.to_csv(penultimate_comparison_path, index=False)
print(f"\nComparison of penultimate pair vs. base merge sort saved to '{penultimate_comparison_path}'")
print("\n### Penultimate Pair vs. Base Merge Sort Comparison ###")
print(penultimate_comparison_df.to_string(index=False))

def plot_comparison(df, hybrid_time_col, title, filename):
    plt.figure(figsize=(12, 8))
    for input_type in df['Type'].unique():
        subset = df[df['Type'] == input_type]
        plt.plot(subset['Size'], subset[hybrid_time_col], marker='o', label=f'Hybrid ({input_type})')
        plt.plot(subset['Size'], subset['Time(s)'], marker='x', label=f'Base ({input_type})')
    plt.xlabel('Array Size')
    plt.ylabel('Time (seconds)')
    plt.title(title)
    plt.xscale('log')
    plt.yscale('log')
    plt.legend()
    plt.grid(True, which="both", ls="--")
    plt.savefig(f'{output_dir}/{filename}.pdf')
    plt.close()

plot_comparison(best_comparison_df, 'Best_Hybrid_Time(s)', 
                'Best Hybrid Configurations vs. Base Merge Sort', 
                'best_hybrid_vs_base')

plot_comparison(penultimate_comparison_df, 'Penultimate_Time(s)', 
                f'Penultimate Pair (Sort={penultimate_sort}, Merge={penultimate_merge}) vs. Base Merge Sort', 
                'penultimate_vs_base')

best_improvement_summary = best_comparison_df.groupby('Type')['Improvement(%)'].agg(['mean', 'std', 'min', 'max'])
best_improvement_summary_path = f'{output_dir}/best_improvement_summary.csv'
best_improvement_summary.to_csv(best_improvement_summary_path)
print(f"\nImprovement summary for best hybrid saved to '{best_improvement_summary_path}'")
print("\n### Improvement Summary (Best Hybrid vs. Base) ###")
print(best_improvement_summary.to_string())

print(f"\nAll outputs (CSVs and plots) saved to '{output_dir}/'")