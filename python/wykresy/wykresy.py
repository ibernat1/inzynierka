import os
import matplotlib.pyplot as plt
import numpy as np

data_dir = "data"

files = os.listdir(data_dir)

def getAxes(file):
    X=[]
    Y=[]
    Z=[]
    for line in file:
        axes=line.split(',')
        X.append(axes[0])
        Y.append(axes[0])
        Z.append(axes[0])
    X = np.array(X)
    Y = np.array(Y)
    Z = np.array(Z)
    return X,Y,Z

def getDataFromFile(file):
    file_path = os.path.join(data_dir, file)
    print(file_path)
    with open(file_path, 'r') as f:
        X,Y,Z = getAxes(f)
    return X,Y,Z

def plotCharts(X, Y, Z, name):
    plt.figure(figsize=(30, 10))
    
    plt.subplot(3, 1, 1)
    plt.plot(X, ".")
    
    plt.subplot(3, 1, 2)
    plt.plot(Y, ".")
    
    plt.subplot(3, 1, 3)
    plt.plot(Z, ".")
    
    plt.tight_layout()
    plt.title(name)
    plt.savefig(f"{name}_charts.png")
    plt.show()
            
def plotHistograms(X, Y, Z, name):
    plt.figure(figsize=(30, 10))
    
    plt.subplot(1, 3, 1)
    plt.hist(X, bins=100)
    
    plt.subplot(1, 3, 2)
    plt.hist(Y, bins=100)
    
    plt.subplot(1, 3, 3)
    plt.hist(Z, bins=100)
    
    plt.title(name)
    plt.savefig(f"{name}_hists.png")
    plt.show()
    
def getName(file):
    file = file.split('.')
    return file[0]
    
for file in files:
    name = getName(file)
    X,Y,Z = getDataFromFile(file)
    
    plotCharts(X, Y, Z, name)
    plotHistograms(X, Y, Z, name)