package com.lunatech.workshops.spark

import com.lunatech.workshops.spark.model.Test
import com.typesafe.config.ConfigFactory
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

object Main {

  def main(args: Array[String]): Unit = {

    val config = ConfigFactory.load()

    val spark = SparkSession.builder().appName("Test Application").getOrCreate()

    import spark.implicits._

    val data: DataFrame = spark.read.csv(config.getString("input.path"))

    val ds = data.as[Test]

    ds.write.mode(SaveMode.Append).parquet(config.getString("output.path"))

  }
}
