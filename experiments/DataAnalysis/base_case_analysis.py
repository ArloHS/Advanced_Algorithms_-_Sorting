import pandas as pd
import matplotlib.pyplot as plt
import os
import datetime

# Create output directory
output_dir = '../results/BaseResults'
if not os.path.exists(output_dir):
    os.makedirs(output_dir)

def analyze_and_plot(csv_file, title, plot_name):

    if not os.path.exists(csv_file):
        print(f"Error: File not found at {csv_file}")
        return

    df = pd.read_csv(csv_file)
    # Strip --
    df.columns = df.columns.str.strip()
    df['Type'] = df['Type'].str.strip()

    print(f"\nBasic Statistics for {title}:")
    print(df.groupby('Type')['Time(s)'].describe())

    # Polts
    plt.figure(figsize=(12, 7))
    markers = {'Sorted': 'o', 'Reverse': 's', 'Random': 'D'} 
    colors = {'Sorted': 'blue', 'Reverse': 'red', 'Random': 'green'}
    for array_type in df['Type'].unique():
        subset = df[df['Type'] == array_type]
        plt.plot(subset['Size'], subset['Time(s)'], 
                 label=array_type, 
                 marker=markers.get(array_type, 'x'),
                 linestyle='-', 
                 markersize=6, 
                 color=colors.get(array_type, 'black')) 

    plt.xscale('log')
    plt.yscale('log')
    plt.xlabel('Array Size (log scale)', fontsize=14)
    plt.ylabel('Time (s, log scale)', fontsize=14)
    plt.title(f'Sorting Time vs Array Size ({title})', fontsize=16)
    plt.legend(title="Array Type", fontsize=12)
    plt.xticks(df['Size'].unique(), labels=[f"{s:,}" for s in df['Size'].unique()], fontsize=12)
    plt.yticks(fontsize=12) 
    plt.grid(True, which="both", linestyle="--", alpha=0.6)

    results_dir = "../results/BaseResults"
    os.makedirs(results_dir, exist_ok=True)
    # Save as PDF!!
    pdf_path = os.path.join(results_dir, f"{plot_name}.pdf")
    plt.savefig(pdf_path, bbox_inches='tight')
    print(f"Plot saved as: {pdf_path}")

