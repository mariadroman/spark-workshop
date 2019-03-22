package com.lunatech.workshops.spark.examples

import java.io.File

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object WordCountForeachPartition {

  private val inputFileName = "src/main/resources/lunatech.json"
  private val outputFileName = "/tmp/wcfp"

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .master("local[*]")
      .appName("Word Count - foreachPartition")
      .getOrCreate()

    // Read data
    val input: RDD[String] = spark.sparkContext.textFile(inputFileName)

    // Process data
    val wc = input
      .flatMap(_.split("""\W+"""))
      .map(word => (word, 1))
      .reduceByKey(_ + _)
      .cache()

    val outpath = new File(outputFileName)

    // Cleanup output path
    outpath.delete()
    outpath.mkdirs()

    wc.foreachPartition { iterator =>
      // Create a temp file in the output directory.
      val file = java.io.File
        .createTempFile("WordCountForeachPartition", ".txt", outpath)
      val out = new java.io.PrintWriter(file)

      println(s"Writing output to: $file")

      iterator foreach {
        case (word, count) =>
          out.println("%20s\t%d".format(word, count))
      }
      out.close()
    }

    // Free memory
    wc.unpersist()

    spark.stop()
  }
}
