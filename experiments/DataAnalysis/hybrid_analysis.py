import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
import os

output_dir = '../results/HybridResults'
if not os.path.exists(output_dir):
    os.makedirs(output_dir)

df = pd.read_csv('../results/HybridMergeSort.csv')
df.columns = ['Size', 'SortThreshold', 'MergeThreshold', 'Type', 'Time(s)']
df = df.groupby(['Size', 'SortThreshold', 'MergeThreshold', 'Type']).mean().reset_index()

best_configs = df.loc[df.groupby(['Size', 'Type'])['Time(s)'].idxmin()]
best_configs = best_configs[['Size', 'Type', 'SortThreshold', 'MergeThreshold', 'Time(s)']]
best_configs_path = f'{output_dir}/best_configs.csv'
best_configs.to_csv(best_configs_path, index=False)
print(f"Best configurations saved to '{best_configs_path}'")
print("\nBest Configurations:")
print(best_configs.to_string(index=False))

def plot_time_vs_sortthresh(df, size, input_type):
    subset = df[(df['Size'] == size) & (df['Type'] == input_type)]
    if subset.empty:
        return
    plt.figure(figsize=(10, 6))
    for merge_thresh in sorted(subset['MergeThreshold'].unique()):
        data = subset[subset['MergeThreshold'] == merge_thresh]
        avg_time = data.groupby('SortThreshold')['Time(s)'].mean()
        plt.plot(avg_time.index, avg_time.values, marker='o', label=f'MergeThresh={merge_thresh}')
    plt.xlabel('Sort Threshold')
    plt.ylabel('Average Time (s)')
    plt.title(f'Time vs. Sort Threshold (Size={size}, {input_type})')
    plt.legend()
    plt.savefig(f'{output_dir}/time_vs_sortthresh_size{size}_{input_type}.pdf')
    plt.close()

summary_stats = df.groupby(['Size', 'Type']).agg(
    Avg_Time=('Time(s)', 'mean'),
    Std_Time=('Time(s)', 'std'),
    Min_Time=('Time(s)', 'min'),
    Max_Time=('Time(s)', 'max')
).reset_index()
summary_stats_path = f'{output_dir}/summary_stats.csv'
summary_stats.to_csv(summary_stats_path, index=False)
print(f"\nSummary statistics saved to '{summary_stats_path}'")
print("\nSummary Statistics:")
print(summary_stats.to_string(index=False))

def plot_avg_time_by_type(df):
    avg_time_by_type = df.groupby(['Size', 'Type'])['Time(s)'].mean().unstack()
    plt.figure(figsize=(12, 8))
    for input_type in avg_time_by_type.columns:
        plt.plot(avg_time_by_type.index, avg_time_by_type[input_type], marker='o', label=input_type)
    plt.xlabel('Array Size')
    plt.ylabel('Average Time (s)')
    plt.title('Average Sorting Time by Array Type')
    plt.xscale('log')
    plt.yscale('log')
    plt.legend()
    plt.savefig(f'{output_dir}/avg_time_by_type.pdf')
    plt.close()
    print("Saved average time by type plot")

plot_avg_time_by_type(df)

avg_by_type_thresholds = df.groupby(['Type', 'SortThreshold', 'MergeThreshold'])['Time(s)'].mean().reset_index()
avg_by_type_thresholds_path = f'{output_dir}/avg_by_type_thresholds.csv'
avg_by_type_thresholds.to_csv(avg_by_type_thresholds_path, index=False)
print(f"\nAverage time by type and thresholds saved to '{avg_by_type_thresholds_path}'")
best_by_type = avg_by_type_thresholds.loc[avg_by_type_thresholds.groupby('Type')['Time(s)'].idxmin()]
print("\nBest Thresholds by Type (averaged across sizes):")
print(best_by_type.to_string(index=False))

overall_best = df.groupby(['SortThreshold', 'MergeThreshold'])['Time(s)'].mean().idxmin()
overall_best_sort, overall_best_merge = overall_best
overall_best_time = df.groupby(['SortThreshold', 'MergeThreshold'])['Time(s)'].mean().min()
print(f"\nOverall Best Thresholds (averaged across all sizes and types):")
print(f"SortThreshold: {overall_best_sort}, MergeThreshold: {overall_best_merge}, Avg Time: {overall_best_time:.6f}s")

