package com.lunatech.workshops.spark.examples

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object WordCount {

  private val inputFileName = "src/main/resources/lunatech.json"

  def main(args: Array[String]): Unit = {

    // Create spark session
    val spark: SparkSession = SparkSession
      .builder()
      .master("local[*]")
      .appName("Word count")
      .getOrCreate()

    // Read data
    val input: RDD[String] = spark.sparkContext.textFile(inputFileName)

    // Process data
    val wc: RDD[(String, Int)] = input
      .map(line => line.toLowerCase)
      .flatMap(line => line.split("""\W+"""))
      .map(word => (word, 1))
      .reduceByKey(_ + _)
      .cache()

    // Show results
    println(s"There are ${wc.count} unique words")
    wc.take(25).foreach(println)

    // Free memory
    wc.unpersist()

    spark.stop()
  }
}
