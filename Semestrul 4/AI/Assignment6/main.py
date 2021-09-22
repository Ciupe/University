import csv
import math
import operator
from random import sample
import matplotlib.pyplot as plt

EPSILON = 0.001


class Point:
    def __init__(self, label, val_1, val_2):
        self.val1 = float(val_1)
        self.val2 = float(val_2)
        self.label = label
        self.cluster = None

    def __repr__(self):
        return self.label + ',' + str(self.val1) + ',' + str(self.val2)

    def __eq__(self, newPoint):
        return self.val1 == newPoint.val1 and self.val2 == newPoint.val2

    def get_closest_cluster(self, clusters):
        closest_cluster = clusters[0]

        for cluster in clusters:
            if math.dist((cluster.mean_val1, cluster.mean_val2), (self.val1, self.val2)) < \
                    math.dist((closest_cluster.mean_val1, closest_cluster.mean_val2), (self.val1, self.val2)):
                closest_cluster = cluster

        return closest_cluster


class Cluster:
    def __init__(self, label):
        self.label = label
        self.points = []
        self.mean_val1 = 0
        self.mean_val2 = 0

    def add_point(self, point):
        self.points.append(point)

        if point.cluster:
            self.remove_point(point)

        point.cluster = self
        return self.update_means()

    def remove_point(self, point):
        self.points.remove(point)
        self.update_means()

    def update_means(self):
        old_val1 = self.mean_val1
        old_val2 = self.mean_val2

        sum1 = 0
        sum2 = 0
        for p in self.points:
            sum1 += p.val1
            sum2 += p.val2

        self.mean_val1 = sum1 / len(self.points)
        self.mean_val2 = sum2 / len(self.points)

        return not (abs(self.mean_val1 - old_val1) <= EPSILON and abs(self.mean_val2 - old_val2) <= EPSILON)

    def update_label(self):
        freq = {'A': 0, 'B': 0, 'C': 0, 'D': 0}
        for point in self.points:
            freq[point.label] += 1

        max_occurrences = -1
        for label in freq:
            if freq[label] > max_occurrences:
                max_occurrences = freq[label]
                self.label = label

    def get_stats(self, points):
        TP = FP = TN = FN = 0

        for p in self.points:
            if p.label == self.label:
                TP += 1
            else:
                FP += 1

        for p in points:
            if p not in self.points:
                if p.cluster.label != self.label:
                    if p.label != self.label:
                        TN += 1
                    else:
                        FN += 1

        accuracy = (TP + TN) / (TP + TN + FP + FN)
        precision = TP / (TP + FP)
        recall = TP / (TP + FN)
        score = 2 / ((1 / recall) + (1 / precision))

        return accuracy, precision, recall, score


def read_points():
    points = []
    with open('dataset.csv') as csv_file:
        csv_reader = csv.reader(csv_file, delimiter=',')
        for row in csv_reader:
            if row[0] != 'label':
                point = Point(row[0], row[1], row[2])
                points.append(point)

    return points


def write_points(points):
    f = open("output_points.csv", 'w')
    f.write('cluster,label,val1,val2\n')
    for point in points:
        f.write(point.cluster.label + ',' + point.label + ',' + str(point.val1) + ',' + str(point.val2) + '\n')

    f.close()


def write_clusters(clusters):
    f = open("output_clusters.csv", 'w')
    f.write('label,mean_val1,mean_val2,\n')
    for cluster in clusters:
        f.write(cluster.label + ',' + str(cluster.mean_val1) + ',' + str(cluster.mean_val2) + '\n')
    f.close()


def print_stats(clusters, points):
    for cluster in clusters:
        accuracy, precision, recall, score = cluster.get_stats(points)
        print("------------------------------------------------------------\n")
        print("Label " + cluster.label + ":")
        print("\tAccuracy: " + str(accuracy))
        print("\tPrecision: " + str(precision))
        print("\tRecall: " + str(recall))
        print("\tScore: " + str(score) + "\n")


def main():
    clusters = []
    points = read_points()

    clusters.append(Cluster('A'))
    clusters.append(Cluster('B'))
    clusters.append(Cluster('C'))
    clusters.append(Cluster('D'))

    random_points = sample(points, len(clusters))
    for i in range(0, len(clusters)):
        clusters[i].mean_val1 = random_points[i].val1
        clusters[i].mean_val2 = random_points[i].val2

    valid = True
    while valid:
        for p in points:
            closest_cluster = p.get_closest_cluster(clusters)

            if not (closest_cluster.add_point(p)):
                valid = False

    for cluster in clusters:
        cluster.update_label()

    write_points(points)
    write_clusters(clusters)
    print_stats(clusters, points)


main()