sizes = sorted(df['Size'].unique())
types = sorted(df['Type'].unique())
for size in sizes:
    for input_type in types:
        plot_time_vs_sortthresh(df, size, input_type)


random_best = best_configs[best_configs['Type'] == 'Random']
random_best_path = f'{output_dir}/best_configs_random.csv'
random_best.to_csv(random_best_path, index=False)
print(f"\nBest configurations for Random arrays saved to '{random_best_path}'")

def plot_avg_time_vs_sortthresh_random(df):
    random_df = df[df['Type'] == 'Random']
    avg_time = random_df.groupby(['SortThreshold', 'MergeThreshold'])['Time(s)'].mean().reset_index()
    plt.figure(figsize=(10, 6))
    for merge_thresh in sorted(avg_time['MergeThreshold'].unique()):
        data = avg_time[avg_time['MergeThreshold'] == merge_thresh]
        plt.plot(data['SortThreshold'], data['Time(s)'], marker='o', label=f'MergeThresh={merge_thresh}')
    plt.xlabel('Sort Threshold')
    plt.ylabel('Average Time (s)')
    plt.title('Average Time vs. Sort Threshold for Random Arrays\n(Averaged Across All Sizes)')
    plt.legend()
    plt.savefig(f'{output_dir}/avg_time_vs_sortthresh_random.pdf')
    plt.close()
    print("Saved average time vs. SortThreshold plot for Random arrays")

def plot_best_thresholds_vs_size_random(random_best):
    plt.figure(figsize=(12, 6))
    plt.scatter(random_best['Size'], random_best['SortThreshold'], color='blue', label='Sort Threshold')
    plt.scatter(random_best['Size'], random_best['MergeThreshold'], color='red', label='Merge Threshold')
    plt.xlabel('Array Size')
    plt.ylabel('Threshold Value')
    plt.title('Best Thresholds vs. Array Size for Random Arrays')
    plt.xscale('log')
    plt.legend()
    plt.savefig(f'{output_dir}/best_thresholds_vs_size_random.pdf')
    plt.close()
    print("Saved best thresholds vs. size plot for Random arrays")

def plot_best_threshold_frequency_random(random_best):
    sort_freq = random_best['SortThreshold'].value_counts().sort_index()
    merge_freq = random_best['MergeThreshold'].value_counts().sort_index()
    
    fig, (ax1, ax2) = plt.subplots(2, 1, figsize=(10, 10))
    
    ax1.bar(sort_freq.index, sort_freq.values)
    ax1.set_xlabel('Sort Threshold')
    ax1.set_ylabel('Frequency')
    ax1.set_title('Frequency of Best Sort Threshold for Random Arrays')
    
    ax2.bar(merge_freq.index, merge_freq.values)
    ax2.set_xlabel('Merge Threshold')
    ax2.set_ylabel('Frequency')
    ax2.set_title('Frequency of Best Merge Threshold for Random Arrays')
    
    plt.tight_layout()
    plt.savefig(f'{output_dir}/best_threshold_frequency_random.pdf')
    plt.close()
    print("Saved best threshold frequency plot for Random arrays")

random_summary = df[df['Type'] == 'Random'].groupby('Size').agg(
    Avg_Time=('Time(s)', 'mean'),
    Std_Time=('Time(s)', 'std'),
    Min_Time=('Time(s)', 'min'),
    Max_Time=('Time(s)', 'max')
).reset_index()
random_summary_path = f'{output_dir}/summary_stats_random.csv'
random_summary.to_csv(random_summary_path, index=False)
print(f"\nSummary statistics for Random arrays saved to '{random_summary_path}'")
print("\nSummary Statistics for Random Arrays:")
print(random_summary.to_string(index=False))

plot_avg_time_vs_sortthresh_random(df)
plot_best_thresholds_vs_size_random(random_best)
plot_best_threshold_frequency_random(random_best)

print(f"\nAll plots saved to '{output_dir}/'")