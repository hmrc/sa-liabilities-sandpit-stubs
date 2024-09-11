package uk.gov.hmrc.saliabilitiessandpitstubs.generator

import org.scalatest.funsuite.AnyFunSuiteLike
import org.scalatest.matchers.should.Matchers
import uk.gov.hmrc.saliabilitiessandpitstubs.data.BalanceDetailTestDataFactory
import uk.gov.hmrc.saliabilitiessandpitstubs.time.{DefaultFutureDateGenerator, FutureDateGenerator, StubbedSystemLocalDate, SystemLocalDate}

import scala.util.Random


class BalanceDetailRandomizeSpec extends AnyFunSuiteLike, Matchers:

  given Random = Random(42)
  given FutureDateGenerator = DefaultFutureDateGenerator(using StubbedSystemLocalDate("2024-07-20"))

  object balanceDetailRandomize extends BalanceDetailRandomize

  test("generate should create a BalanceDetail with correct field types"):
      balanceDetailRandomize.generate shouldEqual BalanceDetailTestDataFactory.aValidBalanceDetailGeneratedUsingSeed