def compare_sorting_algorithms(insertion_csv, merge_csv, plot_name):

    if not os.path.exists(insertion_csv) or not os.path.exists(merge_csv):
        print(f"Error: One or both files not found.")
        return
        
    df_insertion = pd.read_csv(insertion_csv)
    df_merge = pd.read_csv(merge_csv)
    df_insertion.columns = df_insertion.columns.str.strip()
    df_insertion['Type'] = df_insertion['Type'].str.strip()
    df_merge.columns = df_merge.columns.str.strip()
    df_merge['Type'] = df_merge['Type'].str.strip()
    df_insertion['Algorithm'] = 'Insertion Sort'
    df_merge['Algorithm'] = 'Merge Sort'
    combined_df = pd.concat([df_insertion, df_merge])
    
    ratio_df = pd.DataFrame()
    for array_type in df_insertion['Type'].unique():
        insertion_times = df_insertion[df_insertion['Type'] == array_type]
        merge_times = df_merge[df_merge['Type'] == array_type]
        
        common_sizes = sorted(set(insertion_times['Size']) & set(merge_times['Size']))
        
        for size in common_sizes:
            i_time = insertion_times[insertion_times['Size'] == size]['Time(s)'].values[0]
            m_time = merge_times[merge_times['Size'] == size]['Time(s)'].values[0]
            ratio = i_time / m_time
            
            ratio_df = pd.concat([ratio_df, pd.DataFrame({
                'Size': [size],
                'Type': [array_type], 
                'Insertion/Merge Ratio': [ratio]
            })])
    
    print("\nComparison Statistics:")
    for array_type in combined_df['Type'].unique():
        print(f"\n{array_type} Arrays:")
        for alg in ['Insertion Sort', 'Merge Sort']:
            subset = combined_df[(combined_df['Type'] == array_type) & 
                              (combined_df['Algorithm'] == alg)]
            print(f"{alg} Average Time: {subset['Time(s)'].mean():.6f} seconds")
        
        type_ratio = ratio_df[ratio_df['Type'] == array_type]
        print(f"Average Insertion/Merge Ratio: {type_ratio['Insertion/Merge Ratio'].mean():.2f}x")
    
    array_types = combined_df['Type'].unique()
    num_types = len(array_types)
    
    fig, axes = plt.subplots(num_types, 1, figsize=(12, 5*num_types), sharex=False)
    
    markers = {'Insertion Sort': 'o', 'Merge Sort': 's'}
    linestyles = {'Insertion Sort': '--', 'Merge Sort': '-'}
    colors = {'Sorted': 'blue', 'Reverse': 'red', 'Random': 'green'}
    
    for i, array_type in enumerate(array_types):
        ax = axes[i]
        for alg in ['Insertion Sort', 'Merge Sort']:
            subset = combined_df[(combined_df['Type'] == array_type) & 
                              (combined_df['Algorithm'] == alg)]
            ax.plot(subset['Size'], subset['Time(s)'], 
                   label=alg, 
                   marker=markers.get(alg, 'x'),
                   linestyle=linestyles.get(alg, '-'),
                   color=colors.get(array_type, 'black'))
            
        ax.set_xscale('log')
        ax.set_yscale('log')

        ax.set_ylabel('Time (s, log scale)', fontsize=12)
        ax.set_title(f'{array_type} Arrays', fontsize=14)
        ax.legend(fontsize=10)
        ax.grid(True, which="both", linestyle="--", alpha=0.6)
        
        sizes_in_plot = sorted(combined_df[(combined_df['Type'] == array_type)]['Size'].unique())
        ax.set_xticks(sizes_in_plot)
        ax.set_xticklabels([f"{s:,}" for s in sizes_in_plot], rotation=45, fontsize=10)
        ax.set_xlabel('Array Size (log scale)', fontsize=12)
    
    fig.suptitle('Insertion Sort vs Merge Sort Performance Comparison', fontsize=16)
    plt.tight_layout(rect=[0, 0, 1, 0.97])
    
    results_dir = "../results/BaseResults"
    os.makedirs(results_dir, exist_ok=True)
    
    # Save PDF!!
    pdf_path = os.path.join(results_dir, f"{plot_name}.pdf")
    plt.savefig(pdf_path, bbox_inches='tight')
    print(f"Comparison plot saved as: {pdf_path}")
    
    plt.figure(figsize=(12, 7))
    for array_type in ratio_df['Type'].unique():
        subset = ratio_df[ratio_df['Type'] == array_type]
        plt.plot(subset['Size'], subset['Insertion/Merge Ratio'], 
                 label=array_type, 
                 marker=markers.get('Insertion Sort', 'o'),
                 color=colors.get(array_type, 'black'))
    
    plt.xscale('log')
    plt.xlabel('Array Size (log scale)', fontsize=14)
    plt.ylabel('Insertion Sort / Merge Sort Time Ratio', fontsize=14)
    plt.title('Performance Ratio: Insertion Sort vs Merge Sort', fontsize=16)
    plt.legend(title="Array Type", fontsize=12)
    plt.grid(True, which="both", linestyle="--", alpha=0.6)
    
    common_sizes = sorted(set(ratio_df['Size'].unique()))
    plt.xticks(common_sizes, labels=[f"{s:,}" for s in common_sizes], fontsize=12, rotation=45)
    
    # PDF!!!
    ratio_pdf_path = os.path.join(results_dir, f"{plot_name}_ratio.pdf")
    plt.savefig(ratio_pdf_path, bbox_inches='tight')
    print(f"Ratio plot saved as: {ratio_pdf_path}")

# Paths
base_merge_csv = '../results/BaseMergeSort.csv'
base_insertion_csv = '../results/BaseInsertionSort.csv'

analyze_and_plot(base_merge_csv, 'Merge Sort', 'merge_sort_plot')
analyze_and_plot(base_insertion_csv, 'Insertion Sort', 'insertion_sort_plot')
compare_sorting_algorithms(base_insertion_csv, base_merge_csv, 'sorting_comparison')