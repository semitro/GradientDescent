# GradientDescent
Parallel implementation of the gradient descent using Apache Spark
# Deploying
To build jar-file execute
```bash
git clone https://github.com/semitro/GradientDescent

cd GradientDescent

mvn clean compile assembly:single
```
# Testing
To run prepared unit-tests perform
```bash
mvn test
```

# Running
After deploying you'll need some datasets. To get ones just execute
```bash
cp ./src/main/resources/*csv ./target
```
Runnung:
```bash

cd ./target

java -jar ./GradientDescent*.jar dataset.csv epsilon speed
```
for example,
```bash
java -jar ./GradientDescent*.jar dataset1.csv 0.1 0.0005
```
---

Notice that result depends on chosen epsilon and speed
