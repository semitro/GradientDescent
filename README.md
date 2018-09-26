# GradientDescent
Parallel implementation of the gradient descent using Apache Spark
# Deploying
git clone https://github.com/semitro/GradientDescent

cd GradientDescent

mvn clean compile assembly:single

# Running
After deploying you'll need some datasets. To get ones just execute

cp ./src/main/resources/*csv ./target
Runnung:

cd ./target

java -jar ./GradientDescent*.jar dataset.csv epsilon speed

for example,

java -jar ./GradientDescent*.jar dataset1.csv 0.1 0.0005

Notice that result depends on chosen epsilon and speed
