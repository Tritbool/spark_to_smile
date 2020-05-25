package com.tritcorp.spark_smile_utilities

import com.typesafe.scalalogging.LazyLogging
import smile.data.{Attribute, AttributeDataset, DataFrame, NumericAttribute}


object Spark_to_smile extends LazyLogging {

  implicit def sparkDF_to_smileDF(df: org.apache.spark.sql.DataFrame): Option[smile.data.DataFrame] = {
    try {
      val attr = df.columns.map(new NumericAttribute(_).asInstanceOf[Attribute])

      val attrx = attr.tail
      val resp = attr.head

      val array = df.collect.map(_.toSeq.toArray.map(_.asInstanceOf[Double])).map(x => (x.head, x.tail))

      val y = array.map(_._1)
      val x = array.map(_._2)

      val ads = new AttributeDataset("tamair", attrx, x, resp, y)

      Some(DataFrame(ads))
    }
    catch {
      case e: ClassCastException => {
        logger.error(s"ONLY NUMERIC DATAFRAMES ARE CONVERTIBLE")
        throw e
      }
      case e: Exception => {
        logger.error(s" ERROR WHILE CREATING SMILE DATAFRAME : ${e.getMessage}")
        throw(e)
      }
    }
  }

}
