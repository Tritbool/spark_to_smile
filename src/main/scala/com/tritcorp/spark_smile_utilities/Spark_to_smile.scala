package com.tritcorp.spark_smile_utilities

import com.typesafe.scalalogging.LazyLogging
import smile.data.`type`.{DataTypes, StructField, StructType}
import smile.data.{DataFrame, Tuple}
import scala.collection.JavaConverters._

object Spark_to_smile extends LazyLogging {

  implicit def sparkDF_to_smileDF(df: org.apache.spark.sql.DataFrame): Option[smile.data.DataFrame] = {
    try {
      val data = df.collect().map(_.toSeq).map(_.toArray)

      val d = data.map(_.map {
        case e: Int => e.toDouble
        case e: Float => e.toDouble
        case e: Long => e.toDouble
        case e: Short => e.toDouble
        case e: Double => e
        case e: String => e.toDouble
        case x => throw new Exception(s"${x.getClass.toString} is not a compatible data type")
      })
      /**
       * UNUSED But could be interesting if data was not force collected as 2-dim Double Array
       */
      /*
    val cols = df.schema.map { field =>
      field.dataType match {
        case org.apache.spark.sql.types.DoubleType => new StructField(field.name, DataTypes.DoubleType)
        case org.apache.spark.sql.types.FloatType => new StructField(field.name, DataTypes.FloatType)
        case org.apache.spark.sql.types.LongType => new StructField(field.name, DataTypes.LongType)
        case org.apache.spark.sql.types.IntegerType => new StructField(field.name, DataTypes.IntegerType)
        case org.apache.spark.sql.types.ShortType => new StructField(field.name, DataTypes.ShortType)
        case _ => new StructField(field.name, DataTypes.ObjectType)
      }
    }.toList
*/
      val columns = df.columns.map { c =>
        new StructField(c, DataTypes.DoubleType)
      }.toList

      val struc = new StructType(columns.asJava)

      val tuples = d.map {
        Tuple.of(_, struc)
      }.toList

      val dtf = DataFrame.of(tuples.asJava)

      Some(dtf)
    }
    catch {
      case e: ClassCastException => {
        logger.error(s"ONLY NUMERIC DATAFRAMES ARE CONVERTIBLE")
        throw e
      }
      case e: Exception => {
        logger.error(s" ERROR WHILE CREATING SMILE DATAFRAME : ${e.getMessage}")
        throw (e)
      }
    }
  }

}
