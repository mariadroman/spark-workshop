package com.lunatech.workshops.spark

import org.scalatest.{Matchers, WordSpec}

class MainSpec extends WordSpec with Matchers {
  "The application" should {
    "work as expected" in {
      val a = 1
      a shouldBe (1)
    }
  }
}
