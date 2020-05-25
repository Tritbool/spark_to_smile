package com.tritcorp.spark_smile_utilities

import com.tritcorp.mt4s.logger.DebugMode.ERROR
import com.tritcorp.mt4s.scalaTest.FlatSpecTest
import Spark_to_smile._

class Spark_to_smile_test extends FlatSpecTest {

  setLogLevel(ERROR)

  import ss.implicits._

  "Spark DataFrame" must "be correctly converted to smile dataframe" in {

    val df =
      Seq(
        (1.0, 2.0, 3.0),
        (1.1, -2.4, 0.5)
      ).toDF("A", "B", "C")

    println(df.get.response)
  }

  "Spark DataFrame" must " not be converted to smile dataframe with non numeric columns" in {
    assertThrows[ClassCastException] {
      val df =
        Seq(
          (1.0, 2.0, "3.0"),
          (1.1, -2.4, "0.5")
        ).toDF("A", "B", "C")

      println(df.get.response)
    }
  }

}
